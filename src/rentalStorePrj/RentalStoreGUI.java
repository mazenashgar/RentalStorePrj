package rentalStorePrj;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RentalStoreGUI extends JFrame implements ActionListener {

    /** Holds menu bar */
    private JMenuBar menus;

    /**
     * menus in the menu bar
     */
    private JMenu fileMenu;
    private JMenu actionMenu;

    /**
     * menu items in each of the menus
     */
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;
    private JMenuItem openTextItem;
    private JMenuItem saveTextItem;
    private JMenuItem rentDVD;
    private JMenuItem rentGame;
    private JMenuItem returnItem;
    private JMenuItem findLateItem;

    /** Holds the list engine */
    private RentalStore list;

    /** Holds JListArea */
    private JList JListArea;

    /** Scroll pane */
    private JScrollPane scrollList;

    private ImageIcon icon;

    private RentalStoreGUI() {

        icon = new ImageIcon("rentalStore.png");

        //adding menu bar and menu items
        menus = new JMenuBar();
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Action");
        openSerItem = new JMenuItem("Open Serial");
        exitItem = new JMenuItem("Exit");
        saveSerItem = new JMenuItem("Save Serial");
        openTextItem = new JMenuItem("Open Text");
        saveTextItem = new JMenuItem("Save Text");
        rentDVD = new JMenuItem("Rent DVD");
        rentGame = new JMenuItem("Rent Game");
        returnItem = new JMenuItem("Return");
        findLateItem = new JMenuItem("Find late");

        //adding items to bar
        fileMenu.add(openTextItem);
        fileMenu.add(openSerItem);
        fileMenu.add(saveTextItem);
        fileMenu.add(saveSerItem);
        fileMenu.add(exitItem);
        actionMenu.add(rentDVD);
        actionMenu.add(rentGame);
        actionMenu.add(findLateItem);
        actionMenu.add(returnItem);

        menus.add(fileMenu);
        menus.add(actionMenu);

        //adding actionListener
        openTextItem.addActionListener(this);
        openSerItem.addActionListener(this);
        saveTextItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        exitItem.addActionListener(this);
        rentDVD.addActionListener(this);
        rentGame.addActionListener(this);
        findLateItem.addActionListener(this);
        returnItem.addActionListener(this);


        setJMenuBar(menus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adding list to the GUI1024
        list = new RentalStore();
        JListArea = new JList(list);
        scrollList = new JScrollPane(JListArea);
        add(scrollList);

        setTitle("Rental Store");
        setVisible(true);
        setSize(700, 800);
    }

    public void actionPerformed(ActionEvent e) {

        Object comp = e.getSource();

        if (openSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openSerItem == comp)
                    list.loadFromSerializable(filename);
            }
        }

        if(openTextItem == comp){
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openTextItem == comp)
                    list.loadFromText(filename);
            }
        }

        if (saveSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource())
                    list.saveAsSerializable(filename);
            }
        }

        if(saveTextItem == comp){
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveTextItem == e.getSource())
                    list.saveAsText(filename);
            }
        }

        //MenuBar options
        if (comp == exitItem) {
            System.exit(1);
        }
        if (e.getSource() == rentDVD) {
            DVD dvd = new DVD();
            RentDVDDialog dialog = new RentDVDDialog(this, dvd);

            if(dialog.closeOK()) {
                list.add(dvd);
            }
        }
        if(comp == rentGame){
            Game game = new Game();
            RentGameDialog dialog = new RentGameDialog(this, game);

            if(dialog.closeOK()){
                list.add(game);
            }
        }

        if(comp == findLateItem){
            lateUnits();
        }

        if (comp == returnItem) {

            returnUnit();
        }
    }

    private void lateUnits(){

        ArrayList<String> lateList = new ArrayList<>();
        String lateOnDate = (String) JOptionPane.showInputDialog(null,
                "Please enter a date to find the late units on it" +
                        "\nPlease use the following format: MM/DD/YYYY", "Late Units Finder",
                JOptionPane.QUESTION_MESSAGE, icon, null, null);

        if(lateOnDate != null) {
            try {

                lateOnDate = list.checkWhiteSpace(lateOnDate);

                if (!list.findLate(lateOnDate, lateList)) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid date to find late units",
                            "Error", JOptionPane.ERROR_MESSAGE, icon);
                    lateUnits();
                }

                if (lateList.size() == 0) {

                    JOptionPane.showMessageDialog(null,
                            "No units are late on " + lateOnDate,
                            "Error", JOptionPane.INFORMATION_MESSAGE, icon);
                } else {

                    //Show list
                    JOptionPane.showMessageDialog(null,
                            new JList(lateList.toArray()), "Late Units",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid date to find late units",
                        "Error", JOptionPane.ERROR_MESSAGE, icon);
                lateUnits();
            }

        }else{
            return;
        }
    }

    private void returnUnit (){
        GregorianCalendar date = new GregorianCalendar();

        String inputDate = (String) JOptionPane.showInputDialog(null,
                "Enter return date: " +
                        "\nPlease use the following format: MM/DD/YYYY", "Return",
                JOptionPane.QUESTION_MESSAGE, icon, null, null);

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        NumberFormat numFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        try {
            if (inputDate != null) {
                inputDate = list.checkWhiteSpace(inputDate);
                Date newDate = df.parse(inputDate);
                date.setTime(newDate);
            }
            else {
                return;
            }
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(null,
                    "Please enter a valid date to return",
                    "Error", JOptionPane.ERROR_MESSAGE, icon);
            returnUnit();
        }

        try {
            int index = JListArea.getSelectedIndex();
            DVD unit = list.get(index);

            if(unit.checkReturnDate(inputDate)) {

                double cost = unit.getCost(date);

                JOptionPane.showMessageDialog(null,
                        "Thanks " + unit.getNameOfRenter() +
                                " for returning " + unit.getTitle() + "\nYou owe: "
                                + numFormatter.format(cost) + " dollars",
                        "Returned", JOptionPane.INFORMATION_MESSAGE, icon);

                list.remove(unit, index);
            }else{
                JOptionPane.showMessageDialog(null,
                        "Return date is invalid or it is before the rented on Date",
                        "Error", JOptionPane.ERROR_MESSAGE, icon);
                returnUnit();
            }

        }catch(ArrayIndexOutOfBoundsException a){
            JOptionPane.showMessageDialog(null,
                    "Please select a unit to return it", "Error",
                    JOptionPane.ERROR_MESSAGE, icon);
        }catch(NumberFormatException n){
            return;
        }
    }

    public static void main(String[] args) {
        new RentalStoreGUI();
    }
}
