import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Bai04Lab01 {
    // Data structures for the Markov model
    private Map<String, List<String>> transitionMap;
    private Random random;
    private Set<String> vocabulary;
    
    // Constructor
    public Bai04Lab01() {
        transitionMap = new HashMap<>();
        vocabulary = new HashSet<>();
        random = new Random();
        loadDataset();
    }
    
    // Load and process UIT-ViOCD dataset
    private void loadDataset() {
        try {
            // Path to the UIT-ViOCD dataset file
            File datasetFile = new File("Bai thuc hanh 01/Tai lieu/IE303 P22 Practice 01.txt");
            
            // If the file doesn't exist at the specified path, search for it
            if (!datasetFile.exists()) {
                System.out.println("Dataset file not found at default location.");
                System.out.println("Please enter the full path to the UIT-ViOCD dataset:");
                Scanner scanner = new Scanner(System.in);
                String filePath = scanner.nextLine().trim();
                datasetFile = new File(filePath);
                
                if (!datasetFile.exists()) {
                    System.err.println("Dataset file not found. Using sample data for demonstration.");
                    useSampleData();
                    return;
                }
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(datasetFile));
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;
                
                String[] words = line.split("\\s+");
                
                for (String word : words) {
                    vocabulary.add(word.toLowerCase());
                }
                
                for (int i = 0; i < words.length - 1; i++) {
                    String currentWord = words[i].toLowerCase();
                    String nextWord = words[i + 1].toLowerCase();
                    
                    // Build transition map
                    if (!transitionMap.containsKey(currentWord)) {
                        transitionMap.put(currentWord, new ArrayList<>());
                    }
                    transitionMap.get(currentWord).add(nextWord);
                }
            }
            reader.close();
            
            System.out.println("Dataset loaded successfully.");
            System.out.println("Vocabulary size: " + vocabulary.size());
            System.out.println("Number of unique words with transitions: " + transitionMap.size());
            
        } catch (IOException e) {
            System.err.println("Error loading dataset: " + e.getMessage());
            System.err.println("Using sample data for demonstration.");
            useSampleData();
        }
    }
    
    // Use sample data if dataset is not available
    private void useSampleData() {
        String[] sampleSentences = {
            "xin chào các bạn hôm nay",
            "tôi đang học lập trình java",
            "việt nam là một quốc gia xinh đẹp",
            "học tập chăm chỉ để thành công",
            "trường đại học công nghệ thông tin"
        };
        
        for (String sentence : sampleSentences) {
            String[] words = sentence.split("\\s+");
            
            for (String word : words) {
                vocabulary.add(word);
            }
            
            for (int i = 0; i < words.length - 1; i++) {
                String currentWord = words[i];
                String nextWord = words[i + 1];
                
                if (!transitionMap.containsKey(currentWord)) {
                    transitionMap.put(currentWord, new ArrayList<>());
                }
                transitionMap.get(currentWord).add(nextWord);
            }
        }
        
        System.out.println("Sample data loaded successfully.");
        System.out.println("Vocabulary size: " + vocabulary.size());
        System.out.println("Number of unique words with transitions: " + transitionMap.size());
    }
    
    // Generate next word based on current word
    private String getNextWord(String currentWord) {
        // If the current word is not in our model or has no transitions, return null
        if (!transitionMap.containsKey(currentWord) || transitionMap.get(currentWord).isEmpty()) {
            return null;
        }
        
        // Get the list of possible next words
        List<String> possibleNextWords = transitionMap.get(currentWord);
        
        // Choose a random word from the list based on frequency
        return possibleNextWords.get(random.nextInt(possibleNextWords.size()));
    }
    
    // Generate a text sequence of up to 5 words
    public String generateText(String startWord) {
        if (startWord == null || startWord.isEmpty()) {
            return "Input word not provided";
        }
        
        startWord = startWord.toLowerCase();
        
        if (!vocabulary.contains(startWord)) {
            return "Input word not found in dataset";
        }
        
        StringBuilder result = new StringBuilder(startWord);
        String currentWord = startWord;
        
        // Generate up to 4 more words (total of 5 including the start word)
        for (int i = 0; i < 4; i++) {
            String nextWord = getNextWord(currentWord);
            
            if (nextWord == null) {
                break;
            }
            
            result.append(" ").append(nextWord);
            currentWord = nextWord;
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        Bai04Lab01 model = new Bai04Lab01();
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a Vietnamese word: ");
        String inputWord = scanner.nextLine().trim();
        scanner.close();
        
        String generatedText = model.generateText(inputWord);
        System.out.println("Generated text: " + generatedText);
    }
}
