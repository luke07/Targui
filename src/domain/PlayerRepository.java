package domain;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import targui.Constants;

/**
 *
 * @author Lukas.Pasta
 */
public class PlayerRepository {
    private ArrayList<Player> players;
    private Game game;
    
    public PlayerRepository() {
        players = new ArrayList<Player>();
    }
    
    public void setGame(Game gameParam) {
        game = gameParam;
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    public void registerPlayer(String nameParam, String colorParam, int sectorParam) {
            try {
                validateName(nameParam);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("name");
            }
            
           // Color color;
            try {
                validateColor(colorParam);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("color");
            }
            
            int sectorInt = sectorParam - 1;
            Sector sector;
            try {
                sector = validateSector(sectorInt);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("sector");
            }
            players.add(new Player(nameParam, colorParam, sector));
        }
    
    
    
    private void validateName(String nameParam) {
        for (Player p : players) {
            if (p.getName().compareToIgnoreCase(nameParam) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    private void validateColor(String colorParam) {
        /*
        Color color;
        try {
            Field field = Class.forName("java.awt.Color").getField(colorParam.toLowerCase());
            color = (Color)field.get(null);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        */
        

        for (Player p : players) {
            if (p.getColor().compareTo(colorParam) == 0)
                throw new IllegalArgumentException();
        }
    }
    
    private Sector validateSector(int sectorParam) {
        if (sectorParam < 0 || sectorParam >= Constants.SectorCount)
            throw new IllegalArgumentException();
        Sector sector = game.getSector(sectorParam);
        for (Player p : players) {
          if (p.getSector() == sector)
              throw new IllegalArgumentException();
        }
        return sector;
    }
    
    public Player getPlayer(int i) {
        for (Player p : players)
            if (p.getNumber() == i)
                return p;
        return null;
    }
    
    public Player getPlayer(Sector s) {
        for (Player p : players)
            if (p.getSector() == s)
                return p;
        return null;
    }
}
