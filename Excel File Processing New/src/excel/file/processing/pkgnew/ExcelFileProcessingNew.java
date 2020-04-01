/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel.file.processing.pkgnew;

import Algorithm.LinkExcelToDB;
import database.BankNamerFromDB;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Information;
import model.Information_from_Excel_file;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExcelFileProcessingNew {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelReader excelReader = new ExcelReader();
        List<String> bankingNameList = BankNamerFromDB.getBankFromDB();
        //  System.out.println(bankingNameList); 
        Information_from_Excel_file information_from_Excel_file = excelReader.read("SORTED_FILE_FOR_TEST.xls");
        Map<String, List<Information>> treeMap = RoutingNumber.putBranchUnderBank(
                information_from_Excel_file.getBankNameList(), information_from_Excel_file.getBranchNameList(),
                information_from_Excel_file.getReferenceNoList());

//        treeMap.forEach((k, v) -> {
//            System.out.println("Bank Name: " + k + " branch no: " + v.size());
//        });

        System.out.println("**************************\n********************\n********************");

        treeMap = BankNamerFromDB.correctBankName(treeMap, bankingNameList);
        treeMap = new TreeMap<String, List<Information>>(treeMap);
     //   treeMap.forEach((k, v) -> {
          //  System.out.println("Bank Name: " + k + " branch no: " + v.size());
       // });

        LinkExcelToDB.linkBranchToRoutingNumber(treeMap);
    }

}
