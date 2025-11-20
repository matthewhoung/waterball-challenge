package tw.waterballsa.challenge.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tw.waterballsa.challenge.common.exceptions.ValidationException;
import tw.waterballsa.challenge.contexts.identity.domain.enums.Roles;

import java.util.UUID;

public class AuthenticationUtil {

    public static UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new ValidationException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UUID) {
            return (UUID) principal;
        }

        throw new ValidationException("Invalid authentication principal");
    }

    public static Roles getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ValidationException("User not authenticated");
        }

        return authentication.getAuthorities().stream()
                .findFirst()
                .map(authority -> {
                    String role = authority.getAuthority();
                    if (role.startsWith("ROLE_")) {
                        role = role.substring(5);
                    }
                    return Roles.valueOf(role);
                })
                .orElseThrow(() -> new ValidationException("User role not found"));
    }

    public static boolean hasRole(Roles role) {
        try {
            Roles currentRole = getCurrentUserRole();
            return currentRole == role;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser");
    }
}