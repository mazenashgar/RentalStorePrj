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

    protected ImageIcon icon;

    public Dialog(JFrame parent, boolean model) {
        super(parent, model);

        icon = new ImageIcon("rentalStore.png");
    }

    protected boolean checkDateRented(String dateRented){

        String[] s = dateRented.split("/");

        try {
            monthRented = Integer.parseInt(s[0]);
            dayRented = Integer.parseInt(s[1]);
            yearRented = Integer.parseInt(s[2]);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,
                    "Please check your inputs" +
                            "\nNOTE: The dates can only contain numbers" +
                            "\n**No letters or special characters**",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        }

        if(yearRented < 0){
            JOptionPane.showMessageDialog(null,
                    "Year rented on is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        }

        if(monthRented < 1 || monthRented > 12){
            JOptionPane.showMessageDialog(null,
                    "Month rented on is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if (dayRented < 1 || dayRented > 31){
            JOptionPane.showMessageDialog(null,
                    "Day rented on is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        }
        else{
            return true;
        }
    }

    protected boolean checkDateDue(String dateDue){

        String[] s = dateDue.split("/");
        try{
            monthDue = Integer.parseInt(s[0]);
            dayDue = Integer.parseInt(s[1]);
            yearDue = Integer.parseInt(s[2]);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,
                    "Please check your inputs" +
                            "\nNOTE: The dates can only contain numbers" +
                            "\n**No letters or special characters**",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        }

        boolean mistake = false;

        if(yearDue < 0) {
            JOptionPane.showMessageDialog(null,
                    "Year due back is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;

        } else if(monthDue < 1 || monthDue > 12){
            JOptionPane.showMessageDialog(null,
                    "Month due back is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if (dayDue < 1 || dayDue > 31){
            JOptionPane.showMessageDialog(null,
                    "Day due back is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(yearDue < yearRented){
            mistake = true;
        } else if (yearDue == yearRented && monthDue < monthRented){
            mistake = true;
        } else if (yearDue == yearRented && monthDue == monthRented && dayDue < dayRented){
            mistake = true;
        }

        if(mistake){
            JOptionPane.showMessageDialog(null,
                    "Due date can't be before rented date",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
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
