package primitives;

import java.util.ArrayList;

import structures.Box;
import structures.IntersectionInfo;
import structures.Ray;
import structures.Vector3;

public class BVHTreeNode extends Primitive {

	private Primitive left;
	private Primitive right;

	protected BVHTreeNode(Box box, Primitive left, Primitive right) {
		super(box);
		this.left = left;
		this.right = right;
		this.boundingBox = box;
	}

	@Override
	public IntersectionInfo intersect(Ray theRay) {
		// TODO Auto-generated method stub
		if (this.boundingBox.isHit(theRay) == false) {
			return new IntersectionInfo(Double.POSITIVE_INFINITY, null, null);
		}

		IntersectionInfo leftIntersect = null;
		IntersectionInfo rightIntersect = null;

		double distLeft, distRight;

		if (left != null) {
			leftIntersect = left.intersect(theRay);
		}
		if (right != null) {
			rightIntersect = right.intersect(theRay);
		}
		if (leftIntersect.IntersectionPoint != null
				&& rightIntersect.IntersectionPoint != null) {
			distLeft = leftIntersect.IntersectionPoint.subtract(theRay.origin)
					.getLength();
			distRight = rightIntersect.IntersectionPoint
					.subtract(theRay.origin).getLength();
			if (Double.compare(distLeft, distRight) < 0) {
				return leftIntersect;
			} else {
				return rightIntersect;
			}
		} else if (leftIntersect.IntersectionPoint != null) {
			return leftIntersect;
		} else if (rightIntersect.IntersectionPoint != null) {
			return rightIntersect;
		} else {
			return new IntersectionInfo(Double.POSITIVE_INFINITY, null, null);
		}
	}

	public static BVHTreeNode createBVHTree(ArrayList<Primitive> primitives,
			int axis) {
		int size = primitives.size();
		if (size == 0) {
			return null;
		} else if (size == 1) {
			Primitive first = primitives.get(0);
			return new BVHTreeNode(first.getBoundingBox(), first, null);
		} else if (size == 2) {
			Primitive first = primitives.get(0);
			Primitive second = primitives.get(1);
			Box boundBox = first.getBoundingBox().combine(
					second.getBoundingBox());
			return new BVHTreeNode(boundBox, first, second);
		} else {
			Box boundBox = Box.combineBoundingBoxes(primitives);
			Vector3 mid = boundBox.getMidPoint();
			ArrayList<Primitive> leftList = new ArrayList<Primitive>();
			ArrayList<Primitive> rightList = new ArrayList<Primitive>();
			Box.partition(primitives, mid, axis, leftList, rightList);
			BVHTreeNode leftNode = createBVHTree(leftList, (axis + 1) % 3);
			BVHTreeNode rightNode = createBVHTree(rightList, (axis + 1) % 3);
			return new BVHTreeNode(boundBox, leftNode, rightNode);
		}
	}

}
