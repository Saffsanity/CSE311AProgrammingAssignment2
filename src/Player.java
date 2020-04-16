import java.util.Collections;
import java.util.Comparator;

public class Player {
    enum TYPE {
        T4T,
        G,
        AC,
        AD
    }

    enum ACTION {
        COOPERATE,
        DEFECT
    }

    public int payoff;
    private TYPE type;
    private ACTION action;

    public Player() {
        type = TYPE.T4T;
        action = ACTION.COOPERATE;
        payoff = 0;
    }

    public Player(TYPE t) {
        type = t;
        if (t == TYPE.AD) {
            action = ACTION.DEFECT;
        } else {
            action = ACTION.COOPERATE;
        }
        payoff = 0;
    }

    public Player(Player copy) {
        this.type = copy.type;
        this.action = copy.action;
        this.payoff = copy.payoff;
    }

    public TYPE getType() {
        return type;
    }
//
//    public ACTION getAction() {
//        return action;
//    }

    public void calculatePayoff(Player other) {
        if (this.action == ACTION.COOPERATE && other.action == ACTION.COOPERATE) {
            this.payoff += 3;
            other.payoff += 3;
        } else if (this.action == ACTION.COOPERATE && other.action == ACTION.DEFECT) {
            other.payoff += 5;
        } else if (this.action == ACTION.DEFECT && other.action == ACTION.COOPERATE) {
            this.payoff += 5;
        } else if (this.action == ACTION.DEFECT && other.action == ACTION.DEFECT) {
            this.payoff += 1;
            other.payoff += 1;
        }
    }

    public void calculateNextAction(Player other) {
        if (this.type == TYPE.T4T) {
            this.action = other.action;
        } else if (this.type == TYPE.G && other.action == ACTION.DEFECT) {
            this.action = ACTION.DEFECT;
        }
    }

    public void resetState(){
        if (this.type == TYPE.AD) {
            action = ACTION.DEFECT;
        } else {
            action = ACTION.COOPERATE;
        }
    }

    public void playDilemma(int m, Player other) {
        for (int i = 0; i < m; i++) {
            this.calculatePayoff(other);
            this.calculateNextAction(other);
            other.calculateNextAction(this);
        }
        this.resetState();
        other.resetState();
    }


}
