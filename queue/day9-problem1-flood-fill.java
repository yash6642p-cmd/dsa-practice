// Day 9 - Problem 1: Flood Fill (LC 733) - Easy
// Topic: Queue (BFS on a 2D grid)
//
// PROBLEM (Hinglish):
// Ek m x n grid image hai, har cell ek pixel ka color hai. Tumhe
// starting pixel (sr, sc) se flood fill karna hai - matlab starting
// pixel ka color aur usse 4-direction (up, down, left, right) se
// jude saare SAME color ke pixels ko naye color se bhar do. Jaise
// MS Paint ka paint-bucket tool!
// Example: image = [[1,1,1],[1,1,0],[1,0,1]], sr=1, sc=1, color=2
//   -> [[2,2,2],[2,2,0],[2,0,1]]
//
// APPROACH (Hinglish):
// BFS queue se chalao: starting pixel ko queue me daalo, phir ek-ek
// karke nikalo. Jo pixel abhi bhi original color ka hai, use naya
// color do aur uske 4 neighbours ko queue me daal do (sirf woh jo
// abhi bhi original color ke hain).
// Edge case: agar naya color == original color, to seedha image
// wapas de do, warna infinite loop lag jaayega.
//
// TIME COMPLEXITY: O(m * n) - har pixel zyada se zyada ek baar
// SPACE COMPLEXITY: O(m * n) - worst case queue me saare pixels
//
// KEY LEARNING:
// Grid par BFS = queue + 4 directions ka array + boundary check.
// "Connected region" wale saare problems isi pattern se bante hain.

import java.util.*;

class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int original = image[sr][sc];
        if (original == color) {
            return image; // pehle se hi wahi color, kuch nahi karna
        }
        int rows = image.length, cols = image[0].length;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{sr, sc});
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int r = cell[0], c = cell[1];
            if (image[r][c] != original) {
                continue; // pehle hi rang diya ya alag color ka pixel
            }
            image[r][c] = color;
            for (int[] d : directions) {
                int nr = r + d[0], nc = c + d[1];
                // Boundary check + sirf original color wale neighbours
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && image[nr][nc] == original) {
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return image;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.deepToString(
            s.floodFill(new int[][]{{1,1,1},{1,1,0},{1,0,1}}, 1, 1, 2)));
        // Expected: [[2, 2, 2], [2, 2, 0], [2, 0, 1]]
        System.out.println(Arrays.deepToString(
            s.floodFill(new int[][]{{0,0,0},{0,0,0}}, 0, 0, 0)));
        // Expected: [[0, 0, 0], [0, 0, 0]]
        System.out.println(Arrays.deepToString(
            s.floodFill(new int[][]{{1,1},{1,1}}, 0, 0, 5)));
        // Expected: [[5, 5], [5, 5]]
    }
}
