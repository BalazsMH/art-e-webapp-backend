package com.arte.backend.model.authentication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCredentials {
    private String email;
    private String password;
}
