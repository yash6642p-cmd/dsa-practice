import java.util.Arrays;

class Solution {
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);
        int total = 0;
        for (int[] box : boxTypes) {
            if (truckSize == 0) {
                break;
            }
            int take = Math.min(box[0], truckSize);
            total += take * box[1];
            truckSize -= take;
        }
        return total;
    }
}
