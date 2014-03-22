package domain;
import targui.Constants;
import java.util.ArrayList;

/**
 *
 * @author Lukas.Pasta
 */
public class Board {
        
    private Cell[][] Cells;
    private Sector[] Sectors;
    
    public Board() {
        Sectors = new Sector[Constants.SectorCount];
        for (int i = 0; i < Constants.SectorCount; i++)
            Sectors[i] = new Sector();
        
        Cells = new Cell[Constants.BoardSize][Constants.BoardSize];  
        for (int i = 0; i < Constants.BoardSize; i++) {
            for (int j = 0; j < Constants.BoardSize; j++) {
                if ( (i == 0 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == 2) ||
                     (i == 1 && j == 0) || (i == 1 && j == 1) || (i == 1 && j == 2) ||
                     (i == 2 && j == 0) || (i == 2 && j == 1)
                   )
                   Cells[i][j] = new Cell(Sectors[0]);
                
                else if ( (i == 0 && j == 4) || (i == 0 && j == 5) || (i == 0 && j == 6) ||
                          (i == 1 && j == 4) || (i == 1 && j == 5) || (i == 1 && j == 6) ||
                                                (i == 2 && j == 5) || (i == 2 && j == 6)
                   )
                   Cells[i][j] = new Cell(Sectors[1]);
                
                else if ( (i == 4 && j == 0) || (i == 4 && j == 1) ||
                          (i == 5 && j == 0) || (i == 5 && j == 1) || (i == 5 && j == 2) ||
                          (i == 6 && j == 0) || (i == 6 && j == 1) || (i == 6 && j == 2)
                   )
                   Cells[i][j] = new Cell(Sectors[2]);
  
                else if (                       (i == 4 && j == 5) || (i == 4 && j == 6) ||
                          (i == 5 && j == 4) || (i == 5 && j == 5) || (i == 5 && j == 6) ||
                          (i == 6 && j == 4) || (i == 6 && j == 5) || (i == 6 && j == 6)
                   )
                   Cells[i][j] = new Cell(Sectors[3]);                
                
                else
                   Cells[i][j] = new Cell();
            }
        }
        
    }
    
    
    public Sector getSector(int sectorNum) {
        return Sectors[sectorNum];
    }
    
    public void placeCardsOnBoard(TCard mine, ArrayList<TCard> tCards) {
        int row = 3, column = 3, times = 1, direction = 2, counter = 0;
        boolean firstTimeAlready = false;
        
        Cells[row][column].setTCard(mine);       
        do {
            switch (direction) {
                case 1: row -= 1;       break;
                case 2: column += 1;    break;
                case 3: row += 1;       break;
                case 4: column -= 1;    break;
            }
            Cells[row][column].setTCard(tCards.remove(0));
            counter++;
            
            if (counter == times) {
                counter = 0;
                if (firstTimeAlready) {
                    firstTimeAlready = false;
                    times += 1;
                }
                else
                    firstTimeAlready = true;
                direction = (direction < 4) ? (direction + 1) : 1;
            }
        } while (row != 0 || column != (Constants.BoardSize - 1));
    }
    
    public Cell getCell(int row, int column) {
        return Cells[row][column];
    }
}
