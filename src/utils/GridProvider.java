package utils;

@FunctionalInterface
public interface GridProvider<T> {
    T provide(int x, int y);
}
