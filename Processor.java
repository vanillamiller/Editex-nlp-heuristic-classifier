import java.util.*;

public class Processor {
    Editex edx;
    Tdidf tdidf;
  

    List<Candidate> predictions;
    Map<String, Map<Character, List<String>>> dictionary;

    public Processor(Map<String, Map<Character, List<String>>> dictionary) {
        this.edx = new Editex();
        this.dictionary = dictionary;
    }

    public void process(List<Candidate> candidates){

        long startMillis=System.currentTimeMillis();

        for(Candidate c : candidates){
            if(c.getScore() >= 0)
                this.makePrediction(c);
            }
            
        long endMillis=System.currentTimeMillis();
        long timeInMillis=endMillis-startMillis;
        System.out.println(candidates.size()+" candidates processed in "+(double)(timeInMillis/1000)+" seconds");
        
    }

    public void makePrediction(Candidate candidate) {

        Prediction pref=this.prefPrediction(candidate.prefix()), 
          suff=this.suffPrediction(candidate.suffix());

        

        if(pref.equals(suff)){

            candidate.setScore(0);

        }else{

            candidate.setFirstWord(pref);
            candidate.setSecondWord(suff);
            candidate.setScore((pref.getSimilarity()+suff.getSimilarity())/2);
            //System.out.println(pref.getSimilarity());
            //System.out.println(suff.getSimilarity());
            //System.out.println(candidate.output());

        }
    }

    private Prediction prefPrediction(String pref) {
        char c=pref.charAt(0);
        double maxSim=0, currSim=0;
        String best="";
        
        //List<Prediction> potentialMatches = new ArrayList<Prediction>();

        for (String word : dictionary.get("prefix").get(c)) {

            currSim = edx.similarity(pref, word);
            if (currSim > maxSim && word.length() > 2) {
                maxSim=currSim;
                best=word;
                }
        }
        //System.out.println(maxSim);
        return new Prediction(best, maxSim);

    }

    private Prediction suffPrediction(String suff) {
        char c = suff.charAt(suff.length()-1);
        double maxSim=0, currSim=0;
        String best="";

        //List<Prediction> potentialMatches = new ArrayList<Prediction>();

        for (String word : dictionary.get("suffix").get(c)) {

            currSim = edx.similarity(suff, word);
            if (currSim > maxSim && word.length() > 2) {
                maxSim=currSim;
                best=word;
            }
            //potentialMatches.add(new Prediction(word, edx.similarity(suff, word)));
        }

        //Collections.sort(potentialMatches, new PredictionComparator());
        //return potentialMatches.subList(0,10);
        //System.out.println(maxSim);
        return new Prediction(best, maxSim);
    }
}