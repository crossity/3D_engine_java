import java.awt.Color;

public class Triangle {
    public Vec3[] p;
    public Vec3 color;

    Triangle() {
        this.p = new Vec3[3];
        this.color = new Vec3(255, 255, 255);
    }
    Triangle(Vec3[] p, Vec3 color) {
        this.p = new Vec3[3];
        for (int i = 0; i < 3; i++) {
            this.p[i] = new Vec3(p[i]);
        }
        this.color = new Vec3(color);
    }
    Triangle(Vec3 p1, Vec3 p2, Vec3 p3, Vec3 color) {
        this.p = new Vec3[3];
        p[0] = new Vec3(p1);
        p[1] = new Vec3(p2);
        p[2] = new Vec3(p3);
        this.color = new Vec3(color);
    }
    Triangle(Triangle t) {
        this.p = new Vec3[3];
        for (int i = 0; i < 3; i++) {
            this.p[i] = new Vec3(t.p[i]);
        }
        this.color = new Vec3(t.color);
    }

    public Vec3 center() {
        Vec3 ret = new Vec3();
        for (int i = 0; i < 3; i++) {
            ret.x += p[i].x;
            ret.y += p[i].y;
            ret.z += p[i].z;
        }
        ret = Vec3.div(ret, 3.f);
        return ret;
    }

    public float sqr_dist(Vec3 a) {
        Vec3 c = this.center();
        float x = a.x - c.x;
        float y = a.y - c.y;
        float z = a.z - c.z;
        return x*x + y*y + z*z;
    }

    public Vec3 normal() {
        Vec3 u = new Vec3(), v = new Vec3();
        u = Vec3.sub(this.p[1], this.p[0]);
        v = Vec3.sub(this.p[2], this.p[0]);

        Vec3 n = new Vec3();
        n.x = u.y * v.z - u.z * v.y;
        n.y = u.z * v.x - u.x * v.z;
        n.z = u.x * v.y - u.y * v.x;

        return n;
    }

    static public Triangle to_camera(Triangle t, Camera camera) {
        Triangle nt = new Triangle(t);
        for (int i = 0; i < 3; i++)
            nt.p[i] = Vec3.to_camera(nt.p[i], camera);
        return nt;
    }
}