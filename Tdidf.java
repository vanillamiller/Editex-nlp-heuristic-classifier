import java.util.*;

public class Tdidf {

    private double tf(List<String> words, String term) {
        double result = 0;
        for (String word : words) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / words.size();
    }

    private double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

    public double tfIdf(List<String> words, List<List<String>> docs, String term) {

        return tf(words, term) * idf(docs, term);
        
    }

}