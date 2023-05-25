import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordNormalization {
    public static void main(String[] args) {
        System.out.println("Enter your Word!!!!!");
        Scanner scanner = new Scanner(System.in);
        String inputWord = scanner.nextLine();
        String outputWord = normalizeWord(inputWord);
        System.out.println("Normalized word: " + outputWord);
    }

    private static String normalizeWord(String word) {
        Map<String, String> normalizationRules = createNormalizationRules();

        if (word.endsWith("s")) {
            word = word.substring(0, word.length() - 1);
        }

        if (normalizationRules.containsKey(word)) {
            return normalizationRules.get(word);
        }

        int minDistance = Integer.MAX_VALUE;
        String mostSimilarWord = "";

        for (String dictWord : normalizationRules.keySet()) {
            int distance = calculateLevenshteinDistance(word, dictWord);
            if (distance < minDistance) {
                minDistance = distance;
                mostSimilarWord = dictWord;
            }
        }

        return normalizationRules.getOrDefault(mostSimilarWord, word);
    }

    //REGEX I WILL USE IN FUTURE FOR MORE PERFECTED

    private static int calculateLevenshteinDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int insert = dp[i][j - 1] + 1;
                    int delete = dp[i - 1][j] + 1;
                    int replace = dp[i - 1][j - 1] + 1;
                    dp[i][j] = Math.min(Math.min(insert, delete), replace);
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    private static Map<String, String> createNormalizationRules() {
        Map<String, String> normalizationRules = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/artyom7577/Desktop/JavaOOP/TextFormat/src/words_alpha.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                normalizationRules.put(trimmedLine, trimmedLine);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the normalization rules file: " + e.getMessage());
        }

        return normalizationRules;
    }
}
