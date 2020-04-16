import java.util.Comparator;

public class PlayerSorter  implements Comparator<Player> {
    // Sorting
    public int compare(Player one, Player two) {
        return two.payoff - one.payoff;
    }
}
