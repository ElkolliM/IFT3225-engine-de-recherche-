package com.tfidf;


import java.util.List;

//<editor-fold defaultstate="collapsed" desc="TFIDF calculator">
/**
 * calculer les TfIdf des terms.
 * @par montassar el kolli
 */
public class TfIdf {
    
    //<editor-fold defaultstate="collapsed" desc="TF Calculator">
    /**
     *  calculer les tf des terms a verifier
     * @param totalterms : Array of all the words under processing document
     * @param termToCheck : term of which tf is to be calculated.
     * @return tf(term frequency) of term termToCheck
     */
    public double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count / totalterms.length;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Idf Calculator">
    /**
     * calculer les idf pour les terms a verifier
     * @param termToCheck
     * @retourner les idf(inverse document frequency) 
     */
    public double idfCalculator(List<String[]> allTerms, String termToCheck) {
        double count = 0;
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        return Math.log(allTerms.size() / count);
    }
//</editor-fold>
}
//</editor-fold>
