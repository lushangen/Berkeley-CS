package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int bottom;
    private int top;
    private boolean[] openness;
    private int count;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF backwash;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        count = 0;
        top = N * N;
        bottom = N * N + 1;
        grid = new WeightedQuickUnionUF(top + 2);
        backwash = new WeightedQuickUnionUF(top + 1);
        openness =  new boolean[top + 2];
        for (int i = 0; i < top; i++) {
            openness[i] = false;
        }
        openness[top] = true;
        openness[bottom] = true;
    }
    private int toOneD(int row, int col) {
        return ((row * N) + col);
    }
    public void open(int row, int col) {
        if (row > N || col > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            int pos = toOneD(row, col);
            openness[pos] = true;
            if (row == 0) {
                grid.union(top, pos);
                backwash.union(top, pos);
            }
            if (row == N - 1) {
                grid.union(pos, bottom);
            }
            if (row != 0 && isOpen(row - 1, col)) {
                grid.union(toOneD(row - 1, col), pos);
                backwash.union(toOneD(row - 1, col), pos);
            }
            if (row != N - 1 && isOpen(row + 1, col)) {
                grid.union(toOneD(row + 1, col), pos);
                backwash.union(toOneD(row + 1, col), pos);
            }
            if (col != 0 && isOpen(row, col - 1)) {
                grid.union(toOneD(row, col - 1), pos);
                backwash.union(toOneD(row, col - 1), pos);
            }
            if (col != N - 1 && isOpen(row, col + 1)) {
                grid.union(toOneD(row, col + 1), pos);
                backwash.union(toOneD(row, col + 1), pos);
            }
            count++;
        }
    }
    public boolean isOpen(int row, int col) {
        if (row > N || col > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int pos = toOneD(row, col);
        return openness[pos];
    }
    public boolean isFull(int row, int col) {
        if (row > N || col > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int pos = toOneD(row, col);
        return backwash.connected(top, pos);
    }
    public int numberOfOpenSites() {
        return count;
    }
    public boolean percolates() {
        return grid.connected(top, bottom);
    }
    public static void main(String[] args) {

    }

}
