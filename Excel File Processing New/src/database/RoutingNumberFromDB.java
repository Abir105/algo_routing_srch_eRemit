package database;
   
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.List; 
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Information;
import model.RoutingInfo;

/**
 *
 * @author Administrator
 */
public class RoutingNumberFromDB {

    public static List<RoutingInfo> getBankCDFromDB(String bankName) {

        DBConnectionHandler dbConnectionHandler = new DBConnectionHandler();
        Connection con = dbConnectionHandler.getConnection();

        List<RoutingInfo> BANKCD = new ArrayList<RoutingInfo>();
        String query = "select ROUTNO,BRNNAM,BRNCTY from frsy_bank_master a, frsy_branch_master b where a.BANKNM like ? and b.COMCOD=a.BANKCD";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1, "%"+bankName + "%"); 

            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                RoutingInfo routInfo = new RoutingInfo();
                routInfo.setRoutNo(rs.getString("ROUTNO"));
                String branchName = rs.getString("BRNNAM");
                String branchCity = rs.getString("BRNCTY");
                routInfo.setBranchName(branchName);
                routInfo.setBranchCity(branchCity);
                BANKCD.add(routInfo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoutingNumberFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbConnectionHandler.releaseConnection(con);
        Collections.sort(BANKCD, (RoutingInfo a1, RoutingInfo a2) -> a1.getBranchName().compareTo(a2.getBranchName()));
        return BANKCD;
    } 
    
    public static Information containsName(final List<Information> list, final String name) {
        for (Information info : list) {
            if (info != null && info.getBrachName().equals(name)) {
                return info;
            }
        }

        return null;
    } 
    
    public static void main(String[] args) {
        List<RoutingInfo> list = RoutingNumberFromDB.getBankCDFromDB("THE CITY BANK");
        list.forEach(v -> {
            System.out.println("BranchName: "+v.getBranchName());
        });
    }

}
