package coordinator.shared.avro.deserializers;

import java.util.function.Supplier;

import coordinator.shared.avro.serializers.GenericSerializer;

import static java.util.Objects.requireNonNull;

/**
 * {@link Supplier} implementation that lazily deserializes a given byte[].
 *
 * @param <T> Type of the object supplied.
 */
public class LazyDeserializerSupplier<T> implements Supplier<T> {
    private final byte[] bytes;
    private T obj = null;

    public LazyDeserializerSupplier(byte[] bytes) {
        this.bytes = requireNonNull(bytes);
    }

    public synchronized T get() {
        if (this.obj == null) {
            deserializeBytes();
        }
        
        return this.obj;
    }

    private void deserializeBytes() {
        this.obj = new GenericSerializer<T>().deserialize(bytes);
    }
}
