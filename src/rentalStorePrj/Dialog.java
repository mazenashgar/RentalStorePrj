package rentalStorePrj;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Dialog extends JDialog {

    protected JTextField titleTxt;
    protected JTextField renterTxt;
    protected JTextField rentedOnTxt;
    protected JTextField DueBackTxt;

    protected JButton okButton;
    protected JButton cancelButton;
    protected boolean closeStatus;
    protected SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    protected ImageIcon icon;

    public Dialog(){}

    public Dialog(JFrame parent, boolean model) {
        super(parent, model);

        icon = new ImageIcon("rentalStore.png");
    }

    protected boolean checkDateRented(String dateRented){

        String[] s = dateRented.split("/");
        int monthRented;
        int dayRented;
        int yearRented;

        GregorianCalendar gc = new GregorianCalendar();

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

        if(yearRented < 0) {
            JOptionPane.showMessageDialog(null,
                    "Year rented on is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if (dayRented < 1 || dayRented > 31){
            JOptionPane.showMessageDialog(null,
                     "Day rented on is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(monthRented < 1 || monthRented > 12){
            JOptionPane.showMessageDialog(null,
                    "Month rented on is incorrect",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if (monthRented == 4 && dayRented > 30){
            JOptionPane.showMessageDialog(null,
                    "April has only 30 days",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(monthRented == 6 && dayRented > 30){
            JOptionPane.showMessageDialog(null,
                    "June has only 30 days",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(monthRented == 9 && dayRented > 30){
            JOptionPane.showMessageDialog(null,
                    "September has only 30 days",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(monthRented == 11 && dayRented > 30){
            JOptionPane.showMessageDialog(null,
                    "November has only 30 days",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        }  else if (!gc.isLeapYear(yearRented)){
            if (monthRented == 2 && dayRented > 28){
                JOptionPane.showMessageDialog(null,
                        "February has only 28 days on "+
                                yearRented,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }
        } else if(gc.isLeapYear(yearRented)){
            if (monthRented == 2 && dayRented > 29) {
                JOptionPane.showMessageDialog(null,
                        "February has only 29 days on "+
                                yearRented,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }
        }

        return true;

    }

    protected boolean checkDateDue(String dateDue, String dateRented){

        String[] s = dateDue.split("/");
        int monthDue;
        int dayDue;
        int yearDue;

        GregorianCalendar gc = new GregorianCalendar();

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
        } else if (monthDue == 4 && dayDue > 30){
            JOptionPane.showMessageDialog(null,
                    "April has only 30 days",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(monthDue == 6 && dayDue > 30){
            JOptionPane.showMessageDialog(null,
                    "June has only 30 days",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(monthDue == 9 && dayDue > 30){
            JOptionPane.showMessageDialog(null,
                    "September has only 30 days",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(monthDue == 11 && dayDue > 30){
            JOptionPane.showMessageDialog(null,
                    "November has only 30 days",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } else if(!gc.isLeapYear(yearDue)){
            if (monthDue == 2 && dayDue > 28) {
                JOptionPane.showMessageDialog(null,
                        "February has only 28 days on "+
                                yearDue,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }
        } else if(gc.isLeapYear(yearDue)) {
            if (monthDue == 2 && dayDue > 29) {
                JOptionPane.showMessageDialog(null,
                        "February has only 29 days on "+
                                yearDue,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }
        }

        Date dueDate;
        Date rentDate;

        try {
            dueDate = DATE_FORMAT.parse(dateDue);
            rentDate = DATE_FORMAT.parse(dateRented);

        }catch (ParseException e){
            return false;
        }

        if(rentDate.after(dueDate)){
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
