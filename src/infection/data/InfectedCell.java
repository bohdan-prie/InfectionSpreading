package infection.data;

public class InfectedCell extends Cell {

    private static final int LIFE_OF_INFECTED_CELL = 6;

    public InfectedCell() {
        super(LIFE_OF_INFECTED_CELL);
    }
}
