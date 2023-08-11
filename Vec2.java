public class Vec2 {
    public float x, y;
    Vec2() {
        this.x = 0.f;
        this.y = 0.f;
    }
    Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    Vec2(Vec2 a) {
        this.x = a.x;
        this.y = a.y;
    }
    public boolean zero_vector() {
        return this.x == 0 && this.y == 0;
    }

    static public Vec2 add(Vec2 a, Vec2 b) {
        return new Vec2(a.x + b.x, a.y + b.y);
    }
    static public Vec2 sub(Vec2 a, Vec2 b) {
        return new Vec2(a.x - b.x, a.y - b.y);
    }
}