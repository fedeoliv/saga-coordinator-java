package coordinator.shared.avro.serializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import coordinator.shared.avro.messages.headers.Headers;

/**
 * Simple {@link Serializer} implementation for {@link Integer}s
 */
public class GenericSerializer<T> implements Serializer<T> {
    public byte[] serialize(T obj) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            
            os.writeObject(obj);
            
            return out.toByteArray();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public T deserialize(byte[] data) {
        try {
            ObjectInputStream inputStream = createInputStream(data);            
            return (T) inputStream.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Headers deserializeHeaders(byte[] data) {
        try {
            ObjectInputStream inputStream = createInputStream(data);
            return (Headers) inputStream.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ObjectInputStream createInputStream(byte[] data) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        return new ObjectInputStream(in);
    }
}
