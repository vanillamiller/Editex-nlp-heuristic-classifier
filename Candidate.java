/** */
public class Candidate {

    private final String fullString;
    private Prediction firstWord;
    private Prediction secondWord;
    private double score=0;

    /**
     * A constructor for insantiating the candidate strings, with an empty
     *  first and second word which will be added by the processor
     * 
     * @param candidate: a candidate query string 
     */
    public Candidate(String candidate) { this.fullString = candidate; }

    /**
     * A constructor for the test set strings which will set the fullstring, first word and second word
     * defined by the test set. This was used to see if correct blendword predictions were indeed consisting 
     * of the same words.
     * 
     * @param candidate: a true blendword
     * @param firstWord: the true first word of the blend word
     * @param secondWord: the true second word of the blend word
     */
    public Candidate(String candidate, String firstWord, String secondWord) { 
        this.fullString = candidate; 
        this.firstWord=new Prediction(firstWord);
        this.secondWord=new Prediction(secondWord);
    }

    @Override
    /**
     * return the candidate word
     */
    public String toString() { return this.fullString; }

    /**
     * For comparing candidates to the candidatised test set
     */
    public boolean equals(Object other) {
        
        if (other instanceof Candidate){

            Candidate toCompare=(Candidate) other;
            return this.toString().equals(toCompare.toString());

        }

        return false;
    }

    public Prediction getSecondWord() { return this.secondWord; }

    /**
     * 
     * @return an informative output message used for verifying the algorthim on specific sets of words
     */
    public String output(){
        return this.fullString+"    | "+this.firstWord.getWord()+", "+this.secondWord.getWord()+" |  "+this.score;
    }

    /**
     * 
     * @param secondWord: will set the word the process thinks is the blend word's most likely suffix
     */
	public void setSecondWord(Prediction secondWord) { this.secondWord = secondWord; }

    /**
     * 
     * @return will get the word the process thinks is the blend word's most likely prefix
     */
	public Prediction getFirstWord() { return this.firstWord; }

    /**
     * 
     * @param firstWord: will set the word the process thinks is the blend word's most likely prefix
     */
	public void setFirstWord(Prediction firstWord) { this.firstWord = firstWord; }

    /**
     * 
     * @return will return the prefix as defined by the prefix range.
     */
    public String prefix() { return this.fullString.substring(0, 2*((int) (this.fullString.length()/3))); }

    /**
     * 
     * @return will return the suffix as defined by the suffix range.
     */
    public String suffix() { return this.fullString.substring((int) (this.fullString.length()/3)); }

    /**
     * 
     * @param score will set the score to be the average similarity of the firstword and second word
     */
    public void setScore(double score) { this.score = score; }

    /**
     * 
     * @return returns the assigned score which will be evaluated against some threshold
     */
    public double getScore() { return this.score; }
}