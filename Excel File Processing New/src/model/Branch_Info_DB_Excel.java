/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Administrator
 */
public class Branch_Info_DB_Excel {
    
    String branchNameFromDB,branchNameFromExcel;
    String RoutingNoFromDB,referenceNo,branchCity;
    double similarityScore;
    String algorithmName; 

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    } 

    public String getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(String branchCity) {
        this.branchCity = branchCity;
    }
    
    

    public Branch_Info_DB_Excel(String branchNameFromExcel) {  
        this.branchNameFromExcel = branchNameFromExcel;
        similarityScore=0.0;
    } 

    public String getBranchNameFromDB() {
        return branchNameFromDB;
    }

    public void setBranchNameFromDB(String branchNameFromDB) {
        this.branchNameFromDB = branchNameFromDB;
    }

    public String getBranchNameFromExcel() {
        return branchNameFromExcel;
    }

    public void setBranchNameFromExcel(String branchNameFromExcel) {
        this.branchNameFromExcel = branchNameFromExcel;
    }

    public String getRoutingNoFromDB() {
        return RoutingNoFromDB;
    }

    public void setRoutingNoFromDB(String RoutingNoFromDB) {
        this.RoutingNoFromDB = RoutingNoFromDB;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }    

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    } 
}
