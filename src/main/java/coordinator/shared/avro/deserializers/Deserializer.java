package coordinator.shared.avro.deserializers;

import coordinator.shared.avro.messages.headers.Headers;

/**
 * Simple contract to be implemented by deserialization classes.
 * 
 * @param <T> The type this class deserializes.
 */
public interface Deserializer<T> {
    T deserialize(byte[] bytes);
    Headers deserializeHeaders(byte[] bytes);
}
