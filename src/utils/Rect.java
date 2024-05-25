package utils;

public class Rect {
    public final Vec2f pos;
    public final Vec2f size;

    public Rect(Vec2f pos, Vec2f size) {
        this.pos = pos;
        this.size = size;
    }

    public Vec2f getCorner() {
        return pos.clone().add(size);
    }

    public boolean contains(Vec2f p) {
        var corner = getCorner();
        return pos.x <= p.x && corner.x >= p.x && pos.y <= p.y && corner.y >= p.y;
    }
}
