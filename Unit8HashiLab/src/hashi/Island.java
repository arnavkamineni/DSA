package hashi;

public class Island {
    private int id;
    private int row;
    private int col;
    private int requiredBridges; // total bridges the island must have
    private int currentBridges;  // total bridges currently connected

    public Island(int id, int row, int col, int requiredBridges) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.requiredBridges = requiredBridges;
        this.currentBridges = 0;
    }

    public int getId() {
        return id;
    }

    public int getRequiredBridges() {
        return requiredBridges;
    }

    // increment current bridge count
    public void addBridges(int count) {
        this.currentBridges += count;
    }

    public int getCurrentBridges() {
        return currentBridges;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    @Override
    public String toString() {
        return "Island " + id + " (row=" + row + ", col=" + col 
                + ", required=" + requiredBridges + ", current=" + currentBridges + ")";
    }
}
