package tw.waterballsa.challenge.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tw.waterballsa.challenge.contexts.identity.domain.User;
import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;
import tw.waterballsa.challenge.contexts.identity.repository.UserRepository;

@Component
public class DatabaseSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public DatabaseSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seedDatabase() {
        seedAdminAccount();
    }

    private void seedAdminAccount() {
        // Check if admin already exists
        if (userRepository.existsByEmail(adminEmail)) {
            logger.info("Admin account already exists. Skipping seeding.");
            return;
        }

        // Get next student ID (current count + 1)
        int studentId = (int) (userRepository.count() + 1);

        // Create admin account
        User admin = new User(
                "admin",
                adminEmail,
                passwordEncoder.encode(adminPassword)
        );
        admin.setRole(Roles.ADMIN);
        admin.setStudentId(studentId);

        userRepository.save(admin);
        logger.info("Admin account created successfully: {} (Display Name: {}, Student ID: {})",
                adminEmail, admin.getDisplayName(), studentId);
    }
}