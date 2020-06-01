import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> ls = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        //double currentSlope = 0;
        ArrayList<Double> seenslopes = new ArrayList<>();
        for (int i = 0; (i + 3) < points.length; i++) {
            //  we sort the points according to their slope relative to the ith point
            Arrays.sort(points, pointsCopy[i].slopeOrder());
            // Arrays.sort(points);

            // if (Arrays.equals(points, pointsCopy)) {
            System.out.println("Slope order with respect to point: " + pointsCopy[i]);
            for (Point p : points) {
                System.out.println(p);
            }

            int counter = 0;
            Point maximum = new Point(0, 0);
            for (int j = 0; j < points.length - 3; j++) {

                if (pointsCopy[i].slopeTo(points[j + 1]) == pointsCopy[i].slopeTo(points[j + 2])
                        && pointsCopy[i].slopeTo(points[j + 1]) == pointsCopy[i]
                        .slopeTo(points[j + 3])) {
                    System.out.println("origin: " + pointsCopy[i]);

                    // Check if any 3 (or more) adjacent points in the sorted order
                    // have equal slopes with respect to p. If so, these points, together with p, are collinear.
                    ++counter;
                    Point[] pClone = Arrays
                            .copyOfRange(points, j + 1,
                                         j + 4);  // clone the array between i and j+4
                    Arrays.sort(pClone);
                    System.out.println("sorted: ");
                    for (Point p : pClone) {
                        System.out.println(p);
                    }

                    if (pClone[pClone.length - 1].compareTo(maximum) > 0) {
                        maximum = pClone[pClone.length - 1];
                    }
                    // get the maximum point according to natural order and then create a segment from the origin to it below
                    System.out.println(
                            points[i] + " slope to " + (points[j + 1]) + "= " + points[i]
                                    + "slope to" + (points[j + 2]) + "=" +
                                    points[i].slopeTo(points[j + 1]) +
                                    "&& " + points[i] + "slope to " + (points[j + 1]) + "= "
                                    + points[i]
                                    + "slope to" + (points[j + 3]) + "slope: " + points[i]
                                    .slopeTo(points[j + 1]));


                }


            }

            System.out.println("segment between" + pointsCopy[i] + "-> " + maximum);


            double seenSlope = pointsCopy[i].slopeTo(maximum);
            boolean test = seenSlope == Double.POSITIVE_INFINITY;

            if (counter > 0 && (!seenslopes
                    .contains(seenSlope))) {

                //case with slope 0:
                //if the smallest point is smaller than

                LineSegment seg = new LineSegment(pointsCopy[i], maximum);
                ls.add(seg);
                if (seenSlope != 0 && seenSlope != Double.POSITIVE_INFINITY)
                    seenslopes.add(seenSlope);


            }

        }
    }


    //}

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
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(255, 0, 0);
        StdDraw.setPenRadius(0.02);
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
        StdDraw.show();
    }
}
