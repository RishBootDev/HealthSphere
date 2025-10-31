package org.rishbootdev.healthsphere.authorizationDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishbootdev.healthsphere.authorizationModels.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String id;
    private String email;
    private String password;
    private Role role;
}
