package collections;

public class stringCharFunctions {
    public static void main(String[] args) {
        char ch = 'A';
        String str = "Ritik Bhateley";

        boolean isLetter = Character.isLetter('a'); // true

        boolean isDigit = Character.isDigit('5'); // true

        boolean isLetterOrDigit = Character.isLetterOrDigit('#'); // false

        boolean isUpperCase = Character.isUpperCase('A'); // true

        boolean isLowerCase = Character.isLowerCase('a'); // true

        char upperCase = Character.toUpperCase('a'); // 'A'

        char lowerCase = Character.toLowerCase('A'); // 'a'

        boolean isWhitespace = Character.isWhitespace(' '); // true

        String charString = Character.toString('a'); // "a"

        int comparison = Character.compare('a', 'b'); // Returns a negative integer (-1)

        int value = Character.digit('5', 10); // 5

        boolean isISOControl = Character.isISOControl('\u0007'); // true


    }
}
