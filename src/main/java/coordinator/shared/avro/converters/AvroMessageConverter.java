package coordinator.shared.avro.converters;

import org.apache.commons.lang3.tuple.Pair;

import coordinator.shared.avro.deserializers.LazyDeserializerSupplier;
import coordinator.shared.avro.messages.AvroMessage;
import coordinator.shared.avro.messages.Message;
import coordinator.shared.avro.messages.headers.Headers;
import coordinator.shared.avro.serializers.GenericSerializer;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class AvroMessageConverter<T> implements Converter<Message<T>, AvroMessage> {

    @Override
    public AvroMessage convert(Message<T> message) {
        AvroMessage.Builder builder = AvroMessage.newBuilder();
        Map<CharSequence, CharSequence> headers = getHeaders(message);
        ByteBuffer payload = getPayload(message);

        builder.setHeaders(headers);
        builder.setPayload(payload);

        return builder.build();
    }

    @Override
    public Message<T> revert(AvroMessage avroMessage) {
        Headers headers = revertHeaders(avroMessage);
        Supplier<T> payload = revertPayload(avroMessage);

        return new Message<>(headers, payload);
    }

    public Headers revertHeaders(AvroMessage avroMessage) {
        Collection<Pair<String, String>> formattedHeaders = avroMessage
            .getHeaders()
            .entrySet().stream()
            .map(e -> Pair.of(e.getKey().toString(), e.getValue().toString()))
            .collect(toList());

        return new Headers(formattedHeaders);
    }

    public Supplier<T> revertPayload(AvroMessage avroMessage) {
        byte[] payloadBytes = avroMessage.getPayload().array();
        return new LazyDeserializerSupplier<T>(payloadBytes);
    }

    private Map<CharSequence, CharSequence> getHeaders(Message<T> message) {
        return message
            .getAllHeaders()
            .stream()
            .collect(toMap(Pair::getKey, Pair::getValue));
    }

    private ByteBuffer getPayload(Message<T> message) {
        T payload = message.getPayload();
        byte[] payloadBytes = new GenericSerializer<T>().serialize(payload);

        return ByteBuffer.wrap(payloadBytes);
    }
}
