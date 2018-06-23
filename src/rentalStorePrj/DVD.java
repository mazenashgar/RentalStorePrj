package rentalStorePrj;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;

    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	/** The date the DVD was rented */
	protected Date bought;

	/** The DVD is the DVD is due back */
	protected Date dueBack;

	/** The title of the DVD */
	protected String title;

	/** The name of the person who is renting the DVD */
	protected String nameOfRenter;

    protected int monthReturned;
    protected int dayReturned;
    protected int yearReturned;
    protected int monthDue;
    protected int dayDue;
    protected int yearDue;
    protected String [] Due;
    String [] returnedDate;

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

	public double getCost(GregorianCalendar dateReturned) {

        double total = 1.20;


        df.setCalendar(dateReturned);
        String returnedOn = df.format(dateReturned.getTime());
        String dueDate = df.format(getDueBack());

        setReturnDate(returnedOn, dueDate);

        if (yearDue < yearReturned){
            total += 2.0;
        }else if( monthDue < monthReturned){
            total += 2.0;
        }else if(dayDue < dayReturned){
            total += 2.0;
        }

        return total;
	}

	protected void setReturnDate (String returnedOn, String dueDate){

        returnedDate = returnedOn.split("/");

        monthReturned = Integer.parseInt(returnedDate[0]);
        dayReturned = Integer.parseInt(returnedDate[1]);
        yearReturned = Integer.parseInt(returnedDate[2]);


        Due = dueDate.split("/");

        monthDue = Integer.parseInt(Due[0]);
        dayDue = Integer.parseInt(Due[1]);
        yearDue = Integer.parseInt(Due[2]);
    }


}
