package game;

public interface PowerUp {
    void apply(Entity p);
    void step(Entity p);
    boolean isActive(Entity p);
}
