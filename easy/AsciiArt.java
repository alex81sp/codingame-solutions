/* https://www.codingame.com/training/easy/ascii-art */

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

class AsciiArt {

    private static final int NUMBER_OF_CHARACTERS = 27;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        final int length = in.nextInt();
        final int height = in.nextInt();
        if (in.hasNextLine())
            in.nextLine();
        String text = in.nextLine();

        char[][] charactersAsciiArt = fetchCharactersAsciiArt(length, height, in);
        char[][] asciiArt = textToAsciiArt(text, length, height, charactersAsciiArt);

        Stream.of(asciiArt).flatMap(Stream::of).forEach(System.out::println);
    }

    private static char[][] fetchCharactersAsciiArt(final int length, final int height, Scanner in) {
        final char[][] characters = new char[height][length * NUMBER_OF_CHARACTERS];
        for (int i = 0; i < height; i++) {
            characters[i] = (in.nextLine()).toCharArray();
        }
        return characters;
    }

    private static char[][] textToAsciiArt(final String text, final int length, final int height,
            final char[][] charactersAsciiArt) {
        final int colPosOfQuestionmark = (length * NUMBER_OF_CHARACTERS) - length;
        final Function<Character, Integer> colPos = (c) -> (c - 97) * length;

        final char[] textCharacters = text.toLowerCase().replaceAll("[^a-z]", "?").toCharArray();
        char[][] result = new char[height][length * text.length()];

        for (int iTextCharacter = 0; iTextCharacter < textCharacters.length; iTextCharacter++) {
            final int colInitialTextCharacter = (iTextCharacter * length);
            int colCurrentTextCharacter = 0;
            int colPosition = (textCharacters[iTextCharacter] == '?')
                    ? colPosOfQuestionmark
                    : colPos.apply(textCharacters[iTextCharacter]);
            for (int col = colPosition; col < colPosition + length; col++) {
                for (int row = 0; row < height; row++) {
                    result[row][colInitialTextCharacter + colCurrentTextCharacter] = charactersAsciiArt[row][col];
                }
                colCurrentTextCharacter++;
            }
        }
        return result;
    }
}
