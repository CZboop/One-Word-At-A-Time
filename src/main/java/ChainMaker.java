import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class ChainMaker {
    public static void main(String[] args) {

        System.out.println("\n" +
                "   _____ _____  ______ ______ _______ _____ _   _  _____  _____ \n" +
                "  / ____|  __ \\|  ____|  ____|__   __|_   _| \\ | |/ ____|/ ____|\n" +
                " | |  __| |__) | |__  | |__     | |    | | |  \\| | |  __| (___  \n" +
                " | | |_ |  _  /|  __| |  __|    | |    | | | . ` | | |_ |\\___ \\ \n" +
                " | |__| | | \\ \\| |____| |____   | |   _| |_| |\\  | |__| |____) |\n" +
                "  \\_____|_|  \\_\\______|______|  |_|  |_____|_| \\_|\\_____|_____/ \n" +
                "                                                                \n" + "-------------\n" +
                "WELCOME! HOPE YOU'RE READY TO CREATE A STORY WITH A COMPUTER, ONE WORD AT A TIME :)\n" +
                "SELECT A NUMBER TO CHOOSE WHICH AUTHOR/COLLECTION YOU WANT THE COMPUTER TO CREATE TEXT BASED ON.\n" +
                "THEN, WHEN YOU'RE READY, TYPE YOUR FIRST WORD AND PRESS ENTER.\n" +
                "THEN THE COMPUTER WILL ADD ONE TOO, AND SO ON UNTIL YOU WISH TO EXIT.\n" +
                "TYPE \".exit\" TO EXIT. \n" +
                "YOU CAN THEN SAVE THE STORY IF YOU LIKE IT!\n\n" +
                "TYPE A NUMBER IN THE TERMINAL TO SELECT AN OPTION:\n" +
                "1. COMBINATION\n2. WILLIAM SHAKESPEARE\n3. MARY SHELLEY\n4. CHARLES DICKENS\n5. H.G. WELLS\n" +
                "6. FRANZ KAFKA\n7. SYLVIA PLATH");

        String authorChoiceInput = getInput();
        String authorChosen = chooseAuthor(authorChoiceInput);
        Story thisStory = new Story("");
        while (true){
            String userWord = getInput();
            if (userWord.equals(".exit")){
                System.out.println("HERE'S YOUR COMPLETE STORY: \n" + thisStory.getTale() + "\nDO YOU WANT TO SAVE IT?\nY/N?");
                String answer = getInput();
                if (answer.equalsIgnoreCase("y")){
                    System.out.println("WHAT DO YOU WANT TO NAME YOUR STORY?");
                    String filename = getInput();
                    try {
                        File fileObj = new File(filename + ".txt");
                        if (fileObj.createNewFile()) {
                            System.out.println("File created: " + fileObj.getName());
                            try {
                                FileWriter fileWriter = new FileWriter(filename + ".txt");
                                fileWriter.write(thisStory.getTale());
                                fileWriter.close();
                                System.out.println("Successfully saved your story to the file.");
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("File already exists.");
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred saving the file");
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
                return;
            } else{
                thisStory.setTale(thisStory.getTale() + " " + userWord);
                runProgram(userWord, thisStory, authorChosen);
            }
        }
    }

    public static String chooseAuthor(String choiceNum){
        switch(choiceNum) {
            case "1":
                return "all";
            case "2":
                return "shakespeare";
            case "3":
                return "shelley";
            case "4":
                return "dickens";
            case "5":
                return "wells";
            case "6":
                return "kafka";
            case "7":
                return "plath";
            default:
                return "all";
        }
    }


    public static void runProgram(String userWord, Story storyObj, String filename){
        Map<String, ArrayList<String>> chainFromFile = ChainFileIO.ReadMapFromFile(filename);
        giveWord(chainFromFile, userWord, storyObj);
    }

    public static String giveWord(Map<String, ArrayList<String>> markovChain, String seed, Story storyObj){
        try{
            ArrayList<String> choiceList = markovChain.get(seed);
            String chosen = choiceList.get(new Random().nextInt(choiceList.size()));
            storyObj.setTale(storyObj.getTale() + " " + chosen);
            System.out.println(chosen);
            return chosen;
        } catch (NullPointerException e) {
            List<String> keysArray = new ArrayList<String>(markovChain.keySet());
            ArrayList<String> choiceList = markovChain.get(keysArray.get(new Random().nextInt(keysArray.size())));
            String chosen = choiceList.get(new Random().nextInt(choiceList.size()));
            storyObj.setTale(storyObj.getTale() + " " + chosen);
            System.out.println(chosen);
            return chosen;
        }

    }

    public static Map<String, ArrayList<String>> CreateMarkov(ArrayList<String> wordsList, String filename){
        Map<String, ArrayList<String>> markovMap = new HashMap<String, ArrayList<String>>();
//        takes tokenised list of words (includes their punctuation but may remove later)
//        iterates through list[:] and list[1:], gets which words follow what and adds to map
        List<String> one = wordsList.subList(0, wordsList.size()-1);
        List<String> two = wordsList.subList(1, wordsList.size());
        for (int i = 0; i < one.size(); i++){
            String word1 = one.get(i);
            String word2 = two.get(i);
            if (markovMap.containsKey(word1)){
//                append to values arraylist
                ArrayList<String> followingWords = markovMap.get(word1);
                followingWords.add(word2);
                markovMap.put(word1, followingWords);

            } else{
//                create key with array value
                markovMap.put(word1, new ArrayList<String>(Collections.singleton(word2)));
            }
        }
        ChainFileIO.WriteMapToFile(markovMap, filename);
        return markovMap;
    }

    public static ArrayList<String> CreateTokenisedWordList(String filename){
        ArrayList<String> wordsList = new ArrayList<>();
        try {
            File testText = new File("src/textfiles/" + filename);
            Scanner fileScanner = new Scanner(testText);
//            handle empty file
            while (fileScanner.hasNextLine()){
                String[] line = fileScanner.nextLine().split(" ");
                wordsList.addAll(Arrays.asList(line));
            }

        } catch (Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return wordsList;
    }

    public static String getInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static void assignAuthorChain(Author author, String[] filenamesToRead, String filenameToWrite){
        ArrayList<String> combinedWords = new ArrayList<>();
        for (String f : filenamesToRead){
            ArrayList<String> fileWords = CreateTokenisedWordList(f);
            combinedWords.addAll(fileWords);
        }
        Map<String, ArrayList<String>> chainMap = CreateMarkov(combinedWords, filenameToWrite);
        author.setChain(chainMap);
    }
}
