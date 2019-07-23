package coordinator.repositories;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCursor;
import com.google.gson.Gson;
import static com.mongodb.client.model.Filters.*;

public class DefaultRepository<TKey, TEntity> implements Repository<TKey, TEntity> {

    private final MongoCollection<Document> mongoCollection;
    private final String keyName;
    private final Gson gson;
    private final Class<TEntity> beanTypeTEntity;

    public DefaultRepository(String mongoConnectionString, String database, String collection, String keyName) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoConnectionString));
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        this.mongoCollection = mongoDatabase.getCollection(collection);
        this.keyName = keyName;
        this.gson = new Gson();
        this.beanTypeTEntity = GetBeanType();
        mongoClient.close();
    }

    @Override
    public TEntity Add(TEntity value) {
        Document document = ConvertFromEntityToDocument(value);
        mongoCollection.insertOne(document);

        return value;
    }

    @Override
    public TEntity Update(TKey key, TEntity value) {
        Document document = ConvertFromEntityToDocument(value);
        mongoCollection.updateOne(eq(keyName, key), new Document("$set", document));

        return value;
    }

    @Override
    public void Delete(TKey key) {
        mongoCollection.deleteOne(eq(keyName, key));
    }

    @Override
    public TEntity FindOneByKey(TKey key) {
        Document myDocObj = mongoCollection.find(eq(keyName, key)).first();
        TEntity value = ConvertFromDocumentToEntity(myDocObj);

        return value;
    }

    private TEntity FindOneByFilter(Bson filter) {
        Document myDocObj = mongoCollection.find(filter).first();
        TEntity value = ConvertFromDocumentToEntity(myDocObj);

        return value;
    }

    @Override
    public List<TEntity> FindManyByFilter(Object filter) {
        List<TEntity> list = new ArrayList<TEntity>();

        MongoCursor<Document> cursor = mongoCollection.find((Bson) filter).iterator();
        try {
            while (cursor.hasNext()) {
                ConvertFromDocumentToEntity(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    private Document ConvertFromEntityToDocument(TEntity value) {
        // Serialize object to json string
        String json = gson.toJson(value);
        // Parse to bson document
        Document document = Document.parse(json);

        return document;
    }

    private TEntity ConvertFromDocumentToEntity(Document document) {
        // Deserialize object to json string
        String myDocJson = document.toJson();
        // Parse to TEntity
        TEntity value = this.gson.fromJson(myDocJson, this.beanTypeTEntity);

        return value;
    }

    @SuppressWarnings("unchecked")
    private Class<TEntity> GetBeanType() {
        return ((Class<TEntity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
    }

    @Override
    public TEntity FindOneByFilter(String key1, String value1) {
        return FindOneByFilter(eq(key1, value1));
    }

    @Override
    public TEntity FindOneByFilter(String key1, String value1, String key2, String value2) {
        return FindOneByFilter(and(eq(key1, value1), eq(key2, value2)));
    }
}
