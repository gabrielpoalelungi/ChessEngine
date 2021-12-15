import java.awt.*;
import java.util.Vector;

public class Rook extends Piece{

    boolean hasBeenMoved = false;
    boolean hasBeenTaken = false;
    Vector<CellPosition> moves;

    public Rook (Color color, CellPosition pos){
        this.color = color;
        this.pos = pos;
    }

    public void getMoves() {
        CellPosition currentPosition = Board.getInstance().getPiecePosition(this);
        moves = new Vector<CellPosition>();

        CellPosition currentKingPos;
        if (Board.getInstance().currentColor == Color.BLACK) {
            currentKingPos = Board.getInstance().blackKing.pos;
        } else {
            currentKingPos = Board.getInstance().whiteKing.pos;
        }

        //Mutari la stanga
        for (int i = currentPosition.x - 1; i >= 0; i--) {

            //Daca pozitia actuala se afla o piesa de aceeasi culoare ies din for
            if (Board.getInstance().gameboard[i][currentPosition.y] != null && Board.getInstance().gameboard[i][currentPosition.y].color == this.color) {
                break;
            }
            //Daca pe pozitia actuala se afla o piesa inamica, adaug pozitia in vector si ies din for
            else if (Board.getInstance().gameboard[i][currentPosition.y] != null && Board.getInstance().gameboard[i][currentPosition.y].color != this.color) {

                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, i, currentPosition.y);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    moves.add(new CellPosition(i, currentPosition.y));
                }
                break;
            }
            //Daca nu se afla nimic pe pozitia actual, o adaug in vector si trec la urmatoarea pozitie
            else {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, i, currentPosition.y);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    moves.add(new CellPosition(i, currentPosition.y));
                }
            }
        }

        //Mutari la dreapta
        for (int i = currentPosition.x + 1; i < 8; i++) {
            if (Board.getInstance().gameboard[i][currentPosition.y] != null && Board.getInstance().gameboard[i][currentPosition.y].color == this.color) {
                break;
            } else if (Board.getInstance().gameboard[i][currentPosition.y] != null && Board.getInstance().gameboard[i][currentPosition.y].color != this.color) {

                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, i, currentPosition.y);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    moves.add(new CellPosition(i, currentPosition.y));
                }
                break;
            }
            //Daca nu se afla nimic pe pozitia actual, o adaug in vector si trec la urmatoarea pozitie
            else {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, i, currentPosition.y);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    moves.add(new CellPosition(i, currentPosition.y));
                }
            }
        }

        //Mutari in sus
        for (int i = currentPosition.y - 1; i >= 0 ; i--) {
            if (Board.getInstance().gameboard[currentPosition.x][i] != null && Board.getInstance().gameboard[currentPosition.x][i].color == this.color) {
                break;
            } else if (Board.getInstance().gameboard[currentPosition.x][i] != null && Board.getInstance().gameboard[currentPosition.x][i].color != this.color) {

                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, currentPosition.x, i);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    moves.add(new CellPosition(currentPosition.x, i));
                }
                break;
            }
            //Daca nu se afla nimic pe pozitia actual, o adaug in vector si trec la urmatoarea pozitie
            else {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y,currentPosition.x, i);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    moves.add(new CellPosition(currentPosition.x, i));
                }
            }
        }

        //Mutari in jos
        for (int i = currentPosition.y + 1; i < 8; i++) {
            if (Board.getInstance().gameboard[currentPosition.x][i] != null && Board.getInstance().gameboard[currentPosition.x][i].color == this.color) {
                break;
            } else if (Board.getInstance().gameboard[currentPosition.x][i] != null && Board.getInstance().gameboard[currentPosition.x][i].color != this.color) {

                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, currentPosition.x, i);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    moves.add(new CellPosition(currentPosition.x, i));
                }
                break;
            }
            //Daca nu se afla nimic pe pozitia actual, o adaug in vector si trec la urmatoarea pozitie
            else {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y,currentPosition.x, i);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    moves.add(new CellPosition(currentPosition.x, i));
                }
            }
        }
    }

    public boolean move() {
        System.out.println("Rook From: " + this.pos);
        getMoves();
        if (moves.size() > 0) {
            hasBeenMoved = true;
            CellPosition currentPosition = Board.getInstance().getPiecePosition(this);
            CellPosition position = moves.get((int) (Math.random() * moves.size()));
            moveFromToEngine(currentPosition.x, currentPosition.y, position.x, position.y);
            return true;
        }
        return false;
    }

    public String toString() {
        if(color == Color.black)
            return "R";
        return "r";
    }
}