import java.util.Collections;
import java.util.PriorityQueue;

class Solution {
    public int halveArray(int[] nums) {
        PriorityQueue<Double> heap = new PriorityQueue<>(Collections.reverseOrder());
        double sum = 0;
        for (int x : nums) {
            heap.offer((double) x);
            sum += x;
        }
        double target = sum / 2;
        double reduced = 0;
        int ops = 0;
        while (reduced < target) {
            double x = heap.poll();
            double half = x / 2;
            reduced += half;
            heap.offer(half);
            ops++;
        }
        return ops;
    }
}
