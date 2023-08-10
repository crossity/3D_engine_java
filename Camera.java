public class Camera {
    public Vec3 pos;
    public Vec3 rot;
    public float f_plane, n_plane;
    public int height, width;

    Camera(int height, int width) {
        this.pos = new Vec3();
        this.rot = new Vec3();
        this.height = height;
        this.width = width;
        this.f_plane = 1000.f;
        this.n_plane = 0.1f;
    }
    Camera(int width, int height, Vec3 pos, Vec3 rot) {
        this.pos = new Vec3(pos);
        this.rot = new Vec3(rot);
        this.height = height;
        this.width = width;
        this.f_plane = 1000.f;
        this.n_plane = 0.1f;
    }

    Vec2 render(Vec3 p) {
        Vec3 np = new Vec3(p);
        if (np.z >= n_plane)
            return new Vec2(
                (np.x / np.z * 0.42f + 0.4998f) * this.width, (0.5002f - np.y / np.z * 0.42f) * this.height
            );
        return new Vec2();
    }
}