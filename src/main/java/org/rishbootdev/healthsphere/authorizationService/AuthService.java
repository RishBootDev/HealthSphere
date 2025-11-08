package org.rishbootdev.healthsphere.authorizationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.authorizationDto.LoginRequest;
import org.rishbootdev.healthsphere.authorizationDto.LoginResponse;
import org.rishbootdev.healthsphere.authorizationModels.Role;
import org.rishbootdev.healthsphere.authorizationModels.User;
import org.rishbootdev.healthsphere.dto.*;
import org.rishbootdev.healthsphere.exception.ResourceNotFoundException;
import org.rishbootdev.healthsphere.repository.UserRepository;
import org.rishbootdev.healthsphere.service.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final HospitalService hospitalService;
    private final LabService labService;
    private final PharmaService pharmaService;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()) ) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
       // Cookie ck=new Cookie("token",token);
        return new LoginResponse(token,user.getUserId(),user.getRole());
    }

    public void logout(String token) {
        // stateless system hai but i will add this logic afterwards
    }

    @Transactional
    public String registerDoctor(DoctorDto doctorDto, String email,String password) {

        String customId = "DOC" + UUID.randomUUID().toString().substring(0,5);
        doctorDto.setDoctorId(customId);
        User user=new User(email,
                passwordEncoder.encode(password)
                ,Role.DOCTOR,
                customId);
        userRepository.save(user);
        return doctorService.createDoctor(doctorDto);
    }
    @Transactional
    public String registerHospital(HospitalDto hospitalDto,String email,String password) {
        String customId = "HOSP" + UUID.randomUUID().toString().substring(0,5);
        hospitalDto.setHospitalId(customId);
        User user=new User(email,
                passwordEncoder.encode(password)
                ,Role.HOSPITAL,
                customId);
        userRepository.save(user);
        return hospitalService.registerHospital(hospitalDto);
    }

    @Transactional
    public String registerLab(LabDto labDto,String email,String password) {

        String customId = "LAB" + UUID.randomUUID().toString().substring(0,5);
        labDto.setLabId(customId);

        User user=new User(email,
                passwordEncoder.encode(password)
                ,Role.LAB,
                customId);
        userRepository.save(user);
        return labService.createLab(labDto);
    }
@Transactional
    public PatientDto registerPatient(PatientDto patientDto,String email,String password) {

        String customId = "PAT" + UUID.randomUUID().toString().substring(0,5);
        patientDto.setPatientId(customId);
        User user=new User(email,
                passwordEncoder.encode(password)
                ,Role.PATIENT,
                customId);
        userRepository.save(user);
        return patientService.createPatient(patientDto);
    }
    @Transactional
    public PharmaDto registerPharma(PharmaDto pharmaDto,String email,String password) {

        String customId = "PHA" + UUID.randomUUID().toString().substring(0,5);
        pharmaDto.setPharmaId(customId);
        User user=new User(email,
                passwordEncoder.encode(password)
                ,Role.PHARMA,
                customId);

        userRepository.save(user);
        return pharmaService.createPharma(pharmaDto);
    }
}
