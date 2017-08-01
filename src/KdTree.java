import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;

    private class Node {
        private Point2D key;
//        private Node father; // father node
        private Node left, right;
        private int N;
        private int D; // the depth of the node
        private RectHV rect; // the rectangle represented by the node

        public Node(Node father, Point2D p, int N) {
//            this.father = father;
            key = p;
            this.N = N;

            if (father == null) {
                D = 1;
                rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            } else {
                D = father.D + 1;
                if (D % 2 == 1) {
                    if (key.y() < father.key.y())
                        rect = new RectHV(father.rect.xmin(), father.rect.ymin(), father.rect.xmax(), father.key.y());
                    else
                        rect = new RectHV(father.rect.xmin(), father.key.y(), father.rect.xmax(), father.rect.ymax());
                } else {
                    if (key.x() < father.key.x())
                        rect = new RectHV(father.rect.xmin(), father.rect.ymin(), father.key.x(), father.rect.ymax());
                    else
                        rect = new RectHV(father.key.x(), father.rect.ymin(), father.rect.xmax(), father.rect.ymax());
                }
            }
        }
    }

    public KdTree()                      // construct an empty set of points
    {
    }

    public boolean isEmpty()             // is the set empty?
    {
        return root == null;
    }

    public int size()                   // number of points in the set
    {
        return size(root);
    }

    public void insert(Point2D p)       // add the point to the set (if it is not already in the set)
    {
        root = insert(root, null, p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return contains(root, p);
    }

    public void draw()                         // draw all points to standard draw
    {
        draw(root);
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        List<Point2D> plist = new ArrayList<>();
        range(rect, root, plist);
        return plist;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        Point2D best = root.key;
        best = nearest(p, root, best);
        return best;
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    private Node insert(Node x, Node xfather, Point2D p) {
        if (x == null)
            return new Node(xfather, p, 1);
        if (p.equals(x.key))
            return x;
        if (x.D % 2 == 1) {
            if (p.x() < x.key.x()) {
                x.left = insert(x.left, x, p);
            } else {
                x.right = insert(x.right, x, p);
            }
        } else {
            if (p.y() < x.key.y()) {
                x.left = insert(x.left, x, p);
            } else {
                x.right = insert(x.right, x, p);
            }
        }
        x.N = size(x.left) + size(x.right) + 1;
        // x.D = x.father.D + 1;
        return x;
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null)
            return false;
        if (p.equals(x.key))
            return true;
        if (x.D % 2 == 1) {
            if (p.x() < x.key.x()) {
                return contains(x.left, p);
            } else {
                return contains(x.right, p);
            }
        } else {
            if (p.y() < x.key.y()) {
                return contains(x.left, p);
            } else {
                return contains(x.right, p);
            }
        }
    }

    private void draw(Node x) {
        if (x == null)
            return;

        // use inorder traversal to draw the tree
        draw(x.left);
        if (x.D % 2 == 1) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.key.x(), x.rect.ymin(), x.key.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.key.y(), x.rect.xmax(), x.key.y());
        }
        draw(x.right);
    }

    private void range(RectHV rect, Node x, List<Point2D> plist) {
        if (rect.contains(x.key))
            plist.add(x.key);
        if (x.left != null) {
            if (x.left.rect.intersects(rect))
                range(rect, x.left, plist);
        }
        if (x.right != null) {
            if (x.right.rect.intersects(rect))
                range(rect, x.right, plist);
        }
    }

    private Point2D nearest(Point2D p, Node x, Point2D best) {
        if (p.distanceSquaredTo(x.key) < p.distanceSquaredTo(best))
            best = p;
//        double shortest = p.distanceSquaredTo(best);
        if (x.D % 2 == 1) {
            if (p.x() < x.key.x()) {
                // search left subtree first
                if (x.left != null && x.left.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
                    best = nearest(p, x.left, best);
                if (x.right != null && x.right.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
                    best = nearest(p, x.right, best);
            } else {
                // search right subtree first
                if (x.right != null && x.right.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
                    best = nearest(p, x.right, best);
                if (x.left != null && x.left.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
                    best = nearest(p, x.left, best);
            }
        } else {
            if (p.y() < x.key.y()) {
                // search left subtree first
                if (x.left != null && x.left.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
                    best = nearest(p, x.left, best);
                if (x.right != null && x.right.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
                    best = nearest(p, x.right, best);
            } else {
                // search right subtree first
                if (x.right != null && x.right.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
                    best = nearest(p, x.right, best);
                if (x.left != null && x.left.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
                    best = nearest(p, x.left, best);
            }
        }
        return best;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {

    }

}
