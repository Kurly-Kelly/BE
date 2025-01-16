package org.example.be.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailVerificationResult {

    private String completeMessage;
    private Boolean status;

    public static EmailVerificationResult of(Boolean authResult) {
        if(authResult) return new EmailVerificationResult("성공", true);
        else return new EmailVerificationResult("실패", false);
    }
}
