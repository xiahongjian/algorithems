package tech.hongjian.algorithms.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** 
 * @author xiahongjian
 * @time   2018-03-04
 */
public class MyArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    
    private int size;
    private T[] items;
    
    public MyArrayList() {
        doClear();
    }
    
    public void clear() {
        doClear();
    }
    
    public int size() {
        return size;
    }
    
    public void trimToSize() {
        ensureCapacity(size());
    }
    
    public T get(int index) {
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        return items[index];
    }
    
    public T set(int index, T newVal) {
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        T old = items[index];
        items[index] = newVal;
        return old;
    }
    
    public boolean add(T e) {
        add(size(), e);
        return true;
    }
    
    public void add(int index, T e) {
        if (items.length == size())
            ensureCapacity((size() << 1) + 1); 
        for (int i = size; i > index; i--) {
            items[i] = items[i - 1];
        }
        items[index] = e;
        size++;
    }
    
    public T remove(int index) {
        T removedItem = items[index];
        for (int i = index; i < size; i++)
            items[i] = items[i+1];
        return removedItem;
    }
    
    
    private void doClear() {
        size = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    private void ensureCapacity(int capacity) {
        if (capacity < size)
            return;
        T[] old = items;
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            newItems[i] = old[i];
        }
        items = newItems;
    }
    

    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        private int current = 0;
        
        public boolean hasNext() {
            return current < size();
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return items[current++];
        }
        
        public void remove() {
            MyArrayList.this.remove(--current);
        }
    }
}
