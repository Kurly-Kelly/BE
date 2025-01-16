package org.example.be.repository;

import java.util.Optional;
import org.example.be.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);

}
