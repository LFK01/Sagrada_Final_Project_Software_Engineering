package it.polimi.se2018.model.events.messages;

public class SendWinnerMessage extends Message {
    private String winnerName;
    private int winnerScore;
    public SendWinnerMessage(String sender, String recipient,String winnerName, int winnerScore) {
        super(sender, recipient);
        this.winnerName = winnerName;
        this.winnerScore = winnerScore;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public int getWinnerScore() {
        return winnerScore;
    }
}
