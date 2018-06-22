package rentalStorePrj;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentalStore extends AbstractListModel {

	private ArrayList<DVD> listDVDs;

	private boolean filter;

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

	public Object getElementAt(int arg0) {	

			//return "Happy";

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		DVD unit = listDVDs.get(arg0);

		//		String rentedOnDateStr = DateFormat.getDateInstance(DateFormat.SHORT)
		//				.format(unit.getRentedOn().getTime());

		String line = "Name: " + listDVDs.get(arg0).getNameOfRenter()
			+ "\t\t" + "DVD: " + listDVDs.get(arg0).getTitle()
                + "\t\t" + "Rented: " + (df.format(listDVDs.get(arg0).getBought()))
                + "\t\t" + "Due Date: " + (df.format(listDVDs.get(arg0).getDueBack()));

		//		if (unit instanceof Game)
		//			line += ", Car Player: " + ((Game)unit).getPlayer();

		return line;
	}

	public int getSize() {
	//	return 5;
		return listDVDs.size();
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
}
