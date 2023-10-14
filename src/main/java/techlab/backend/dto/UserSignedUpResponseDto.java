package techlab.backend.dto;

public record UserSignedUpResponseDto(String username, boolean registered, String role) {
}
