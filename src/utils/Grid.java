package utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
        return grid[v.y % height][v.x % width];
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

    public Vec2i getSize() {
        return new Vec2i(width, height);
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

    public ArrayList<T> getNeighbours(Vec2i f) {
        var neighbours = new ArrayList<T>();
        neighbours.add(get(f.clone().add(new Vec2i(1, 0))));
        neighbours.add(get(f.clone().add(new Vec2i(-1, 0))));
        neighbours.add(get(f.clone().add(new Vec2i(0, 1))));
        neighbours.add(get(f.clone().add(new Vec2i(0, -1))));
        return neighbours;
    }

    public ArrayList<Vec2i> getNeighboursPos(Vec2i f) {
        var neighbours = new ArrayList<Vec2i>();
        neighbours.add(f.clone().add(new Vec2i(1, 0)));
        neighbours.add(f.clone().add(new Vec2i(-1, 0)));
        neighbours.add(f.clone().add(new Vec2i(0, 1)));
        neighbours.add(f.clone().add(new Vec2i(0, -1)));
        return neighbours;
    }
}
