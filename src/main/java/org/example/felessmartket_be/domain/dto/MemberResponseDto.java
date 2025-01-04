package org.example.felessmartket_be.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.felessmartket_be.domain.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private String username;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
            member.getUsername(),
            member.getName(),
            member.getEmail(),
            member.getPassword(),
            member.getPhone()
        );
    }
}
