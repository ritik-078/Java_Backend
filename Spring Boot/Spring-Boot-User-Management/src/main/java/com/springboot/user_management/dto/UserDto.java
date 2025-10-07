package com.springboot.user_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "UserDto Model Information"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private Long id;

    @Schema(
            description = "User First Name"
    )
    @NotEmpty(message = "First Name should not be empty.")
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    private String email;
}
