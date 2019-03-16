package restauthshared.dto;

import fontysmultipurposelibrary.communication.rest.dto.BaseRequestDto;

public class AuthRequestDto extends BaseRequestDto {
    private String username;
    private String hashedPassword;

    public AuthRequestDto(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public AuthRequestDto() {}

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
