import java.io.*;
import java.util.*;

public class PreProcessor {

    int maxBlendLength;

    /**
     * This function compartmentalises two dictionaries:
     * 
     *      one for prefixes, keyed by their starting character in alphabetical order
     *      under the assumption that all blend words start with the same char as their
     *      first component word
     * 
     *      one for suffixes, keyed by their ending character in alphabetical order
     *      under the assumption that all blend words end with the same character as their
     *      second component word
     * 
     * @return Returns two dictionaries that are compartmentalised alphabetically
     * @throws IOException
     * @throws FileNotFoundException
     */
    public Map<String, Map<Character, List<String>>> preProcessDictionary() throws IOException, FileNotFoundException {

        List<String> preProcessedDictionary = new ArrayList<String>();
        Reader reader = new FileReader("./2019S2-COMP90049_proj1-data_windows/dict.txt");

        Map<String, Map<Character, List<String>>> firstAndLast = new HashMap<String, Map<Character, List<String>>>();

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line = bufferedReader.readLine();
            while (line != null) {

                preProcessedDictionary.add(line);
                line = bufferedReader.readLine();
            }
        }

        long startMillis=System.currentTimeMillis();

        firstAndLast.put("prefix", this.divideDictionary(preProcessedDictionary, "first"));
        firstAndLast.put("suffix", this.divideDictionary(preProcessedDictionary, "last"));

        long endMillis=System.currentTimeMillis();
        long timeInMillis=endMillis-startMillis;
        System.out.println("dictionary partitioned in "+timeInMillis+" milliseconds");
        return firstAndLast;
    }

    /**
     * Helper function that compartmentalises the dictionary for preProcessDictionary()
     * based on 
     * @param dictionary Plaintext tokenised  dictionary
     * @param firstOrLast values can only be "first" or "last"
     * @return a compartmentalised dictionary indexed by the first or last character
     */
    private Map<Character, List<String>> divideDictionary
      (List<String> dictionary, String firstOrLast) {

        Map<Character, List<String>> dividedDictionary 
          = new HashMap<Character, List<String>>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char letter;
        int j = 0, index = 0;

       
        // System.out.println(index);
        
        for (int i = 0; i < alphabet.length(); i++) {

            letter = alphabet.charAt(i);
            dividedDictionary.put(letter, new ArrayList<String>());
            j = 0;

                while (j < dictionary.size()) {
                    
                    if(firstOrLast.equals("last"))
                        index = dictionary.get(j).length()-1;

                    if(dictionary.get(j).charAt(index) == letter)
                        dividedDictionary.get(letter).add(dictionary.get(j));  
                   
                    j++;
                }
        }
        
        return dividedDictionary;
    }

    /**
     * reads in plaintext file of tokenised candidate blend words and converts it to
     * a list of candidates
     * 
     * @return A list of candidates
     * @throws IOException
     * @throws FileNotFoundException
     */
    public List<Candidate> preProcessCandidates() throws IOException, FileNotFoundException {
        List<Candidate> preProcessedCandidates = new ArrayList<Candidate>();
        Reader reader = new FileReader("./2019S2-COMP90049_proj1-data_windows/candidates.txt");
        long startMillis=System.currentTimeMillis();
        Candidate notBlend;
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line = bufferedReader.readLine();
            while (line != null) {
                // do something with line
                if (line.length() > 2 && line.length() < maxBlendLength){
                    preProcessedCandidates.add(new Candidate(line));
                } else {
                    notBlend=new Candidate(line);
                    notBlend.setScore(-1);
                    preProcessedCandidates.add(notBlend);
                }
                line = bufferedReader.readLine();
            }
        }

        long endMillis=System.currentTimeMillis();
        long timeInMillis=endMillis-startMillis;
        System.out.println("candidates preprocessed in "+timeInMillis+" milliseconds");

        return preProcessedCandidates;
    }

    /**
     * 
     * @return 
     * @throws IOException
     * @throws FileNotFoundException
     */
    public List<Candidate> preProcessTestSet() throws IOException, FileNotFoundException{

        List<Candidate> processedTestSet = new ArrayList<Candidate>();
        Reader reader = new FileReader("./2019S2-COMP90049_proj1-data_windows/blends.txt");

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line = bufferedReader.readLine();

            while (line != null) {
                
                String [] tokens=line.split("\\s+");
                processedTestSet.add(new Candidate(tokens[0], tokens[1], tokens[2]));
                line = bufferedReader.readLine();

            }
        }
        this.setMaxBlendLength(processedTestSet);
        this.verifyFirstAndLastHeuristic(processedTestSet);
        this.verifySoundsLikeSuffixHeuristic(processedTestSet);

        return processedTestSet;
    }

    private void verifyFirstAndLastHeuristic(List<Candidate> testSet){

        double tf=0, ff=0, tl=0, fl=0, tt=0, at=0;
        int lindex, lindexLastWord;

        for(Candidate c : testSet){

            lindex=c.toString().length()-1;
            lindexLastWord=c.getSecondWord().getWord().length()-1;
            at=0;

            if(c.toString().charAt(0) == c.getFirstWord().getWord().charAt(0)){
                tf++;
                at++;
            } else {
                ff++;
            }

            if(c.toString().charAt(lindex) == c.getSecondWord().getWord().charAt(lindexLastWord)){
                tl++;
                at++;
            } else {
                fl++;
            }

            if(at==2){
                tt++;
            }
        }

        String trueFirst=String.format("%.2f",(tf/testSet.size())*100),
          trueLast=String.format("%.2f",(tl/testSet.size())*100),
          trueTotal=String.format("%.2f",(tt/testSet.size())*100);

        System.out.println("Degree to which blend words have: ");
        System.out.println("the first character of their first component"
          +" word and final character of their second component word: "+trueTotal+"%");
        System.out.println("the first character of their first component word: "
          +trueFirst+"%");
        System.out.println("the final character of their second component word: "
          +trueLast+"%");
        

    }

    private void verifySoundsLikeSuffixHeuristic(List<Candidate> testSet){
        
        double t=0, f=0;
        Editex edx=new Editex();
        String pref, suff, fullword;


        for(Candidate c : testSet){

            fullword=c.toString();
            pref=c.getFirstWord().getWord();
            suff=c.getSecondWord().getWord();

            if(edx.similarity(fullword, pref) < edx.similarity(fullword, suff)){
                t++;
            } else {
                f++;
            }
        }

        String adherence=String.format("%.2f", (t/(t+f))*100);
        System.out.println("Heuristic 2 adherence: "+adherence+"%");

    }

    private void setMaxBlendLength(List<Candidate> testSet){

        int curr=0, max=0;

        for(Candidate c : testSet){

            curr=c.toString().length();

            if(curr > max)
                max=curr;
        }

        this.maxBlendLength=max;
        System.out.println("The longest blendword is "+max+" characters long, all candidates longer than"+
            " this will be set to false");
    }
}