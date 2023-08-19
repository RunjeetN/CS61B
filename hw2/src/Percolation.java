
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int N;
    private WeightedQuickUnionUF p;
    private WeightedQuickUnionUF t;
    private boolean[] ids;
    private int numOpen;
    private int virtualTop;
    private int virtualBtm;
    private int virtualTop2;


    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        this.N = N;
        p = new WeightedQuickUnionUF(N * N + 2);
        t = new WeightedQuickUnionUF(N * N + 1);
        ids = new boolean[N * N];
        numOpen = 0;
        virtualTop = N * N;
        virtualBtm = N * N + 1;
        virtualTop2 = N * N;
        connectVirtuals();
        connectVirtuals2();
    }

    public void open(int row, int col) {
        if (!ids[rcToID(row, col)]) {
            ids[rcToID(row, col)] = true;
            updateConnectedness(row, col);
            numOpen++;
        }
        if (row == N - 1) {
            p.union(rcToID(row, col), virtualBtm);
        }
    }
    public boolean isOpen(int row, int col) {
        return (ids[rcToID(row, col)]);
    }

    public boolean isFull(int row, int col) {
        if (ids[rcToID(row, col)]) {
            return t.connected(rcToID(row, col), virtualTop2);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        if (N == 1) {
            return p.connected(virtualTop, virtualBtm) && ids[rcToID(0, 0)];
        }
        return p.connected(virtualTop, virtualBtm);
    }
    public int rcToID(int row, int col) {
        if (row < 0) {
            return rcToID(0, col);
        }
        if (col < 0) {
            return rcToID(row, 0);
        }
        if (row > N - 1) {
            return rcToID(N - 1, col);
        }
        if (col > N - 1) {
            return rcToID(row, N - 1);
        }
        return row * N + col;
    }
    public void updateConnectedness(int row, int col) {
        if (isOpen(row - 1, col)) {
            p.union(rcToID(row, col), rcToID(row - 1, col));
            t.union(rcToID(row, col), rcToID(row - 1, col));
        }
        if (isOpen(row + 1, col)) {
            p.union(rcToID(row, col), rcToID(row + 1, col));
            t.union(rcToID(row, col), rcToID(row + 1, col));
        }
        if (isOpen(row, col - 1)) {
            p.union(rcToID(row, col), rcToID(row, col - 1));
            t.union(rcToID(row, col), rcToID(row, col - 1));
        }
        if (isOpen(row, col + 1)) {
            p.union(rcToID(row, col), rcToID(row, col + 1));
            t.union(rcToID(row, col), rcToID(row, col + 1));
        }
    }
    public boolean isTopRow(int row, int col) {
        if (row == N - 1 && !(col >= N || col < 0)) {
            return true;

        }
        return false;

    }
    public void connectVirtuals() { // union virtualTop to the top row in p
        for (int i = 0; i < N; i++) {
            p.union(virtualTop, i);
        }
    }
    public void connectVirtuals2() { // union top row to virtualTop2 in t
        for (int i = 0; i < N; i++) {
            t.union(virtualTop2, i);

        }

    }
}

