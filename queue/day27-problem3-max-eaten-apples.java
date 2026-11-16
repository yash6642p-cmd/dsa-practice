import java.util.PriorityQueue;

class Solution {
    public int eatenApples(int[] apples, int[] days) {
        int n = apples.length;
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int eaten = 0;
        int day = 0;
        while (day < n || !heap.isEmpty()) {
            if (day < n && apples[day] > 0) {
                heap.offer(new int[]{day + days[day], apples[day]});
            }
            while (!heap.isEmpty() && heap.peek()[0] <= day) heap.poll();
            if (!heap.isEmpty()) {
                int[] top = heap.poll();
                eaten++;
                if (top[1] > 1) heap.offer(new int[]{top[0], top[1] - 1});
            }
            day++;
        }
        return eaten;
    }
}
