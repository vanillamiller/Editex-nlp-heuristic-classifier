import java.util.*;

class Prediction {

    private String word;
    private double similarity;


    public Prediction(String word){ this.word=word; }

    public Prediction(String word, double similarity){
        this.word = word;
        this.similarity = similarity;
    }

    
    

    public String getWord() { return this.word; }

    public double getSimilarity() { return this.similarity; }

    public void setWord(String word) { this.word = word; }
}