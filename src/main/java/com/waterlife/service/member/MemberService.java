package com.waterlife.service.member;

import com.waterlife.entity.Member;
import com.waterlife.exception.member.MemberErrorResult;
import com.waterlife.exception.member.MemberException;
import com.waterlife.repository.MemberRepository;
import com.waterlife.service.utils.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailUtil mailUtil;

    /**
     * 회원가입 메소드
     * @param form
     * @return DB member_id
     */
    @Transactional
    public Long register(MemberRegisterForm form){
        validateForm(form);

        String encodedPassword = encodePassword(form.getPassword());
        form.encodePassword(encodedPassword);

        Member member = Member.createMember(form);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    /**
     * 로그인 메소드
     * @param form
     * @return Member
     * 아이디를 찾을 수 없을 시
     * @throw MemberException(LOGIN_INFO_NOT_MATCH)
     */
    public Member login(LoginForm form){
        String loginId = form.getLoginId();

        Member findMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(()->new MemberException(MemberErrorResult.LOGIN_INFO_NOT_MATCH));

        boolean result = passwordEncoder.matches(form.getPassword(), findMember.getPassword());

        if(result == false){
            throw new MemberException(MemberErrorResult.LOGIN_INFO_NOT_MATCH);
        }

        return findMember;
    }
    
    /* --- 비밀번호 암호화 메소드 --- */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    /* --- form dto 검증 메소드 --- */
    public void validateForm(MemberRegisterForm form) {
        String loginId = form.getLoginId();
        String email = form.getEmail();

        validateLoginId(loginId);
        validateEmail(email);

        String password = form.getPassword();
        String passwordConfirm = form.getPasswordConfirm();

        validatePasswords(password, passwordConfirm, MemberErrorResult.LOGIN_INFO_NOT_MATCH);
    }

    /* --- if문 리펙토링 메소드(비밀번호 2개 비교) --- */
    private static boolean isPasswordNotMatch(String password, String passwordConfirm) {
        return !password.equals(passwordConfirm);
    }

    /* --- 비밀번호 2개 비교 메소드 --- */
    private void validatePasswords(String password, String passwordConfirm, MemberErrorResult memberErrorResult){
        if(isPasswordNotMatch(password, passwordConfirm)){
            throw new MemberException(memberErrorResult);
        }
    }

    /**
     * 아이디 중복 검증 메소드
     * @param loginId
     * 검증 실패 시
     * @throw MemberException(DUPLICATED_LOGIN_ID)
     */
    public void validateLoginId(String loginId) {
        Optional<Member> findMemberByLoginId = memberRepository.findByLoginId(loginId);

        if(findMemberByLoginId.isPresent()){
            throw new MemberException(MemberErrorResult.DUPLICATED_LOGIN_ID);
        }
    }

    /**
     * 이메일 주소 중복 검증 메소드
     * @param email
     * 검증 실패 시
     * @throw MemberException(DUPLICATED_EMAIL)
     */
    public void validateEmail(String email) {
        Optional<Member> findMemberByEmail = memberRepository.findByEmail(email);

        if(findMemberByEmail.isPresent()){
            throw new MemberException(MemberErrorResult.DUPLICATED_EMAIL);
        }
    }

    /**
     * 회원정보 찾기 메소드
     * @param memberId
     * @return MemberInformationResponse
     * 찾기 실패 시
     * @throw MemberException(MEMBER_NOT_FOUND_BY_FIND_MEMBER_ID)
     */
    public MemberInformationResponse findMemberInformation(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorResult.MEMBER_NOT_FOUND_BY_FIND_MEMBER_ID));
        return MemberInformationResponse.createResponse(member);
    }

    /**
     * 이메일 찾기 메소드
     * @param email
     * @return maskedEmail
     * 찾기 실패 시
     * @throw MemberException(MEMBER_NOT_FOUND_BY_FIND_LOGIN_ID)
     */
    public String findLoginId(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorResult.MEMBER_NOT_FOUND_BY_FIND_LOGIN_ID));

        String loginId = findMember.getLoginId();
        return loginId.replaceAll("(?<=.{4}).", "*");
    }

    /**
     * 비밀번호 찾기(재설정) 메소드
     * @param loginId
     * @param email
     * 1. 유효성 검사 이후
     * 2. 임시 비밀번호로 변경 이후
     * 3. 이메일 전송(임시 비밀번호)
     * 찾기 실패 시
     * @throw MemberException(MEMBER_NOT_FOUND_BY_FIND_PASSWORD)
     */
    @Transactional
    public void findPassword(String loginId, String email) {
        Member findMember = memberRepository.findByLoginIdAndEmail(loginId, email)
                .orElseThrow(() -> new MemberException(MemberErrorResult.MEMBER_NOT_FOUND_BY_FIND_PASSWORD));

        String tempPassword = createTempPassword();
        mailUtil.passwordFindEmailSend(email, tempPassword);

        String encodedTempPassword = passwordEncoder.encode(tempPassword);
        findMember.updatePassword(encodedTempPassword);
    }

    private String createTempPassword() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 8);
    }

    /**
     * 비밀번호 확인 메소드
     * @param memberId
     * @param confirmRequestPassword
     * 비밀번호가 일치하지 않을 시
     * @throw MemberException(PASSWORD_CONFIRM_REQUEST_FAIL)
     * 멤버 인덱스(id)가 일치하지 않을 시 - 세션 끊김 등의 사유로...
     * @throw MemberException(MEMBER_NOT_FOUND_BY_FIND_MEMBER_ID)
     */
    public ChangeMemberInfoResponse confirmPasswordAndReturnMemberInfo(Long memberId, String confirmRequestPassword) {
        Member findMember = confirmPassword(memberId, confirmRequestPassword);

        return ChangeMemberInfoResponse.createResponse(findMember);
    }

    /* --- 비밀번호 확인 메소드 --- */
    private Member confirmPassword(Long memberId, String confirmRequestPassword) {
        Member findMember = findMemberByMemberId(memberId);

        boolean result = passwordEncoder.matches(confirmRequestPassword, findMember.getPassword());

        if(result == false){
            throw new MemberException(MemberErrorResult.PASSWORD_CONFIRM_REQUEST_FAIL);
        }
        return findMember;
    }

    /**
     * id로 멤버 검색 메소드
     * @param memberId
     * @return Member
     * 찾지 못할 경우
     * @Throw MemberException(MEMBER_NOT_FOUND_BY_FIND_MEMBER_ID)
     */
    public Member findMemberByMemberId(Long memberId) {
        if(memberId == null){
            throw new MemberException(MemberErrorResult.MEMBER_NOT_FOUND_BY_FIND_MEMBER_ID);
        }

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorResult.MEMBER_NOT_FOUND_BY_FIND_MEMBER_ID));
        return findMember;
    }

    /**
     * 비밀번호 변경 메소드
     * @param memberId
     * @param request
     * memberId가 null or 찾을 수 없을 때
     * @thow MemberException(MEMBER_NOT_FOUND_BY_FIND_MEMBER_ID)
     */
    @Transactional
    public void updatePassword(Long memberId, ChangePasswordRequest request) {
        Member findMember = confirmPassword(memberId, request.getPassword());

        String password = request.getPasswordNew();
        String passwordConfirm = request.getPasswordConfirm();
        validatePasswords(password, passwordConfirm, MemberErrorResult.PASSWORD_NOT_MATCH);

        String encodedPassword = encodePassword(password);
        findMember.updatePassword(encodedPassword);
    }

    /**
     * 닉네임 변경 메소드
     * @param memberId
     * @param nickname
     */
    @Transactional
    public void updateNickname(Long memberId, String nickname) {
        Member findMember = findMemberByMemberId(memberId);

        findMember.updateNickname(nickname);
    }

    /**
     * 이메일 변경 메소드
     * @param memberId
     * @param email
     */
    @Transactional
    public void updateEmail(Long memberId, String email) {
        Member findMember = findMemberByMemberId(memberId);

        findMember.updateEmail(email);
    }
}