package rentalStorePrj;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Dialog extends JDialog {

    protected JTextField titleTxt;
    protected JTextField renterTxt;
    protected JTextField rentedOnTxt;
    protected JTextField DueBackTxt;

    protected JButton okButton;
    protected JButton cancelButton;
    protected boolean closeStatus;
    protected SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    protected String dateEntered;

    protected int monthRented;
    protected int dayRented;
    protected int yearRented;
    protected int monthDue;
    protected int dayDue;
    protected int yearDue;

    public Dialog(JFrame parent, boolean model) {
        super(parent, model);
    }



    protected boolean checkDateRented(String dateRented){

        String[] s = dateRented.split("/");

        monthRented = Integer.parseInt(s[0]);
        dayRented = Integer.parseInt(s[1]);
        yearRented = Integer.parseInt(s[2]);

        if(monthRented < 0 || monthRented > 12){
            JOptionPane.showMessageDialog(null,
                    "Month rented on is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (dayRented < 0 || dayRented > 31){
            JOptionPane.showMessageDialog(null,
                    "Day rented on is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }

    protected boolean checkDateDue(String dateDue){

        String[] s = dateDue.split("/");

        monthDue = Integer.parseInt(s[0]);
        dayDue = Integer.parseInt(s[1]);
        yearDue = Integer.parseInt(s[2]);

        if(monthDue < 0 || monthDue > 12){
            JOptionPane.showMessageDialog(null,
                    "Month Due back is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (dayDue < 0 || dayDue > 31){
            JOptionPane.showMessageDialog(null,
                    "Day Due back is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(yearDue < yearRented){
            JOptionPane.showMessageDialog(null,
                    "Due date can't be before rented date",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (yearDue == yearRented && monthDue < monthRented){
            JOptionPane.showMessageDialog(null,
                    "Due date can't be before rented date",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (yearDue == yearRented && monthDue == monthRented && dayDue < dayRented){
            JOptionPane.showMessageDialog(null,
                    "Due date can't be before rented date",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            return true;
        }
    }

    public boolean closeOK() {
        return closeStatus;
    }
}
