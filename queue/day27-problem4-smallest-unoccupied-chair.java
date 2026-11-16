import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int smallestChair(int[][] times, int targetFriend) {
        int n = times.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> times[a][0] - times[b][0]);
        PriorityQueue<Integer> free = new PriorityQueue<>();
        for (int i = 0; i < n; i++) free.offer(i);
        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i : order) {
            int arr = times[i][0];
            int leave = times[i][1];
            while (!busy.isEmpty() && busy.peek()[0] <= arr) {
                free.offer(busy.poll()[1]);
            }
            int chair = free.poll();
            if (i == targetFriend) return chair;
            busy.offer(new int[]{leave, chair});
        }
        return -1;
    }
}
