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
}