package org.rishbootdev.healthsphere.authcontroller;


import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.authorizationDto.LoginRequest;
import org.rishbootdev.healthsphere.authorizationDto.LoginResponse;
import org.rishbootdev.healthsphere.authorizationService.AuthService;
import org.rishbootdev.healthsphere.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/doctor/login")
    public ResponseEntity<LoginResponse> doctorLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/hospital/login")
    public ResponseEntity<LoginResponse> hospitalLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/lab/login")
    public ResponseEntity<LoginResponse> labLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/pharma/login")
    public ResponseEntity<LoginResponse> pharmaLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/patient/login")
    public ResponseEntity<LoginResponse> patientLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.replace("Bearer ", ""));
        return ResponseEntity.ok("Logged out successfully");
    }
    @PostMapping("/registerDoctor")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorDto doctorDto,
                                                 @RequestParam String email,@RequestParam String password) {
        return ResponseEntity.ok(authService.registerDoctor(doctorDto,email,password));
    }
    @PostMapping("/registerHospital")
    public ResponseEntity<String> registerHospital(@RequestBody HospitalDto hospitalDto,
                                                   @RequestParam String email,@RequestParam String password) {
        return ResponseEntity.ok(authService.registerHospital(hospitalDto,email,password));
    }

    @PostMapping("/registerLab")
    public ResponseEntity<String> registerLab(@RequestBody LabDto labDto,
                                              @RequestParam String email,@RequestParam String password) {
        return ResponseEntity.ok(authService.registerLab(labDto,email,password));
    }

    @PostMapping("/registerPatient")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientDto patientDto,
                                                      @RequestParam String email,@RequestParam String password) {
        return ResponseEntity.ok(authService.registerPatient(patientDto,email,password));
    }

    @PostMapping("/registerPharma")
    public ResponseEntity<PharmaDto> registerPharma(@RequestBody PharmaDto pharmaDto,
                                                    @RequestParam String email,@RequestParam String password) {
        return ResponseEntity.ok(authService.registerPharma(pharmaDto,email,password));
    }
}
