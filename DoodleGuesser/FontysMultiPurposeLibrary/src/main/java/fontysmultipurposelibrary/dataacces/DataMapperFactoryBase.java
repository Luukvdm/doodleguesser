package fontysmultipurposelibrary.dataacces;

public abstract class DataMapperFactoryBase {

    public abstract BaseDataMapper getMapper(Class entityType);

    public abstract BaseDataMapper getMapper(String simpleType);
}
