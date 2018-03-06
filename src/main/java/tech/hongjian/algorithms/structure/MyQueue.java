package tech.hongjian.algorithms.structure;

public class MyQueue<T> {
	private int size;
	private Node<T> front;
	private Node<T> back;
	
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clean() {
		front = back = null;
		size = 0;
	}
	
	public boolean offer(T e) {
		addLast(e);
		return true;
	}
	
	public T poll() {
		if (isEmpty())
			return null;
		return removeFirst();
	}
	
	public T peek() {
		return front == null ? null : front.data;
	}
	
	private T removeFirst() {
		Node<T> f = front;
		front = f.next;
		size--;
		return f.data;
	}
	
	private void addLast(T e) {
		Node<T> newNode = new Node<>(e, null);
		if (!isEmpty()) {
			back.next = newNode;
			back = newNode;
		} else {
			front = back = newNode;
		}
		size++;
	}
	
	private static class Node<T> {
		T data;
		Node<T> next;
		
		Node(T data, Node<T> next) {
			this.data = data;
			this.next = next;
		}
	}
	
	public static void main(String[] args) {
		MyQueue<String> queue = new MyQueue<>();
		queue.offer("a");
		queue.offer("b");
		queue.offer("c");
		while (!queue.isEmpty()) {
			System.out.println(queue.poll());
		}
	}
}
