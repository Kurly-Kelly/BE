package org.example.felessmartket_be.repository;

import java.util.List;
import java.util.Optional;
import org.example.felessmartket_be.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);

    // [아이디/비밀번호 찾기] 위해 이름과 이메일이 동시에 일치하는 회원
    Optional<Member> findByNameAndEmail(String name, String email);
}
