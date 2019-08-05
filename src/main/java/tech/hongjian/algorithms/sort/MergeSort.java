package tech.hongjian.algorithms.sort;

import java.util.stream.Stream;

/** 
 * 时间复杂度O(N log N), 空间复杂度O(N)
 * @author xiahongjian
 * @time   2019-08-05 19:20:37
 */
public class MergeSort {
    
    
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> void sort(T[] eles) {
        mergeSort(eles, (T[])new Comparable[eles.length], 0, eles.length - 1);
    }
    
    private static <T extends Comparable<? super T>> void mergeSort(T[] eles, T[] temp, int begin, int end) {
        if (begin == end) {
            return;
        }
        int middle = (begin + end) / 2;
        mergeSort(eles, temp, begin, middle);
        mergeSort(eles, temp, middle + 1, end);
        merge(eles, temp, begin, middle + 1, end);
    }
    
    private static <T extends Comparable<? super T>> void merge(T[] eles, T[] temp, int leftPos, int rightPos, int rightEnd) {
        int tempIndex = leftPos;
        int leftEnd = rightPos - 1;
        int lIndex = leftPos;
        int rIndex = rightPos;
        
        while (lIndex <= leftEnd && rIndex <= rightEnd) {
            if (eles[lIndex].compareTo(eles[rIndex]) <= 0) {
                temp[tempIndex++] = eles[lIndex++];
            } else {
                temp[tempIndex++] = eles[rIndex++];
            }
        }
        // 处理left剩余的元素
        while (lIndex <= leftEnd) {
            temp[tempIndex++] = eles[lIndex++];
        }
        
        // 处理right剩余的元素
        while (rIndex <= rightEnd) {
            temp[tempIndex++] = eles[rIndex++];
        }
        // 将排序后的结果复制会原数组
        for (int i = leftPos; i <= rightEnd; i++) {
            eles[i] = temp[i];
        }
    }
    
    public static void main(String[] args) {
        Integer[] eles = {1, 23, 3, 5, 9, 6, 13, 10, 28, 32, 16};
        sort(eles);
        Stream.of(eles).forEach(System.out::println);
    }
}
