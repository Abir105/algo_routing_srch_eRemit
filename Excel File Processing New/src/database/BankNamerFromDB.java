package database;

import static excel.file.processing.pkgnew.ExcelReader.removeWord;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Information;

/**
 *
 * @author Administrator
 */
public class BankNamerFromDB {

    public static List<String> getBankFromDB() {
        List<String> list = new ArrayList<String>();

        Connection con = DBConnectionHandler.getConnection();

        String query = "select BANKNM from frsy_bank_master";
        try {
            PreparedStatement pr = con.prepareStatement(query);

            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                list.add(processBankName(rs.getString(1)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(BankNamerFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnectionHandler.releaseConnection(con);

        Collections.sort(list);
        return list;
    }

    public static String processBankName(String string) {
        string = string.trim();
        if (string.startsWith("THE")) {
            string = string.substring(3);
        }

        string = removeWord(string, "ltd");
        string = removeWord(string, "limited");
        string = string.replace(".", "");
        return string.trim();
    }

    public static Map<String, List<Information>> correctBankName(Map<String, List<Information>> treeMap, List<String> bankingNameList) {

        List<String> listBank = new ArrayList<String>(bankingNameList);
        listBank.replaceAll(str -> str.replaceAll("[^a-zA-Z0-9]", ""));
        Map<String, List<Information>> strMap = new HashMap<String, List<Information>>();

        treeMap.forEach((k, v) -> {
            if (!bankingNameList.contains(k)) {
                String bankNameFromexcel = k.replaceAll("[^a-zA-Z0-9]", "");
                if (listBank.contains(bankNameFromexcel)) {
                    int index = listBank.indexOf(bankNameFromexcel);
                    String str = bankingNameList.get(index);

                    List<Information> list1 = treeMap.get(k);
                    List<Information> list2 = strMap.get(str);

                    if (list1 == null) {
                        list1 = new ArrayList<Information>();
                       
                    } 
                    if (list2 == null) {
                        list2 = new ArrayList<Information>(); 
                    }
                     
                    List<Information> newList = (List<Information>) Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
                    strMap.put(str, newList);
                     
                } else {
                    boolean flag = false;
                    String str1 = "";
                    for (String str : bankingNameList) {
                        double score = similarityLCS(str, k);
                        if (score == 1.0) {
                            flag = true;
                            str1 = str;
                            break;
                        }
                    }

                    if (flag) {

                        List<Information> list1 = treeMap.get(k);
                        List<Information> list2 = strMap.get(str1);

                        if (list1 == null) {
                            list1 = new ArrayList<Information>(); 
                        } 
                        if (list2 == null) {
                            list2 = new ArrayList<Information>(); 
                        } 
                        
                      //  System.out.println("previous name: "+k+" actual name: "+str1);

                        List<Information> newList = (List<Information>) Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
                        strMap.put(str1, newList);
                     
                    }

                }
            } else {
                int index = bankingNameList.indexOf(k);
                strMap.put(k, v);

//                String str = bankingNameList.get(index);
//                System.out.println(k + " ------: " + str);
            }

        });
        return strMap;
    }

    public static double similarityLCS(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return 0.0;
        }
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        int m = s1.length(), n = s2.length();
        double length = ((m + n) - lcs.distance(s1, s2)) / 2;
        int min = Math.min(m, n);
        return length / min;
    }

    public static void main(String[] args) {

        List list1 = new ArrayList<>();
        list1.add("AB");
        list1.add("AC");
        list1.add("AD");
        list1.add("AE");
        list1.add("AF");

        List list2 = new ArrayList<>();
        list2.add("AB");
        list2.add("AC");
        list2.add("AD");
        list2.add("AE");
        list2.add("AF");

        List list3 = new ArrayList<>();
        list3.add(list1);
        list3.add(list2);
        System.out.println(list3.toString());

    }
}
