import java.awt.*;
import java.util.Vector;

public class Queen extends Piece{
    Vector<CellPosition> canMoveTo;

    public Queen (Color color, CellPosition pos){
        this.color = color;
        this.pos = pos;
    }

    public boolean move(){
        getMoves();
        System.out.println("Queen From: " + this.pos);
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
        while(posx < 8 && posy < 8 && myBoard.gameboard[posx][posy] == null){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
            posx++;
            posy++;
        }
        if(posx < 8 && posy < 8 && !myBoard.gameboard[posx][posy].color.equals(color)){
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
        while(posx >= 0 && posy < 8 && myBoard.gameboard[posx][posy] == null){

            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
            posx--;
            posy++;
        }
        if(posx >= 0 && posy < 8 && !myBoard.gameboard[posx][posy].color.equals(color)){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
        }

        //---------------------------diagonala stanga sus----------------------------------
        posx = this.pos.x + 1;
        posy = this.pos.y - 1;
        while(posx < 8 && posy >= 0 && myBoard.gameboard[posx][posy] == null){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
            posx++;
            posy--;
        }
        if(posx < 8 && posy >= 0 && !myBoard.gameboard[posx][posy].color.equals(color)){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
        }

        //----------------------------diagonala stanga jos----------------------------------
        posx = this.pos.x - 1;
        posy = this.pos.y - 1;
        while(posx >= 0 && posy >= 0 && myBoard.gameboard[posx][posy] == null){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
            posx--;
            posy--;
        }
        if(posx >= 0 && posy >= 0 && !myBoard.gameboard[posx][posy].color.equals(color)){
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, posx, posy);
            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                canMoveTo.add(new CellPosition(posx, posy));
            }
        }


        CellPosition currentPosition = Board.getInstance().getPiecePosition(this);

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
                    canMoveTo.add(new CellPosition(i, currentPosition.y));
                }
                break;
            }
            //Daca nu se afla nimic pe pozitia actual, o adaug in vector si trec la urmatoarea pozitie
            else {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, i, currentPosition.y);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    canMoveTo.add(new CellPosition(i, currentPosition.y));
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
                    canMoveTo.add(new CellPosition(i, currentPosition.y));
                }
                break;
            }
            //Daca nu se afla nimic pe pozitia actual, o adaug in vector si trec la urmatoarea pozitie
            else {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, i, currentPosition.y);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    canMoveTo.add(new CellPosition(i, currentPosition.y));
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
                    canMoveTo.add(new CellPosition(currentPosition.x, i));
                }
                break;
            }
            //Daca nu se afla nimic pe pozitia actual, o adaug in vector si trec la urmatoarea pozitie
            else {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y,currentPosition.x, i);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    canMoveTo.add(new CellPosition(currentPosition.x, i));
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
                    canMoveTo.add(new CellPosition(currentPosition.x, i));
                }
                break;
            }
            //Daca nu se afla nimic pe pozitia actual, o adaug in vector si trec la urmatoarea pozitie
            else {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y,currentPosition.x, i);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    canMoveTo.add(new CellPosition(currentPosition.x, i));
                }
            }
        }
    }

    public String toString() {
        if(color == Color.black)
            return "Q";
        return "q";
    }
}