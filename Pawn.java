
import java.awt.Color;
public class Pawn extends Piece{

    public boolean firstMove = true;
    public boolean canBeTakenEnPassant = false;

    public Pawn (Color color, CellPosition pos){
        this.color = color;
        this.pos = pos;
    }

    public Pawn(Color color) {
        this.color = color;
    }

    public boolean takePiece() {

        CellPosition position = this.pos;
        int right, left, nextline;

        CellPosition currentKingPos;
        if (Board.getInstance().currentColor == Color.BLACK) {
            currentKingPos = Board.getInstance().blackKing.pos;
        } else {
            currentKingPos = Board.getInstance().whiteKing.pos;
        }

        if (color.equals(Color.black)) {
            right = position.y - 1;
            left = position.y + 1;
            nextline = position.x + 1;
        } else {
            right = position.y + 1;
            left = position.y - 1;
            nextline = position.x - 1;
        }

        if ((right >= 0) && (right < 8) && (nextline >= 0) && (nextline < 8) && (Board.getInstance().gameboard[nextline][right] != null)) {
            if(!(Board.getInstance().gameboard[nextline][right].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, nextline, right);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    if (nextline == 0 || nextline == 7)
                        promotionMoveFromToEngine(position.x, position.y, nextline, right);
                    else
                        moveFromToEngine(position.x, position.y, nextline, right);
                    firstMove = false;
                    return true;
                }
            }
        }
        if ((left >= 0) && (left < 8) && (nextline >= 0) && (nextline < 8) && (Board.getInstance().gameboard[nextline][left] != null) ) {
            if(!(Board.getInstance().gameboard[nextline][left].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, nextline, left);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    if (nextline == 0 || nextline == 7)
                        promotionMoveFromToEngine(position.x, position.y, nextline, left);
                    else
                        moveFromToEngine(position.x, position.y, nextline, left);
                    firstMove = false;
                    return true;
                }
            }
        }
        if (left >= 0 && left <= 7 && Board.getInstance().gameboard[this.pos.x][left] instanceof Pawn) {
            if (((Pawn)Board.getInstance().gameboard[this.pos.x][left]).canBeTakenEnPassant) {
                if(!(Board.getInstance().gameboard[this.pos.x][left].color.equals(Board.getInstance().currentColor))) {
                    Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                    cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x, left);
                    if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                        Board.getInstance().blackPieces.remove(Board.getInstance().gameboard[this.pos.x][left]);
                        Board.getInstance().whitePieces.remove(Board.getInstance().gameboard[this.pos.x][left]);
                        Board.getInstance().gameboard[this.pos.x][left] = null;
                        moveFromToEngine(position.x, position.y, nextline, left);
                        firstMove = false;
                        return true;
                    }
                }
            }
        }

        if (right >= 0 && right <= 7 && Board.getInstance().gameboard[this.pos.x][right] instanceof Pawn) {
            if (((Pawn)Board.getInstance().gameboard[this.pos.x][right]).canBeTakenEnPassant) {
                if (!(Board.getInstance().gameboard[this.pos.x][right].color.equals(Board.getInstance().currentColor))) {
                    Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                    cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x, right);
                    if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                        Board.getInstance().blackPieces.remove(Board.getInstance().gameboard[this.pos.x][right]);
                        Board.getInstance().whitePieces.remove(Board.getInstance().gameboard[this.pos.x][right]);
                        Board.getInstance().gameboard[this.pos.x][right] = null;
                        moveFromToEngine(position.x, position.y, nextline, right);
                        firstMove = false;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean moveFrontBlack() {
        CellPosition position = Board.getInstance().getPiecePosition(this);
        CellPosition currentKingPos = Board.getInstance().blackKing.pos;

        if (firstMove && position.x + 2 < 7 && (Board.getInstance().gameboard[position.x + 2][position.y] == null)
                && (Board.getInstance().gameboard[position.x + 1][position.y] == null)) {
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x + 2, this.pos.y);

            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                moveFromToEngine(position.x, position.y, position.x + 2, position.y);
                firstMove = false;
                canBeTakenEnPassant = true;
                Main.pawnCandidateForEnPassant = this;
                this.pos.x = position.x + 2;
                this.pos.y = position.y;
                return true;
            }
        }
        if (position.x + 1 < 7 && Board.getInstance().gameboard[position.x + 1][position.y] == null) {
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x + 1, this.pos.y);

            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                moveFromToEngine(position.x, position.y, position.x + 1, position.y);
                this.pos.x = position.x + 1;
                this.pos.y = position.y;

                firstMove = false;
                canBeTakenEnPassant = false;
                return true;
            }
        }
       else if (position.x + 1 == 7 && Board.getInstance().gameboard[position.x + 1][position.y] == null) {
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x + 1, this.pos.y);

            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                promotionMoveFromToEngine(position.x, position.y, position.x + 1, position.y);
                this.pos.x = position.x + 1;
                this.pos.y = position.y;
                return true;
            }
        }
        return false;
    }

    public boolean moveFrontWhite() {
        CellPosition position = Board.getInstance().getPiecePosition(this);
        CellPosition currentKingPos = Board.getInstance().whiteKing.pos;

        if (firstMove && position.x - 2 >= 0 && (Board.getInstance().gameboard[position.x - 2][position.y] == null)
                && (Board.getInstance().gameboard[position.x - 1][position.y] == null)) {
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x - 2, this.pos.y);

            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                moveFromToEngine(position.x, position.y, position.x - 2, position.y);
                firstMove = false;
                canBeTakenEnPassant = true;
                Main.pawnCandidateForEnPassant = this;
                this.pos.x = position.x - 2;
                this.pos.y = position.y;
                return true;
            }
        }
        if (position.x - 1 > 0 && Board.getInstance().gameboard[position.x - 1][position.y] == null) {
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x - 1, this.pos.y);

            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                moveFromToEngine(position.x, position.y, position.x - 1, position.y);
                this.pos.x = position.x - 1;
                this.pos.y = position.y;
                firstMove = false;
                canBeTakenEnPassant = false;
                return true;
            }
        }
        else if (position.x - 1 == 0 && Board.getInstance().gameboard[position.x - 1][position.y] == null) {
            Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
            cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, this.pos.x - 1, this.pos.y);

            if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                promotionMoveFromToEngine(position.x, position.y, position.x - 1, position.y);
                this.pos.x = position.x - 1;
                this.pos.y = position.y;
                return true;
            }
        }
        return false;
    }

    public boolean move() {
        System.out.println("Pawn From: " + this.pos);
        if (!takePiece()) {
            if (color.equals(Color.black)) {
                return moveFrontBlack();
            }
            else
                return moveFrontWhite();
        }
        return true;
    }

    public String toString() {
        if(color == Color.black)
            return "P";
        return "p";
    }
}