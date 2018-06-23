package rentalStorePrj;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentalStore extends AbstractListModel {

	private ArrayList<DVD> listDVDs;
	private boolean filter;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	public RentalStore() {
		super();
		filter = false;
		listDVDs = new ArrayList<DVD>();

	}

	public void add (DVD a) {
		listDVDs.add(a);
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	public DVD get (int i) {
		return listDVDs.get(i);
	}
	public void update(int index){
	    fireContentsChanged(this, index, index);
    }

	public void remove (DVD r, int index){
	    listDVDs.remove(r);
	    fireIntervalRemoved(this, index, listDVDs.size());
    }

	public Object getElementAt(int arg0) {



		DVD unit = listDVDs.get(arg0);

		//		String rentedOnDateStr = DateFormat.getDateInstance(DateFormat.SHORT)
		//				.format(unit.getRentedOn().getTime());

		String line = linePrinter(arg0);

		return line;
	}

	public int getSize() {

		return listDVDs.size();
	}

	public String linePrinter (int arg0){

        DVD unit = listDVDs.get(arg0);

        String line = "Name: " + listDVDs.get(arg0).getNameOfRenter()
                + ",\t\t";

        if(unit instanceof Game){
            line += "Game: ";
        }else{
            line += "DVD: ";
        }

        line += listDVDs.get(arg0).getTitle()
                + ",\t\t" + "Rented: " + (df.format(listDVDs.get(arg0).getBought()))
                + ",\t\t" + "Due Date: " + (df.format(listDVDs.get(arg0).getDueBack()));

        if (unit instanceof Game)
            line += ",\t\t"+ "Car Player: " + ((Game)unit).getPlayer();

        return line;
    }

    public void saveAsSerializable(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(listDVDs);
			os.close();
		}
		catch (IOException ex) {
			JOptionPane.showMessageDialog(null,"Error in saving db");

		}
	}

	public void loadFromSerializable(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			listDVDs = (ArrayList<DVD>) is.readObject();
			fireIntervalAdded(this, 0, listDVDs.size() - 1);
			is.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error in loading db");
		}
	}

    public void saveAsText (String filename){

        int index = 0;
        PrintWriter out = null;


        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

            while(index < listDVDs.size()){
                out.println(linePrinter(index));
                index++;
            }
            out.close();
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Error in saving text to" + filename);

        }
    }

    public void loadFromText (String filename){

	    int index = 0;
        Scanner fileReader;
        listDVDs = new ArrayList<DVD>();
        String temp;
        String[] s;

	    try{
	        fileReader = new Scanner(new File(filename));

	        while(fileReader.hasNextLine()){

	            temp = fileReader.nextLine();
	            s = temp.split("\t\t");

                String name = s[0].substring(5,s[0].length()-1);
                String title;
                if(s[1].substring(0,5).equals("Game:")){
                    title = s[1].substring(6, s[1].length()-1);
                }else{
                    title = s[1].substring(5, s[1].length()-1);
                }
                String rentDate = s[2].substring(8);
                String dueDate = s[3].substring(10);


	            if(s.length == 4){
	                DVD dvd = new DVD(df.parse(rentDate),df.parse(dueDate),title,name);
                    add(dvd);

                }else if (s.length == 5){
                    String player = s[4].substring(12);
                    Game game = new Game(df.parse(rentDate),df.parse(dueDate),title,name, PlayerType.valueOf(player));
	                add(game);
                }
                update(index);
	            index++;
            }

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error in load text from" + filename);
        }
    }
}
