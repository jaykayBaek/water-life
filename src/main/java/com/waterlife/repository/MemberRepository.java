package com.waterlife.repository;

import com.waterlife.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByLoginIdAndEmail(String loginId, String email);
}
