# Day 4 - Problem 3: Binary Tree Level Order Traversal (LC 102) - Medium
# ============================================================
# PROBLEM (Hinglish):
# Binary tree ka level-order traversal return karo, har level
# ek alag list me: [[level0], [level1], [level2], ...]
# Example: [3,9,20,null,null,15,7] -> [[3],[9,20],[15,7]]
#
# APPROACH (Hinglish):
# Queue se BFS. Har level ke liye:
#   1. level_size = queue ka current size (snapshot lo)
#   2. Utne nodes pop karo, unki values ek level-list me daalo,
#      unke children queue me daalo.
#   3. Level-list ko result me daalo.
# Snapshot zaroori hai kyunki loop ke andar queue badh raha hai
# (children add ho rahe hain) - bina snapshot ke levels mix ho jayenge.
#
# TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
# SPACE COMPLEXITY: O(w) - w = sabse chaude level ki width + result
#
# KEY LEARNING:
# BFS ka standard pattern: queue + level-size snapshot.
# Yehi pattern Problem 1 (average of levels) me bhi tha, bas wahan
# list ki jagah average nikaala tha. Pattern pehchano, dobara likhna
# aasan ho jayega. Interviews me ye sabse zyada poochha jaane wala
# tree pattern hai.

from collections import deque
from typing import List, Optional


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


class Solution:
    def levelOrder(self, root: Optional[TreeNode]) -> List[List[int]]:
        if not root:
            return []
        result = []
        q = deque([root])
        while q:
            level_size = len(q)
            level = []
            for _ in range(level_size):
                node = q.popleft()
                level.append(node.val)
                if node.left:
                    q.append(node.left)
                if node.right:
                    q.append(node.right)
            result.append(level)
        return result


# TEST
if __name__ == "__main__":
    root = TreeNode(3, TreeNode(9), TreeNode(20, TreeNode(15), TreeNode(7)))
    print(Solution().levelOrder(root))  # Expected: [[3], [9, 20], [15, 7]]
    print(Solution().levelOrder(None))  # Expected: []
