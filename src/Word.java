import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.Normalizer;
import java.util.*;

public class Word {

    public static void main(String[] args) {


        String file1Path = "1.txt"; // write here your absolute path for "1.txt";
        String file2Path = "2.txt"; // write here your absolute path for "2.txt";
        String dbFilePath = "db.txt"; // write here your absolute path for "db.txt";


        // NEW FILE FOR NORMALIZED WORDS!
        String newDbFilePath = "newDbFile.txt"; // write here your absolute path for "db.txt";


        try {
            Map<String, List<Integer>> wordMap = new HashMap<>();

            parseFile(file1Path, 1, wordMap);
            parseFile(file2Path, 2, wordMap);

            writeToFile(dbFilePath, wordMap);
            System.out.println("Parsing completed successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while parsing the files: " + e.getMessage());
        }

        normalizeAndWriteToFile(dbFilePath, newDbFilePath);
    }

    public static void parseFile(String filePath, int fileNumber, Map<String, List<Integer>> wordMap)
            throws IOException {
        Set<String> uniqueWords = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

                    if (!word.isEmpty() && uniqueWords.add(word)) {
                        List<Integer> fileNumbers = wordMap.computeIfAbsent(word, k -> new ArrayList<>());
                        if (!fileNumbers.contains(fileNumber)) {
                            fileNumbers.add(fileNumber);
                        }
                    }
                }
            }
        }
    }

    public static void writeToFile(String filePath, Map<String, List<Integer>> wordMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, List<Integer>> entry : wordMap.entrySet()) {
                String word = entry.getKey();
                List<Integer> fileNumbers = entry.getValue();


                StringBuilder sb = new StringBuilder(word);
                sb.append(':');
                for (int i = 0; i < fileNumbers.size(); i++) {
                    sb.append(fileNumbers.get(i));
                    if (i < fileNumbers.size() - 1) {
                        sb.append(',');
                    }
                }

                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }

    public static void normalizeAndWriteToFile(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            Set<String> uniqueWords = new HashSet<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    String normalizedWord = normalizeWord(word);

                    if (!normalizedWord.isEmpty() && !normalizedWord.equals(":") && uniqueWords.add(normalizedWord)) {
                        writer.write(normalizedWord + " ");
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String normalizeWord(String word) {
        StringBuilder sb = new StringBuilder();


        for (char c : word.toCharArray()) {
            if (c != ':') {
                sb.append(c);
            }
        }

        return Normalizer.normalize(sb.toString(), Normalizer.Form.NFC).toLowerCase()
                .replaceAll("[^a-zA-Z ]", "")
                .replaceAll("\\d", "");
    }

}
