package tech.hongjian.algorithms.structure.heap;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author xiahongjian 
 * @time   2018-04-04 11:35:13
 *
 */
public class BinaryHeap<T extends Comparable<? super T>> {
	private static final int DEFAULT_CAPACITY = 10;
	private T[] items;
	private int currentSize;
	
	public BinaryHeap() {
		this(DEFAULT_CAPACITY);
	}
	
	public BinaryHeap(int capacity) {
		if (capacity < 0) 
			capacity = DEFAULT_CAPACITY;
		items = createArray(capacity);
	}
	
	public BinaryHeap(T[] items) {
		this(items == null ? DEFAULT_CAPACITY : items.length);
		if (items != null) {
			currentSize = items.length;
			System.arraycopy(items, 0, this.items, 1, currentSize);
			buildHeap();
		}
	}
	
	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public void clear() {
		currentSize = 0;
		items = createArray(DEFAULT_CAPACITY);
	}
	
	public T findMin() {
		if (currentSize < 1) 
			throw new NoSuchElementException();
		return items[1];
	}
	
	public void insert(T item) {
		if (currentSize == items.length - 1) {
			enlargeArray((items.length << 1) + 1);
		}
		int hole = ++currentSize;
		for (items[0] = item; item.compareTo(items[hole >> 1]) < 0; hole >>= 1) {
			items[hole] = items[hole >> 1];
		}
		items[hole] = item;
	}
	
	public T deleteMin() {
		if (isEmpty())
			throw new NoSuchElementException();
		T min = findMin();
		items[1] = items[currentSize--];
		percolateDown(1);
		
		return min;
	}
	
	private void percolateDown(int hole) {
		int child;
		T tmp = items[hole];
		for (; hole << 1 <= currentSize; hole = child) {
			child = hole << 1;
			if (child != currentSize && items[child + 1].compareTo(items[child]) < 0) {
				child++;
			}
			if (items[child].compareTo(items[hole]) < 0) {
				items[hole] = items[child];
			} else {
				break;
			}
		}
		items[hole] = tmp;
	}
	
	private void buildHeap() {
		for (int i = currentSize >> 1; i > 0; i--) {
			percolateDown(i);
		}
	}
	
	@Override
	public String toString() {
		return "BinaryHeap [items=" + Arrays.toString(items) + ", currentSize=" + currentSize + "]";
	}

	@SuppressWarnings("unchecked")
	private void enlargeArray(int newSize) {
		T[] tmp = (T[]) new Comparable[newSize];
		System.arraycopy(items, 1, tmp, 1, currentSize);
		items = tmp;
	}
	
	@SuppressWarnings("unchecked")
	private T[] createArray(int capacity) {
		return (T[]) new Comparable[capacity + 1];
	}
	
	public static void main(String[] args) {
		BinaryHeap<Integer> heap = new BinaryHeap<>(new Integer[] {2, 5, 7, 9, 1});
		for (int i = 10; i > 0; i--)
			heap.insert(i);
		System.out.println("Min:" + heap.findMin());
		System.out.println(heap);
	}
}
