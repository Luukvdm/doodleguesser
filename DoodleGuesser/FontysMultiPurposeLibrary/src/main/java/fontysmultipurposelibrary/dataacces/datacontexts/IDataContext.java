package fontysmultipurposelibrary.dataacces.datacontexts;

import fontysmultipurposelibrary.dataacces.Entity;

import java.util.List;

public interface IDataContext {

    <T> T getSingle(long id, Class<T> returnType, String simpleType);

    <T> List<T> getAll(Class<T> returnType, String simpleType);

    <T> void add(Entity obj, Class<T> returnType);

    <T> void update(Entity obj, Class<T> returnType, String simpleType);

    <T> void remove(Entity obj, Class<T> returnType, String simpleType);

}
