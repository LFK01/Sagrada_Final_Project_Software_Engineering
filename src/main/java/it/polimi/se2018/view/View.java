package it.polimi.se2018.view;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.MoveMessage;
import it.polimi.se2018.model.events.PlayerMove;
//Giovanni

public abstract class View {
    private Player player;
    private Model model;

    public View(Player player, Model model){
        this.player= player;
        this.model = model;
    }
    public Player getPlayer() {
        return player;
    }
    public void handleDiceMove(int pos){
        notify(new PlayerMove(player));
    }
    public void handleToolCardMove(int index){


    }
    public void showMessage(String message){


    }
    public void reportError(String message){


    }
    public void update(MoveMessage message){


    }
}


