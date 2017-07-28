import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {

	private TreeSet<Point2D> points;

	public PointSET() // construct an empty set of points
	{
		points = new TreeSet<>();
	}

	public boolean isEmpty() // is the set empty?
	{
		return points.isEmpty();
	}

	public int size() // number of points in the set
	{
		return points.size();
	}

	public void insert(Point2D p) // add the point to the set (if it is not
									// already in the set)
	{
		if (p == null)
			throw new IllegalArgumentException();

		if (contains(p))
			return;

		points.add(p);
	}

	public boolean contains(Point2D p) // does the set contain point p?
	{
		if (p == null)
			throw new IllegalArgumentException();

		return points.contains(p);
	}

	public void draw() // draw all points to standard draw
	{
		for (Point2D point : points) {
			point.draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) // all points that are inside
												// the rectangle
	{
		if (rect == null)
			throw new IllegalArgumentException();

		List<Point2D> pointList = new ArrayList<>();
		for (Point2D point : points) {
			if (rect.contains(point))
				pointList.add(point);
		}
		return pointList;
	}

	public Point2D nearest(Point2D p) // a nearest neighbor in the set to point
										// p; null if the set is empty
	{
		if (p == null)
			throw new IllegalArgumentException();

		Point2D nearest = null;
		double dis = Double.MAX_VALUE;
		for (Point2D point : points) {
			double temp = p.distanceTo(point);
			if (temp < dis) {
				nearest = point;
				dis = temp;
			}
		}

		return nearest;
	}

	public static void main(String[] args) // unit testing of the methods
											// (optional)
	{

	}

}
