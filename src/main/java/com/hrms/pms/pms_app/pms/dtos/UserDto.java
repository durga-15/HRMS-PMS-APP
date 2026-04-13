package com.hrms.pms.pms_app.pms.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID id;

    @Size(min = 3, max = 100, message = "User name must be between 3 and 100 characters")
    private String userName;

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean status = true;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instant createdAt = Instant.now();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instant updatedAt = Instant.now();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<RoleDto> roles = new HashSet<>();
}
