package com.example.my_app.modules.Register.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import com.example.my_app.Enum.user.StatusUserEntry;
import com.example.my_app.model.Role_Permission.Role;

import lombok.Builder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStepThreeDTO {

    @NotBlank(message = "Email  is not have value ")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    String email;

    @NotBlank(message = "password  is not have value ")
    @NotEmpty(message = "password cannot be empty")
    @Size(min = 0, max = 200)
    String password;

    @NotBlank(message = "username  is not have value ")
    @NotEmpty(message = "username cannot be empty")
    @Size(min = 0, max = 200)
    String username;

    StatusUserEntry statusEntry;

    Role user_role;
}
