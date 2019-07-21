package coordinator.shared.avro.converters;

/**
 * Two-way conversion contract. Converts from {@link F} to {@link T} and the other way around.
 * @param <F> Convert objects of this type
 * @param <T> To objects of this type (and vice-versa)
 */
interface Converter<F, T> {
    T convert(F toConvert);
    F revert(T toReverse);
}
