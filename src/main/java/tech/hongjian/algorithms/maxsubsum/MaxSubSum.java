package tech.hongjian.algorithms.maxsubsum;

/**
 * 计算数组最大子序列算法
 * 
 * @author xiahongjian
 * @time 2018-02-19
 */
public class MaxSubSum {


    /**
     * 采用三层for循环，计算所有子序列和从而求得最大子序列
     * 时间复杂度O(n^3)
     * @param arr
     * @return
     */
    public static int solutionA(int[] arr) {
        int maxSum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int thisSum = 0;
                for (int k = i; k < j; k++)
                    thisSum += arr[k];
                if (thisSum > maxSum)
                    maxSum = thisSum;
            }
        }
        return maxSum;
    }
    
    /**
     * 去除一层多余的循环
     * 时间复杂度O(n^2)
     * @param arr
     * @return
     */
    public static int solutionB(int[] arr) {
        int maxSum = 0;
        for (int i = 0; i < arr.length; i++) {
            int thisSum = 0;
            for (int j = i; j < arr.length; j++) {
                thisSum += arr[j];
                if (thisSum > maxSum)
                    maxSum = thisSum;
            }
        }
        return maxSum;
    }
    
    /**
     * 采用分治思想，递归求解
     * 分为三种情况，最大子序列在左边、右边和横跨中间，
     * 横跨中间的最大子序列和的计算方式：只需从中间开始向两端最大子序列，再将两个值相加
     * 时间复杂度O(n*log(n))
     * @param arr
     * @return
     */
    public static int solutionC(int[] arr) {
        return maxSumRes(arr, 0, arr.length - 1);
    }
    
    private static int maxSumRes(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left] > 0 ? arr[left] : 0;
        }
        
        int center = (left + right) / 2;
        int leftMax = maxSumRes(arr, left, center);
        int rightMax = maxSumRes(arr, center + 1, right);
        
        int maxLeftBorderSum = 0, sum = 0;
        for (int i = center; i >= left; i--) {
            sum += arr[i];
            if (sum > maxLeftBorderSum)
                maxLeftBorderSum = sum;
        }
        int maxRightBorderSum = 0;
        sum = 0;
        for (int i = center + 1; i <= right; i++) {
            sum += arr[i];
            if (sum > maxRightBorderSum)
                maxRightBorderSum = sum;
        }
        
        return max(leftMax, rightMax, maxLeftBorderSum + maxRightBorderSum);
    }
    private static int max(int a, int b, int c) {
        return a >= b ? (a >= c ? a : c) : (b >= c ? b : c);
    }
    
    /**
     * 根据和为负数的子序列不可能为最大子序列的前缀序列优化
     * 时间复杂度O(n)
     * @param arr
     * @return
     */
    public static int solutionD(int[] arr) {
        int maxSum = 0, thisSum = 0;
        for (int i = 0; i < arr.length; i++) {
            thisSum += arr[i];
            
            // 子序列和小于0，则此子序列必然不可能为最大子序列的前缀序列
            if (thisSum < 0) {
                thisSum = 0;
                continue;
            }
            
            if (thisSum > maxSum)
                maxSum = thisSum;
        }
        return maxSum;
    }


    public static void main(String[] args) {
        int arr[] = new int[]{1, -2, 2, -3, 2, 3, 5, -1, 3, -2};
        System.out.println("solutionA:" + solutionA(arr));
        
        System.out.println("solutionB:" + solutionB(arr));
        
        System.out.println("solutionC:" + solutionC(arr));
        
        System.out.println("solutionD:" + solutionD(arr));
    }
}
