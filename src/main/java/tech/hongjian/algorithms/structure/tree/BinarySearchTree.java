package tech.hongjian.algorithms.structure.tree;

import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> {
	private BinaryNode<T> root;
	
	public void clean() {
		root = null;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public boolean contains(T e) {
		return contains(e, root);
	}
	
	public T findMin() {
		if (isEmpty())
			throw new NoSuchElementException();
		return findMin(root).data;
	}
	
	public T findMax() {
		if (isEmpty())
			throw new NoSuchElementException();
		return findMax(root).data;
	}
	
	public void insert(T e) {
		root = insert(e, root);
	}
	
	public void remove(T e) {
		root = remove(e, root);
	}
	
	private boolean contains(T e, BinaryNode<T> node) {
		if (node == null)
			return false;
		int res = node.data.compareTo(e);
		if (res > 0) {
			return contains(e, node.left);
		} else if (res < 0) {
			return contains(e, node.right);
		}
		return true;
	}
	
	private BinaryNode<T> findMin(BinaryNode<T> node) {
		if (node != null) {
			while (node.left != null)
				node = node.left;
		}
		return node;
	}
	
	private BinaryNode<T> findMax(BinaryNode<T> node) {
		if (node != null) {
			while (node.right != null) 
				node = node.right;
		}
		return node;
	}
	
	private BinaryNode<T> insert(T e, BinaryNode<T> node) {
		if (node == null) {
			return new BinaryNode<>(e, null, null);
		}
		int res = node.data.compareTo(e);
		if (res > 0) {
			node.left = insert(e, node.left);
		} else if (res < 0) {
			node.right = insert(e, node.right);
		} else {
			// 数据相同，什么都不做，返回当前节点
		}
		return node;
	}
	
	private BinaryNode<T> remove(T e, BinaryNode<T> node) {
		if (node == null)
			return node;
		
		int res = node.data.compareTo(e);
		
		if (res > 0) {
			return remove(e, node.left);
		} else if (res < 0) {
			return remove(e, node.right);
		} else if (node.left != null && node.right != null) {
			node.data = findMin(node.right).data;
			node.right = remove(node.data, node.right);
		} else {
			node = node.right != null ? node.left : node.right;
		}
		return node;
	}
	
	private static class BinaryNode<T> {
		T data;
		BinaryNode<T> left;
		BinaryNode<T> right;
		
		public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}
}
