package rentalStorePrj;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**********************************************************************
 * Description: This program controls the units being rented from the
 * store. Like adding, removing, and displaying a rented unit (DVD or
 * Game). Also, the user can save the rented lists as serial or text,
 * as well as load them from files.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 6/30/2018
 *********************************************************************/

public class RentalStore extends AbstractListModel {

    /** Declare an ArrayList to store the units in */
    private ArrayList<DVD> listDVDs;

    /** SimpleDateFormat sets how the date is formatted */
    private SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("MM/dd/yyyy");

    /******************************************************************
     * Constructor for RentalStore
     *****************************************************************/
    public RentalStore() {

        //Call the constructor of AbstractListModel
        super();

        //instantiate the ArrayList
        listDVDs = new ArrayList<DVD>();
    }

    /******************************************************************
     * This method adds a unit to the ArrayList where the
     * units are stored. Then it tells the GUI that the list expanded
     * and it should update the list displayed.
     *
     * @param a The unit to be added to the list
     *****************************************************************/
    public void add (DVD a) {

        //add the unit at the end of the list
        listDVDs.add(a);

        //update the displayed list in the GUI
        fireIntervalAdded(this, 0, listDVDs.size());
    }

    /******************************************************************
     * This method returns a unit (DVD or Game) from the list at the
     * index passed to the method through the parameter.
     *
     * @param i The index of the unit in the ArrayList
     * @return - The unit of type DVD or Game from the list
     *****************************************************************/
    public DVD get (int i) {
        return listDVDs.get(i);
    }

    /******************************************************************
     * The method tells the GUI that the list it is displaying was
     * updated, which means that the displayed list needs to be
     * updated.
     *
     * @param index The index of which the list was updated.
     *****************************************************************/
    public void update(int index){

        //The contents of the list displayed was updated
        fireContentsChanged(this, index, index);
    }

    /******************************************************************
     * This method removes a unit from the list at a specific index,
     * then it tells the GUI that that unit was removed, so update
     * the display.
     *
     * @param r The unit the needs to be removed from the list.
     * @param index The index of the unit that was removed.
     *****************************************************************/
    public void remove (DVD r, int index){

        //remove the unit from the list
        listDVDs.remove(r);

        //update the displayed list in the GUI
        fireIntervalRemoved(this, index, listDVDs.size());
    }

    /******************************************************************
     * This method is overriding the method from the AbstractListModel
     * class. The method collects the information of the unit using
     * a helper method (linePrinter) in a string,
     * then returns that string.
     *
     * @param arg0 - the index of the unit in the list
     * @return line - a line of the information of the unit
     *****************************************************************/
    public Object getElementAt(int arg0) {

        //store the information of the unit in the string "line"
        String line = linePrinter(arg0);

        //return the string created
        return line;
    }

    /******************************************************************
     * This method is overriding the method from the AbstractListModel
     * class. The method returns the size of the list.
     *
     * @return - The size of the current list
     *****************************************************************/
    public int getSize() {

        //return the size of the list
        return listDVDs.size();
    }

    /******************************************************************
     * This method collects the information of the unit of the passed
     * index, forms it into a string, then returns the string.
     *
     * @param index The index of the unit to collect information from
     * @return line - A string with all the information of the unit
     *****************************************************************/
    private String linePrinter (int index){

        /*create a unit, and instantiate it to the specified unit
          from the list*/
        DVD unit = listDVDs.get(index);

        /*create a string to store the information, collect the
          information from the DVD class.*/
        String line = "Name: " + listDVDs.get(index).getNameOfRenter()
                + ",\t\t";

        //if this is a Game, add "Game:" to line, otherwise add "DVD:"
        if(unit instanceof Game){
            line += "Game: ";
        }else{
            line += "DVD: ";
        }

        line += listDVDs.get(index).getTitle()
                + ",\t\t" + "Rented: " +
                (DATE_FORMAT.format(listDVDs.get(index).getBought()))
                + ",\t\t" + "Due Date: " +
                (DATE_FORMAT.format(listDVDs.get(index).getDueBack()));

        //if this is a Game, add a console to the string
        if (unit instanceof Game)
            line += ",\t\t"+ "Console: " + ((Game)unit).getPlayer();

        //return the formed string
        return line;
    }

    /******************************************************************
     * This method saves the currently displayed list to a file as
     * serial.
     *
     * @param filename - The name of the file to save the info in
     * @throws IOException  If the file can't be created, if it
     *          already exists, or if an I/O error occurs while
     *          writing stream header.
     *****************************************************************/
    public void saveAsSerializable(String filename) {

        //try to create filename and save the information in it
        try {

            //create file
            FileOutputStream fos = new FileOutputStream(filename);

            //write serial stream header to the underlying stream
            ObjectOutputStream os = new ObjectOutputStream(fos);

            //write the units in the file
            os.writeObject(listDVDs);

            //close the file
            os.close();
        }
        catch (IOException ex) {

            //If an error happens, tell the user
            JOptionPane.showMessageDialog(null,"Error in saving db");

        }
    }

    /******************************************************************
     * This method loads a list from a serial file then updates
     * the display in the GUI
     *
     * @param filename - The name of the file to load the info from
     * @throws Exception If the file doesn't exist, cannot be opened
     *          for some reason, the stream header is incorrect, or
     *          if an I/O error occurs while reading stream header.
     *****************************************************************/
    public void loadFromSerializable(String filename) {

        //Try to open the file and read from it
        try {

            //open the file
            FileInputStream fis = new FileInputStream(filename);

            //create an object that reads from the file
            ObjectInputStream is = new ObjectInputStream(fis);

            //read the object from the file and store it in listDVDs
            listDVDs = (ArrayList<DVD>) is.readObject();

            /*tell the GUI that an unit has been added to the list,
              update the displayed list.*/
            fireIntervalAdded(this, 0, listDVDs.size());

            //close the file
            is.close();
        }
        catch (Exception ex) {

            //if an error happens, tell the user
            JOptionPane.showMessageDialog(null,"Error in loading db");
        }
    }

    /******************************************************************
     * This method saves the currently displayed list to a file as
     * Text.
     *
     * @param filename - The name of the file to save the info in
     * @throws IOException If the file can't be created, if it
     *          already exists, cannot be opened for some reason.
     *****************************************************************/
    public void saveAsText (String filename){

        //create an index number and a file printing object
        int index = 0;
        PrintWriter out;

        /*try to create the file, print the info in the file
          line by line*/
        try {

            //create the file
            out = new PrintWriter(new BufferedWriter
                    (new FileWriter(filename)));

            //while there are units in the list, print them in the file
            while(index < listDVDs.size()){
                out.println(linePrinter(index));
                index++;
            }

            //close file
            out.close();
        }
        catch (IOException ex) {

            //If an error happens, tell the user
            JOptionPane.showMessageDialog(null,
                    "Error in saving text to " + filename);
        }
    }

    /******************************************************************
     * This method loads a list from a text file then updates
     * the display in the GUI.
     *
     * @param filename - The name of file to load the info from
     * @throws Exception if file was not found, index out of bounds,
     *****************************************************************/
    public void loadFromText (String filename){

        //create index number and file scanner object
        int index = 0;
        Scanner fileReader;

        //create a new list, a string, and a string array
        listDVDs = new ArrayList<DVD>();
        String temp = null;
        String[] s;

        //try to read from the file and store it in the list
        try{

            //find file and open it
            fileReader = new Scanner(new File(filename));

            //while the file has a line, do:
            while(fileReader.hasNextLine()) {

                //read the whole line
                temp = fileReader.nextLine();

                //split the line using the double tabs pattern
                s = temp.split("\t\t");

                //store the name of the renter
                String name = s[0].substring(6, s[0].length() - 1);

                //store title of unit
                String title;

                if (s[1].substring(0, 5).equals("Game:")) {

                    title = s[1].substring(6, s[1].length() - 1);
                } else {

                    title = s[1].substring(5, s[1].length() - 1);
                }

                //store the Rented On date
                String rentDate = s[2].substring(8);

                //store the Due Date
                String dueDate = s[3].substring(10);


                //if this unit is a DVD
                if (s.length == 4) {

                    //create a DVD using the info collected
                    DVD dvd = new DVD(DATE_FORMAT.parse(rentDate),
                            DATE_FORMAT.parse(dueDate), title, name);

                    //add the DVD to the list
                    add(dvd);

                    //if the unit is a game
                } else if (s.length == 5) {

                    //store the console
                    String player = s[4].substring(9);

                    //create a game using the info collected
                    Game game = new Game(DATE_FORMAT.parse(rentDate),
                            DATE_FORMAT.parse(dueDate), title, name,
                            PlayerType.valueOf(player));

                    //add the Game to the list
                    add(game);
                }

                //update the displayed list
                update(index);

                //get the next unit on the list
                index++;
            }
        }catch (Exception ex){

            //If an error happens, tell the user
            JOptionPane.showMessageDialog(null,
                    "Error in load text from " + filename);
        }

        if(temp == null){

            //If an error happens, tell the user
            JOptionPane.showMessageDialog(null,
                    "FYI: This file is empty");
        }
    }

    /******************************************************************
     * This method finds the late units on a specific dates and
     * collect it in an ArrayList. It starts by checking if the date
     * passed to it is valid, then goes through the current list and
     * checks if the lateOn date is after any of the units' due date,
     * if so it adds that unit to the lateList.
     *
     * @param lateOn - The date which the units due date will be
     *                  compared to
     * @param lateList - A list containing units that are
     *                  considered late
     * @return boolean - if the method wasn't interrupted before
     *                  finding the late units
     * @throws ParseException If the date string parameter had
     *                  anything other than integers, throw and error
     * @throws IllegalArgumentException If the user entered a date
     *                  with more than 2 "/"
     *****************************************************************/
    public boolean findLate (String lateOn, ArrayList lateList)
            throws ParseException{

        //check if date is valid
        String []lateOnDate = lateOn.split("/");
        int lateOnMonth = Integer.parseInt(lateOnDate[0]);
        int lateOnDay = Integer.parseInt(lateOnDate[1]);
        int lateOnYear = Integer.parseInt(lateOnDate[2]);

        if(lateOnDate.length > 3){
            throw new IllegalArgumentException();
        }

        //create a GregorianCalendar object
        GregorianCalendar gc = new GregorianCalendar();

        //if the year was less than one, interrupt the method
        if(lateOnYear < 1){
            return false;
        }

        //if the month was greater than 12 or less than 1, interrupt
        else if(lateOnMonth > 12 || lateOnMonth < 1){
            return false;
        }

        /*if the year wasn't a leap year, february has only 28 days,
          if its greater, interrupt the method
         */
        else if(!gc.isLeapYear(lateOnYear)){
            if (lateOnMonth == 2 && lateOnDay > 28){
                return false;
            }
        }

        /*if the year was a leap year, february has 29 days,
          if its greater, interrupt the method
         */
        else if(gc.isLeapYear(lateOnYear)){
            if (lateOnMonth == 2 && lateOnDay > 29){
                return false;
            }
        }

        /* For months 4,6,9,11 they only have 30 days,
            otherwise, interrupt the method
         */
        else if ((lateOnMonth == 4 || lateOnMonth == 6 ||
                lateOnMonth == 9 || lateOnMonth == 11) &&
                lateOnDay > 30){
            return false;
        }

        //for the rest of the months,if greater than 31 days, interrupt
        else if(lateOnDay > 31 || lateOnDay < 1){
            return false;
        }

        //if the date passes all tests, convert it to a date object
        Date lateDate = DATE_FORMAT.parse(lateOn);

        //create a long to store the difference between date
        long diff;

        //create a string to store all of the unit's info
        String info;

        /*loop to compare the specified date to the due date of
          all of the units on the list
         */
        for (int i = 0; i < listDVDs.size(); i++) {

            //if the passed date is after the unit's due date
            if (lateDate.after(listDVDs.get(i).getDueBack())) {

                //calculate the difference between the two dates in days
                diff = (lateDate.getTime() - listDVDs.get(i).
                        getDueBack().getTime())/ (1000 * 60 * 60 * 24);

                //collect the unit's info and store it in "info"
                info = linePrinter(i);

                //add the information to the lateList
                lateList.add(i, "" + diff + " Day(s) late for:"
                        + info.substring(5));
            }
        }

        //the method wasn't interrupted, a list was built
        return true;
    }

    /******************************************************************
     * This method checks if the parameter string has any
     * leading white space, if so delete it using recursion.
     *
     * @param string - The string to be check for leading white space
     * @return - The same string without leading white space
     *****************************************************************/
    public String checkWhiteSpace (String string){

        //create a new DVD object
        DVD unit = new DVD();

        /* call DelLeadWhiteSpace with the string parameter and return
           the string
         */
        return unit.DelLeadWhiteSpace(string);
    }
}