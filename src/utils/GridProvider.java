package utils;

@FunctionalInterface
public interface GridProvider<T> {
    public T provide(int x, int y);
}
