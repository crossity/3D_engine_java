public class Vec3 {
    public float x, y, z;
    Vec3() {
        this.x = 0.f;
        this.y = 0.f;
        this.z = 0.f;
    }
    Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vec3(Vec3 a) {
        this.x = a.x;
        this.y = a.y;
        this.z = a.z;
    }

    static public Vec3 add(Vec3 a, Vec3 b) {
        return new Vec3(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    static public Vec3 sub(Vec3 a, Vec3 b) {
        return new Vec3(a.x - b.x, a.y - b.y, a.z - b.z);
    }
    static public Vec3 div(Vec3 a, float b) {
        return new Vec3(a.x / b, a.y / b, a.z / b);
    }
    static public Vec3 mul(Vec3 a, float b) {
        return new Vec3(a.x * b, a.y * b, a.z * b);
    }
    static public float dot_product(Vec3 a, Vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    static public Vec3 rotate_y(Vec3 p_ptr, float angle, Vec3 c) {
        Vec3 p = new Vec3(p_ptr.x, p_ptr.y, p_ptr.z);

        float sn = (float)Math.sin(angle);
        float cs = (float)Math.cos(angle);

        p.z -= c.z;
        p.x -= c.x;

        float znew = p.z * cs - p.x * sn;
        float xnew = p.z * sn + p.x * cs;

        p.x = xnew + c.x;
        p.z = znew + c.z;

        return new Vec3(p.x, p.y, p.z);
    }
    static public Vec3 rotate_x(Vec3 p_ptr, float angle, Vec3 c) {
        Vec3 p = new Vec3(p_ptr.x, p_ptr.y, p_ptr.z);
        float sn = (float)Math.sin(angle);
        float cs = (float)Math.cos(angle);

        p.z -= c.z;
        p.y -= c.y;

        float znew = p.z * cs - p.y * sn;
        float ynew = p.z * sn + p.y * cs;

        p.y = ynew + c.y;
        p.z = znew + c.z;
        return new Vec3(p.x, p.y, p.z);
    }

    public static float dist(Vec3 a, Vec3 b) {
        float x = a.x - b.x;
        float y = a.y - b.y;
        float z = a.z - b.z;
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public Vec3 norm() {
        return Vec3.div(this, dist(this, new Vec3()));
    }

    static public Vec3 to_camera(Vec3 p, Camera camera) {
        Vec3 np = Vec3.sub(p, camera.pos);
        np = Vec3.rotate_y(np, -camera.rot.y, new Vec3());
        np = Vec3.rotate_x(np, -camera.rot.x, new Vec3());
        return np;
    }
}