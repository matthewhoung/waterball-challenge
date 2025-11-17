package tw.waterballsa.challenge.features.systems;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@RestController
@RequestMapping("/api/systems")
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        return Map.of(
                "status", "UP",
                "message", "Waterball Backend running on Port 5000",
                "stack", "Java Spring Boot"
        );
    }
}