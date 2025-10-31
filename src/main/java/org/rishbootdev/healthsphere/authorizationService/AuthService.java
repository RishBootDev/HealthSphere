package org.rishbootdev.healthsphere.authorizationService;

import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.authorizationDto.LoginRequest;
import org.rishbootdev.healthsphere.authorizationDto.LoginResponse;
import org.rishbootdev.healthsphere.authorizationModels.User;
import org.rishbootdev.healthsphere.exception.ResourceNotFoundException;
import org.rishbootdev.healthsphere.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token,user.getUserId(),user.getRole());
    }

    public void logout(String token) {
        // stateless system hai but i will add this logic afterwards
    }
}
