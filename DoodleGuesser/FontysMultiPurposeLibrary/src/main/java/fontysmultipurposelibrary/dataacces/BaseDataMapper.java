package fontysmultipurposelibrary.dataacces;

import java.sql.ResultSet;
import java.util.List;

public abstract class BaseDataMapper<T> {

    public List<T> mapFromDatabase(ResultSet rs) {
        return mapFromDatabaseInternal(rs);
    }

    public String mapToSql(Object object) {
        return mapToSqlInternal((T) object);
    }

    public abstract String mapToSqlInternal(T object);

    public abstract List<T> mapFromDatabaseInternal(ResultSet rs);
}
