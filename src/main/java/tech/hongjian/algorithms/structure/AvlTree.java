package tech.hongjian.algorithms.structure;

import java.util.NoSuchElementException;

/**
 * AVL树，是一种平衡二叉搜索树
 * 
 * @author xiahongjian
 *
 * @param <T>
 */
public class AvlTree<T extends Comparable<? super T>> {
	private static final int ALLOWED_IMBALANCE = 1;
	private AvlNode<T> root;
	private int size;
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public void clear() {
		root = null;
	}
	
	public void insert(T e) {
		root = insert(e, root);
	}
	
	public void remove(T e) {
	    remove(e, root);
	}
	
	public T findMin() {
	    if (isEmpty())
	        throw new NoSuchElementException();
	    return findMin(root).elementData;
	}
	
	public T ifindMax() {
	    if (isEmpty())
	        throw new NoSuchElementException();
	    return findMax(root).elementData;
	}
	
	private AvlNode<T> remove(T e, AvlNode<T> node) {
	    if (node == null)
	        return null;
	    int res = node.elementData.compareTo(e);
	    
	    if (res > 0) {
	        node.left = remove(e, node.left);
	    } else if (res < 0) {
	        node.right = remove(e, node.right);
	    } else if (node.left != null && node.right != null) { 
	        // 该节点是要删除节点，并且左右子树不为空
	        // 在右子树中找到包含最小的值的节点n，将当前节点的值使用n的值替换，
	        // 递归删除节点n（因为n节点的值为当前节点右子树最小值，因此n的左子树必然为空）
	        node.elementData = findMin(node.right).elementData;
	        node.right = remove(node.elementData, node.right);
	    } else {
	        // 该节点为要删除的节点，并且至少有一个为空
	        node = node.left != null ? node.left : node.right;
	    }
	    return balance(node);
	}
	
	private AvlNode<T> insert(T e, AvlNode<T> node) {
		if (node == null)
			return new AvlNode<>(e, null, null);
		
		int res = node.elementData.compareTo(e);
		
		if (res > 0) {
			node.left = insert(e, node.left);
		} else if (res < 0) {
			node.right = insert(e, node.right);
		} else {
			// equals, do nothing
		}
		return balance(node);
	}
	
	/**
	 * 插入节点的情况共有四种
	 * 1. 对n节点的左儿子的左子树进行一次插入（左-左，需要单旋转）
	 * 2. 对n节点的左儿子的右子树进行一次插入（左-右，需要双旋转）
	 * 3. 对n节点的右儿子的左子树进行一次插入（右-左，需要双旋转）
	 * 4. 对n节点的右儿子的右子树进行一次插入（右-右，需要单旋转）
	 */
	private AvlNode<T> balance(AvlNode<T> node) {
		if (node == null)
			return null;
		int leftHeight = height(node.left);
		int rightHeight = height(node.right);
		
		// 左子树高
		if (leftHeight - rightHeight > ALLOWED_IMBALANCE) {
			
			// 情况1
			if (height(node.left.left) >= height(node.left.right)) 	
				node = rotateWithLeftChild(node);
			// 情况2
			else 
				node = doubleWithLeftChild(node);
		}
		// 右子树高
		else if (rightHeight - leftHeight > ALLOWED_IMBALANCE) {
			// 情况4
			if (height(node.right.right) >= height(node.right.left)) 
				node = rotateWithRightChild(node);
			// 情况3
			else
				node = doubleWithRightChild(node);
		}
		
		// 树仍然是平衡的,则不需要旋转了
		
		node.height = Math.max(height(node.right), height(node.left)) + 1;
		return node;
	}
	/**
	 *				node
	 *			    /  \
	 * 			  left  ...
	 * 			/   \
	 * 		   A     B
	 * 将B子树变成node的左孩子，node变成left的右孩子，更新node，和left高度
	 */
	private AvlNode<T> rotateWithLeftChild(AvlNode<T> node) {
		AvlNode<T> left = node.left;
		node.left = left.right;
		left.right = node;
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		left.height = Math.max(height(left.left), node.height) + 1;
		return left;
	}
	
	/**
	 * 			 node
	 * 		    /    \
	 * 		  ...	right
	 * 			    /   \
	 *			   A     B
	 * 将A子树变成node的右孩子，node变成right的左孩子，更新node和right高度 
	 */
	private AvlNode<T> rotateWithRightChild(AvlNode<T> node) {
		AvlNode<T> right = node.right;
		node.right  = right.left;
		right.left = node;
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		right.height = Math.max(node.height, height(right.right)) + 1;
		return right;
	}
	
	private AvlNode<T> doubleWithLeftChild(AvlNode<T> node) {
		node.left = rotateWithRightChild(node.left);
		return rotateWithLeftChild(node); 
	}
	
	private AvlNode<T> doubleWithRightChild(AvlNode<T> node) {
		node.right = rotateWithLeftChild(node.right);
		return rotateWithRightChild(node);
	}
	
	private int height(AvlNode<T> node) {
		return node == null ? -1 : node.height;
	}
	
	private AvlNode<T> findMin(AvlNode<T> node) {
	    if (node != null) {
	        while (node.left != null)
	            node = node.left;
	    }
	    return node;
	}
	
	private AvlNode<T> findMax(AvlNode<T> node) {
	    if (node != null) {
	        while (node.right != null)
	            node = node.right;
	    }
	    return node;
	}
	
	private static class AvlNode<T> {
		T elementData;
		AvlNode<T> left;
		AvlNode<T> right;
		int height;
		
		public AvlNode(T data, AvlNode<T> left, AvlNode<T> right) {
			elementData = data;
			this.left = left;
			this.right = right;
		}
	}
	
	@Override
	public String toString() {
		return "[" + listNode(root) + "]";
	}

	private String listNode(AvlNode<T> node) {
		if (node == null)
			return "";
		
		String str = "";
		str += listNode(node.right);
		str += "{val=" + node.elementData.toString() + ", height=" + height(node) + "}";
		str += listNode(node.left);
		return str;
	}
	
	public static void main(String[] args) {
		AvlTree<Integer> avlTree = new AvlTree<>();
		for (int i = 0; i < 3; i++) {
			avlTree.insert(i);
		}
		System.out.println(avlTree);
	}
	
}
