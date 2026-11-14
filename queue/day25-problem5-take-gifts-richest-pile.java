import java.util.*;

class Solution {
    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int g : gifts) {
            heap.offer(g);
        }
        for (int t = 0; t < k; t++) {
            int biggest = heap.poll();
            int left = (int) Math.sqrt(biggest);
            heap.offer(left);
        }
        long total = 0;
        while (!heap.isEmpty()) {
            total += heap.poll();
        }
        return total;
    }
}
