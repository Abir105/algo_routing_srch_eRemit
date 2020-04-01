package model;
 
import java.util.ArrayList;
import java.util.List;


public class Information {
    String brachName;
    List<String> referenceNo; 

    public Information() {
        this.referenceNo = new ArrayList<String>();
    } 

    public String getBrachName() {
        return brachName;
    }

    public void setBrachName(String brachName) {
        this.brachName = brachName;
    }

    public  List<String> getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo( String referenceNumber) {
        referenceNo.add(referenceNumber);
    } 
}
