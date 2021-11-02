import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssignAuthorData {
    public static void main(String[] args) {
        String[] shk = {"shakespeare.txt"};
        AuthorSetup("William Shakespeare", shk, "shakespeare");
        String[] maryshelley = {"frankenstein.txt"};
        AuthorSetup("Mary Shelley", maryshelley, "shelley");
        String[] wells = {"drmoreau.txt", "timemachine.txt", "waroftheworlds.txt"};
        AuthorSetup("H.G. Wells", wells, "wells");
        String[] kafka = {"metamorphosis.txt"};
        AuthorSetup("Franz Kafka", kafka, "kafka");
        String[] dickens = {"greatexpectations.txt", "twocities.txt", "xmascarol.txt"};
        AuthorSetup("Charles Dickens", dickens, "dickens");
        String[] plath = {"belljar.txt", "colossus.txt"};
        AuthorSetup("Sylvia Plath", plath, "plath");
        String[] all = {"shakespeare.txt", "frankenstein.txt","drmoreau.txt",
                "timemachine.txt", "waroftheworlds.txt",
                "metamorphosis.txt", "greatexpectations.txt", "twocities.txt", "xmascarol.txt",
                "belljar.txt", "colossus.txt",
        };
        AuthorSetup("All", all, "all");
    }

    public static void AuthorSetup(String name, String[] fileNamesToRead, String fileNameToWrite){
//        Map<String, ArrayList<String>> testMarkov = ChainMaker.CreateMarkov(testList, "test");

        Author sylvia = new Author(name, new HashMap<String, ArrayList<String>>(), null);
        ChainMaker.assignAuthorChain(sylvia, fileNamesToRead, fileNameToWrite);
    }

}
