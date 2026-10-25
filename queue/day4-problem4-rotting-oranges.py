# Day 4 - Problem 4: Rotting Oranges (LC 994) - Medium
# ============================================================
# PROBLEM (Hinglish):
# Grid me: 0 = khaali cell, 1 = fresh orange, 2 = rotten orange.
# Har minute me har rotten orange apne 4 neighbours (up/down/left/right)
# ke fresh oranges ko rotten kar deta hai. Sab fresh oranges ko
# rotten hone me kitne minutes lagenge? Agar koi fresh kabhi rotten
# na ho sake to -1 return karo.
# Example: [[2,1,1],[1,1,0],[0,1,1]] -> 4
#
# APPROACH (Hinglish):
# Multi-source BFS! Shuru me SAARE rotten oranges ko queue me daalo
# (ek source se nahi, sabse ek saath - kyunki sab ek saath failte hain).
# Har level = 1 minute. Har level me queue ke saare rotten cells ke
# neighbours ko rotten karo aur queue me daalo.
# End me: agar koi fresh bacha hai to -1, warna minutes count.
# Fresh count pehle se rakhna trick hai - end me poora grid dobara
# scan karne ki zaroorat nahi padti.
#
# TIME COMPLEXITY: O(m*n) - har cell ek baar visit hota hai
# SPACE COMPLEXITY: O(m*n) - worst case queue me saare cells
#
# KEY LEARNING:
# Multi-source BFS: jab kai starting points ek saath fail rahe hon,
# sabko shuru me hi queue me daalo. Har BFS level = 1 time step.
# Ye pattern grid problems (islands, walls-and-gates, shortest path)
# me baar-baar aata hai.

from collections import deque
from typing import List


class Solution:
    def orangesRotting(self, grid: List[List[int]]) -> int:
        rows, cols = len(grid), len(grid[0])
        q = deque()
        fresh = 0
        # Shuru me saare rotten queue me, fresh count karo
        for r in range(rows):
            for c in range(cols):
                if grid[r][c] == 2:
                    q.append((r, c))
                elif grid[r][c] == 1:
                    fresh += 1
        if fresh == 0:
            return 0  # pehle se koi fresh nahi

        minutes = 0
        directions = [(1, 0), (-1, 0), (0, 1), (0, -1)]
        while q and fresh > 0:
            minutes += 1  # ek level = ek minute
            for _ in range(len(q)):
                r, c = q.popleft()
                for dr, dc in directions:
                    nr, nc = r + dr, c + dc
                    # grid ke andar hai aur fresh hai?
                    if 0 <= nr < rows and 0 <= nc < cols and grid[nr][nc] == 1:
                        grid[nr][nc] = 2  # rotten kar do
                        fresh -= 1
                        q.append((nr, nc))
        return minutes if fresh == 0 else -1


# TEST
if __name__ == "__main__":
    s = Solution()
    print(s.orangesRotting([[2, 1, 1], [1, 1, 0], [0, 1, 1]]))  # Expected: 4
    print(s.orangesRotting([[2, 1, 1], [0, 1, 1], [1, 0, 1]]))  # Expected: -1
    print(s.orangesRotting([[0, 2]]))                            # Expected: 0
