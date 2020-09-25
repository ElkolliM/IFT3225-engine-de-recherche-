package com.tfidf;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.math.RoundingMode;
import java.text.DecimalFormat;
/**
 * lire les doccuments
 *
 * @par montassar El kolli
 */
public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();
    private List<String> fileNames = new ArrayList<String>();
    /**
     * lire les fichier et les stocker dans tableau.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(String filePath) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
                fileNames.add(f.getName());
            }
        }

    }

    /**
     * cree les terms vercteurs.
     * @throws IOException 
     */
    public void tfIdfCalculator() throws IOException {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency   
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        File fout = new File("tfidf_out.txt");
		
    	FileOutputStream fos = new FileOutputStream(fout);
		
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	int index = 0;
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                System.out.println("File : "+fileNames.get(index)+" \t Term : "+terms+" \t tf : "+df.format(tf)+" \t idf : "+df.format(idf)+" \t tfidf : "+df.format(tfidf));
                count++;
                bw.write("File@@"+fileNames.get(index)+"@@Term@@"+terms+"@@tf : "+df.format(tf)+"@@idf : "+df.format(idf)+"@@tfidf : "+df.format(tfidf));
        		bw.newLine();
            }
           index ++;
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;   
        }
        bw.close();
    }

    /**
     * calcule de similarité entre les documents ( facultatif ) .
     */
    public void getCosineSimilarity() {
    	DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
       
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            for (int j = 0; j < tfidfDocsVector.size(); j++) {
                System.out.println("CosineSimilarity between " + fileNames.get(i) + " and " + fileNames.get(j) + "  =  "
                                   + df.format(new CosineSimilarity().cosineSimilarity
                                       (
                                         tfidfDocsVector.get(i), 
                                         tfidfDocsVector.get(j)
                                       ))
                                  );
            }
        }
    }
}
