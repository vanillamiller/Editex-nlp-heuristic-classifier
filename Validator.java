import java.util.*;

class Validator {

    private double threshold = 0.9;
    public void validate(List<Candidate> candidates, List<Candidate> testSet){

        double correct=0, falsePos=0, falseNeg=0, all=0;
        double precision, recall;

        for(Candidate c : candidates){

            if (c.getScore() >= threshold && testSet.contains(c)){
                //System.out.println("correct");
                correct++;
            } else if (c.getScore() >= threshold && !testSet.contains(c)){
                //System.out.println("falsep");
                falsePos++;
            } else{
                //System.out.println("falsen");
                falseNeg++;
            }
        }

        //System.out.println(correct);
        //System.out.println(falseNeg+falsePos+falseNeg);
        precision=correct/(correct+falsePos+falseNeg);
        recall=correct/testSet.size();

        System.out.println("total precision over "+candidates.size()+" candidates: "+precision);
        System.out.println("recall: "+recall);
    }


}