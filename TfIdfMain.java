package com.tfidf;


import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @par montassar el kolli
 */
public class TfIdfMain {
    
    /**
     * Main method
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        DocumentParser dp = new DocumentParser();
        dp.parseFiles("C:\\Users\\montassar\\Desktop\\montassar_kolli\\com\\tfidf\\input");
        
        dp.tfIdfCalculator(); //calculer les tf idf
        dp.getCosineSimilarity(); //calcul de similarité entre les documents    
    }
}
