package fontysmultipurposelibrary.dataacces.datacontexts;

import fontysmultipurposelibrary.dataacces.Entity;

import java.util.ArrayList;
import java.util.List;

public class InMemoryContext implements IDataContext {

    private List<Entity> entities = new ArrayList<>();

    @Override
    public <T> T getSingle(long id, Class<T> returnType, String simpleType) {
        for (Entity e : entities) {
            if (e.getEntityId() == id)
                return (T) e;
        }
        return null;
    }

    @Override
    public <T> List<T> getAll(Class<T> returnType, String simpleType) {
        List<T> items = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getClass() == returnType)
                items.add((T) e);
        }
        return items;
    }

    @Override
    public <T> void add(Entity obj, Class<T> returnType) {
        entities.add(obj);
    }

    @Override
    public <T> void update(Entity obj, Class<T> returnType, String simpleType) {
        remove(obj, returnType, simpleType);
        add(obj, returnType);
    }

    @Override
    public <T> void remove(Entity obj, Class<T> returnType, String simpleType) {
        for (Entity e : entities) {
            if (e.getEntityId() == obj.getEntityId())
                entities.remove(e);
        }
    }
}
