import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root;
    private int size = 0;

    private class Node {
        private Hashtable<Character, Node> map = new Hashtable<Character, Node>();;
        private char c;
        private boolean isKey;
        public Node(char c, boolean key) {
            this.c = c;
            this.isKey = key;
        }
    }

    public MyTrieSet() {
        root = new Node('\u0000', false);
    }

    @Override
    public void clear() {
        root = new Node('\u0000', false);
    }
    @Override
    public boolean contains(String key) {
        return get(key) != '\u0000';
    }
    private char get(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node x = get(root, key, 0);
        if (x == null) {
            return '\u0000';
        }
        return x.c;
    }
    private Node get(Node node, String key, int d) {
        if (node == null) {
            return null;
        }
        if (d == key.length()) {
            return node;
        }
        char c = key.charAt(d);
        return get(node.map.get(c), key, d + 1);
    }
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> result = new LinkedList<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), result);
        return result;
    }
    private void collect(Node node, StringBuilder prefix, List<String> result) {
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
    @Override
    public String longestPrefixOf(String key) {
        return null;
    }
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

}
