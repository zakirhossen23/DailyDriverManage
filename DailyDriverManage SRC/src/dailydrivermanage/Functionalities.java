/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailydrivermanage;

import java.sql.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author StudentK
 */
public class Functionalities {

    public static Object[] inserting(int Serialbox, String timebox, String Pickupbox,
            String Destinationbox, String DriversIDbox, String PassengerBox,
            String Amountbox, String Optionalbox, String Accountbox,
            String Telephonebox, Connection con) {
        Object[] row = new Object[10];
        row[0] = Serialbox;
        row[1] = timebox;
        row[2] = Pickupbox;
        row[3] = Destinationbox;
        row[4] = DriversIDbox;
        row[5] = PassengerBox;
        row[6] = Amountbox;
        row[7] = Optionalbox;
        row[8] = Accountbox;
        row[9] = Telephonebox;
        try {
            String query = "INSERT INTO DAILYDRIVER (SERIAL,\"TIME\", PICKUPPOINT, DESTINATION, DRIVERID, PASSENGERNAME, AMOUNT, OPTIONALTIP, ACCOUNT, TELEPHONE)"
                    + "VALUES (" + Serialbox + ",'" + timebox + "', '" + Pickupbox + "', '" + Destinationbox + "', " + DriversIDbox + ", '" + PassengerBox + "', " + Amountbox + ",' " + Optionalbox + "',' " + Accountbox + "', '" + Telephonebox + "')";
            //Inserting into database
            DBclass.insert(con, query);
        } catch (Exception e) {
        }

        return row;
    }

    public static TableModel SecondInserting(int Driver, Float Takings, DefaultTableModel Model) {
        //Initializing variables
        int ExistingJobsDone = 0;                                               //Intializing The Existed JobsDone Value from table
        Float ExistingTakings = 0.00f;                                          //Intializing The Existed Takings Value of that driver from table
        Boolean ExistedDriver = false;                                          //Getting from Table and setting the Existing variables
        int ExistedRowNo = 0;

        //Checking if any existed Driver on the Second Table
        for (int i = 0; i < Model.getRowCount(); i++) {
            var driverid = Model.getValueAt(i, 0).toString();
            if (Integer.valueOf(driverid) == Driver) {
                ExistingJobsDone = Integer.valueOf(Model.getValueAt(i, 3).toString());
                ExistingTakings = Float.valueOf(Model.getValueAt(i, 1).toString());
                ExistedDriver = true;
                ExistedRowNo = i;
            }
        }
        // If that driver id is there then all the things will be updated into that row only.
        if (ExistedDriver == true) {
            ExistingTakings = ExistingTakings + Takings;
            ExistingJobsDone = ExistingJobsDone + 1;
            float expected = ExistingTakings / 5;
            Model.setValueAt(ExistingTakings, ExistedRowNo, 1);
            Model.setValueAt(expected, ExistedRowNo, 2);
            Model.setValueAt(ExistingJobsDone, ExistedRowNo, 3);
            return Model;
        } else {
            //If the Driver Id is new then it will create a new row.
            Object[] row = new Object[4];
            row[0] = Driver;
            row[1] = Takings;
            row[2] = Takings / 5;
            row[3] = 1;
            Model.addRow(row);
            return Model;
        }

    }

    public static TableModel SecondInsertingModel(DefaultTableModel Model, DefaultTableModel SecondModel) {
        for (int i = 0; i < Model.getRowCount(); i++) { //Writing all the rows of that Table
            SecondInserting(Integer.valueOf(Model.getValueAt(i, 4).toString()), Float.valueOf(Model.getValueAt(i, 6).toString()), SecondModel);
        }
        return SecondModel;
    }

    public static void TotalCount(JLabel totalearned, JLabel totaljobdone, JLabel expectedday, DefaultTableModel Model) {
        //Initializing all new storing variable
        float TotalDayTakingCount = 0.00f;
        int TotalJobsCount = 0;
        float TotalExpectedCount = 0.00f;
        //Counting all stored Total variable from 2nd Teble
        for (int i = 0; i < Model.getRowCount(); i++) {
            TotalDayTakingCount = TotalDayTakingCount + Float.valueOf(Model.getValueAt(i, 1).toString());
            TotalJobsCount = TotalJobsCount + Integer.valueOf(Model.getValueAt(i, 3).toString());

        }
        //Setting to all those Counted Label
        totalearned.setText("£" + TotalDayTakingCount);
        totaljobdone.setText(String.valueOf(TotalJobsCount));
        expectedday.setText("£" + TotalDayTakingCount / 5);
    }

    public static boolean exportToCSV(JTable tableToExport, String pathToExportTo) {

        try {

            TableModel model = tableToExport.getModel(); // Get the model of that Table
            FileWriter csv = new FileWriter(new File(pathToExportTo)); //Creating a file to that saving path

            for (int i = 0; i < model.getColumnCount(); i++) { //Writing all the Columns
                csv.write(model.getColumnName(i) + ",");
            }

            csv.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) { //Writing all the rows of that Table
                for (int j = 0; j < model.getColumnCount(); j++) {
                    try {
                        String value = model.getValueAt(i, j).toString();
                        csv.write(value + ",");
                    } catch (Exception e) {             //If any Cell is empty then skip       
                    }

                }
                csv.write("\n");
            }

            csv.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
