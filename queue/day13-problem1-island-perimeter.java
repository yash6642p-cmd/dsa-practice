// Day 13 - Problem 1: Island Perimeter (LC 463) - Easy
// Topic: Queue (grid BFS/counting)
//
// PROBLEM (Hinglish):
// Ek grid diya hai jisme 1 = land aur 0 = water hai. Grid me exactly ek
// island hai. Island ka perimeter return karo. Har land cell ki 4 sides
// hoti hain; jo side water se ya grid ke bahar se lagti hai, woh
// perimeter me gini jayegi.
// Example: grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]] -> output 16
//
// APPROACH (Hinglish):
// Har land cell ke liye 4 se shuru karo, aur har land padosi ke liye
// 1 ghata do (woh side andar chhup jati hai). Sab cells ka yog hi
// perimeter hai.
//
// TIME COMPLEXITY: O(m*n) - har cell ek baar dekha jata hai
// SPACE COMPLEXITY: O(1) - extra space nahi chahiye
//
// KEY LEARNING:
// Grid problems me har cell ke 4 padosi check karna ek basic pattern hai.

class Solution {
    public int islandPerimeter(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int perimeter = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    perimeter += 4; // is cell ki 4 sides
                    if (r > 0 && grid[r - 1][c] == 1) perimeter--; // upar land
                    if (r < rows - 1 && grid[r + 1][c] == 1) perimeter--; // neeche land
                    if (c > 0 && grid[r][c - 1] == 1) perimeter--; // left land
                    if (c < cols - 1 && grid[r][c + 1] == 1) perimeter--; // right land
                }
            }
        }
        return perimeter;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.islandPerimeter(new int[][]{
            {0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}})); // Expected: 16
        System.out.println(s.islandPerimeter(new int[][]{{1}})); // Expected: 4
        System.out.println(s.islandPerimeter(new int[][]{{1, 0}})); // Expected: 4
    }
}
