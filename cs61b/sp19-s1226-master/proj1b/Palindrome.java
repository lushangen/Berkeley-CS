public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> someWord = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            someWord.addLast(word.charAt(i));
        }
        return someWord;
    }
    public boolean isPalindrome(String word) {
        if ((word.length() == 1) || (word.length() == 0)) {
            return true;
        }
        return helperisPalindrome(word, 0);
    }
    private boolean helperisPalindrome(String word, int i) {
        int pointer = i + 1;
        int iinverse = word.length() - pointer;
        if (word.charAt(i) != word.charAt(iinverse)) {
            return false;
        }
        if (i >= word.length() / 2) {
            return true;
        }
        return helperisPalindrome(word, i + 1);
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return helperisPalindromeOffByOne(word, 0, cc);
    }
    private boolean helperisPalindromeOffByOne(String word, int i, CharacterComparator cc) {
        int pointer = i + 1;
        int iinverse = word.length() - pointer;
        if (!(cc.equalChars(word.charAt(i), word.charAt(iinverse))) && (i != iinverse)) {
            return false;
        }
        if (i >= Math.floorDiv(word.length(), 2)) {
            return true;
        }
        return helperisPalindromeOffByOne(word, i + 1, cc);
    }
}
