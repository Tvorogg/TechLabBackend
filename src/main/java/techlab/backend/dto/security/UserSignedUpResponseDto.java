package techlab.backend.dto.security;

public record UserSignedUpResponseDto(String username, boolean registered, String role) {
}
