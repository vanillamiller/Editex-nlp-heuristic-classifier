import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Candidate> candidates, testSet;
        Map< String, Map< Character, List<String>>> dictionary;
        PreProcessor preProcessor;
        Processor processor;
        Validator validator;

        try {
    
            preProcessor=new PreProcessor();
            dictionary=preProcessor.preProcessDictionary();
            testSet=preProcessor.preProcessTestSet();
            candidates=preProcessor.preProcessCandidates();
            

            processor=new Processor(dictionary);
            processor.process(candidates);

            validator=new Validator();
            validator.validate(candidates, testSet);

        } catch (Exception e) {
            System.out.println(e.getStackTrace()[0]); 
        }
        
    }


}   