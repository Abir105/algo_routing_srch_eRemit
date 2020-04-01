/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;
 
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import info.debatty.java.stringsimilarity.MetricLCS;  
 

/**
 *
 * @author Administrator
 */
public class StringSimilarity { 
    

    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
            /* both strings are zero length */ }
        /* // If you have StringUtils, you can use it to calculate the edit distance:
        return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
                                                             (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }
    
    public static String removeSpecialCharBranchName(String string) 
    {     
        if(string == null)
            return string;
       else
            return string.replaceAll("[^A-Za-z]+", "").toUpperCase();
    } 
    
     public static double similarityLCS(String s1, String s2) {
        if(s1 == null || s2 == null){
            System.out.println("S1= "+s1+" S2= "+s2+" =============================");
            LinkExcelToDB.counterNull++;
            return 0.0; 
        }
        s1=removeSpecialCharBranchName(s1);
        s2=removeSpecialCharBranchName(s2);
        LongestCommonSubsequence lcs = new LongestCommonSubsequence(); 
        int m = s1.length(),n=s2.length();
        double length = ((m+n)-lcs.distance(s1,s2))/2;
        int min = Math.min(m, n);
        return length/min;  
     } 
     
    static int lcs(String X,String Y, int m, int n ) 
    { 
        if (m == 0 || n == 0) 
            return 0; 
        if (X.charAt(m-1) == Y.charAt(n-1)) 
            return 1 + lcs(X, Y, m-1, n-1); 
        else
            return Math.max(lcs(X, Y, m, n-1), lcs(X, Y, m-1, n)); 
    } 
     
    public static double similarity4(String X, String Y) 
    { 
        int m = X.length(),n=Y.length();
        info.debatty.java.stringsimilarity.MetricLCS lcs = 
                new info.debatty.java.stringsimilarity.MetricLCS();
        return lcs.distance(X,Y);  
    } 
    
    public static double similarity1(String s1, String s2) { 
        MetricLCS lcs = new MetricLCS(); 
        return 1-lcs.distance(s1, s2); 
    }
    
    public static double Similarity2(String s1,String s2) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        double similarity = lcs.distance(s1,s2);
        int min = Math.min(s1.length(), s2.length()),max = Math.max(s2.length(),s2.length());
        System.out.println("Similarity: "+similarity/min);
        return similarity/min;
    }
 

    // Example implementation of the Levenshtein Edit Distance
    // See http://r...content-available-to-author-only...e.org/wiki/Levenshtein_distance#Java
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
                costs[s2.length()] = lastValue;
            }
        }
        return costs[s2.length()];
    }

    public static void printSimilarity(String s, String t) {
        System.out.println(String.format(
                "%.3f is the similarity between \"%s\" and \"%s\"", similarity(s, t), s, t));
    }
    
    public static void main(String[] args) {
         double score = similarity("B U E T","B.U.E.T");
         System.out.println("str: "+score);
       // printSimilarity("CUMILLA", "COMILLA");
       /* printSimilarity("", "");
        printSimilarity("1234567890", "1");
        printSimilarity("1234567890", "123");
        printSimilarity("1234567890", "1234567");
        printSimilarity("1234567890", "1234567890");
        printSimilarity("1234567890", "1234567980");
        printSimilarity("47/2010", "472010");
        printSimilarity("47/2010", "472011");
        printSimilarity("47/2010", "AB.CDEF");
        printSimilarity("47/2010", "4B.CDEFG");
        printSimilarity("47/2010", "AB.CDEFG");
        printSimilarity("The quick fox jumped", "The fox jumped");
        printSimilarity("The quick fox jumped", "The fox");
        printSimilarity("The quick fox jumped", "The quick fox jumped off the balcany");
        printSimilarity("kitten", "sitting");  */
    }
}