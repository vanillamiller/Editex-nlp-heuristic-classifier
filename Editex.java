/**
 * This class is based on the following paper:
 * 
 *      Zobel, J. and Dart, P., 1996, August. Phonetic string matching: Lessons from information retrieval. 
 *      In Proceedings of the 19th annual international ACM SIGIR conference on Research and development in 
 *      information retrieval (pp. 166-172). ACM.
 * 
 * The correctness of this algorithm was validated using the python library as a test oracle:
 *      
 *      classpy_stringmatching.similarity_measure.editex.Editex(match_cost=0, group_cost=1, mismatch_cost=2, local=False)
 * 
 *      which can be found at: https://anhaidgroup.github.io/py_stringmatching/v0.3.x/Editex.html
 */

import java.lang.Math;

public class Editex {

    private final int matchCost=0, groupCost=1, misMatchCost=2;
    private final String [] groups={ "aeiouy","bp","ckq","dt", "mn","gj","fpv","sxz","csj" };
    
    /**
     * This function computes the distance between the candidate word and words in the dictionary 
     * 
     * @param candidate: candidate potential blend word
     * @param word: words in the dictionary
     * @return The distance between the two words
     */
    private int rawScore(String candidate, String word){   
        String cpad=' '+candidate, wpad=' '+word;
        int candLen=candidate.length(), wordLen=word.length();
        int [] [] vector = new int[candLen + 1][wordLen + 1];

        for (int i=1; i<candLen+1; i++){
            vector[i][0] = vector[i-1][0] 
                + this.dCost(cpad.charAt(i-1),cpad.charAt(i));
        }

        for (int i=1; i<wordLen+1; i++){
            vector[0][i] =  vector[0][i-1] 
                + dCost(wpad.charAt(i-1),wpad.charAt(i));
        }

        for (int i=1; i<candLen+1; i++){
            for(int j=1; j<wordLen+1; j++) {
                vector[i][j]= Math.min(vector[i-1][j]+this.dCost(cpad.charAt(i-1), cpad.charAt(i)),
                 Math.min(vector[i][j-1]+this.dCost(wpad.charAt(j-1), wpad.charAt(j)),
                 vector[i-1][j-1]+this.dCost(cpad.charAt(i), wpad.charAt(j))));
            }
        }

        return vector[candLen][wordLen];
    }

    /**
     * Computes the normalised similarity of the editex raw score
     * 
     * @param candidate: 
     * @param word
     * @return
     */
    public double similarity(String candidate, String word){

        double score=this.rawScore(candidate, word);
        int cLen=candidate.length(), wLen=word.length();

        return 1.0 - (score/Math.max(cLen * this.misMatchCost, wLen * this.misMatchCost));
        
    }

    /**
     * 
     * @param a
     * @param b
     * @return
     */
    private int rCost(char a, char b){
        
        if (a == b) 
            return this.matchCost;
        
        for (String i : groups){
            if (i.indexOf(a) >= 0 && i.indexOf(b) >= 0)
                return this.groupCost;
        }

        return this.misMatchCost;
    }

    public int dCost(char a, char b){
        if (a != b && (a == 'h' || a == 'w'))
            return this.groupCost;
        
        return this.rCost(a,b);
    }

    

    
}