import java.util.Collections;
import java.util.PriorityQueue;
class Solution {
    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int p : piles) { heap.offer(p); }
        for (int i = 0; i < k; i++) {
            int biggest = heap.poll();
            biggest -= biggest / 2;
            heap.offer(biggest);
        }
        int total = 0;
        for (int p : heap) { total += p; }
        return total;
    }
}
