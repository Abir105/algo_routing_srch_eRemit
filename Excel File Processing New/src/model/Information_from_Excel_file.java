/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class Information_from_Excel_file {
    
    List<String> referenceNoList,bankNameList,branchNameList;

    public Information_from_Excel_file() {
        this.referenceNoList = new ArrayList<String>();
        this.bankNameList = new ArrayList<String>();
        this.branchNameList = new ArrayList<String>();
    }

    public List<String> getReferenceNoList() {
        return referenceNoList;
    }

    public void setReferenceNoList(String referenceNo) {
        referenceNoList.add(referenceNo);
    }

    public List<String> getBankNameList() {
        return bankNameList;
    }

    public void setBankNameList(String bankName) {
        bankNameList.add(bankName);
    }

    public List<String> getBranchNameList() {
        return branchNameList;
    }

    public void setBranchNameList(String branchName) {
        branchNameList.add(branchName);
    }  
    
    public void printElement(){
        System.out.println("Refernce_No\tBankName\tBranchName");
        for(int i=0;i<referenceNoList.size();i++){
            System.out.print(referenceNoList.get(i)+"\t");
            System.out.print(bankNameList.get(i)+"\t\t");
            System.out.println(branchNameList.get(i)); 
        }
        
    } 
}
