package com.exapmle.gateway.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
}
