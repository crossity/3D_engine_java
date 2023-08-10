import java.util.ArrayList;

import java.io.File;
import java.util.Scanner;

import java.awt.Color;

public class Mesh {
    ArrayList<Triangle> m;

    Mesh() {
        m = new ArrayList<Triangle>();
    }

    public void open_obj(String name) {
        try {
            ArrayList<Vec3> points = new ArrayList<Vec3>();
            File file = new File(name);
            Scanner file_scan = new Scanner(file);
            while (file_scan.hasNextLine()) {
                String line = file_scan.nextLine();
                if (line.charAt(0) == 'v') {
                    line = line.substring(2, line.length());

                    float[] point = new float[3];
                    for (int i = 0; i < 3; i++) {
                        String num = new String();
                        int j = 0;
                        for (; j < line.length() && line.charAt(j) != ' '; j++)
                            num += line.charAt(j);
                        if (i != 2)
                            line = line.substring(j + 1, line.length());
                        point[i] = new Float(num);
                    }
                    points.add(new Vec3(point[0], point[1], point[2]));
                }
                else if (line.charAt(0) == 'f') {
                    line = line.substring(2, line.length());

                    Triangle tri = new Triangle();
                    for (int i = 0; i < 3; i++) {
                        String num = new String();
                        int j = 0;
                        for (; j < line.length() && line.charAt(j) != ' '; j++)
                            num += line.charAt(j);
                        if (i != 2)
                            line = line.substring(j + 1, line.length());
                        tri.p[i] = points.get((new Integer(num)) - 1);
                    }
                    m.add(tri);
                }
            }
        }
        catch (Exception e) {
            System.out.println("No such file");
        }
    }

    public void colorize(Vec3 color) {
        for (int i = 0; i < m.size(); i++) {
            Triangle tri = m.get(i);
            m.set(i, new Triangle(tri.p, color));
        }
    }

    private void heapify(int N, int i, Camera camera) {
        // Find largest among root, left child and right child
     
        // Initialize largest as root
        int largest = i;
     
        // left = 2*i + 1
        int left = 2 * i + 1;
     
        // right = 2*i + 2
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < N && this.m.get(left).sqr_dist(camera.pos) < this.m.get(largest).sqr_dist(camera.pos))
     
            largest = left;
     
        // If right child is larger than largest
        // so far
        if (right < N && this.m.get(right).sqr_dist(camera.pos) < this.m.get(largest).sqr_dist(camera.pos))
     
            largest = right;
     
        // Swap and continue heapifying if root is not largest
        // If largest is not root
        if (largest != i) {
            {
                Triangle t = this.m.get(i);
                this.m.set(i, this.m.get(largest));
                this.m.set(largest, t);
            }
     
            // Recursively heapify the affected
            // sub-tree
            heapify(N, largest, camera);
        }
    }
 
    // Main function to do heap sort
    public void heapSort(int N, Camera camera) {
     
        // Build max heap
        for (int i = N / 2 - 1; i >= 0; i--)
     
            heapify(N, i, camera);
     
        // Heap sort
        for (int i = N - 1; i >= 0; i--) {
            {
                Triangle t = this.m.get(0);
                this.m.set(0, this.m.get(i));
                this.m.set(i, t);
            }
     
            // Heapify root element to get highest element at
            // root again
            heapify(i, 0, camera);
        }
    }

    public static void z_clip(Mesh output, Triangle old_t, Camera camera) {
      short clip_points = 0;
      int[] cliped_points = new int[3];
      Triangle t = Triangle.to_camera(old_t, camera);
      for (int i = 0; i < 3; i++) {
        if (t.p[i].z < camera.n_plane) {
          cliped_points[clip_points] = i;
          clip_points++;
        }
      }
      if (clip_points == 0)
        output.m.add(t);
      else if (clip_points == 1) {
        Vec3 p1 = new Vec3(), p2 = new Vec3();
        Triangle ret1 = new Triangle(), ret2 = new Triangle();
        float kx1, ky1, kx2, ky2;
        float bx1, by1, bx2, by2;
        kx1 = (t.p[cliped_points[0]].x - t.p[(cliped_points[0] + 1) % 3].x)/(t.p[cliped_points[0]].z - t.p[(cliped_points[0] + 1) % 3].z);
        bx1 = t.p[cliped_points[0]].x - kx1 * t.p[cliped_points[0]].z;
        ky1 = (t.p[cliped_points[0]].y - t.p[(cliped_points[0] + 1) % 3].y)/(t.p[cliped_points[0]].z - t.p[(cliped_points[0] + 1) % 3].z);
        by1 = t.p[cliped_points[0]].y - ky1 * t.p[cliped_points[0]].z;
        p1.z = camera.n_plane;
        p1.x = kx1 * p1.z + bx1;
        p1.y = ky1 * p1.z + by1;

        kx2 = (t.p[cliped_points[0]].x - t.p[(cliped_points[0] + 2) % 3].x)/(t.p[cliped_points[0]].z - t.p[(cliped_points[0] + 2) % 3].z);
        bx2 = t.p[cliped_points[0]].x - kx2 * t.p[cliped_points[0]].z;
        ky2 = (t.p[cliped_points[0]].y - t.p[(cliped_points[0] + 2) % 3].y)/(t.p[cliped_points[0]].z - t.p[(cliped_points[0] + 2) % 3].z);
        by2 = t.p[cliped_points[0]].y - ky2 * t.p[cliped_points[0]].z;
        p2.z = camera.n_plane;
        p2.x = kx2 * p2.z + bx2;
        p2.y = ky2 * p2.z + by2;

        output.m.add(new Triangle(p1, t.p[(cliped_points[0] + 1) % 3], t.p[(cliped_points[0] + 2) % 3], t.color));
        output.m.add(new Triangle(p1, t.p[(cliped_points[0] + 2) % 3], p2, t.color));
      }
      else if (clip_points == 2) {
        Vec3 p1 = new Vec3(), p2 = new Vec3();
        Vec3 p3 = new Vec3(t.p[3 - cliped_points[0] - cliped_points[1]]);
        
        Triangle ret = new Triangle();

        float kx1, ky1, kx2, ky2;
        float bx1, by1, bx2, by2;

        kx1 = (t.p[cliped_points[0]].x - p3.x)/(t.p[cliped_points[0]].z - p3.z);
        bx1 = p3.x - kx1 * p3.z;
        ky1 = (t.p[cliped_points[0]].y - p3.y)/(t.p[cliped_points[0]].z - p3.z);
        by1 = p3.y - ky1 * p3.z;

        p1.z = camera.n_plane;
        p1.x = kx1 * p1.z + bx1;
        p1.y = ky1 * p1.z + by1;

        kx2 = (t.p[cliped_points[1]].x - p3.x)/(t.p[cliped_points[1]].z - p3.z);
        bx2 = p3.x - kx2 * p3.z;
        ky2 = (t.p[cliped_points[1]].y - p3.y)/(t.p[cliped_points[1]].z - p3.z);
        by2 = p3.y - ky2 * p3.z;

        p2.z = camera.n_plane;
        p2.x = kx2 * p2.z + bx2;
        p2.y = ky2 * p2.z + by2;

        ret.p[3 - cliped_points[0] - cliped_points[1]] = p3;
        ret.p[cliped_points[0]] = p1;
        ret.p[cliped_points[1]] = p2;

        ret.color = new Vec3(t.color);

        output.m.add(ret);
      }
      // else
      //   output.push_back(t);
    }
}