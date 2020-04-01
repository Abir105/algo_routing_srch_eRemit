package excel.file.processing.pkgnew;
  
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*; 
import java.io.IOException; 
import java.io.InputStream;
import java.util.regex.Pattern;
import model.Information_from_Excel_file;

public class ExcelReader {
    public Information_from_Excel_file read(String path) throws IOException, InvalidFormatException {
        
        Information_from_Excel_file information_from_Excel_file = new Information_from_Excel_file();
        InputStream in =  getClass().getResourceAsStream("/Excel_File/" + path );
        Workbook workbook = WorkbookFactory.create(in);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        int rownumber = 0 ; 
        
        for (Row row: sheet) {
            ++rownumber;
            
            if(row.getRowNum()==0)
                continue;
            for(int i=0;i< row.getLastCellNum();i++) { 
                if(i==1){
                    String cellValue = dataFormatter.formatCellValue(row.getCell(i));
                    information_from_Excel_file.setReferenceNoList(cellValue);
                }else if(i==6){
                    String cellValue = dataFormatter.formatCellValue(row.getCell(i)); 
                    String str = processBankName(cellValue.toUpperCase()); 
                    information_from_Excel_file.setBankNameList(str);
                }else if(i==7){ 
                    String cellValue = dataFormatter.formatCellValue(row.getCell(i));
                    String str = processBranchName(cellValue,"branch").toUpperCase();
                    information_from_Excel_file.setBranchNameList(str);
                }else
                    continue;
            } 
        }
        workbook.close();
        
        return information_from_Excel_file;
    }
    
    
   
   public static String processBankName(String string) 
    {  
        string = string.trim();
        if(string.startsWith("THE")) 
            string = string.substring(3);
               
        string = removeWord(string, "ltd");
        string = removeWord(string, "limited"); 
        return string; 
    }
   
   public static String removeWord(String string, String word) 
    { 
        string = string.replaceAll(" +", " ");
        boolean bool= Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE).matcher(string).find();
        if (bool){            //string.contains(word)) { 
            String tempWord = "(?i)"+word; 
            string = string.replaceAll(tempWord, ""); 
            tempWord = " " + word; 
            string = string.replaceAll(tempWord, ""); 
        }  
        return string.trim(); 
    }  
   
   public static String processBranchName(String string,String word) 
   {      
        return removeWord(string,word); 
   }  
   
    public static void main(String[] args) {
        String name = "  The bank The".trim(); 
        if(name.startsWith("The")){
            name = name.substring(3);
            System.out.println(name);
        }
    }
}
