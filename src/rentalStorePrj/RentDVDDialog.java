package rentalStorePrj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class RentDVDDialog extends Dialog implements ActionListener {

    private JTextField titleTxt;
    private JTextField renterTxt;
    private JTextField rentedOnTxt;
    private JTextField DueBackTxt;

    private JButton okButton;
    private JButton cancelButton;
    private boolean closeStatus;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private String dateEntered;

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

        textPanel.add(new JLabel("Due Back: "));
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

            if(renterTxt.getText().equals("")){

                JOptionPane.showMessageDialog(null,
                        "Please enter renter's name",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                        closeStatus = false;

            } else if(titleTxt.getText().equals("")){

                JOptionPane.showMessageDialog(null,
                        "Please enter DVD's title",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                closeStatus = false;

            } else if(rentedOnTxt.getText().equals("")){

                JOptionPane.showMessageDialog(null,
                        "Please enter the date rented on",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                closeStatus = false;

            } else if(DueBackTxt.getText().equals("")){

                JOptionPane.showMessageDialog(null,
                        "Please enter the returning date",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                closeStatus = false;

            } else {

                // save the information in the object
                closeStatus = true;

                //try to save the rent and due back
                try {
                    dateEntered = rentedOnTxt.getText();
                    unit.setBought(DATE_FORMAT.parse(dateEntered));

                } catch (ParseException p) {
                    System.out.println("Could'nt convert date to string");
                } catch (NullPointerException p) {
                    System.out.println("String was empty");
                }

                try {
                    dateEntered = DueBackTxt.getText();
                    unit.setDueBack(DATE_FORMAT.parse(dateEntered));

                } catch (ParseException p) {
                    System.out.println("Could'nt convert date to string");
                } catch (NullPointerException p) {
                    System.out.println("String was empty");
                }

                unit.setNameOfRenter(renterTxt.getText());
                unit.setTitle(titleTxt.getText());

                //If all of the information is correct, make the dialog disappear
                dispose();
            }
        }
        else if(clicked == cancelButton){

            //DON'T save anything to the object
            closeStatus = false;

            //If all of the information is correct, make the dialog disappear
            dispose();
        }


    }

    public boolean closeOK() {
        return closeStatus;
    }
}
