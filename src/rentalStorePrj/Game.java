package rentalStorePrj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Game extends DVD {

    /** Represents the type of player */
    private PlayerType player;  // Xbox360, Xbox1, PS4, WiiU, NintendoSwitch

    public Game() {}

    public Game(Date bought, Date dueBack, String title, String name, PlayerType player) {

        setBought(bought);
        setDueBack(dueBack);
        setTitle(title);
        setNameOfRenter(name);
        setPlayer(player);
    }

    public PlayerType getPlayer() {
        return player;
    }

    public void setPlayer(PlayerType player) {
        this.player = player;

    }

    public double getCost(GregorianCalendar dateReturned) {

        double total = 5.0;


        df.setCalendar(dateReturned);
        String returnedOn = df.format(dateReturned.getTime());
        String dueDate = df.format(getDueBack());

        setReturnDate(returnedOn, dueDate);

        if (yearDue < yearReturned){
            total += 10.0;
        }else if(monthDue < monthReturned){
            total += 10.0;
        }else if(dayDue < dayReturned){
            total += 10.0;
        }

        return total;
    }
}
