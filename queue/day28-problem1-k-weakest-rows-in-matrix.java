import java.util.Arrays;
class Solution {
    public int[] kWeakestRows(int[][] mat, int k) {
        int m = mat.length;
        int[][] strength = new int[m][2];
        for (int i = 0; i < m; i++) {
            int count = 0;
            for (int v : mat[i]) { count += v; }
            strength[i][0] = count;
            strength[i][1] = i;
        }
        Arrays.sort(strength, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) { ans[i] = strength[i][1]; }
        return ans;
    }
}
