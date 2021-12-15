import java.awt.*;
import java.util.*;
import java.util.concurrent.BlockingDeque;

public class Main {

    public static Pawn pawnCandidateForEnPassant = null;
    public static void main(String[] args) {
    	//Trimitem setarile initiale;
        System.out.println("feature myname=\"pierdutipetabla\" sigint=0 san=0");

        //Aceasta variabila va reprezenta fiecare linie citita la stdin;
        Scanner sc= new Scanner(System.in);

        Board.getInstance();
        Piece movingPiece = Board.getInstance().blackPieces.get((int)(Math.random() * Board.getInstance().blackPieces.size()));

        while(true) {
        	//forceFlag este un flag ce ne va ajuta la procesarea comenzii go
            int forceFlag = 0;
            String s = sc.next();

            if(s.equals("force")) {
                forceFlag = 1;
                s = sc.next();
                	//Cat timp este in force si nu am primit la stdin black/white, fiecare mutare este procesata
                	//in tabla interna a jocului. Practic, se face un update live;
                    while (!s.equals("black") && !s.equals("white") && !s.equals("new")) {
                        if (s.matches("[a-h][1-8][a-h][1-8]")) {
                        	//Daca se muta un pion ce nu a apucat sa mute pana acum, firstMove-ul lui devine 0, ca sa nu poata
                        	//fi capabil sa mute 2 patratele;
                            Piece pieceMoved = Board.getInstance().gameboard[8 - Character.getNumericValue(s.charAt(1))]
                                    [Character.getNumericValue(s.charAt(0)) - 10];

                            if (pieceMoved instanceof Pawn) {
                                ((Pawn) pieceMoved).firstMove = false;
                                if(((Pawn) pieceMoved).canBeTakenEnPassant) {
                                    ((Pawn) pieceMoved).canBeTakenEnPassant = false;
                                }
                            } else {
                                if (pieceMoved instanceof Rook) {
                                    ((Rook) pieceMoved).hasBeenMoved = true;
                                } else {
                                    if (pieceMoved instanceof  King) {
                                        if (s.charAt(0) - s.charAt(2) == 2) {
                                            ((King) pieceMoved).doLongCastleFromPlayer();
                                        } else if (s.charAt(0) - s.charAt(2) == -2){
                                            ((King) pieceMoved).doShortCastleFromPlayer();
                                        }
                                        ((King) pieceMoved).hasBeenMoved = true;
                                    }
                                }
                            }

                            if (pieceMoved instanceof Pawn && (Math.abs(s.charAt(3) - s.charAt(1)) == 2)) {
                                ((Pawn)pieceMoved).canBeTakenEnPassant = true;
                                pawnCandidateForEnPassant = (Pawn)pieceMoved;
                            }
                            //Update-ul tablei de joc cu ajutorul metodei din Piece, movePiece;
                            pieceMoved.movePiece(8 - Character.getNumericValue(s.charAt(1)),
                                            Character.getNumericValue(s.charAt(0)) - 10,
                                            8 - Character.getNumericValue(s.charAt(3)),
                                            Character.getNumericValue(s.charAt(2)) - 10);
                            System.out.println(Board.getInstance());
                        }
                        if (s.matches("[a-h][1-8][a-h][1-8][a-z]")) {
                            Pawn pieceMoved = (Pawn) Board.getInstance().gameboard[8 - Character.getNumericValue(s.charAt(1))]
                                [Character.getNumericValue(s.charAt(0)) - 10];

                            pieceMoved.promotionMovePiece(8 - Character.getNumericValue(s.charAt(1)), Character.getNumericValue(s.charAt(0)) - 10,
                                8 - Character.getNumericValue(s.charAt(3)), Character.getNumericValue(s.charAt(2)) - 10, s.charAt(4));
                        }
                        s = sc.next();
                    }
                    // Dupa terminarea force-ului, se primeste o secventa de black-white sau white-black;
                    // Prima culoare reprezinta cu ce joaca engine-ul, iar a doua culoare reprezinta player-ul
                    // Daca prima culoare e white, engine-ul incepe sa mute un pion alb, iar currentColor devine alb;
                    // Analog pentru negru;
                    if(s.equals("white")){
                        Board.getInstance().currentColor = Color.white;
                        movingPiece = Board.getInstance().whitePieces.get((int)(Math.random() * Board.getInstance().whitePieces.size()));
                    }
                    else
                        if (s.equals("black")){
                            Board.getInstance().currentColor = Color.black;
                            movingPiece = Board.getInstance().blackPieces.get((int)(Math.random() * Board.getInstance().blackPieces.size()));
                        } else {
                            if(s.equals("new")) {
                                Board.Reinitialize();
                                movingPiece = Board.getInstance().blackPieces.get((int)(Math.random() * Board.getInstance().blackPieces.size()));
                            }
                        }
            }
            //Pentru un new clasic (dupa terminarea meciului trecut prin sah mat sau abandon);
            //Bot-ul va incepe tot cu negru;
            if(s.equals("new")) {
                Board.Reinitialize();
                movingPiece = Board.getInstance().blackPieces.get((int)(Math.random() * Board.getInstance().blackPieces.size()));
            }

            if(s.equals("go")){
            	//Daca s-a primit force, doar mutam pionul, deoarece schimbarile s-au facut deja in if-ul corespondent lui force;
                if (forceFlag == 1) {
                	System.out.println(Board.getInstance());

                    if (movingPiece.color == Color.BLACK) {
                        if(!Board.getInstance().blackKing.doLongCastle() && !Board.getInstance().blackKing.doShortCastle()) {
                            while (!movingPiece.move()) {
                                movingPiece = Board.getInstance().blackPieces.get((int) (Math.random() * Board.getInstance().blackPieces.size()));
                            }
                        }
                    } else {
                        if(!Board.getInstance().whiteKing.doLongCastle() && !Board.getInstance().whiteKing.doShortCastle()) {
                            while (!movingPiece.move()) {
                                movingPiece = Board.getInstance().whitePieces.get((int) (Math.random() * Board.getInstance().whitePieces.size()));
                            }
                        }
                    }
                    forceFlag = 0;
                }
                //In cazul in care primim go fara force (doar prin schimbarea partii cu care muta bot-ul),
                //schimbam tabara si mutam;
                else {
                    if(Board.getInstance().currentColor.equals(Color.black)) {
                        Board.getInstance().currentColor = Color.white;
                        King currentKing = Board.getInstance().whiteKing;

                        if (currentKing.detectChess(Board.getInstance().gameboard)) {
                            if (!currentKing.escapeFromChess()) {
                                System.out.println("resign");
                            }
                        } else {
                            movingPiece = Board.getInstance().whitePieces.get((int) (Math.random() * Board.getInstance().whitePieces.size()));
                            if (!Board.getInstance().whiteKing.doLongCastle() && !Board.getInstance().whiteKing.doShortCastle()) {
                                while (!movingPiece.move()) {
                                    movingPiece = Board.getInstance().whitePieces.get((int) (Math.random() * Board.getInstance().whitePieces.size()));
                                }
                            }
                        }
                        System.out.println(Board.getInstance());
                        if (pawnCandidateForEnPassant != null) {
                            pawnCandidateForEnPassant.canBeTakenEnPassant = false;
                            pawnCandidateForEnPassant = null;
                        }
                    }
                    else {
                        Board.getInstance().currentColor = Color.black;
                        King currentKing = Board.getInstance().blackKing;

                        if (currentKing.detectChess(Board.getInstance().gameboard)) {
                            if (!currentKing.escapeFromChess()) {
                                System.out.println("resign");
                            }
                        } else {
                            movingPiece = Board.getInstance().blackPieces.get((int) (Math.random() * Board.getInstance().blackPieces.size()));
                            if (!Board.getInstance().blackKing.doLongCastle() && !Board.getInstance().blackKing.doShortCastle()) {
                                while (!movingPiece.move()) {
                                    movingPiece = Board.getInstance().blackPieces.get((int) (Math.random() * Board.getInstance().blackPieces.size()));
                                }
                            }
                        }
                        System.out.println(Board.getInstance());
                        if (pawnCandidateForEnPassant != null) {
                            pawnCandidateForEnPassant.canBeTakenEnPassant = false;
                            pawnCandidateForEnPassant = null;
                        }
                    }
                    System.out.println(movingPiece + "------------------------------" + movingPiece.pos);
                }
            }

            // Primim mutarea facuta de player in mod clasic si updatam tabla interna;
            if (s.matches("[a-h][1-8][a-h][1-8]")) {

                Piece pieceMoved = Board.getInstance().gameboard[8 - Character.getNumericValue(s.charAt(1))]
                        [Character.getNumericValue(s.charAt(0)) - 10];
                if (pieceMoved instanceof Pawn) {
                    ((Pawn) pieceMoved).firstMove = false;
                    if(((Pawn) pieceMoved).canBeTakenEnPassant) {
                        ((Pawn) pieceMoved).canBeTakenEnPassant = false;
                    }
                } else {
                    if (pieceMoved instanceof Rook) {
                        ((Rook) pieceMoved).hasBeenMoved = true;
                    } else {
                        if (pieceMoved instanceof  King) {
                            if (s.charAt(0) - s.charAt(2) == 2) {
                                ((King) pieceMoved).doLongCastleFromPlayer();
                            } else if (s.charAt(0) - s.charAt(2) == -2){
                                ((King) pieceMoved).doShortCastleFromPlayer();
                            }
                            ((King) pieceMoved).hasBeenMoved = true;
                        }
                    }
                }
                if (pieceMoved instanceof Pawn && (Math.abs(s.charAt(3) - s.charAt(1)) == 2)) {
                    ((Pawn)pieceMoved).canBeTakenEnPassant = true;
                    pawnCandidateForEnPassant = (Pawn)pieceMoved;
                }
                pieceMoved.moveFromToPlayer(8 - Character.getNumericValue(s.charAt(1)) ,
                                Character.getNumericValue(s.charAt(0)) - 10,
                                8 - Character.getNumericValue(s.charAt(3)),
                                Character.getNumericValue(s.charAt(2)) - 10);
                System.out.println(Board.getInstance());
                System.out.println(movingPiece + "------------------------------" + movingPiece.pos);

                King currentKing;
                if (Board.getInstance().currentColor == Color.BLACK) {
                    currentKing = Board.getInstance().blackKing;
                } else {
                    currentKing = Board.getInstance().whiteKing;
                }

                if (currentKing.detectChess(Board.getInstance().gameboard)) {
                    if (!currentKing.escapeFromChess()) {
                        System.out.println("resign");
                    }
                } else {
                    if (Board.getInstance().currentColor.equals(Color.white)) {
                        movingPiece = Board.getInstance().whitePieces.get((int) (Math.random() * Board.getInstance().whitePieces.size()));
                        if (!Board.getInstance().whiteKing.doLongCastle() && !Board.getInstance().whiteKing.doShortCastle()) {
                            while (!movingPiece.move()) {
                                movingPiece = Board.getInstance().whitePieces.get((int) (Math.random() * Board.getInstance().whitePieces.size()));
                            }
                        }
                    } else {
                        movingPiece = Board.getInstance().blackPieces.get((int) (Math.random() * Board.getInstance().blackPieces.size()));
                        if (!Board.getInstance().blackKing.doLongCastle() && !Board.getInstance().blackKing.doShortCastle()) {
                            while (!movingPiece.move()) {
                                movingPiece = Board.getInstance().blackPieces.get((int) (Math.random() * Board.getInstance().blackPieces.size()));
                            }
                        }
                    }

                    System.out.println(Board.getInstance());
                    if (pawnCandidateForEnPassant != null) {
                        pawnCandidateForEnPassant.canBeTakenEnPassant = false;
                        pawnCandidateForEnPassant = null;
                    }
                }
            }
            if (s.matches("[a-h][1-8][a-h][1-8][a-z]")) {
                Pawn pieceMoved = (Pawn) Board.getInstance().gameboard[8 - Character.getNumericValue(s.charAt(1))]
                    [Character.getNumericValue(s.charAt(0)) - 10];

                pieceMoved.promotionMovePiece(8 - Character.getNumericValue(s.charAt(1)), Character.getNumericValue(s.charAt(0)) - 10,
                    8 - Character.getNumericValue(s.charAt(3)), Character.getNumericValue(s.charAt(2)) - 10, s.charAt(4));
                King currentKing;
                if (Board.getInstance().currentColor == Color.BLACK) {
                    currentKing = Board.getInstance().blackKing;
                } else {
                    currentKing = Board.getInstance().whiteKing;
                }

                if (currentKing.detectChess(Board.getInstance().gameboard)) {
                    if (!currentKing.escapeFromChess()) {
                        System.out.println("resign");
                    }
                } else {
                    if (Board.getInstance().currentColor.equals(Color.white)) {
                        movingPiece = Board.getInstance().whitePieces.get((int) (Math.random() * Board.getInstance().whitePieces.size()));
                        if (!Board.getInstance().whiteKing.doLongCastle() && !Board.getInstance().whiteKing.doShortCastle()) {
                            while (!movingPiece.move()) {
                                movingPiece = Board.getInstance().whitePieces.get((int) (Math.random() * Board.getInstance().whitePieces.size()));
                            }
                        }
                    } else {
                        movingPiece = Board.getInstance().blackPieces.get((int) (Math.random() * Board.getInstance().blackPieces.size()));
                        if (!Board.getInstance().blackKing.doLongCastle() && !Board.getInstance().blackKing.doShortCastle()) {
                            while (!movingPiece.move()) {
                                movingPiece = Board.getInstance().blackPieces.get((int) (Math.random() * Board.getInstance().blackPieces.size()));
                            }
                        }
                    }

                    System.out.println(Board.getInstance());
                    if (pawnCandidateForEnPassant != null) {
                        pawnCandidateForEnPassant.canBeTakenEnPassant = false;
                        pawnCandidateForEnPassant = null;
                    }
                }
            }
        }
    }
}
