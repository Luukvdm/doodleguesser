package restauthshared.client;

public interface IAuthRestClient {
    String login(String username, String password);
    boolean register(String username, String password);
}
