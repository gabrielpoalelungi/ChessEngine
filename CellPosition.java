
//Clasa ce contine 2 variable intregi ce reprezinta coordonatele unei piese pe tabla de joc interna
public class CellPosition {
    int x, y;

    public CellPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public CellPosition() {
        x = 0;
        y = 0;
    }
    public String toString() {
        return "" + x + "." + y;
    }
}