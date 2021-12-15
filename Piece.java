import java.awt.Color;
import java.util.Random;
public abstract class Piece {
    Color color;
    CellPosition pos;

    public abstract boolean move();

    public void testMovePiece(Piece[][] gameboard, int fromLine, int fromColumn, int toLine, int toColumn) {
        Piece pieceToBeMoved = gameboard[fromLine][fromColumn];
        gameboard[toLine][toColumn] = pieceToBeMoved;
        pieceToBeMoved.pos.x = toLine;
        pieceToBeMoved.pos.y = toColumn;
        gameboard[fromLine][fromColumn] = null;
    }

    public void movePiece(int fromLine, int fromColumn, int toLine, int toColumn){
        //In caz de capturare a unui pion, acesta va fi scos din vectorul de pioni din tabla de joc;
        Board.getInstance().blackPieces.remove(Board.getInstance().getPiece(toLine, toColumn));
        Board.getInstance().whitePieces.remove(Board.getInstance().getPiece(toLine, toColumn));

        if (Board.getInstance().gameboard[toLine][toColumn] instanceof Rook) {
            ((Rook) Board.getInstance().gameboard[toLine][toColumn]).hasBeenTaken = true;
        }
        //Se face o interschimbare de pozitii in matricea interna;
        //Piesa isi schimba coordonatele in cele noi, iar fosta pozitie devine nula;
        Piece pieceToBeMoved = Board.getInstance().getPiece(fromLine, fromColumn);
        Board.getInstance().gameboard[toLine][toColumn] = pieceToBeMoved;
        pieceToBeMoved.pos.x = toLine;
        pieceToBeMoved.pos.y = toColumn;
        Board.getInstance().gameboard[fromLine][fromColumn] = null;
    }

    public void promotionMovePiece(int fromLine, int fromColumn, int toLine, int toColumn, int promotion){
        //In caz de capturare a unui pion, acesta va fi scos din vectorul de pioni din tabla de joc;
        Board.getInstance().blackPieces.remove(Board.getInstance().getPiece(toLine, toColumn));
        Board.getInstance().whitePieces.remove(Board.getInstance().getPiece(toLine, toColumn));

        //Se face o interschimbare de pozitii in matricea interna;
        //Piesa isi schimba coordonatele in cele noi, iar fosta pozitie devine nula;
        //Piesa este inlocuita de piesa in care se va face promovarea
        Piece pieceToBeMoved = Board.getInstance().getPiece(fromLine, fromColumn);
        Board.getInstance().blackPieces.remove(pieceToBeMoved);
        Board.getInstance().whitePieces.remove(pieceToBeMoved);
        switch(promotion) {
            case 98:
                Board.getInstance().gameboard[toLine][toColumn] = new Bishop(pieceToBeMoved.color, new CellPosition(toLine, toColumn));
                break;
            case 110:
                Board.getInstance().gameboard[toLine][toColumn] = new Knight(pieceToBeMoved.color, new CellPosition(toLine, toColumn));
                break;
            case 113:
                Board.getInstance().gameboard[toLine][toColumn] = new Queen(pieceToBeMoved.color, new CellPosition(toLine, toColumn));
                break;
            case 114:
                Board.getInstance().gameboard[toLine][toColumn] = new Rook(pieceToBeMoved.color, new CellPosition(toLine, toColumn));
                break;
        }
        if(pieceToBeMoved.color == Color.BLACK) {
            Board.getInstance().blackPieces.add(Board.getInstance().gameboard[toLine][toColumn]);
        }
        else {
            Board.getInstance().whitePieces.add(Board.getInstance().gameboard[toLine][toColumn]);
        }

        Board.getInstance().gameboard[fromLine][fromColumn] = null;
    }

    public void moveFromToPlayer(int fromLine, int fromColumn, int toLine, int toColumn) {
        movePiece(fromLine, fromColumn, toLine, toColumn);
    }

    public void moveFromToEngine(int fromLine, int fromColumn, int toLine, int toColumn) {
        movePiece(fromLine, fromColumn, toLine, toColumn);
        System.out.println("move " + (char)(fromColumn + 97) + (8 - fromLine) +
                (char)(toColumn + 97) + (8 - toLine) + "\n");
    }

    public void promotionMoveFromToEngine(int fromLine, int fromColumn, int toLine, int toColumn) {
        Random random = new Random();
        int rand = random.nextInt(3);
        int[] possiblePromotions = {98, 110, 113, 114};
        promotionMovePiece(fromLine, fromColumn, toLine, toColumn, possiblePromotions[rand]);
        System.out.println("move " + (char)(fromColumn + 97) + (8 - fromLine) +
                (char)(toColumn + 97) + (8 - toLine) + (char)(possiblePromotions[rand]) + "\n");
    }
}