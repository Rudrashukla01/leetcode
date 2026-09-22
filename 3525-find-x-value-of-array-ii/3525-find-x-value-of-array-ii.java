class Solution {
    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            cnt = new int[k];
        }
    }

    int n, k;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;

        tree = new Node[4 * n];
        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            update(1, 0, n - 1, index, value);

            Node res = query(1, 0, n - 1, start, n - 1);

            ans[i] = res.cnt[x];
        }

        return ans;
    }

    void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            tree[node] = new Node(k);

            int v = nums[l] % k;
            tree[node].prod = v;
            tree[node].cnt[v] = 1;

            return;
        }

        int mid = (l + r) >>> 1;

        build(node << 1, l, mid, nums);
        build(node << 1 | 1, mid + 1, r, nums);

        tree[node] = merge(tree[node << 1], tree[node << 1 | 1]);
    }

    void update(int node, int l, int r, int idx, int value) {
        if (l == r) {
            tree[node] = new Node(k);

            int v = value % k;
            tree[node].prod = v;
            tree[node].cnt[v] = 1;

            return;
        }

        int mid = (l + r) >>> 1;

        if (idx <= mid) {
            update(node << 1, l, mid, idx, value);
        } else {
            update(node << 1 | 1, mid + 1, r, idx, value);
        }

        tree[node] = merge(tree[node << 1], tree[node << 1 | 1]);
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) >>> 1;

        if (qr <= mid) {
            return query(node << 1, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node << 1 | 1, mid + 1, r, ql, qr);
        }

        Node left = query(node << 1, l, mid, ql, qr);
        Node right = query(node << 1 | 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    Node merge(Node a, Node b) {
        Node res = new Node(k);

        res.prod = (a.prod * b.prod) % k;

        for (int i = 0; i < k; i++) {
            res.cnt[i] += a.cnt[i];
        }

        for (int i = 0; i < k; i++) {
            int newRem = (a.prod * i) % k;
            res.cnt[newRem] += b.cnt[i];
        }

        return res;
    }
}