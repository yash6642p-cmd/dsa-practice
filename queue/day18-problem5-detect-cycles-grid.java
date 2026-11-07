// Day 18 - Problem 5: Detect Cycles in 2D Grid (LC 1559) - Medium
// Topic: Queue (BFS - same letter ka cycle hai ya nahi, parent yaad rakh kar check karo)
//
// PROBLEM (Hinglish):
// m x n grid hai jisme lowercase letters hain. Kya grid me koi
// CYCLE hai jo ek hi letter se bana ho?
// Cycle ka matlab: kam se kam 4 cells ka path jo wahin wapas
// aa jaaye jahan se start hua tha. Har step me up/down/left/right
// ja sakte ho, sirf SAME letter wali cell par, aur jis cell se
// abhi aaye ho us par turant wapas NAHI ja sakte (wo cycle nahi,
// backtrack hai).
// Agar aisa cycle hai to true, nahi to false.
// Example 1:
//   a a a a
//   a b b a
//   a b b a
//   a a a a   -> true ('a' ka outer ring cycle banata hai)
// Example 2: [["a","b","b"],["b","z","b"],["b","b","a"]] -> false
//
// APPROACH (Hinglish):
// Har unvisited cell se BFS chalao, aur queue me har cell ke saath
// uska PARENT (jis cell se aaye) bhi rakho. BFS me jab kisi
// neighbour par jaao:
// - Agar neighbour same letter ka hai aur wo PARENT nahi hai,
//   lekin pehle se VISITED hai, to cycle mil gaya - true!
// - Agar neighbour same letter ka hai aur unvisited hai, to
//   use queue me daal do (current cell uska parent banegi).
// Sab cells check karne ke baad bhi cycle na mile to false.
// Parent yaad rakhna ZAROORI hai - nahi to (A -> B -> A) wapas
// jaane ko bhi cycle samajh loge, jo galat hai.
//
// TIME COMPLEXITY: O(m * n) - har cell ek baar visit hoti hai
// SPACE COMPLEXITY: O(m * n) - visited array + queue
//
// KEY LEARNING:
// Undirected graph me cycle detect karne ka rule: visited node
// dobara mile AUR wo parent na ho = cycle. Parent ko track karna
// hi is problem ki jaan hai - bina parent ke har do cells ke
// beech ka wapas jaana bhi cycle lagega. Yehi idea graph wale
// cycle problems me bhi kaam aata hai.

import java.util.*;

class Solution {
    public boolean containsCycle(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j]) continue;
                // (x, y, parent_x, parent_y) - parent yaad rakhna zaroori hai
                visited[i][j] = true;
                Queue<int[]> q = new LinkedList<>();
                q.offer(new int[]{i, j, -1, -1});
                while (!q.isEmpty()) {
                    int[] cell = q.poll();
                    int x = cell[0], y = cell[1], px = cell[2], py = cell[3];
                    for (int[] d : dirs) {
                        int nx = x + d[0], ny = y + d[1];
                        if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                        if (grid[nx][ny] != grid[x][y]) continue;
                        // wahi cell jahan se aaye (parent) - ye backtrack hai, cycle nahi
                        if (nx == px && ny == py) continue;
                        if (visited[nx][ny]) {
                            // same letter, visited, aur parent nahi = CYCLE!
                            return true;
                        }
                        visited[nx][ny] = true;
                        q.offer(new int[]{nx, ny, x, y});
                    }
                }
            }
        }
        return false;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1: 'a' ka outer ring cycle banata hai
        char[][] grid1 = {{'a', 'a', 'a', 'a'},
                          {'a', 'b', 'b', 'a'},
                          {'a', 'b', 'b', 'a'},
                          {'a', 'a', 'a', 'a'}};
        System.out.println(s.containsCycle(grid1)); // Expected: true
        // Example 2 (LeetCode): 'c' ka cycle hai
        char[][] grid2 = {{'c', 'c', 'c', 'a'},
                          {'c', 'd', 'c', 'c'},
                          {'c', 'c', 'e', 'c'},
                          {'f', 'c', 'c', 'c'}};
        System.out.println(s.containsCycle(grid2)); // Expected: true
        // Example 3 (LeetCode): koi cycle nahi
        char[][] grid3 = {{'a', 'b', 'b'},
                          {'b', 'z', 'b'},
                          {'b', 'b', 'a'}};
        System.out.println(s.containsCycle(grid3)); // Expected: false
        // 2x2 same letter - chhota cycle (4 cells)
        char[][] grid4 = {{'a', 'a'}, {'a', 'a'}};
        System.out.println(s.containsCycle(grid4)); // Expected: true
        // single row - cycle ban hi nahi sakta
        char[][] grid5 = {{'a', 'a', 'a', 'a'}};
        System.out.println(s.containsCycle(grid5)); // Expected: false
    }
}
