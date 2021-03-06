package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.lang.Exception.*;
import domain.MCards.*;
import targui.Constants;

/**
 *
 * @author Lukas.Pasta
 */
public class Round {
    private boolean[] playerLoseCell;
    private boolean mountanEconomicValueChanged;
    private boolean ergStrategicValueChanged;
    private ArrayList<RoundCard> rCards;
    private ArrayList<RoundCard> usedCards;
    private int number;
    protected int moveActionsAvailable, purchaseActionsAvailable;
    Round(int roundNum) {        
        mountanEconomicValueChanged = ergStrategicValueChanged = false;
        number = roundNum;
        rCards = new ArrayList<RoundCard>();
        usedCards = new ArrayList<RoundCard>();
        playerLoseCell = new boolean[Constants.PlayerCount];
        for (int i = 0; i < Constants.PlayerCount; i++)
            playerLoseCell[i] = false;
    }
    
    public int getNumber() {
        return number;
    }
    
    public boolean isRoundEnd() {
        return (rCards.isEmpty());
    }
    
    public void addCard(RoundCard card) {
        rCards.add(card);
    }
    
    public int getNextCard() {
        RoundCard card = rCards.remove(0);
        setNewActionsAvailable();
        
        
        usedCards.add(card);
        if (card instanceof TribalCard) {
            Player player = ((TribalCard)card).getPlayer();
            return player.getNumber();
        }
        else if (card instanceof MCard) {
            ((MCard)card).proceed();
            if (card instanceof SilverDiscoveredMCard)
                
            return -1;
        }
        return 0;
    }
    
    protected void setNewActionsAvailable() {
        throw new UnsupportedOperationException();
    }
    
    public void shuffleCards() {
        Collections.shuffle(rCards, new Random(System.currentTimeMillis()));
    }
    
    public void returnCards() {
        RoundCard card;
        
        while(!usedCards.isEmpty()) {
            card = usedCards.remove(0);
            if (card instanceof TribalCard) {
                ((TribalCard)card).getPlayer().insertTCard((TribalCard)card);
            }
        }     
    }
    
    
    public boolean canPerformMove() {
        return (moveActionsAvailable != 0);
    }
    
    public boolean canPerformPurchase() {
        return (purchaseActionsAvailable != 0);
    }
    
    public void performMove() {
        decreaseMoveAvailable();
    }
    
   public void performPurchase() {
        decreasePurchaseAvailable();
    }
   
   private void decreaseMoveAvailable() {
       moveActionsAvailable -= 1;
       if (moveActionsAvailable < purchaseActionsAvailable - 1) purchaseActionsAvailable -= 1;
   };
   private void decreasePurchaseAvailable() {
       purchaseActionsAvailable -= 1;
       if (moveActionsAvailable > purchaseActionsAvailable) moveActionsAvailable -= 1;
   }
   
   public void playerLoseCell(int player) {
       playerLoseCell[player] = true;
   }
   
   public boolean didPlayerLostCell(int player) {
       return playerLoseCell[player];
   }
   
   public boolean isNextRoundCardMCard() {
       RoundCard card = rCards.get(0);
       return ( card instanceof MCard);
   }
   
   public boolean didErgStrategicValueChanged() {
       return ergStrategicValueChanged;
   }
   
   public boolean didMountanEconomicValueChanged() {
       return mountanEconomicValueChanged;
   }
}
