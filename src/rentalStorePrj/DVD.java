package rentalStorePrj;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DVD implements Serializable {

    private static final long serialVersionUID = 1L;

    protected SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    /** The date the DVD was rented */
    private Date bought;

    /** The DVD is the DVD is due back */
    private Date dueBack;

    /** The title of the DVD */
    private String title;

    /** The name of the person who is renting the DVD */
    private String nameOfRenter;

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

        GregorianCalendar dueDate = new GregorianCalendar();
        dueDate.setTime(dueBack);

        if (dateReturned.after(dueDate)){
            total += DVD_LATE_FEE;
        }

        return total;
    }

    protected boolean checkReturnDate(String dateReturned){

        String [] returnedDate;
        returnedDate = dateReturned.split("/");

        int monthReturned = Integer.parseInt(returnedDate[0]);
        int dayReturned = Integer.parseInt(returnedDate[1]);
        int yearReturned = Integer.parseInt(returnedDate[2]);

        if(yearReturned < 1){
            return false;
        }else if(monthReturned > 12 || monthReturned < 1){
            return false;
        }else if(dayReturned > 31 || dayReturned < 1){
            return false;
        }

        GregorianCalendar calendarReturn = new GregorianCalendar();
        GregorianCalendar calendarBought = new GregorianCalendar();
        Date returnDate;

        try {
            returnDate = DATE_FORMAT.parse(dateReturned);

        }catch (ParseException e){
            return false;
        }

        calendarReturn.setTime(returnDate);
        calendarBought.setTime(bought);

        if(calendarBought.after(calendarReturn)){
            return false;
        }

        return true;
    }

    public String DelLeadWhiteSpace (String string){

        if(string.equals("")){
            return string;
        }

        if(string.charAt(0) == ' '){
            string = DelLeadWhiteSpace(string.substring(1));
        }

        return string;

    }
}
