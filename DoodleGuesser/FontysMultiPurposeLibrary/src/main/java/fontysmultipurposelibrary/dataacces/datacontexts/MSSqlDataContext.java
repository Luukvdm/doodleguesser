package fontysmultipurposelibrary.dataacces.datacontexts;

import fontysmultipurposelibrary.dataacces.BaseDataMapper;
import fontysmultipurposelibrary.dataacces.DataMapperFactoryBase;
import fontysmultipurposelibrary.dataacces.Entity;
import fontysmultipurposelibrary.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MSSqlDataContext implements IDataContext {

    private DataMapperFactoryBase mapperFatory;
    private String connectionUrl;

    public MSSqlDataContext(DataMapperFactoryBase mapperFatory, String connectionUrl) {
        this.mapperFatory = mapperFatory;
        this.connectionUrl = connectionUrl;
    }

    public Connection getConnection() {
        // Load the SQLServerDriver class, build the
        // connection string, and get a connection
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            Logger.getInstance().log(e);
            return null;
        }
    }

    public boolean executeNonQuery(String query) {
        try {
            Connection con = getConnection();
            // Create and execute an SQL statement that returns some data.
            try (Statement stmt = con.createStatement()) {
                return stmt.execute(query);
            }
        } catch (Exception e) {
            Logger.getInstance().log(e);
            return false;
        }
    }

    private <T> List<T> executeQuery(String query, Class<T> returnType, String simpleType) {
        try {
            Connection con = getConnection();
            // Create and execute an SQL statement that returns some data.
            try (Statement stmt = con.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(query)) {
                    BaseDataMapper<T> mapper = null;
                    mapper = mapperFatory.getMapper(returnType);
                    if (mapper != null)
                        return mapper.mapFromDatabase(rs);
                    else {
                        mapper = mapperFatory.getMapper(simpleType);
                        return mapper.mapFromDatabase(rs);
                    }
                }
            }
        } catch (Exception e) {
            Logger.getInstance().log(e);
            return new ArrayList();
        }
    }

    public <T> T getSingle(long id, Class<T> returnType, String simpleType) {
        List<T> list = executeQuery("select top 1 * from [" + simpleType + "s] where Id=" + id, returnType, simpleType);
        if (list != null && !list.isEmpty())
            return list.get(0);
        else
            return null;
    }

    public <T> List<T> getAll(Class<T> returnType, String simpleType) {
        String query = "select * from [" + simpleType + "s]";
        return executeQuery(query, returnType, simpleType);
    }

    private <T> void writeToDatabase(Object obj, Class<T> returnType) {
        BaseDataMapper<T> mapper = null;
        mapper = mapperFatory.getMapper(returnType);
        if (mapper != null) {
            String sql = mapper.mapToSql(obj);
            executeNonQuery(sql);
        }
    }

    public <T> void add(Entity obj, Class<T> returnType) {
        writeToDatabase(obj, returnType);
    }

    public <T> void update(Entity obj, Class<T> returnType, String simpleType) {
        writeToDatabase(obj, returnType);
    }

    public <T> void remove(Entity obj, Class<T> returnType, String simpleType) {
        String sql = "delete from [" + simpleType + "s] where Id=" + obj.getEntityId();
        executeNonQuery(sql);
    }
}
