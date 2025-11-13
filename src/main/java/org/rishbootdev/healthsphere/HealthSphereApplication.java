package org.rishbootdev.healthsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthSphereApplication.class, args);

//        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
//        String hashed=encoder.encode("abcdefg");
//        System.out.println(hashed);
    }

}
