import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Knight extends Piece{

    Vector<CellPosition> mutariPosibile;

    public Knight (Color color, CellPosition pos){
        this.color = color;
        this.pos = pos;
    }

    public Knight(Color color) {
        this.color = color;
    }

    public void getMovesKnight() {
        CellPosition position = this.pos;
        mutariPosibile = new Vector<>();

        CellPosition currentKingPos;
        if (Board.getInstance().currentColor == Color.BLACK) {
            currentKingPos = Board.getInstance().blackKing.pos;
        } else {
            currentKingPos = Board.getInstance().whiteKing.pos;
        }

        if ((position.y - 1 >= 0) && (position.x + 2 < 8)) {
            if(Board.getInstance().gameboard[position.x + 2][position.y - 1] == null ||
                    !(Board.getInstance().gameboard[position.x + 2][position.y - 1].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, position.x + 2, position.y - 1);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    mutariPosibile.add(new CellPosition(position.x + 2, position.y - 1));
                }
            }
        }

        if ((position.y - 2 >= 0) && (position.x + 1 < 8)) {
            if(Board.getInstance().gameboard[position.x + 1][position.y - 2] == null ||
                    !(Board.getInstance().gameboard[position.x + 1][position.y - 2].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, position.x + 1, position.y - 2);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    mutariPosibile.add(new CellPosition(position.x + 1, position.y - 2));
                }
            }
        }

        if ((position.y - 2 >= 0) && (position.x - 1 >= 0)) {
            if(Board.getInstance().gameboard[position.x - 1][position.y - 2] == null ||
                    !(Board.getInstance().gameboard[position.x - 1][position.y - 2].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, position.x - 1, position.y - 2);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    mutariPosibile.add(new CellPosition(position.x - 1, position.y - 2));
                }
            }
        }

        if ((position.y - 1 >= 0) && (position.x - 2 >= 0)) {
            if(Board.getInstance().gameboard[position.x - 2][position.y - 1] == null ||
                    !(Board.getInstance().gameboard[position.x - 2][position.y - 1].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, position.x - 2, position.y - 1);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    mutariPosibile.add(new CellPosition(position.x - 2, position.y - 1));
                }
            }
        }

        if ((position.y + 1 < 8) && (position.x + 2 < 8)) {
            if(Board.getInstance().gameboard[position.x + 2][position.y + 1] == null ||
                    !(Board.getInstance().gameboard[position.x + 2][position.y + 1].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, position.x + 2, position.y + 1);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    mutariPosibile.add(new CellPosition(position.x + 2, position.y + 1));
                }
            }
        }

        if ((position.y + 2 < 8) && (position.x + 1 < 8)) {
            if(Board.getInstance().gameboard[position.x + 1][position.y + 2] == null ||
                    !(Board.getInstance().gameboard[position.x + 1][position.y + 2].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, position.x + 1, position.y + 2);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    mutariPosibile.add(new CellPosition(position.x + 1, position.y + 2));
                }
            }
        }

        if ((position.y + 2 < 8) && (position.x - 1 >= 0)) {
            if(Board.getInstance().gameboard[position.x - 1][position.y + 2] == null ||
                    !(Board.getInstance().gameboard[position.x - 1][position.y + 2].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, position.x - 1, position.y + 2);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    mutariPosibile.add(new CellPosition(position.x - 1, position.y + 2));
                }
            }
        }

        if ((position.y + 1 < 8) && (position.x - 2 >= 0)) {
            if(Board.getInstance().gameboard[position.x - 2][position.y + 1] == null ||
                    !(Board.getInstance().gameboard[position.x - 2][position.y + 1].color.equals(Board.getInstance().currentColor))) {
                Piece[][] cloned_gameboard = Board.getInstance().cloneBoard();
                cloned_gameboard[this.pos.x][this.pos.y].testMovePiece(cloned_gameboard, this.pos.x, this.pos.y, position.x - 2, position.y + 1);
                if (!((King)cloned_gameboard[currentKingPos.x][currentKingPos.y]).detectChess(cloned_gameboard)) {
                    mutariPosibile.add(new CellPosition(position.x - 2, position.y + 1));
                }
            }
        }
    }

    public boolean move()
    {
        CellPosition position = this.pos;
        Random random = new Random();
        getMovesKnight();
        System.out.println("Moving Knight From: " + this.pos);
        if (mutariPosibile.size() > 0) {
            int rand = random.nextInt(mutariPosibile.size());
            CellPosition nextMove = mutariPosibile.get(rand);
            moveFromToEngine(position.x, position.y, nextMove.x, nextMove.y);
            return true;

        }
        return false;
    }

    public String toString(){
        if(color == Color.black)
            return "N";
        return "n";
    }
}