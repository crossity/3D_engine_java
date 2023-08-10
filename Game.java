// Main File you can write code in, in update function

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends MyPanel{
    public Mesh cube_mesh;
    public Camera camera;

    Vec3 light;

    Game() {
        super();
        cube_mesh = new Mesh();
        cube_mesh.open_obj("models/box.obj");
        camera = new Camera(WIDTH, HEIGHT, new Vec3(0, 0, -4.f), new Vec3(0, 0, 0));
        light = new Vec3(0, 0, 1);
    }

    @Override
    void update(Graphics2D g2d) {
        System.out.println("fps: " + fps);

        // Rotating cube
        for (int i = 0; i < cube_mesh.m.size(); i++) {
            Triangle tri = cube_mesh.m.get(i);
            for (int j = 0; j < 3; j++) {
                tri.p[j] = Vec3.rotate_y(tri.p[j], 0.002f * delta, new Vec3());
                cube_mesh.m.set(i, tri);
            }
        }

        // Main mesh rendering to screen
        cube_mesh.heapSort(cube_mesh.m.size(), camera);

        Mesh clipped_mesh = new Mesh();
        for (int i = 0; i < cube_mesh.m.size(); i++)
            Mesh.z_clip(clipped_mesh, cube_mesh.m.get(i), camera);

        for (Triangle tri : clipped_mesh.m) {
            Vec3 n = tri.normal().norm();
            Vec3 camera_ray = Vec3.sub(tri.center(), camera.pos);
            if (Vec3.dot_product(n, camera_ray) < 0.f) {
                Vec2[] ps = new Vec2[3];

                for (int i = 0; i < 3; i++)
                    ps[i] = camera.render(tri.p[i]);

                Color lighted_color;
                float dp = map(Vec3.dot_product(Vec3.mul(light, -1.f), n), -1.f, 1.f, 0.f, 1.f);
                dp = Math.min(dp, 1.f);
                dp = Math.max(dp, 0.f);
                Vec3 lc = new Vec3(tri.color);
                lc = Vec3.mul(lc, dp);
                lighted_color = new Color((int)lc.x, (int)lc.y, (int)lc.z);

                g2d.setPaint(lighted_color);
                fillTriangle(g2d, ps[0], ps[1], ps[2]);
            }
        }
    }

    float map(float x, float a, float b, float c, float d) {
        return (x-a)/(b-a) * (d-c) + c;
    }
}