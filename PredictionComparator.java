import java.util.*;

public class PredictionComparator implements Comparator<Prediction> {

    public int compare(Prediction a, Prediction b) { 
        if(a.getSimilarity() > b.getSimilarity())
            return -1;

        if(a.getSimilarity() < b.getSimilarity())
            return 1;

        return 0;
        
    }
}