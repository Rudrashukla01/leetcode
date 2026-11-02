class Solution {

    int[] parent;
    int[] rank;

    public int makeConnected(int n, int[][] connections) {

        if (connections.length < n - 1) {
            return -1;
        }

        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        for (int[] edge : connections) {
            union(edge[0], edge[1]);
        }

        int components = 0;

        for (int i = 0; i < n; i++) {
            if (parent[i] == i) {
                components++;
            }
        }

        return components - 1;
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    void union(int x, int y) {

        int px = find(x);
        int py = find(y);

        if (px == py) {
            return;
        }

        if (rank[px] < rank[py]) {
            parent[px] = py;
        } 
        else if (rank[px] > rank[py]) {
            parent[py] = px;
        } 
        else {
            parent[py] = px;
            rank[px]++;
        }
    }
}