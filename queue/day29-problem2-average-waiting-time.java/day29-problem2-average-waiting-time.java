class Solution {
    public double averageWaitingTime(int[][] customers) {
        long totalWaiting = 0;
        long freeTime = 0;
        for (int[] c : customers) {
            int arrival = c[0];
            int time = c[1];
            long start = Math.max(arrival, freeTime);
            long finish = start + time;
            totalWaiting += finish - arrival;
            freeTime = finish;
        }
        return (double) totalWaiting / customers.length;
    }
}
