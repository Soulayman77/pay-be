package com.example.school.Dto;

import com.example.school.Validators.PasswordMatches;
import com.example.school.Validators.VerifyOldPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatches(password = "password", passwordConfirmation = "confirmPassowrd")
public class EditPasswordDto {

    @NotBlank(message = "The old password is required")
    @VerifyOldPassword
    private String oldPassword;

    @NotBlank(message = "The new password is required")
    @Pattern(regexp = "(?=^.{8,100}$)(?=.*\\d)(?=.*[^A-Za-z0-9]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "The password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit and must be at least 8 characters")
    private String password;

    @NotBlank(message = "The password confirmation is required")
    @JsonProperty("confirmation")
    private String confirmPassowrd;
}
