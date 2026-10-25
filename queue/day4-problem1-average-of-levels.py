# Day 4 - Problem 1: Average of Levels in Binary Tree (LC 637) - Easy
# ============================================================
# PROBLEM (Hinglish):
# Ek binary tree diya hai. Har level ke nodes ka average nikaalo,
# aur averages ki list return karo (level 0 se shuru karke).
# Example: root=3, left=9, right=20, right ke children 15,7
#   -> [3.0, 14.5, 11.0]
#
# APPROACH (Hinglish):
# Queue se BFS karo (level-order traversal). Har level ke liye:
#   1. Queue ka current size = us level ke nodes ki count.
#   2. Utne nodes pop karo, sum jodo, unke children queue me daalo.
#   3. average = sum / count, list me daalo.
# Queue yahan isliye perfect hai kyunki BFS me nodes usi order me
# process hote hain jis order me level-wise aate hain (FIFO).
#
# TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
# SPACE COMPLEXITY: O(w) - w = sabse chaude level ki width (queue size)
#
# KEY LEARNING:
# Level-wise kaam = BFS = Queue. Level boundary ke liye har baar
# queue ka size snapshot lo, usi ko loop limit banao.

from collections import deque
from typing import List, Optional


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


class Solution:
    def averageOfLevels(self, root: Optional[TreeNode]) -> List[float]:
        if not root:
            return []
        result = []
        q = deque([root])
        while q:
            level_size = len(q)  # is level me kitne nodes hain
            level_sum = 0
            for _ in range(level_size):
                node = q.popleft()
                level_sum += node.val
                if node.left:
                    q.append(node.left)
                if node.right:
                    q.append(node.right)
            result.append(level_sum / level_size)
        return result


# TEST
if __name__ == "__main__":
    # Tree:     3
    #         /   \
    #        9    20
    #            /  \
    #           15   7
    root = TreeNode(3, TreeNode(9), TreeNode(20, TreeNode(15), TreeNode(7)))
    print(Solution().averageOfLevels(root))  # Expected: [3.0, 14.5, 11.0]
    print(Solution().averageOfLevels(None))  # Expected: []
