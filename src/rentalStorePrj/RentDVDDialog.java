package rentalStorePrj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class RentDVDDialog extends Dialog implements ActionListener {

    private DVD unit;

    public RentDVDDialog(JFrame parent, DVD d) {
        // call parent and create a 'modal' dialog
        super(parent, true);

        setTitle("Rent a DVD:");
        closeStatus = false;
        setSize(400, 200);

        unit = d;

        // prevent user from closing window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // instantiate and display text fields

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(6, 2));


        textPanel.add(new JLabel("Your Name:"));
        renterTxt = new JTextField("John Doe", 30);
        textPanel.add(renterTxt);

        textPanel.add(new JLabel("Title of DVD:"));
        titleTxt = new JTextField("Avengers", 30);
        textPanel.add(titleTxt);

        Date date = Calendar.getInstance().getTime();


        textPanel.add(new JLabel("Rented on Date: "));
        rentedOnTxt = new JTextField(DATE_FORMAT.format(date), 30);
        textPanel.add(rentedOnTxt);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);  // number of days to add
        date = c.getTime();

        textPanel.add(new JLabel("Due Back Date: "));
        DueBackTxt = new JTextField(DATE_FORMAT.format(date),15);
        textPanel.add(DueBackTxt);


        getContentPane().add(textPanel, BorderLayout.CENTER);

        // Instantiate and display two buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setSize(300, 300);
        setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();

        // if OK clicked then fill the object
        if (clicked == okButton) {

            //if the input is all correct, save info to the object, else don't
            closeStatus = inputCheck();

        }
        else if(clicked == cancelButton){

            //DON'T save anything to the object
            closeStatus = false;

            //If all of the information is correct, make the dialog disappear
            dispose();
        }
    }

    private boolean inputCheck(){

        if(renterTxt.getText().equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter renter's name",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;

        } else if(titleTxt.getText().equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the title",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;

        } else if(rentedOnTxt.getText().equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the date rented on",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;

        } else if(DueBackTxt.getText().equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the due back date",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;

        } else {

            unit.setNameOfRenter(renterTxt.getText());
            unit.setTitle(titleTxt.getText());


            Date temp;

            //try to save the rent and due back
            try {
                dateEntered = rentedOnTxt.getText();

                temp = DATE_FORMAT.parse(dateEntered);

                if(!checkDateRented(dateEntered)){
                    return false;
                }

                unit.setBought(temp);

            } catch (ParseException p) {
                JOptionPane.showMessageDialog(null,
                        "Rented on date is incorrect",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            try {

                dateEntered = DueBackTxt.getText();
                temp = DATE_FORMAT.parse(dateEntered);

                if(!checkDateDue(dateEntered)){
                    return false;
                }

                unit.setDueBack(temp);

            } catch (ParseException p) {
                JOptionPane.showMessageDialog(null,
                        "Due back date is incorrect",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            //If all of the information is correct, make the dialog disappear
            dispose();

            return true;
        }
    }
}
