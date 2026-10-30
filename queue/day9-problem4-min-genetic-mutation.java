// Day 9 - Problem 4: Minimum Genetic Mutation (LC 433) - Medium
// Topic: Queue (BFS - shortest path in an implicit graph)
//
// PROBLEM (Hinglish):
// Gene ek 8-character ki string hai, characters sirf 'A', 'C', 'G',
// 'T'. Ek mutation = ek character badalna. startGene se endGene
// tak pahunchna hai, lekin beech ka har gene bank me hona chahiye.
// Minimum kitne mutations lagenge? Na mumkin ho to -1.
// Example: start = "AACCGGTT", end = "AACCGGTA",
//   bank = ["AACCGGTA"] -> 1
//
// APPROACH (Hinglish):
// Har gene ko graph ka node samjho; do genes connected hain agar
// sirf ek character alag ho. BFS start se chalao kyunki BFS pehli
// baar end tak pahunchte hi SHORTEST path de deta hai. Har gene se
// uske saare 1-character mutations banao (8 positions x 4 letters),
// jo bank me ho aur pehle visit na hua ho, use queue me daalo.
// Shortcut: agar endGene bank me hi nahi hai to seedha -1.
//
// TIME COMPLEXITY: O(B * L * 4) - B = bank size, L = 8 (gene length)
// SPACE COMPLEXITY: O(B) - bank set + visited + queue
//
// KEY LEARNING:
// "Minimum steps / shortest transformation" dekhte hi BFS yaad
// rakho - DFS shortest guarantee nahi deta.

import java.util.*;

class Solution {
    public int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        if (!bankSet.contains(endGene)) {
            return -1; // manzil bank me hi nahi, kabhi nahi pahunchenge
        }
        Queue<String[]> q = new LinkedList<>(); // [gene, steps]
        q.offer(new String[]{startGene, "0"});
        Set<String> visited = new HashSet<>();
        visited.add(startGene);
        char[] letters = {'A', 'C', 'G', 'T'};

        while (!q.isEmpty()) {
            String[] cur = q.poll();
            String gene = cur[0];
            int steps = Integer.parseInt(cur[1]);
            if (gene.equals(endGene)) {
                return steps; // BFS: pehli mulaqat = shortest path
            }
            for (int i = 0; i < gene.length(); i++) {
                for (char ch : letters) {
                    if (ch == gene.charAt(i)) continue;
                    String mutated = gene.substring(0, i) + ch + gene.substring(i + 1);
                    // Sirf bank wale aur unvisited genes aage badhenge
                    if (bankSet.contains(mutated) && !visited.contains(mutated)) {
                        visited.add(mutated);
                        q.offer(new String[]{mutated, String.valueOf(steps + 1)});
                    }
                }
            }
        }
        return -1; // queue khaali, end tak koi raasta nahi
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.minMutation("AACCGGTT", "AACCGGTA",
            new String[]{"AACCGGTA"})); // Expected: 1
        System.out.println(s.minMutation("AACCGGTT", "AAACGGTA",
            new String[]{"AACCGGTA", "AACCGCTA", "AAACGGTA"})); // Expected: 2
        System.out.println(s.minMutation("AAAAACCC", "AACCCCCC",
            new String[]{"AAAACCCC", "AAACCCCC", "AACCCCCC"})); // Expected: 3
        System.out.println(s.minMutation("AACCGGTT", "AACCGGTA",
            new String[]{})); // Expected: -1
    }
}
