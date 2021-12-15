import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Bishop extends Piece{
    Vector<CellPosition> canMoveTo;

    public Bishop (Color color, CellPosition pos){
        this.color = color;
        this.pos = pos;
    }

    public String toString() {
        if (color == Color.black)
            return "B";
        return "b";
    }

    public boolean move(){
        getMoves();

        System.out.println("Bishop From: " + this.pos);
        System.out.println("Possible moves: ");
        for(CellPosition c : canMoveTo) {
            System.out.print(c + " ");
        }
        System.out.println("");

        if (canMoveTo.size() > 0) {
            CellPosition currentPosition = Board.getInstance().getPiecePosition(this);
            CellPosition position = canMoveTo.get((int) (Math.random() * canMoveTo.size()));
            moveFromToEngine(currentPosition.x, currentPosition.y, position.x, position.y);
            return true;
        }
        return false;
    }


    public void getMoves() {
        Board myBoard = Board.getInstance();
        canMoveTo = new Vector<>();
        CellPosition currentKingPos;
        if (Board.getInstance().currentColor == Color.BLACK) {
            currentKingPos = Board.getInstance().blackKing.pos;
        } else {
            currentKingPos = Board.getInstance().whiteKing.pos;
        }

        //-------------------------diagonala dreapta sus------------------------------------
        int posx = this.pos.x + 1;
        int posy = this.pos.y + 1;
        while (posx < 8 && posy < 8 && myBoard.gameboard[posx][posy] == null){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
            posx++;
            posy++;
        }
        if (posx < 8 && posy < 8 && !myBoard.gameboard[posx][posy].color.equals(color)){
            //cazul in care da peste o piesa de culare diferitasi poate sa o ia
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
        }

        //--------------------------diagonala dreapta jos-------------------------------------
        posx = this.pos.x - 1;
        posy = this.pos.y + 1;
        while (posx >= 0 && posy < 8 && myBoard.gameboard[posx][posy] == null){

            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
            posx--;
            posy++;
        }
        if (posx >= 0 && posy < 8 && !myBoard.gameboard[posx][posy].color.equals(color)){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
        }

        //---------------------------diagonala stanga sus----------------------------------
        posx = this.pos.x + 1;
        posy = this.pos.y - 1;
        while (posx < 8 && posy >= 0 && myBoard.gameboard[posx][posy] == null){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
            posx++;
            posy--;
        }
        if (posx < 8 && posy >= 0 && !myBoard.gameboard[posx][posy].color.equals(color)){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
        }

        //----------------------------diagonala stanga jos----------------------------------
        posx = this.pos.x - 1;
        posy = this.pos.y - 1;
        while (posx >= 0 && posy >= 0 && myBoard.gameboard[posx][posy] == null){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
            posx--;
            posy--;
        }
        if (posx >= 0 && posy >= 0 && !myBoard.gameboard[posx][posy].color.equals(color)){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
        }
    }
}