package tech.hongjian.algorithms.structure;

import java.util.EmptyStackException;

/**
 * 使用链表实现Stack
 * 
 * @author xiahongjian
 * @time   2018:03:06 13:53:04
 * @param <T>	Stack中存储元素的类型
 */
public class MyStack<T> {
	private Node<T> top;
	private int size;
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public synchronized T pop() {
		T e = peek();
		removeFirst();
		return e;
	}
	
	public T push(T e) {
		addElement(e);
		return e;
	}
	
	public synchronized T peek() {
		if (size == 0)
			throw new EmptyStackException();
		return top.data;
	}
	
	private static class Node<T> {
		public T data;
		public Node<T> next;
		public Node(T e, Node<T> next) {
			data = e;
			this.next = next;
		}
	}
	
	private void addElement(T e) {
		Node<T> newNode = new Node<>(e, top);
		if (top == null)
			top = newNode;
		size++;
	}
	
	private void removeFirst() {
		top = top.next;
		size--;
	}
}
