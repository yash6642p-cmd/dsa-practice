import java.util.PriorityQueue;

class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>(
            (a, b) -> (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1]));
        for (int[] p : points) {
            heap.offer(p);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int[][] ans = new int[k][2];
        int i = 0;
        while (!heap.isEmpty()) {
            ans[i++] = heap.poll();
        }
        return ans;
    }
}
