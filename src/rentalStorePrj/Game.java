package rentalStorePrj;

import java.util.Date;
import java.util.GregorianCalendar;

public class Game extends DVD {

    /** Represents the type of player */
    private PlayerType player;  // Xbox360, Xbox1, PS4, WiiU, NintendoSwitch
    private final double GAME_RENT_FEE = 5.00;
    private final double GAME_LATE_FEE = 10.00;

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

        double total = GAME_RENT_FEE;


        DATE_FORMAT.setCalendar(dateReturned);
        String returnedOn = DATE_FORMAT.format(dateReturned.getTime());
        String dueDate = DATE_FORMAT.format(getDueBack());

        setReturnDate(returnedOn, dueDate);

        if (yearDue < yearReturned){
            total += GAME_LATE_FEE;
        }else if(monthDue < monthReturned){
            total += GAME_LATE_FEE;
        }else if(dayDue < dayReturned){
            total += GAME_LATE_FEE;
        }

        return total;
    }
}
