package restauthserver.dal.repositories;

import restauthshared.model.Token;
import fontysmultipurposelibrary.dataacces.RepositoryBase;
import fontysmultipurposelibrary.dataacces.datacontexts.IDataContext;
import fontysmultipurposelibrary.dataacces.helpers.DateHelper;

import java.util.Date;
import java.util.List;

public class TokenRepo extends RepositoryBase<Token> implements ITokenRepository {

    public TokenRepo(IDataContext context)
    {
        super(context);
    }

    public Token getTokenForUser(long userId)
    {
        Date currentDate = new Date();
        List<Token> allTokens = super.getDataContext().getAll(Token.class, "Token");

        for(Token t : allTokens)
        {
            if( t.getPlayerId() == userId) {
                Date newDateWithTTL = DateHelper.addMinutesToDate(t.getTimeToLive(), t.getCreationDate());
                if (currentDate.before(newDateWithTTL)) {
                    // current date is before token expirationdate
                    return t;
                }
            }
        }
        return null;
    }

    public String generateToken(long userId)
    {
        String token = java.util.UUID.randomUUID().toString();
        Date date = new Date();
        Token t = new Token(token, date, 60, userId);
        super.getDataContext().add(t, Token.class);
        return token;
    }
}

