class Solution {
    static final int MOD = 1_000_000_007;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        long ans = 0;

        SegmentTree seg = new SegmentTree(n);
        int[] last = new int[n + 1];

        java.util.HashMap<Integer, Integer> lastSeen = new java.util.HashMap<>();

        for (int i = 0; i < n; i++) {
            int prev = lastSeen.getOrDefault(nums[i], -1);

            seg.update(prev + 1, i, 1);

            ans = (ans + seg.query(0, i)) % MOD;

            lastSeen.put(nums[i], i);
        }

        return (int) ans;
    }

    static class SegmentTree {
        int n;
        long[] sum;
        long[] lazy;
        long[] squareSum;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            squareSum = new long[4 * n];
            lazy = new long[4 * n];
        }

        void apply(int node, int l, int r, long val) {
            long len = r - l + 1L;

            squareSum[node] =
                (squareSum[node] + 2L * val % MOD * sum[node] % MOD
                + len % MOD * val % MOD * val % MOD) % MOD;

            sum[node] = (sum[node] + len * val) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
        }

        void push(int node, int l, int r) {
            if (lazy[node] == 0 || l == r) return;

            int mid = (l + r) / 2;

            apply(node * 2, l, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, r, lazy[node]);

            lazy[node] = 0;
        }

        void update(int node, int l, int r, int ql, int qr, long val) {
            if (ql > r || qr < l) return;

            if (ql <= l && r <= qr) {
                apply(node, l, r, val);
                return;
            }

            push(node, l, r);

            int mid = (l + r) / 2;

            update(node * 2, l, mid, ql, qr, val);
            update(node * 2 + 1, mid + 1, r, ql, qr, val);

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
            squareSum[node] =
                (squareSum[node * 2] + squareSum[node * 2 + 1]) % MOD;
        }

        long query(int node, int l, int r, int ql, int qr) {
            if (ql > r || qr < l) return 0;

            if (ql <= l && r <= qr) {
                return squareSum[node];
            }

            push(node, l, r);

            int mid = (l + r) / 2;

            return (query(node * 2, l, mid, ql, qr)
                  + query(node * 2 + 1, mid + 1, r, ql, qr)) % MOD;
        }

        void update(int l, int r, long val) {
            if (l <= r) update(1, 0, n - 1, l, r, val);
        }

        long query(int l, int r) {
            if (l > r) return 0;
            return query(1, 0, n - 1, l, r);
        }
    }
}