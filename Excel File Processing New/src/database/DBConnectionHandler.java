/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ERA
 */

public class DBConnectionHandler {
 
    String errorMessge;

    public DBConnectionHandler() {
        errorMessge = "";
    }

    public static Connection getConnection() {
        Connection con = null;
 
        String IPAdress = "10.88.1.80",PORT = "1534",serviceName = "frpspdb";
        String URL = "jdbc:oracle:thin:@" + IPAdress + ":" + PORT + "/" + serviceName + "";
         
        String userName = "remit";
        String password = "baremit#12";

     //   System.out.println("username: " + userName +" "+ "password: " + password);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(URL, userName, password);//database for xe      
        } catch (SQLException ex) {
           
        }  
        
        return con;
    } 

    public String getErrorMessge() {
        return errorMessge;
    }

    public static void releaseConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("args = " + new DBConnectionHandler().getConnection());
    }

}
