// Day 14 - Problem 3: Pacific Atlantic Water Flow (LC 417) - Medium
// Topic: Queue (multi-source BFS, reverse thinking)
//
// PROBLEM (Hinglish):
// Grid me har cell ki height di hai. Pacific upar/left kinare se,
// Atlantic neeche/right kinare se lagta hai. Paani barabar ya kam
// height wale padosi par beh sakta hai. Woh cells return karo jinse
// paani DONO oceans tak beh sakta hai.
// Example: [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
//   -> [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
//
// APPROACH (Hinglish):
// Har cell se dono ocean check karna mehnga hai, isliye ulta chalo:
// ocean kinare se andar ki taraf BFS, reverse me "uphill" (padosi ki
// height barabar ya zyada). Pacific aur Atlantic dono ke liye alag
// BFS, phir unka intersection hi jawab hai.
//
// TIME COMPLEXITY: O(m*n) - har cell har ocean ke liye ek baar visit hota hai
// SPACE COMPLEXITY: O(m*n) - do visited grids + queue
//
// KEY LEARNING:
// Destination se reverse BFS chalana multi-source problems ka classic
// tareeqa hai, aur reverse me condition ulta ho jati hai (>= check).

import java.util.*;

class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        List<int[]> pacStarts = new ArrayList<>();
        List<int[]> atlStarts = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            pacStarts.add(new int[]{0, c});          // top row -> Pacific
            atlStarts.add(new int[]{rows - 1, c});   // bottom row -> Atlantic
        }
        for (int r = 0; r < rows; r++) {
            pacStarts.add(new int[]{r, 0});          // left column -> Pacific
            atlStarts.add(new int[]{r, cols - 1});   // right column -> Atlantic
        }
        boolean[][] pac = bfs(heights, pacStarts);
        boolean[][] atl = bfs(heights, atlStarts);
        // dono me aane wali cells hi jawab hain
        List<List<Integer>> ans = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (pac[r][c] && atl[r][c]) {
                    ans.add(Arrays.asList(r, c));
                }
            }
        }
        return ans;
    }

    // kinare ki cells se shuru karke uphill BFS
    private boolean[][] bfs(int[][] heights, List<int[]> starts) {
        int rows = heights.length;
        int cols = heights[0].length;
        boolean[][] reachable = new boolean[rows][cols];
        Queue<int[]> q = new LinkedList<>();
        for (int[] s : starts) {
            if (!reachable[s[0]][s[1]]) {
                reachable[s[0]][s[1]] = true;
                q.offer(s);
            }
        }
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            for (int[] d : dirs) {
                int nr = cell[0] + d[0];
                int nc = cell[1] + d[1];
                // grid ke andar, visit nahi, aur uphill (barabar ya zyada)
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && !reachable[nr][nc]
                        && heights[nr][nc] >= heights[cell[0]][cell[1]]) {
                    reachable[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return reachable;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.pacificAtlantic(new int[][]{
            {1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}}));
        // Expected: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]] (kisi bhi order me)
        System.out.println(s.pacificAtlantic(new int[][]{{1}})); // Expected: [[0,0]]
    }
}
