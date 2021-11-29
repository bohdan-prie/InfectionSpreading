package infection.data;

public abstract class Cell {
    private int counter;

    public Cell(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return this.counter;
    }

    public void decreaseCounter() {
        this.counter--;
    }
}
