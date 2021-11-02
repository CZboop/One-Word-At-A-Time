import java.util.ArrayList;
import java.util.Map;

public class Author {
    private String name;
    private Map<String, ArrayList<String>> chain;
    private String chainFileName;
// may be able to change these parameters since now using files to store data

    public Map<String, ArrayList<String>> getChain() {
        return chain;
    }

    public Author(String name, Map<String, ArrayList<String>> chain, String chainFileName) {
        this.name = name;
        this.chain = chain;
        this.chainFileName = chainFileName;
    }

    public void setChain(Map<String, ArrayList<String>> chain) {
        this.chain = chain;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getChainFileName() {
        return chainFileName;
    }

    public void setChainFileName(String chainFileName) {
        this.chainFileName = chainFileName;
    }
}
