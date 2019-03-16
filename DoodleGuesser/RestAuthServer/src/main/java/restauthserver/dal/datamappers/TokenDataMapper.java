package restauthserver.dal.datamappers;

import fontysmultipurposelibrary.dataacces.BaseDataMapper;
import fontysmultipurposelibrary.logging.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import restauthshared.model.Token;

public class TokenDataMapper extends BaseDataMapper<Token> {

    @Override
    public String mapToSqlInternal(Token token) {
        if(token.getEntityId() == 0)
        {
            String pattern = "dd-MM-yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(token.getCreationDate());
            //new
            return "insert into [tokens] (Token, CreationDate, TTL, UserId) "
                    + "values ('" + token.getToken() + "' , convert(datetime, '" + date + "', 104), '" + token.getTimeToLive() + "', '" + token.getPlayerId() +"')";
        }
        else
        {
            //update
            return "update [Tokens] set Token='" + token.getToken() + "', TTL='" + token.getTimeToLive() +"' where Id=" + token.getEntityId();
        }
    }

    @Override
    public List<Token> mapFromDatabaseInternal(ResultSet rs) {
        ArrayList<Token> tokens = new ArrayList();

        try {
            while (rs.next()) {
                long id = rs.getLong(1);
                String text = rs.getString(2);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);
                Date date = format.parse(rs.getString(3));
                int ttl = rs.getInt(4);
                long playerId = rs.getLong(5);
                Token t = new Token(text, date, ttl, playerId);
                t.setEntityId(id);
                tokens.add(t);
            }
            return tokens;
        }
        catch(SQLException | ParseException ex)
        {
            Logger.getInstance().log(ex);
            return tokens;
        }
    }
}
