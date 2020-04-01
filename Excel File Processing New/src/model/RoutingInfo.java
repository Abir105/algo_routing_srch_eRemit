/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
 
public class RoutingInfo   implements Comparable< RoutingInfo >{
    String routNo,branchName,branchCity;

    public String getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(String branchCity) {
        this.branchCity = branchCity;
    }

    public String getRoutNo() {
        return routNo;
    }

    public void setRoutNo(String routNo) {
        this.routNo = routNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }  

    @Override
    public int compareTo(RoutingInfo o) {
        return this.getBranchName().compareTo(o.getBranchName());
    }

    @Override
    public String toString() {
        return getBranchName(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
