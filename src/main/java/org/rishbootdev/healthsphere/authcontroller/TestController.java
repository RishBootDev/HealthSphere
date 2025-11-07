package org.rishbootdev.healthsphere.authcontroller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.rishbootdev.healthsphere.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/test")
@CrossOrigin
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(testService.ping());
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok(testService.healthCheck());
    }

    @PostMapping("/record")
    public ResponseEntity<String> createRecord(
            @RequestParam String key,
            @RequestParam String value) {
        return ResponseEntity.ok(testService.createRecordTest(key, value));
    }

    @GetMapping("/record/{key}")
    public ResponseEntity<String> queryRecord(@PathVariable String key) {
        return ResponseEntity.ok(testService.queryRecordTest(key));
    }

    @DeleteMapping("/record/{key}")
    public ResponseEntity<String> deleteRecord(@PathVariable String key) {
        return ResponseEntity.ok(testService.deleteRecordTest(key));
    }

    @PostMapping("/load-test-data")
    public ResponseEntity<String> loadTestData() {
        return ResponseEntity.ok(testService.testLedgerData());
    }
    @GetMapping("/testBackend")
    public ResponseEntity<String> testApi(HttpServletRequest req){
        Date date=new Date();
        String message=" Yes the backend api is consumed by the frontend ---> "+date;
        System.out.println("Request hit on this server");

        return ResponseEntity.ok(message);
    }
}
