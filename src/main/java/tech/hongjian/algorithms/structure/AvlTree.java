package tech.hongjian.algorithms.structure;

import javax.sound.midi.Soundbank;

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
	
	
	
	private AvlNode<T> insert(T e, AvlNode<T> node) {
		if (node == null)
			return new AvlNode<>(e);
		
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
	
	public AvlNode<T> doubleWithRightChild(AvlNode<T> node) {
		node.right = rotateWithLeftChild(node.right);
		return rotateWithRightChild(node);
	}
	
	private int height(AvlNode<T> node) {
		return node == null ? -1 : node.height;
	}
	
	private static class AvlNode<T> {
		T elementData;
		AvlNode<T> left;
		AvlNode<T> right;
		int height;
		
		public AvlNode(T data) {
			elementData = data;
		}
		
		public AvlNode(T data, AvlNode<T> left, AvlNode<T> right) {
			elementData = data;
			this.left = left;
			this.right = right;
		}
	}
	
	@Override
	public String toString() {
		return listNode(root);
	}

	private String listNode(AvlNode<T> node) {
		if (node == null)
			return "";
		
		String str = "";
		str += listNode(node.right);
		str += "[val=" + node.elementData.toString() + ", height=" + height(node) + "]";
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
