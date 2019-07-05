public class UnionFind {

    private int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        int x = parent.length;
        if (vertex >= x) {
            throw new IllegalArgumentException("v1 is too big");
        }
        if (vertex < 0) {
            throw new IllegalArgumentException("v1 is too small");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return (-1 * parent(find(v1)));
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        if (find(v1) != (find(v2))) {
            if (sizeOf(find(v1)) >= sizeOf(find(v2))) {
                parent[find(v2)] += parent[find(v1)];
                parent[find(v1)] = find(v2);
            } else {
                parent[find(v1)] += parent[find(v2)];
                parent[find(v2)] = find(v1);
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (parent[vertex] < 0) {
            return vertex;
        }
        while (parent[vertex] > 0) {
            vertex = parent[vertex];
        }
        return vertex;
    }

}
