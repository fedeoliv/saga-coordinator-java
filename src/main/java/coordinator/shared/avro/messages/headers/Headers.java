package coordinator.shared.avro.messages.headers;

import org.apache.commons.lang3.tuple.Pair;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * Headers to be used by the {@link Message} class. It stores key-value pairs (both represented by {@link String}s for
 * simplicity).
 */
public class Headers {
    private final Map<String, String> headersMap;

    public Headers() {
        this(emptyList());
    }

    public Headers(Collection<Pair<String, String>> keyValuePairs) {
        this.headersMap = keyValuePairs.stream().collect(toMap(Pair::getKey, Pair::getValue));
    }

    public void put(String key, String value) {
        headersMap.put(requireNonNull(key), requireNonNull(value));
    }

    public Optional<String> get(String key) {
        return ofNullable(headersMap.get(key));
    }

    public Collection<Pair<String, String>> getAll() {
        return headersMap
            .entrySet().stream()
            .map(e -> Pair.of(e.getKey(), e.getValue()))
            .collect(toSet());
    }

    public Optional<String> remove(String key) {
        return ofNullable(headersMap.remove(key));
    }
}
