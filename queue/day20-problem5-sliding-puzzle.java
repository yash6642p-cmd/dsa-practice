// Day 20 - Problem 5: Sliding Puzzle (LC 773) - Hard (stretch)
// Topic: Queue (BFS over board STATES - state-space BFS)
//
// PROBLEM (Hinglish):
// 2x3 board hai, tiles 1-5 aur ek khaali jagah (0). Ek move me 0 ko
// uske upar/neeche/left/right wale tile se swap kar sakte ho.
// Board ko solved state [[1,2,3],[4,5,0]] tak laane ke liye MINIMUM
// moves kitne lagenge? Impossible ho to -1 return karo.
// Example: [[1,2,3],[4,0,5]] -> 1 (5 ko left slide karo)
// Example: [[1,2,3],[5,4,0]] -> -1 (solve hi nahi ho sakta)
// Example: [[4,1,2],[5,0,3]] -> 5
//
// APPROACH (Hinglish):
// Board ki har arrangement ek STATE hai. Board ko ek string banao
// ("123405") aur BFS chalao states par: har state se 0 ko uske
// neighbours se swap karke nayi states banao. BFS level-by-level
// jaata hai, toh pehli baar target state mile to wahi minimum moves.
// 2x3 board ko 1D string me likhne par 0 ke neighbours fixed hain:
// index 0 -> [1,3], 1 -> [0,2,4], 2 -> [1,5], 3 -> [0,4], 4 -> [1,3,5], 5 -> [2,4]
// (ye neighbours table pehle se bana lo - baar baar 2D sochne ki zaroorat nahi)
//
// TIME COMPLEXITY: O(6!) = O(720) - kul 720 possible states, har state ek baar
// SPACE COMPLEXITY: O(6!) - visited set me states
//
// KEY LEARNING:
// "State ko node samjho" - jab board/configuration badal rahi ho, toh
// poori configuration ko ek string bana ke BFS karo. Yehi state-space
// BFS 8-puzzle, Rubik's cube jaise problems me bhi kaam aata hai.

import java.util.*;

class Solution {
    public int slidingPuzzle(int[][] board) {
        // 2x3 board ko ek string banao, e.g. "123405"
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int num : row) {
                sb.append(num);
            }
        }
        String start = sb.toString();
        String target = "123450";

        // har index ke neighbours (1D string me) - pehle se fixed table
        int[][] neighbours = {
            {1, 3},
            {0, 2, 4},
            {1, 5},
            {0, 4},
            {1, 3, 5},
            {2, 4}
        };

        // BFS states par
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        Set<String> visited = new HashSet<>();
        visited.add(start);
        int moves = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int k = 0; k < levelSize; k++) {
                String state = queue.poll();
                if (state.equals(target)) {
                    return moves;
                }
                // 0 ki position dhundho
                int zero = state.indexOf('0');
                // 0 ko har neighbour se swap karke nayi state banao
                for (int nb : neighbours[zero]) {
                    char[] arr = state.toCharArray();
                    char tmp = arr[zero];
                    arr[zero] = arr[nb];
                    arr[nb] = tmp;
                    String newState = new String(arr);
                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        queue.offer(newState);
                    }
                }
            }
            moves++;
        }

        return -1; // target kabhi nahi mila - unsolvable
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 - 1 move
        System.out.println(s.slidingPuzzle(new int[][]{{1, 2, 3}, {4, 0, 5}})); // Expected: 1
        // Example 2 - impossible
        System.out.println(s.slidingPuzzle(new int[][]{{1, 2, 3}, {5, 4, 0}})); // Expected: -1
        // Example 3 - 5 moves
        System.out.println(s.slidingPuzzle(new int[][]{{4, 1, 2}, {5, 0, 3}})); // Expected: 5
        // already solved - 0 moves
        System.out.println(s.slidingPuzzle(new int[][]{{1, 2, 3}, {4, 5, 0}})); // Expected: 0
    }
}
