package restauthshared.client;

import fontysmultipurposelibrary.communication.rest.BaseRestClient;
import restauthshared.dto.AuthRequestDto;
import restauthshared.dto.LoginResultDto;
import restauthshared.dto.RegistrationResultDto;

public class AuthRestClient extends BaseRestClient implements IAuthRestClient {

    private String url;

    public AuthRestClient(String url) {
        this.url = url;
    }

    @Override
    public String getBaseUr() {
        return this.url;
    }

    @Override
    public String login(String username, String password) {
        AuthRequestDto dto = new AuthRequestDto(username, password);
        String query = "/login";
        LoginResultDto result = executeQueryPost(dto, query, LoginResultDto.class);
        return result.getToken();
    }

    @Override
    public boolean register(String username, String password) {
        AuthRequestDto dto = new AuthRequestDto(username, password);
        String query = "/register";
        RegistrationResultDto result = executeQueryPost(dto, query, RegistrationResultDto.class);
        return result.isSuccess();
    }
}
