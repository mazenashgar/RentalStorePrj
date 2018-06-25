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

    public Dialog(JFrame parent, boolean model) {
        super(parent, model);

        icon = new ImageIcon("rentalStore.png");
    }

    protected boolean checkDateRented(String dateRented){

        String[] s = dateRented.split("/");
        int monthRented;
        int dayRented;
        int yearRented;

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

    protected boolean checkDateDue(String dateDue, String dateRented){

        String[] s = dateDue.split("/");
        int monthDue;
        int dayDue;
        int yearDue;

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
        }

        GregorianCalendar calendarDue = new GregorianCalendar();
        GregorianCalendar calendarRented = new GregorianCalendar();
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
