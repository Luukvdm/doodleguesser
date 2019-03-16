package restauthserver.dal.datamappers;

import restauthshared.model.Token;
import fontysmultipurposelibrary.dataacces.BaseDataMapper;
import fontysmultipurposelibrary.dataacces.DataMapperFactoryBase;
import restauthshared.model.User;

public class DataMapperFactory extends DataMapperFactoryBase {
    @Override
    public BaseDataMapper getMapper(Class entityType) {
        if(entityType.equals(User.class))
            return new UserDataMapper();
        else if(entityType.equals(Token.class))
            return new TokenDataMapper();
        return null;
    }

    @Override
    public BaseDataMapper getMapper(String simpleType) {
        switch(simpleType)
        {
            case "User":
                return new UserDataMapper();
            case "Token":
                return new TokenDataMapper();
            default:
                return null;
        }

    }
}
