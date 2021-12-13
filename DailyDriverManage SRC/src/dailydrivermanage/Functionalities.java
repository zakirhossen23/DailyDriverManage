/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailydrivermanage;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author zakir
 */
public class Functionalities {

    public static Object[] inserting(String timebox, String Pickupbox,
            String Destinationbox, String DriversIDbox, String PassengerBox,
            String Amountbox, String Optionalbox, String Accountbox,
            String Telephonebox) {
        Object[] row = new Object[9];
        row[0] = timebox;
        row[1] = Pickupbox;
        row[2] = Destinationbox;
        row[3] = DriversIDbox;
        row[4] = PassengerBox;
        row[5] = Amountbox;
        row[6] = Optionalbox;
        row[7] = Accountbox;
        row[8] = Telephonebox;
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
        totalearned.setText("£"+ TotalDayTakingCount );
        totaljobdone.setText(String.valueOf(TotalJobsCount));
        expectedday.setText("£"+ TotalDayTakingCount/5);
    }
    
    

}
