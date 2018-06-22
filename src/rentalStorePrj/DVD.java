package rentalStorePrj;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Date;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The date the DVD was rented */
	protected Date bought;

	/** The DVD is the DVD is due back */
	protected Date dueBack;

	/** The title of the DVD */
	protected String title;

	/** The name of the person who is renting the DVD */
	protected String nameOfRenter; 

	public DVD() {
	}

	public DVD(Date bought, Date dueBack, String title, String name) {
		super();
		this.bought = bought;
		this.dueBack = dueBack;
		this.title = title;
		this.nameOfRenter = name;
	}

	public Date getBought() {
		return bought;
	}
	
	public void setBought(Date bought) {
		this.bought = bought;
	}
	
	public Date getDueBack() {
		return dueBack;
	}
	
	public void setDueBack(Date dueBack) {
		this.dueBack = dueBack;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNameOfRenter() {
		return nameOfRenter;
	}

	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}

	public double getCost(GregorianCalendar date) {
		return 2.0;
	}
}
