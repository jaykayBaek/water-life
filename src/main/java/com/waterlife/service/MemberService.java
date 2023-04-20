package com.waterlife.service;

import com.waterlife.controller.MemberInformationResponse;
import com.waterlife.entity.Member;
import com.waterlife.exception.member.MemberErrorResult;
import com.waterlife.exception.member.MemberException;
import com.waterlife.repository.MemberRepository;
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
    private final EmailSendService emailSendService;

    /**
     * 회원가입 메소드
     * @param form
     * @return DB member_id
     */
    @Transactional
    public Long register(MemberRegisterForm form){
        validateForm(form);

        passwordEncode(form);

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
    private void passwordEncode(MemberRegisterForm form) {
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        form.encodePassword(encodedPassword);
    }
    
    /* --- form dto 검증 메소드 --- */
    public void validateForm(MemberRegisterForm form) {
        String loginId = form.getLoginId();
        String email = form.getEmail();

        validateLoginId(loginId);
        validateEmail(email);

        if(isPasswordNotMatch(form)){
            throw new MemberException(MemberErrorResult.LOGIN_INFO_NOT_MATCH);
        }

    }
    
    /* --- if문 리펙토링 메소드 --- */
    private static boolean isPasswordNotMatch(MemberRegisterForm form) {
        return !form.getPassword().equals(form.getPasswordConfirm());
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
     * 찾기 실패 시
     * @throw MemberException(MEMBER_NOT_FOUND_BY_FIND_PASSWORD)
     */
    @Transactional
    public void findPasswordAndChangeTempPassword(String loginId, String email) {
        Member findMember = memberRepository.findByLoginIdAndEmail(loginId, email)
                .orElseThrow(() -> new MemberException(MemberErrorResult.MEMBER_NOT_FOUND_BY_FIND_PASSWORD));

        String tempPassword = createTempPassword();
        emailSendService.passwordFindEmailSend(email, tempPassword);

        String encodedTempPassword = passwordEncoder.encode(tempPassword);
        findMember.updatePassword(encodedTempPassword);
    }

    private String createTempPassword() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 8);
    }

}