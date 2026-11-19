import java.util.Collections;
import java.util.PriorityQueue;

class Solution {
    public long maxKelements(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int x : nums) {
            heap.offer(x);
        }
        long score = 0;
        for (int i = 0; i < k; i++) {
            int v = heap.poll();
            score += v;
            heap.offer((v + 2) / 3);
        }
        return score;
    }
}
