import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        // Build adjacency list for graph traversal
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] inv : invocations) {
            adj.get(inv[0]).add(inv[1]);
        }

        // Step 1: Identify all suspicious methods using DFS starting from k
        boolean[] isSuspicious = new boolean[n];
        dfs(k, adj, isSuspicious);

        // Step 2: Check if any non-suspicious method invokes a suspicious method
        boolean canRemove = true;
        for (int[] inv : invocations) {
            int u = inv[0];
            int v = inv[1];
            if (!isSuspicious[u] && isSuspicious[v]) {
                canRemove = false;
                break;
            }
        }

        // Step 3: Collect the remaining methods
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!canRemove || !isSuspicious[i]) {
                result.add(i);
            }
        }

        return result;
    }

    private void dfs(int node, List<List<Integer>> adj, boolean[] isSuspicious) {
        isSuspicious[node] = true;
        for (int neighbor : adj.get(node)) {
            if (!isSuspicious[neighbor]) {
                dfs(neighbor, adj, isSuspicious);
            }
        }
    }
}