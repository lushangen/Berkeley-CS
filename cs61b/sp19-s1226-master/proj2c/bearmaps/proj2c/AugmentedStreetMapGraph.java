package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.TrieSET;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */

public class AugmentedStreetMapGraph extends StreetMapGraph {

    WeirdPointSet set;
    HashMap<Point, Node> between = new HashMap<>();
    List<Point> withNeighbors = new ArrayList<>();
    TST trie = new TST();
    TrieSET trie2 = new TrieSET();
    HashMap<String, List<Node>> st = new HashMap<>();

    /*public class MyTrieSet {
        private TrieNode root;
        private int size = 0;

        private class TrieNode {
            private Hashtable<Character, TrieNode> map = new Hashtable<>();
            private char c;
            private boolean isKey;
            public TrieNode(char c, boolean key) {
                this.c = c;
                this.isKey = key;
            }
        }

        public MyTrieSet() {
            root = new TrieNode('\u0000', false);
        }

        public void clear() {
            root = new TrieNode('\u0000', false);
        }
        public boolean contains(String key) {
            return get(key) != '\u0000';
        }
        private char get(String key) {
            if (key == null) {
                throw new IllegalArgumentException();
            }
            TrieNode x = get(root, key, 0);
            if (x == null) {
                return '\u0000';
            }
            return x.c;
        }
        private TrieNode get(TrieNode node, String key, int d) {
            if (node == null) {
                return null;
            }
            if (d == key.length()) {
                return node;
            }
            char c = key.charAt(d);
            return get(node.map.get(c), key, d + 1);
        }
        public List<String> keysWithPrefix(String prefix) {
            List<String> result = new LinkedList<String>();
            TrieNode x = get(root, prefix, 0);
            collect(x, new StringBuilder(prefix), result);
            return result;
        }
        private void collect(TrieNode node, StringBuilder prefix, List<String> result) {
            if (node == null) {
                return;
            }
            if (node.c != '\u0000') {
                result.add(prefix.toString());
            }
            for (char c = 0; c < 128; c++) {
                prefix.append(c);
                collect(node.map.get(c), prefix, result);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        public void add(String key) {
            if (key == null || key.length() < 1) {
                return;
            }
            TrieNode curr = root;
            for (int i = 0, n = key.length(); i < n; i++) {
                char c = key.charAt(i);
                if (!curr.map.containsKey(c)) {
                    curr.map.put(c, new TrieNode(c, false));
                }
                curr = curr.map.get(c);
            }
            curr.isKey = true;
        }

    }*/

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        for (Node node: nodes) {
            if (neighbors(node.id()).size() > 0) {
                Point point = new Point(node.lon(), node.lat());
                between.put(point, node);
                withNeighbors.add(point);
            }
        }
        this.set = new WeirdPointSet(withNeighbors);
        for (Node node: nodes) {
            if (node.name() != null) {
                List<Node> l = new ArrayList<>();
                String x = cleanString(node.name());
                if (st.containsKey(x)) {
                    st.get(x).add(node);
                } else {
                    l.add(node);
                    st.put(x, l);
                }
                if (x.length() != 0){
                    trie.put(x, 9);
                }
                trie2.add(x);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point p = set.nearest(lon, lat);
        Node n = between.get(p);
        return n.id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String pre = cleanString(prefix);
        List<String> t = new LinkedList<>();
        for (Object s: trie.keysWithPrefix(pre)) {
            t.add((String) s);
        }
        List<String> rv = new LinkedList<>();
        for (String s: t) {
            List<Node> list = st.get(s);
            for (Node k: list) {
                rv.add(k.name());
            }
        }
        return rv;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String location = cleanString(locationName);
        List<String> t = new LinkedList<>();
        for (Object s: trie2.keysThatMatch(location)) {
            t.add((String) s);
        }
        List<Map<String, Object>> rv = new LinkedList<>();
        for (String s: t) {
            List<Node> list = st.get(s);
            for (Node k: list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("lat", k.lat());
                map.put("lon", k.lon());
                map.put("name", k.name());
                map.put("id", k.id());
                rv.add(map);
            }
        }
        return rv;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
