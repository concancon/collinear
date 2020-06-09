import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {


    private final ArrayList<LineSegment> ls = new ArrayList<>();


    // finds all line segments containing 4 points
    public BruteCollinearPoints(final Point[] realPoints) {
        if (realPoints == null) {
            throw new IllegalArgumentException();
        }
        Point[] points = realPoints.clone();


        if (Arrays.asList(points).contains(null)) {
            throw new IllegalArgumentException("your array cant contain an element that is null");
        }
        int n = points.length;

        // FIRST we sort the array of points
        Arrays.sort(points);
        for (int u = 0; u < points.length - 1; u++)
            if (points[u].slopeTo(points[u + 1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException(
                        "Cant provide the same point to the BCP constructor");
            }

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {

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
        LineSegment[] seg = ls.toArray(new LineSegment[ls.size()]).clone();

        return seg;
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32797);
        StdDraw.setYscale(0, 32797);
        StdDraw.setPenColor(255, 0, 0);
        StdDraw.setPenRadius(0.004);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        StdDraw.setPenColor(1, 1, 1);
        StdDraw.setPenRadius(0.002);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        //System.out.println("number of segments: " + collinear.numberOfSegments());
        StdDraw.show();
    }
}
