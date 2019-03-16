package restauthserver.dal.repositories;

import restauthshared.model.Token;
import fontysmultipurposelibrary.dataacces.RepositoryBase;
import fontysmultipurposelibrary.dataacces.datacontexts.IDataContext;
import restauthshared.model.User;

import java.util.List;

public class UserRepo extends RepositoryBase<User> implements IUserRepository {

    public UserRepo(IDataContext context)
    {
        super(context);
    }

    public String login(ITokenRepository tokenRepos, String userName, String password)
    {
        List<User> players = getAll();
        for(User p : players)
        {
            if(p.getUsername().equals(userName) && p.getPassword().equals(password)) {
                //Check existing token
                Token existingToken = tokenRepos.getTokenForUser(p.getEntityId());
                if(existingToken != null)
                    return existingToken.getToken();

                //OLD TOKEN NOT FOUND SO GENERATE A NEW ONE
                return tokenRepos.generateToken(p.getEntityId());
            }
        }
        return "";
    }

    public boolean register(String username, String password) {
        List<User> players = getAll();
        for (User p : players) {
            if (p.getUsername().equals(username)) {
                return false;
                //USER ALREADY EXISTS
            }
        }

        User player = new User(username, password);
        add(player);
        return true;
    }
}
