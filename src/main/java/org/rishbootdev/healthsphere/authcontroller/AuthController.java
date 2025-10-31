package org.rishbootdev.healthsphere.authcontroller;

import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.authorizationDto.LoginRequest;
import org.rishbootdev.healthsphere.authorizationDto.LoginResponse;
import org.rishbootdev.healthsphere.authorizationModels.Role;
import org.rishbootdev.healthsphere.authorizationService.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/doctor/login")
    public ResponseEntity<LoginResponse> doctorLogin(@RequestBody LoginRequest request) {
        request.setRole(Role.DOCTOR);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/hospital/login")
    public ResponseEntity<LoginResponse> hospitalLogin(@RequestBody LoginRequest request) {
        request.setRole(Role.HOSPITAL);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/lab/login")
    public ResponseEntity<LoginResponse> labLogin(@RequestBody LoginRequest request) {
        request.setRole(Role.LAB);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/pharma/login")
    public ResponseEntity<LoginResponse> pharmaLogin(@RequestBody LoginRequest request) {
        request.setRole(Role.PHARMA);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/patient/login")
    public ResponseEntity<LoginResponse> patientLogin(@RequestBody LoginRequest request) {
        request.setRole(Role.PATIENT);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.replace("Bearer ", ""));
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/test")
    public ResponseEntity<String> testApi(){
        Date date=new Date();
        String message=" Yes the backend api is consumed by the frontend ---> "+date;

        System.out.println("Request hit on this server");

        return ResponseEntity.ok(message);
    }
}
