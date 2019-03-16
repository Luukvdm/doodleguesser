package fontysmultipurposelibrary.dataacces;

import java.util.List;

public interface IRepository<T extends Entity> {

    void add(T item);

    void update(T item);

    void remove(T item);

    List<T> getAll();

    T getSingle(long id);
}
