/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel.file.processing.pkgnew;
 
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import java.util.Set;
import java.util.TreeMap;
import model.Information; 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Administrator
 */
public class RoutingNumber {
    public static Map<String, List<Information>> putBranchUnderBank(List<String> bankList,
            List<String> branchlist, List<String> referenceNo) {
         
        List<Information> informationlist = new ArrayList<Information>();
        Map<String, List<Information>> strMap = new HashMap<String, List<Information>>();

        for (int i = 0; i < bankList.size(); i++) {// str : bankList) {
            if (strMap.containsKey(bankList.get(i))) {

                informationlist = strMap.get(bankList.get(i));
                Information info = containsName(informationlist, branchlist.get(i));

                if (info != null) {

                    info.setBrachName(branchlist.get(i));
                    info.setReferenceNo(referenceNo.get(i));
                    informationlist.add(info);
                    info = null;

                } else {
                    Information info1 = new Information();
                    info1.setBrachName(branchlist.get(i));

                    info1.setReferenceNo(referenceNo.get(i));
                    informationlist.add(info1);
                }
                strMap.put(bankList.get(i), informationlist);
            } else {
                informationlist = new ArrayList<Information>();
                Information info = new Information();
                info.setBrachName(branchlist.get(i));
                info.setReferenceNo(referenceNo.get(i));
                informationlist.add(info);
                strMap.put(bankList.get(i), informationlist);
            }
        }

        Map<String, List<Information>> treeMap = new TreeMap<String, List<Information>>(strMap);
        return treeMap;
    }
    
    public static Information containsName(final List<Information> list, final String name) {
        for (Information info : list) {
            if (info != null && info.getBrachName().equals(name)) {
                return info;
            }
        }

        return null;
    }
    
    public static void printElement(Map<String, List<Information>> treeMap){
        for (Map.Entry<String, List<Information>> pair : treeMap.entrySet()) {
            String bankName = pair.getKey() ;
            System.out.println("Bank Name: "+bankName);
            
            System.out.println("*******************************************************");
            List<Information> infoList = pair.getValue();
            infoList.forEach(info->{
             //   System.out.println("\t Branch Name: "+info.getBrachName());
                List<String> routingNumber = info.getReferenceNo();
             //   routingNumber.forEach(str -> System.out.println("\t\t Reference number: "+str));
             //   System.out.println("*******************************************************");
            });
        }
    } 
    
    public static void main(String[] args) 
    { 
         
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        XSSFSheet sheet = workbook.createSheet("student Details"); 
  
        // This data needs to be written (Object[]) 
        Map<String, Object[]> data = new TreeMap<String, Object[]>(); 
        data.put("1", new Object[]{ "ID", "NAME", "LASTNAME" }); 
        data.put("2", new Object[]{ 1, "Pankaj", "Kumar" }); 
        data.put("3", new Object[]{ 2, "Prakashni", "Yadav" }); 
        data.put("4", new Object[]{ 3, "Ayan", "Mondal" }); 
        data.put("5", new Object[]{ 4, "Virat", "kohli" }); 
  
        // Iterate over data and write to sheet 
        Set<String> keyset = data.keySet(); 
        int rownum = 0; 
        for (String key : keyset) { 
            // this creates a new row in the sheet 
            Row row = sheet.createRow(rownum++); 
            Object[] objArr = data.get(key); 
            int cellnum = 0; 
            for (Object obj : objArr) { 
                // this line creates a cell in the next column of that row 
                Cell cell = row.createCell(cellnum++); 
                if (obj instanceof String) 
                    cell.setCellValue((String)obj); 
                else if (obj instanceof Integer) 
                    cell.setCellValue((Integer)obj); 
            } 
        } 
        try { 
            // this Writes the workbook gfgcontribute 
            FileOutputStream out = new FileOutputStream(new File("gfgcontribute.xlsx")); 
            workbook.write(out); 
            out.close(); 
            System.out.println("gfgcontribute.xlsx written successfully on disk."); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
}
