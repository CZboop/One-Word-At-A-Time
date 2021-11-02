import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class ChainFileIO {

    public static void WriteMapToFile(Map<String, ArrayList<String>> toWriteMap, String filename){

        Properties properties = new Properties();
//       an additional level for reading and writing, just made the arraylist into a single string then split
//        again when reading and reassign
//        not serialised but could add that later
        Map<String, String> storableMap = new HashMap<>();
        for (var entry: toWriteMap.entrySet()){
            storableMap.put(entry.getKey(), String.join(",", entry.getValue()));
        }
        properties.putAll(storableMap);
        try {
            File fileObj = new File(filename + ".properties");
            if (fileObj.createNewFile()) {
                System.out.println("File created: " + fileObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            properties.store(new FileOutputStream(filename + ".properties"), null);
        } catch (IOException e) {
            System.out.println("An error occurred saving the file");
            e.printStackTrace();
        }

    }

    public static Map<String, ArrayList<String>> ReadMapFromFile(String filename){
//        remember need to handle that the array of strings has been turned into comma separated single string
        Map<String, ArrayList<String>> chainMap = new HashMap<String, ArrayList<String>>();
        Properties properties = new Properties();
//        add try and catch to check for file - also need to have some standardised file name/a variable that is same for
//        read write
        try {
            properties.load(new FileInputStream(filename + ".properties"));
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        for (String key : properties.stringPropertyNames()) {
            ArrayList<String> vals =new ArrayList<String>(Arrays.asList(properties.get(key).toString().split(",")));
            chainMap.put(key, vals);
        }
        return chainMap;
    }
}
