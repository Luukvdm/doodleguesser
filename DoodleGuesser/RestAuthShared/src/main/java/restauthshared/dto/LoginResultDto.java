package restauthshared.dto;

import fontysmultipurposelibrary.communication.rest.dto.BaseResultDto;

public class LoginResultDto extends BaseResultDto {

    private String token;

    public LoginResultDto(String token) {
        this.token = token;
    }

    public LoginResultDto() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
