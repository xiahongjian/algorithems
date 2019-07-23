package tech.hongjian.algorithms.sort;

import java.util.stream.Stream;

/** 
 * @author xiahongjian
 * @time   2019-07-23 22:48:16
 */
public class ShellSort {
    private static <T extends Comparable<? super T>> void  sort(T eles[]) {
        for (int gap = eles.length >> 1; gap > 0; gap >>= 1) {
            for (int i = gap; i < eles.length; i++) {
                T tmp = eles[i];
                int j;
                for (j = i; j >= gap && tmp.compareTo(eles[j - gap]) < 0; j -= gap) {
                    eles[j] = eles[j - gap];
                }
                eles[j] = tmp;
            }
        }
    }
    
    
    public static void main(String[] args) {
        Integer numbers[] = {2, 19, 3, 50, 13, 23, 45, 0, 8, 11};
        sort(numbers);
        Stream.of(numbers).forEach(System.out::println);
    }
}
