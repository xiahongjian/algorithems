package tech.hongjian.algorithms.structure.heap;

/**
 * @author xiahongjian
 * @time 2018-06-06 14:12:49
 *
 */
public class BinoialQueue<T extends Comparable<? super T>> {
	private static final int DEFAULT_TREES = 1;

	private int size;
	private Node<T>[] trees;

	@SuppressWarnings("unchecked")
	public BinoialQueue() {
		trees = (Node<T>[]) new Node[DEFAULT_TREES];
	}

	@SuppressWarnings("unchecked")
	public BinoialQueue(T item) {
		this.size = 1;
		trees = (Node<T>[]) new Node[] { new Node<T>(item) };
	}

	public void merge(BinoialQueue<T> rhs) {
		if (rhs == this)
			return;

		size += rhs.size();
		if (size > capacity()) {
			int maxLength = Math.max(trees.length, rhs.trees.length);
			expandTress(maxLength + 1);
		}
		Node<T> carry = null;
		for (int i = 0, j = 1; j < size; i++, j *= 2) {
			Node<T> t1 = trees[i];
			Node<T> t2 = i < rhs.trees.length ? rhs.trees[i] : null;

			int whichCase = t1 == null ? 0 : 1;
			whichCase += t2 == null ? 0 : 2;
			whichCase += carry == null ? 0 : 4;

			switch (whichCase) {
			case 0: // No trees
			case 1: // Only this
				break;
			case 2: // Only rhs
				trees[i] = rhs.trees[i];
				rhs.trees[i] = null;
				break;
			case 4: // Only carry
				trees[i] = carry;
				carry = null;
				break;
			case 3: // this and rhs
				carry = combineThrees(t1, t2);
				trees[i] = rhs.trees[i] = null;
				break;
			case 5: // this and carry
				carry = combineThrees(t1, carry);
				trees[i] = null;
				break;
			case 6: // rhs and carry
				carry = combineThrees(t2, carry);
				rhs.trees[i] = null;
				break;
			case 7: // All three
				trees[i] = carry;
				carry = combineThrees(t1, t2);
				rhs.trees[i] = null;
				break;
			}
		}
		for (int i = 0; i < rhs.trees.length; i++)
			rhs.trees[i] = null;
		rhs.size = 0;
	}

	public void insert(T item) {
		merge(new BinoialQueue<>(item));
	}

	public T findMin() {
		if (isEmpty())
			return null;
		return trees[findMinIndex()].element;
	}
	
	public T deleteMin() {
		if (isEmpty())
			return null;
		
		int minIdx = findMinIndex();
		T minEle = trees[minIdx].element;
		Node<T> deleteTree = trees[minIdx].leftChild;
		
		BinoialQueue<T> deleteQueue = new BinoialQueue<>();
		deleteQueue.expandTress(minIdx + 1);
		
		deleteQueue.size = (1 << minIdx) - 1;
		for (int i = minIdx - 1; i >= 0; i--) {
			deleteQueue.trees[i] = deleteTree;
			deleteTree = deleteTree.nextSibling;
			deleteQueue.trees[i].nextSibling = null;
		}
		
		trees[minIdx] = null;
		size -= deleteQueue.size + 1;
		
		merge(deleteQueue);
		
		return minEle;
	}

	@SuppressWarnings("unchecked")
	private void expandTress(int newTreeNum) {
		Node<T>[] newTrees = (Node<T>[]) new Node[newTreeNum];
		System.arraycopy(trees, 0, newTrees, 0, trees.length);
		trees = newTrees;
	}

	private Node<T> combineThrees(Node<T> t1, Node<T> t2) {
		if (t1.element.compareTo(t2.element) > 0)
			return combineThrees(t2, t1);
		t2.nextSibling = t1.leftChild;
		t1.leftChild = t2;
		return t1;
	}
	
	private int findMinIndex() {
		int minIndex = 0;
		for (int i = 1; i < trees.length; i++) {
			if (trees[minIndex].element.compareTo(trees[i].element) > 0)
				minIndex = i;
		}
		return minIndex;
	}

	public int size() {
		return this.size;
	}

	public int capacity() {
		return 1 << trees.length - 1;
	}
	
	@SuppressWarnings("unchecked")
	public void clear() {
		size = 0;
		trees = new Node[DEFAULT_TREES];
	}

	public boolean isEmpty() {
		return size == 0;
	}
	
	
	private static class Node<T> {
		T element;
		Node<T> leftChild;
		Node<T> nextSibling;

		Node(T item) {
			this(item, null, null);
		}

		Node(T element, Node<T> lt, Node<T> nt) {
			this.element = element;
			this.leftChild = lt;
			this.nextSibling = nt;
		}
	}
}
