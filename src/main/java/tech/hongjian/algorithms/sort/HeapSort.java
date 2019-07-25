package tech.hongjian.algorithms.sort;

import java.util.stream.Stream;

/** 
 * @author xiahongjian
 * @time   2019-07-25 20:59:53
 */
public class HeapSort {
    
    private static int leftChild(int i) {
        return 2 * i + 1;
    }
    
    private static <T extends Comparable<? super T>> void percolateDown(T[] arr, int i, int n) {
        T temp = arr[i];
        int child;
        for (; leftChild(i) < n; i = child) {
            child = leftChild(i);
            
            // child != n - 1意味着i还有右孩子
            if (child != n - 1 && arr[child+1].compareTo(arr[child]) > 0) {
                child++;
            }
            
            // 小于temp，空穴中应该放入temp
            if (temp.compareTo(arr[child]) > 0) {
                break;
            } else {
                // 空穴下移一层
                arr[i] = arr[child];
            }
        }
        arr[i] = temp;
    }
    
    private static <T extends Comparable<? super T>> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static <T extends Comparable<? super T>> void heapSort(T[] arr) {
        // build max heap
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            percolateDown(arr, i, arr.length);
        }
        
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            percolateDown(arr, 0, i);
        }
    }
    
    public static void main(String[] args) {
        Integer nums[] = {2, 83, 34, 12, 78, 39, 20};
        heapSort(nums);
        Stream.of(nums).forEach(System.out::println);
    }

}
