import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> ls = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(final Point[] realPoints) {
        if (realPoints == null) {
            throw new IllegalArgumentException();
        }
        Point[] points = realPoints.clone();

        if (Arrays.asList(points).contains(null)) {
            throw new IllegalArgumentException("your array cant contain an element that is null");
        }


        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        Arrays.sort(points, pointsCopy[0].slopeOrder());
        //for (int u = 0; u < points.length - 1; u++) {

        for (int u = 0; u < points.length - 1; u++) {
            if (points[u].slopeTo(points[u + 1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException("FCP Cant have duplicate points!");
            }
        }
        for (int i = 0; i < points.length; i++) {
            //  we sort the points according to their slope relative to the ith point
            Arrays.sort(points, pointsCopy[i].slopeOrder());


            double currentSlope = Double.MIN_VALUE;
            for (int j = 0; j < points.length - 3; j++) {

                double justSeen = pointsCopy[i].slopeTo(points[j + 1]);

                if (justSeen == Double.NEGATIVE_INFINITY
                        || pointsCopy[i].slopeTo(points[j + 2]) == Double.NEGATIVE_INFINITY ||
                        pointsCopy[i].slopeTo(points[j + 3]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException(
                            "Cant provide the same point to the FCP constructor");
                }
                if (justSeen == pointsCopy[i]
                        .slopeTo(points[j + 2]) && justSeen == pointsCopy[i]
                        .slopeTo(points[j + 3]) && justSeen != currentSlope

                ) {

                    Point minimum = pointsCopy[i];
                    int k = j + 1;
                    // System.out.println("justseen is: ");
                    Point maximum = points[j + 1];
                    while (pointsCopy[i].slopeTo(points[k]) == justSeen
                    ) {


                        if (points[k].compareTo(minimum) < 0) minimum = points[k];
                        if (points[k].compareTo(maximum) > 0) maximum = points[k];

                        ++k;

                        if (k == points.length) break;

                    }


                    currentSlope = justSeen;

                    // is the points array currently sorted with respect to minimum :=) ?

                    // check that the current origin is also the minimum
                    if (pointsCopy[i].equals(minimum)) {

                        LineSegment l = new LineSegment(minimum, maximum);
                        ls.add(l);

                    }

                }

            }
            //System.out.println("number of slopes:" + seenSlopes.size());
            //what is the maximum corresponding to this slope?

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);

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
