/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;
 
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.Information;
import model.Information_from_Excel_file;
import model.RoutingInfo; 
import  database.RoutingNumberFromDB;
import java.util.Arrays;
import java.util.stream.Collectors;
import model.Branch_Info_DB_Excel;

/**
 *
 * @author Administrator
 */
public class LinkExcelToDB {
    
    public static int counterNull=0 ; 
    public static List<RoutingInfo> getRouteInfoByBranchName(List<RoutingInfo> tempRoutingInfoList, String partialBranchName, String branchName) {
        List<String> list = Arrays.asList(branchName.split(" "));
        return tempRoutingInfoList.stream().filter(p -> p.getBranchName().startsWith((partialBranchName)) || list.stream().anyMatch(str -> p.getBranchName().contains(str)))
                .collect(Collectors.toList());
    }

    public static Branch_Info_DB_Excel checkSimilarityByBranch(List<RoutingInfo> routingInfoList,
            List<RoutingInfo> tempRoutingInfoList, String partialbranchName, String branchName, int itr) {

        if (itr == 0) {
            routingInfoList = getRouteInfoByBranchName(tempRoutingInfoList, partialbranchName, branchName);
        }
        
         
        Branch_Info_DB_Excel branch_Info_DB_Excel = new Branch_Info_DB_Excel(branchName);

        for (RoutingInfo routeInfo : routingInfoList) {
            double similarity = 0;
            if (itr == 0) {
                similarity = StringSimilarity.similarity(branchName, routeInfo.getBranchName());
                
            } else {
                similarity = StringSimilarity.similarity(branchName,
                        routeInfo.getBranchName() + " " + routeInfo.getBranchCity());
            }
            if (similarity == 1) {
                branch_Info_DB_Excel.setSimilarityScore(1.00);
                branch_Info_DB_Excel.setBranchNameFromDB(routeInfo.getBranchName());
                branch_Info_DB_Excel.setRoutingNoFromDB(routeInfo.getRoutNo());
                branch_Info_DB_Excel.setBranchCity(routeInfo.getBranchCity());
                branch_Info_DB_Excel.setAlgorithmName("similarity check");
                break;
            } else if (similarity > branch_Info_DB_Excel.getSimilarityScore()) {
                branch_Info_DB_Excel.setSimilarityScore(similarity);
                branch_Info_DB_Excel.setBranchNameFromDB(routeInfo.getBranchName());
                branch_Info_DB_Excel.setRoutingNoFromDB(routeInfo.getRoutNo());
                branch_Info_DB_Excel.setBranchCity(routeInfo.getBranchCity()); 
                branch_Info_DB_Excel.setAlgorithmName("similarity check");
            }
        }
        if (branch_Info_DB_Excel.getSimilarityScore() == 1.0) {
            return branch_Info_DB_Excel;
        } else if (branch_Info_DB_Excel.getSimilarityScore() <= 0.7 && itr == 0) {
            return checkSimilarityByBranch(routingInfoList,
                    tempRoutingInfoList, partialbranchName, branchName, 1);
        } else {
            return checkSimilairytBrnachNameAgain(branch_Info_DB_Excel);
        }

    }

    private static Branch_Info_DB_Excel checkSimilairytBrnachNameAgain(Branch_Info_DB_Excel branch_Info_DB_Excel) {
        double similarityScore = branch_Info_DB_Excel.getSimilarityScore();
        double score = StringSimilarity.similarityLCS(
                branch_Info_DB_Excel.getBranchNameFromExcel(), branch_Info_DB_Excel.getBranchNameFromDB());
        if (score > similarityScore) {
            branch_Info_DB_Excel.setSimilarityScore(score);
        }
        
        branch_Info_DB_Excel.setAlgorithmName("LCS Algorithm");
        return branch_Info_DB_Excel;
    }
    
    public static void linkBranchToRoutingNumber(Map<String, List<Information>> treeMap) throws FileNotFoundException, IOException {
  
        int totalCounter = 0, percentCounter = 0;
        for (Map.Entry<String, List<Information>> pair : treeMap.entrySet()) {

            String bankName = pair.getKey();
//            if(!bankName.equals("CITY BANK"))
//                continue;
            
            List<RoutingInfo> routingInfoList = RoutingNumberFromDB.getBankCDFromDB(bankName);
            List<Information> infoList = pair.getValue();
            
            int rowCount = 0;
            System.out.println("***********************************************************");
            System.out.println("Bank Name: " + bankName);
            System.out.println("***********************************************************");
           
            for (Information info : infoList) {

                String partialbranchName = info.getBrachName().substring(0, 1); 
                String branchName = info.getBrachName();
                 
                Branch_Info_DB_Excel branch_Info_DB_Excel = checkSimilarityByBranch(null, routingInfoList, partialbranchName, branchName, 0);
               
                if(branch_Info_DB_Excel.getSimilarityScore() == 0){
                    continue;
                }
                else if (branch_Info_DB_Excel.getSimilarityScore() < 0.9) {
                     ++totalCounter;
                    continue;
                }
                
                ++totalCounter;
                ++percentCounter;
                System.out.print("#" + branch_Info_DB_Excel.getBranchNameFromExcel() + " ");
                System.out.print("#" + branch_Info_DB_Excel.getBranchNameFromDB() + " " + branch_Info_DB_Excel.getBranchCity());
                System.out.print("  Score: " + branch_Info_DB_Excel.getSimilarityScore() + " ");
                System.out.println("  Algorithm: " + branch_Info_DB_Excel.getAlgorithmName() + " ");

            }

        }
        System.out.println("Total counter: " + totalCounter);
        System.out.println("null counter: " + counterNull);
        System.out.println("Percent counter: " + percentCounter);

         
    }
    
}
