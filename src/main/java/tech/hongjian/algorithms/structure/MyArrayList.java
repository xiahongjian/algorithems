package tech.hongjian.algorithms.structure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** 
 * @author xiahongjian
 * @time   2018-03-04
 */
public class MyArrayList<T> implements Iterable<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final Object[] EMPTY_ARRAY = {};
	private static final Object[] DEFAULT_CAPACITY_ARRAY = {};

	private T[] items;
	private int size;
	private int modCount;

	@SuppressWarnings("unchecked")
	public MyArrayList() {
		items = (T[]) (new Object[DEFAULT_CAPACITY]);
	}

	@SuppressWarnings("unchecked")
	public MyArrayList(int capacity) {
		if (capacity < 0)
			throw new IllegalArgumentException();
		items = (T[]) DEFAULT_CAPACITY_ARRAY;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		size = 0;
		items = (T[]) EMPTY_ARRAY;
		modCount++;
	}

	public T get(int idx) {
		if (idx < 0 || idx >= size())
			throw new ArrayIndexOutOfBoundsException();
		return items[idx];
	}

	public T set(int idx, T newVal) {
		if (idx < 0 || idx >= size())
			throw new ArrayIndexOutOfBoundsException();
		T old = items[idx];
		items[idx] = newVal;
		modCount++;
		return old;
	}

	public boolean add(T e) {
		add(size(), e);
		return true;
	}

	public void add(int idx, T e) {
		if (items.length <= idx) {
			ensureCapacity(items.equals(DEFAULT_CAPACITY_ARRAY) ? DEFAULT_CAPACITY : (size() << 1) + 1);
		}
		for (int i = size() - 1; i > idx; i--) {
			items[i] = items[i - 1];
		}
		items[idx] = e;
		size++;
		modCount++;
	}

	public T remove(int idx) {
		if (idx < 0 || idx >= size())
			throw new ArrayIndexOutOfBoundsException();
		T e = items[idx];
		for (int i = idx; i < size(); i++)
			items[i] = items[i + 1];
		size--;
		modCount++;
		return e;
	}

	public void trimToSize() {
		ensureCapacity(size());
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity(int capacity) {
		// 调整容量小于当前元素数量，则忽略
		if (capacity < size())
			return;
		T[] old = items;
		T[] newItems = (T[]) new Object[capacity];
		for (int i = 0; i < size; i++)
			newItems[i] = old[i];
		items = newItems;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayListIterator(modCount);
	}

	private class ArrayListIterator implements Iterator<T> {
		private int current;
		private int expectModCount;

		public ArrayListIterator(int modCount) {
			expectModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			return current < size();
		}

		@Override
		public T next() {
			if (modCount != expectModCount)
				throw new ConcurrentModificationException();
			if (!hasNext())
				throw new NoSuchElementException();
			return items[current++];
		}

		public void remove() {
			if (modCount != expectModCount)
				throw new ConcurrentModificationException();
			MyArrayList.this.remove(--current);
		}

	}

	public static void main(String[] args) {
		MyArrayList<String> list = new MyArrayList<>();
		list.add("hello");
		list.add("world");
		for (String s : list) {
			System.out.println(s);
		}
	}

}