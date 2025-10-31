package org.rishbootdev.healthsphere.authorizationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishbootdev.healthsphere.authorizationModels.Role;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String id;
    private Role role;
}
