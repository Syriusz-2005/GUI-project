package utils;

import java.lang.reflect.Array;
import java.util.function.Function;

public class Grid<T> {
    private final T[][] grid;
    private final int width;
    private final int height;

    public Grid(Class<T> c, int width, int height) {
        grid = (T[][]) Array.newInstance(c, height, width);
        this.width = width;
        this.height = height;
    }

    public T[][] getGrid() {
        return grid;
    }

    public T get(int x, int y) {
        return grid[y][x];
    }
    public T get(Vec2i v) {
        return grid[v.y][v.x];
    }

    public void set(int x, int y, T v) {
        grid[y][x] = v;
    }

    public Grid<T> fill(GridProvider<T> provider) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                set(x, y, provider.provide(x, y));
            }
        }
        return this;
    }

    public Vec2i walk(Vec2i from, Vec2i dir, GridElementConsumer<T> next) {
        var curr = from.clone();
        int i = 0;
        do {
            curr.add(dir);
            if (curr.x < 0 || curr.y < 0 || curr.x >= width || curr.y >= height) break;
            i++;
        } while(next.consume(curr, get(curr), i));
        return curr.subtract(dir);
    }
}
