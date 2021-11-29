package infection.data;

public class HealthyCell extends Cell {

    private static final int LIFE_OF_HEALTHY_CELL = Integer.MAX_VALUE;

    public HealthyCell() {
        super(LIFE_OF_HEALTHY_CELL);
    }
}
