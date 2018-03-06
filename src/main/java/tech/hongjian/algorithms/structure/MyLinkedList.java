package tech.hongjian.algorithms.structure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable<T> {
	private int size;
	private Node<T> first;
	private Node<T> last;
	private int modCount;
	

	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public void clear() {
		for (Node<T> p = first; p != null;) {
			Node<T> next = p.next;
			p.prev = null;
			p.data = null;
			p.next = null;
			p = next;
		}
		first = last = null;
		size = 0;
		modCount++;
	}
	
	public T get(int idx) {
		Node<T> p;
		if (idx < size() >> 2) {
			p = first;
			for (int i = 0; i < idx; i++)
				p = p.next;
			return p.data;
		} else {
			p = last;
			for (int i = size() - 1; i > idx; i--)
				p = p.prev;
			return p.data;
		}
	}
	
	public T getFirst() {
		final Node<T> f = first;
		if (f == null)
			throw new NoSuchElementException();
		return f.data;
	}
	
	public T getLast() {
		final Node<T> l = last;
		if (l == null)
			throw new NoSuchElementException();
		return l.data;
	}
	
	public boolean add(T e) {
		linkFirst(e);
		return true;
	}
	
	public void add(int idx, T e) {
		checkPosition(idx);
		linkBefore(e, node(idx));
	}
	
	
	public void addFirst(T e) {
		linkFirst(e);
	}
	
	public void addLast(T e) {
		linkLast(e);
	}
	
	private void checkPosition(int idx) {
		if (!isPositionIndex(idx))
			throw new IndexOutOfBoundsException();
	}
	
	private boolean isPositionIndex(int idx) {
		return idx >= 0 || idx <= size;
	}
	
	private void linkBefore(T e, Node<T> node) {
		Node<T> prevn = node.prev;
		Node<T> newNode = new Node<>(prevn, e, node);
		if (prevn == null) {
			first = newNode;
		} else {
			prevn.next = newNode;
		}
		size++;
		modCount++;
	}
	
	
	private Node<T> node(int idx) {
		Node<T> p;
		if (idx < size >> 1) {
			p = first;
			for (int i = 0; i < idx; i++)
				p = p.next;
		} else {
			p = last;
			for (int i = size -1; i > idx; i--)
				p = p.prev;
		}
		return p;
	}
	
	
	
	private void linkFirst(T e) {
		Node<T> f = first;
		Node<T> newNode = new Node<>(null, e, f);
		first = newNode;
		if (f == null) {
			last = newNode;
		} else {
			f.prev = newNode;
		}
		size++;
		modCount++;
	}
	
	private void linkLast(T e) {
		Node<T> l = last;
		Node<T> newNode = new Node<>(last, e, null);
		last = newNode;
		if (l == null) {
			first = newNode;
		} else {
			l.next = newNode;
		}
		size++;
		modCount++;
	}
	
	private T unlink(Node<T> node) {
		T e = node.data;
		Node<T> prev = node.prev;
		Node<T> next = node.next;
		if (prev == null) {
			first = next;
		} else {
			prev.next = next;
			node.prev = null;
		}
		if (node.next == null) {
			last = node.prev;
		} else {
			next.prev = prev;
			node.next = null;
		}
		node.data = null;
		size--;
		modCount++;
		return e;
	}
	
	
	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator();
	}

	
	private static class Node<T> {
		public T data;
		public Node<T> prev;
		public Node<T> next;
		
		public Node(Node<T> prev, T data, Node<T> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private class LinkedListIterator implements Iterator<T> {
		private Node<T> current = new Node<>(null, null, first);
		private int expectModCount = modCount;
		private int index;
		private boolean canRemove;

		@Override
		public boolean hasNext() {
			return index < size && current.next != null;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			if (expectModCount != modCount)
				throw new ConcurrentModificationException();
			Node<T> node = current.next;
			current = node;
			canRemove = true;
			return node.data;
		}
		
		public void remove() {
			if (!canRemove)
				throw new IllegalStateException();
			if (expectModCount != modCount)
				throw new ConcurrentModificationException();
			unlink(current);
			expectModCount++;
			canRemove = false;
		}
	}
}
