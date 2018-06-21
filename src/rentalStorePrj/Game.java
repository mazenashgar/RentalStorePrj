package rentalStorePrj;

public class Game extends DVD {

    /** Represents the type of player */
    private PlayerType player;  // Xbox360, Xbox1, PS4, WiiU, NintendoSwitch

    public Game() {
    }

    public PlayerType getPlayer() {
        return player;
    }

    public void setPlayer(PlayerType player) {
        this.player = player;
    }
}
