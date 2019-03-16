package restauthserver.dal.repositories;

import restauthshared.model.Token;
import fontysmultipurposelibrary.dataacces.IRepository;

public interface ITokenRepository extends IRepository<Token> {
    Token getTokenForUser(long userId);
    String generateToken(long userId);
}

