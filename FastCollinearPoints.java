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
       /* System.out.println("natural order: ");
        for (Point p : points) {
            System.out.println(p);
        }*/


        ArrayList<Double> seenSlopes = new ArrayList<Double>();

        for (int i = 0; (i + 3) < points.length; i++) {
            //  we sort the points according to their slope relative to the ith point
            Arrays.sort(points, pointsCopy[i].slopeOrder());
          /*  System.out.println("slope order with respect to " + pointsCopy[i]);
            for (Point p : points) {
                System.out.println(p);
            }*/

            seenSlopes.clear();
            //get unique slopes
            for (int j = 0; j < points.length - 2; j++) {

                double justSeen = pointsCopy[i].slopeTo(points[j]);
                if (justSeen == pointsCopy[i].slopeTo(points[j + 1])
                        && justSeen == pointsCopy[i]
                        .slopeTo(points[j + 2])

                ) {
                    if (!(seenSlopes.contains(justSeen))) {
                        //System.out.println("add slope: " + justSeen + " for point: " + pointsCopy[i]);
                        seenSlopes.add(justSeen);
                    }
                }
            }
            //System.out.println("number of slopes:" + seenSlopes.size());
            //what is the maximum corresponding to this slope?
            for (double slope : seenSlopes) {
                Point maximum = pointsCopy[i];
                Point minimum = pointsCopy[i];
                int counter = 0;


                for (int k = 0; k < points.length;
                     k++) {


                    if (pointsCopy[i].slopeTo(points[k]) == slope
                    ) {

                        ++counter;
                        if (points[k].compareTo(maximum) > 0) {
                            maximum = points[k];

                        }

                        if (points[k].compareTo(minimum) < 0) {
                            minimum = points[k];

                        }
                    }
                    //System.out.println("counter is: " + counter);

                }
                if (counter > 2) {

                    // is the points array currently sorted with respect to minimum :=) ?

                    // check that the current origin is also the minimum
                    if (pointsCopy[i].equals(minimum)) {

                        LineSegment l = new LineSegment(minimum, maximum);
                        ls.add(l);
                        // System.out.println("counter is: " + counter);
                        //System.out.println("added segment " + minimum + "-> " + maximum);

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
