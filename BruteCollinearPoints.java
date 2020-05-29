import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {


    private ArrayList<LineSegment> ls = new ArrayList<>();


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }
        if (Arrays.asList(points).contains(null)) {
            throw new IllegalArgumentException("your array cant contain an element that is null");
        }
        int n = points.length;
        // FIRST we sort the array of points
        Arrays.sort(points);


        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY
                                || points[i].slopeTo(points[k]) == Double.NEGATIVE_INFINITY ||
                                points[i].slopeTo(points[l]) == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException(
                                    "Cant provide the same point to the BCP constructor");
                        }
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[j]) == points[i]
                                .slopeTo(points[l])) {
                            // found a lineSegment!
                            LineSegment seg = new LineSegment(points[i], points[l]);
                            ls.add(seg);

                        }

                    }

                }
            }

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return ls.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] seg = ls.toArray(new LineSegment[ls.size()]);

        return seg;
    }


    public static void main(String[] args) {


        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(3, 3);

        Point p5 = new Point(5, 0);
        Point p6 = new Point(5, 0);
        Point p7 = new Point(3, 2);
        Point p8 = new Point(2, 3);

        Point[] pArray = { p1, p2, p3, p4, p5, p6, p7, p8 };
        BruteCollinearPoints bcp = new BruteCollinearPoints(pArray);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 5);
        StdDraw.setYscale(0, 5);
        for (Point p : pArray) {
            p.draw();
        }
        StdDraw.show();

        System.out.println(bcp.numberOfSegments());
        for (LineSegment segment : bcp.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
