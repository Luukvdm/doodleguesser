package fontysmultipurposelibrary.dataacces;

import fontysmultipurposelibrary.dataacces.datacontexts.IDataContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class RepositoryBase<T extends Entity> {

    private IDataContext dataContext;

    public IDataContext getDataContext() {
        return dataContext;
    }

    private String typeToSimpleName(Type type) {
        String typeName = type.getTypeName();
        return typeName.split("\\.")[typeName.split("\\.").length - 1];
    }

    public RepositoryBase(IDataContext dataContext) {
        this.dataContext = dataContext;
    }

    public List<T> getAll() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (List<T>) getDataContext().getAll(type.getClass(), typeToSimpleName(type));
    }

    public T getSingle(long id) {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (T) getDataContext().getSingle(id, type.getClass(), typeToSimpleName(type));
    }

    public void add(T item) {
        getDataContext().add(item, item.getClass());
    }

    public void update(T item) {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        getDataContext().update(item, item.getClass(), typeToSimpleName(type));
    }

    public void remove(T item) {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        getDataContext().remove(item, item.getClass(), typeToSimpleName(type));
    }
}
