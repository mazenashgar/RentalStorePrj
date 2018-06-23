package rentalStorePrj;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;

    protected SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

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
    protected String [] due;
    protected String [] returnedDate;

    private final double DVD_RENT_FEE = 1.20;
    private final double DVD_LATE_FEE = 2.00;

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

        double total = DVD_RENT_FEE;


        DATE_FORMAT.setCalendar(dateReturned);
        String returnedOn = DATE_FORMAT.format(dateReturned.getTime());
        String dueDate = DATE_FORMAT.format(getDueBack());

        setReturnDate(returnedOn, dueDate);

        if (yearDue < yearReturned){
            total += DVD_LATE_FEE;
        }else if( monthDue < monthReturned){
            total += DVD_LATE_FEE;
        }else if(dayDue < dayReturned){
            total += DVD_LATE_FEE;
        }

        return total;
	}

	protected void setReturnDate (String returnedOn, String dueDate){

        checkReturnDate(returnedOn);

        due = dueDate.split("/");

        monthDue = Integer.parseInt(due[0]);
        dayDue = Integer.parseInt(due[1]);
        yearDue = Integer.parseInt(due[2]);

    }

	protected boolean checkReturnDate(String dateReturned){

        returnedDate = dateReturned.split("/");

        monthReturned = Integer.parseInt(returnedDate[0]);
        dayReturned = Integer.parseInt(returnedDate[1]);
        yearReturned = Integer.parseInt(returnedDate[2]);

        String rentedDate = DATE_FORMAT.format(getBought());
        String[] rented = rentedDate.split("/");

        int monthRented = Integer.parseInt(rented[0]);
        int dayRented = Integer.parseInt(rented[1]);
        int yearRented = Integer.parseInt(rented[2]);

        if(yearRented < 1){
            return false;
        }
        if(monthReturned > 12 || monthReturned < 1){
            return false;
        }else if(dayReturned > 31 || dayReturned < 1){
            return false;
        }

        if(yearRented > yearReturned){
            return false;
        }else if (monthRented > monthReturned){
            return false;
        }else if(dayRented > dayReturned){
            return false;
        }
        return true;
	}
}
