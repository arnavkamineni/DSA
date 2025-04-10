package hashi;

public class Bridge {
    private Island island1;
    private Island island2;
    private int weight; // number of bridges (1 or 2)

    public Bridge(Island island1, Island island2, int weight) {
        this.island1 = island1;
        this.island2 = island2;
        this.weight = weight;
    }

    public Island getIsland1() {
        return island1;
    }

    public Island getIsland2() {
        return island2;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Bridge between Island " + island1.getId() + " and Island " 
                + island2.getId() + " [" + weight + "]";
    }
}
