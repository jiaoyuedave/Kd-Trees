import edu.princeton.cs.algs4.Point2D;

public class KdTree {
	
	private Node root;

	private class Node {
		private Point2D key;
		private Node left, right;
		private int N;
	}

	public KdTree() // construct an empty set of points
	{
		
	}

	public boolean isEmpty() // is the set empty?
	{
		return root == null;
	}

	public int size() // number of points in the set
	{
		return size(root);
	}
	   
	   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	   public           boolean contains(Point2D p)            // does the set contain point p? 
	   public              void draw()                         // draw all points to standard draw 
	   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
	   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	   
	   private int size(Node x) {
		   if (x == null)
			   return 0;
		   else 
			   return x.N;
	   }

	   public static void main(String[] args)                  // unit testing of the methods (optional) 
}
