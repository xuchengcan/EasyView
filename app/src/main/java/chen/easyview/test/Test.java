package chen.easyview.test;

import java.util.HashMap;

/**
 * Created by chen on 2018/1/5.
 */

public class Test {

    public static void main(String[] args) {
        int[] s;
        int[] nums = {4,3,6,13,9};
        s = twoSum(nums,19);

        System.out.print(s[0] + "\n");
        System.out.print(s[1]);
    }

    public static int[] twoSum(int[] numbers, int target) {
        int[] res = new int[2];
        if (numbers == null || numbers.length < 2) {
            return null;
        }
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                res[0] = map.get(target - numbers[i]);
                res[1] = i;
                return res;
            }
            map.put(numbers[i], i);
        }
        return null;
    }


}
