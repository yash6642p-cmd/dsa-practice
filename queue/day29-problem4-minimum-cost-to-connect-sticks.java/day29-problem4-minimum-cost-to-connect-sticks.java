import java.util.PriorityQueue;

class Solution {
    public int connectSticks(int[] sticks) {
        if (sticks.length <= 1) {
            return 0;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int s : sticks) {
            heap.offer(s);
        }
        int totalCost = 0;
        while (heap.size() > 1) {
            int a = heap.poll();
            int b = heap.poll();
            int cost = a + b;
            totalCost += cost;
            heap.offer(cost);
        }
        return totalCost;
    }
}
