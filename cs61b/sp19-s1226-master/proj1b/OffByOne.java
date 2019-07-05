public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int z = Math.abs(x - y);
        return (z == 1);
    }
}
