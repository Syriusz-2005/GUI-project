package utils;

@FunctionalInterface
public interface GridElementConsumer<T> {
    boolean consume(Vec2i p, T element, int i);
}
