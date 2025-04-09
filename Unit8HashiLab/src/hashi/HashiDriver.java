package hashi;

public class HashiDriver {
    public static void main(String[] args) {
        Hashi hashi = new Hashi();

        String filename = "Hashi1.txt";


        hashi.loadSolution(filename);
        hashi.displayGraph();

        hashi.checkDegrees();

        hashi.checkConnectivity();
    }
}
