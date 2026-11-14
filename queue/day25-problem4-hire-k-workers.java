import java.util.*;

class Solution {
    public long totalCost(int[] costs, int k, int candidates) {
        int n = costs.length;
        Comparator<int[]> cmp = (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1];
        PriorityQueue<int[]> left = new PriorityQueue<>(cmp);
        PriorityQueue<int[]> right = new PriorityQueue<>(cmp);
        int i = 0, j = n - 1;
        for (int t = 0; t < candidates; t++) {
            if (i <= j) {
                left.offer(new int[]{costs[i], i});
                i++;
            }
            if (i <= j) {
                right.offer(new int[]{costs[j], j});
                j--;
            }
        }
        long total = 0;
        for (int t = 0; t < k; t++) {
            if (!right.isEmpty() && (left.isEmpty() || cmp.compare(right.peek(), left.peek()) < 0)) {
                int[] pick = right.poll();
                total += pick[0];
                if (i <= j) {
                    right.offer(new int[]{costs[j], j});
                    j--;
                }
            } else {
                int[] pick = left.poll();
                total += pick[0];
                if (i <= j) {
                    left.offer(new int[]{costs[i], i});
                    i++;
                }
            }
        }
        return total;
    }
}
