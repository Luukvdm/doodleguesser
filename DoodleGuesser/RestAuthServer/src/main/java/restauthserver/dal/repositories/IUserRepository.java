package restauthserver.dal.repositories;

import restauthshared.model.User;
import fontysmultipurposelibrary.dataacces.IRepository;

public interface IUserRepository extends IRepository<User> {
    String login(ITokenRepository tokenRepos, String username, String password);
    boolean register(String username, String password);
}