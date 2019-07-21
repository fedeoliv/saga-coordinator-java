package coordinator.shared.avro.serializers;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import coordinator.shared.avro.converters.AvroMessageConverter;
import coordinator.shared.avro.messages.AvroMessage;
import coordinator.shared.avro.messages.Message;
import coordinator.shared.avro.messages.headers.Headers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import static java.util.Objects.requireNonNull;

public class AvroSerializer {
    public static <T> byte[] serialize(Message<T> message) {
        requireNonNull(message);
        
        AvroMessageConverter<T> messageConverter = new AvroMessageConverter<>();
        DatumWriter<AvroMessage> datumWriter = new SpecificDatumWriter<>(AvroMessage.getClassSchema());
        AvroMessage avroMessage = messageConverter.convert(message);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            datumWriter.write(avroMessage, encoder);
            encoder.flush();
            out.close();
            return out.toByteArray();
        } 
        catch (Exception e) {
            throw new RuntimeException("Error serializing message", e);
        }
    }
    
    public static <T> Message<T> deserialize(byte[] bytes) {
        requireNonNull(bytes);

        AvroMessageConverter<T> messageConverter = new AvroMessageConverter<>();
        Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
        DatumReader<AvroMessage> datumReader = new SpecificDatumReader<>(AvroMessage.getClassSchema());

        try {
            AvroMessage avroMessage = datumReader.read(null, decoder);
            return messageConverter.revert(avroMessage);
        } 
        catch (IOException e) {
            throw new RuntimeException("Error deserializing message", e);
        }
    }

    public static <T> Headers deserializeHeaders(byte[] bytes) {
        requireNonNull(bytes);

        AvroMessageConverter<T> messageConverter = new AvroMessageConverter<>();
        Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
        DatumReader<AvroMessage> datumReader = new SpecificDatumReader<>(AvroMessage.getClassSchema());

        try {
            AvroMessage avroMessage = datumReader.read(null, decoder);
            return messageConverter.revertHeaders(avroMessage);
        } 
        catch (IOException e) {
            throw new RuntimeException("Error deserializing headers", e);
        }
    }

    public static <T> T deserializePayload(byte[] bytes) {
        requireNonNull(bytes);
        
        AvroMessageConverter<T> messageConverter = new AvroMessageConverter<>();
        Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
        DatumReader<AvroMessage> datumReader = new SpecificDatumReader<>(AvroMessage.getClassSchema());

        try {
            AvroMessage avroMessage = datumReader.read(null, decoder);
            return messageConverter.revertPayload(avroMessage).get();
        } 
        catch (IOException e) {
            throw new RuntimeException("Error deserializing message", e);
        }
    }
}
