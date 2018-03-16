package tech.hongjian.algorithms.structure.hash;

import java.util.Random;

public class CuckooHashTable<T> {
	private static final double MAX_LOAD = 0.4;
	private static final int ALLOWED_REHASHES = 1;
	private static final int DEFAULT_TABLE_SIZE = 101;
	
	private final HashFamily<? super T> hashFunctions;
	private final int numHashFunctions;
	private T[] array;
	private int size;
	
	private int rehashes = 0;
	private Random r = new Random();
	
	public CuckooHashTable(HashFamily<? super T> hf) {
		this(hf, DEFAULT_TABLE_SIZE);
	}
	
	public CuckooHashTable(HashFamily<? super T> hf, int size) {
		allocateArray(nextPrime(size));
		doClear();
		hashFunctions = hf;
		numHashFunctions = hf.getNumberOfFunctions();
	}
	
	public void clear() {
		doClear();
	}
	
	public boolean contains(T e) {
		return findPos(e) != -1;
	}
	
	private int myhash(T e, int which) {
		int hash = hashFunctions.hash(e, which);
		hash %= array.length;
		return hash < 0 ? array.length + hash : hash;
	}
	
	private int findPos(T e) {
		for (int i = 0; i < numHashFunctions; i++) {
			int pos = myhash(e, i);
			if (array[pos] != null && array[pos].equals(e))
				return pos;
		}
		return -1;
	}
	
	public boolean remove(T e) {
		int index = findPos(e);
		if (index == -1)
			return false;
		
		array[index] = null;
		size--;
		return true;
	}
	
	public boolean insert(T e) {
		if (contains(e))
			return false;
		if (size >= array.length * MAX_LOAD)
			expand();
		
		return insertHelper(e);
	}
	
	private boolean insertHelper(T e) {
		final int COUNT_LIMIT = 100;
		while (true) {
			int lastPos = -1;
			int pos;
			
			// 控制交换次数
			for (int count = 0; count < COUNT_LIMIT; count++) {
				// 遍历hash函数，寻找合适的位置
				for (int i = 0; i < numHashFunctions; i++) {
					pos = myhash(e, i);
					if (array[pos] == null) {
						array[pos] = e;
						size++;
						return true;
					}
				}
				
				// 未找到合适的位置, 随机选择一个位置进行交换
				int i = 0;
				do {
					pos = myhash(e, r.nextInt(numHashFunctions));
				} while (pos == lastPos && i++ < 5);
				T tmp = array[lastPos = pos];
				array[pos] = e;
				e = tmp;
			}
			if (++rehashes > ALLOWED_REHASHES) {
				expand();
				rehashes = 0;
			} else {
				rehash();
			}
		}
	}
	
	private void expand() {
		rehash((int) (array.length / MAX_LOAD));
	}
	
	private void rehash() {
		hashFunctions.generateNewFunctions();
		rehash(array.length);
	}
	
	private void rehash(int newLnegth) {
		T[] oldArray = array;
		allocateArray(nextPrime(newLnegth));
		size = 0;
		
		for (T e : oldArray) {
			if (e != null)
				insert(e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void allocateArray(int len) {
		array = (T[]) new Object[len];
	}

	private void doClear() {
		size = 0;
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}
	
	private int nextPrime(int n) {
		if (n <= 2)
			return 2;
		
		if (isPrime(n))
			return n;
		
		n = (n & 1) == 0 ? n + 1 : n + 2;
		while (!isPrime(n)) {
			n += 2;
		}
		return n;
	}
	
	private boolean isPrime(int n) {
		if (n == 2)
			return true;
		// 不是2的所有偶数都不是
		if ((n & 1) == 0)
			return false;
		for (int i = 3; i < n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[ ");
		for (int i = 0; i < array.length; i++) { 
			if (array[i] != null) {
				sb.append(i + ":" + array[i] + ",");
			}
		}
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) {
		CuckooHashTable<String> table = new CuckooHashTable<>(new StringHashFamily(3));
		for (int i = 0; i < 10; i++)
			table.insert(i + "");
		System.out.println(table.toString());
	}
}
