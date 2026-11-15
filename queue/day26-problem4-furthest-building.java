// Day 26 - Problem 4: Furthest Building You Can Reach (LC 1642) - Medium
// Topic: Queue (min-heap + greedy - ladders badi chadhai par, bricks chhoti par)
// Approach: har chadhai par pehle ladder maan lo; heap ladders se bada ho to sabse chhoti chadhai bricks se bharo.
// Time: O(n log n) | Space: O(n)
import java.util.PriorityQueue;
class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < heights.length - 1; i++) {
            int diff = heights[i + 1] - heights[i];
            if (diff <= 0) { continue; }
            heap.offer(diff);
            if (heap.size() > ladders) { bricks -= heap.poll(); }
            if (bricks < 0) { return i; }
        }
        return heights.length - 1;
    }
}
