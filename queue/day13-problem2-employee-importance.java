// Day 13 - Problem 2: Employee Importance (LC 690) - Easy
// Topic: Queue (BFS on a hierarchy graph)
//
// PROBLEM (Hinglish):
// Har employee ki id, importance value, aur subordinates ki list di
// gayi hai. Ek employee ki id di gayi hai - uski aur uske saare
// (direct + indirect) subordinates ki total importance return karo.
// Example: employees = [[1,5,[2,3]],[2,3,[]],[3,3,[]]], id = 1 -> 11
//
// APPROACH (Hinglish):
// Pehle id -> employee ka map banao. Phir di gayi id se BFS shuru karo:
// queue me employee daalo, uski importance total me jodo, aur uske
// subordinates ko queue me daalo. Queue khaali hote hi total answer hai.
//
// TIME COMPLEXITY: O(n) - har employee ek baar visit hota hai
// SPACE COMPLEXITY: O(n) - map aur queue ke liye
//
// KEY LEARNING:
// Kisi bhi hierarchy (org chart) par BFS aise hi chalta hai, aur pehle
// map banana lookup ko O(1) bana deta hai.
//
// NOTE: LeetCode par Employee class pehle se defined hoti hai, isliye
// submit karte waqt sirf Solution class paste karo (Employee class
// dobara paste mat karo).

import java.util.*;

// Local testing ke liye Employee definition (LeetCode par pre-defined hai)
class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
    Employee(int id, int importance, List<Integer> subordinates) {
        this.id = id;
        this.importance = importance;
        this.subordinates = subordinates;
    }
}

class Solution {
    public int getImportance(List<Employee> employees, int id) {
        // id se employee turant dhoondhne ke liye map
        Map<Integer, Employee> empMap = new HashMap<>();
        for (Employee e : employees) {
            empMap.put(e.id, e);
        }
        int total = 0;
        Queue<Integer> q = new LinkedList<>();
        q.offer(id);
        while (!q.isEmpty()) {
            int curId = q.poll();
            Employee emp = empMap.get(curId);
            total += emp.importance; // is employee ki importance jodo
            for (int subId : emp.subordinates) {
                q.offer(subId); // subordinates ko queue me daalo
            }
        }
        return total;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Employee> emps = Arrays.asList(
            new Employee(1, 5, Arrays.asList(2, 3)),
            new Employee(2, 3, new ArrayList<>()),
            new Employee(3, 3, new ArrayList<>()));
        System.out.println(s.getImportance(emps, 1)); // Expected: 11
        System.out.println(s.getImportance(emps, 2)); // Expected: 3
        System.out.println(s.getImportance(
            Arrays.asList(new Employee(1, 15, new ArrayList<>())), 1)); // Expected: 15
    }
}
