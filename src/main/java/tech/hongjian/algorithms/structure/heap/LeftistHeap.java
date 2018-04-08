package tech.hongjian.algorithms.structure.heap;

import java.util.NoSuchElementException;

/**
 * @author xiahongjian 
 * @time   2018-04-08 10:44:40
 *
 */
public class LeftistHeap<T extends Comparable<? super T>> {
	private Node<T> root;

	
	public boolean isEmpty() {
		return root == null;
	}
	
	public void clear() {
		root = null;
	}
	
	public void merge(LeftistHeap<T> rhs) {
		if (this == rhs)
			return;
		root = merge(root, rhs.root);
		rhs.root = null;
	}
	
	public void insert(T ele) {
		root = merge(new Node<T>(ele), root);
	}
	
	public T findMin() {
		if (root == null)
			throw new NoSuchElementException();
		return root.element;
	}
	
	public T deleteMin() {
		if (isEmpty())
			throw new NullPointerException();
		T ele = root.element;
		root = merge(root.left, root.right);
		return ele;
	}
	
	private Node<T> merge(Node<T> h1, Node<T> h2) {
		if (h1 == null)
			return h2;
		if (h2 == null)
			return h1;
		if (h1.element.compareTo(h2.element) < 0) {
			return doMerge(h1, h2);
		} else {
			return doMerge(h2, h1);
		}
	}
	
	private Node<T> doMerge(Node<T> h1, Node<T> h2) {
		if (h1.left == null) {
			h1.left = h2;
		} else {
			h1.right = merge(h1.right, h2);
			if (h1.left.npl < h1.right.npl)
				swapChildren(h1);
			h1.npl = h1.right.npl + 1;
		}
		return h1;
	}
	
	private void swapChildren(Node<T> h) {
		Node<T> tmp = h.left;
		h.left = h.right;
		h.right = tmp;
	}
	
	
	
	private static class Node<T> {
		T element;
		Node<T> left;
		Node<T> right;
		int npl;
		Node(T element) {
			this(element, null, null);
		}
		Node(T element, Node<T> right, Node<T> left) {
			this.element = element;
			this.right = right;
			this.left = left;
			npl = 0;
		}
	}

}
