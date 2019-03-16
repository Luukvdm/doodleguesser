package restauthserver.dal.datamappers;

import restauthshared.model.User;
import fontysmultipurposelibrary.dataacces.BaseDataMapper;
import fontysmultipurposelibrary.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataMapper extends BaseDataMapper<User> {

    @Override
    public String mapToSqlInternal(User user) {
        if(user.getEntityId() == 0)
        {
            return "insert into [users] (Username, Password) values ('" + user.getUsername() + "' , '" + user.getPassword() +"')";
        }
        else
        {
            //update
            return "update [users] set Username='" + user.getUsername() + "', Password='" + user.getPassword() +"' where Id=" + user.getEntityId();
        }
    }

    @Override
    public List<User> mapFromDatabaseInternal(ResultSet rs) {
        ArrayList<User> players = new ArrayList<>();
        try {
            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                String password = rs.getString(3);
                User p = new User(name, password);
                p.setEntityId(id);
                players.add(p);
            }
            return players;
        }
        catch(SQLException ex)
        {
            Logger.getInstance().log(ex);
            return players;
        }
    }
}

