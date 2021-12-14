/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailydrivermanage;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author zakir
 */
public class DBclass {

    public static Connection connectdb() {
        Connection con = null;;
        try {
            con = (Connection) java.sql.DriverManager.getConnection("jdbc:derby://localhost:1527/DailyDriverManage", "root", "root");
            return con;
        } catch (Exception e) {
        }
        return null;
    }

    public static TableModel loaddata(Connection con, DefaultTableModel Model) throws SQLException {
        Statement st = con.createStatement();
        //sql query
        String sql = "SELECT * FROM ROOT.DAILYDRIVER";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            //data will be added until finish
            String[] row = {rs.getString("Serial"),rs.getString("TIME"), rs.getString("PICKUPPOINT"), rs.getString("DESTINATION"),
                rs.getString("DRIVERID"), rs.getString("PASSENGERNAME"), rs.getString("AMOUNT"), rs.getString("OPTIONALTIP"),
                rs.getString("ACCOUNT"), rs.getString("TELEPHONE")};

            //Inserting into Table
            Model.addRow(row);
            
            
        }
        return Model;
    }

    public static void insert(Connection con, String sqlqury) {
        try {
            PreparedStatement pst = con.prepareStatement(sqlqury);
            pst.executeUpdate();
            System.out.println("inserted");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public static void update(Connection con, String sqluery){
       try {
            PreparedStatement pst = con.prepareStatement(sqluery);
            pst.executeUpdate();
            System.out.println("Updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    
    
    }
}
