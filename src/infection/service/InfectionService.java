package infection.service;

import infection.data.Cell;
import infection.data.HealthyCell;
import infection.data.InfectedCell;
import infection.data.ImmunityCell;

public class InfectionService {

    private Cell[][] matrix;

    public void init(int size) {
        if (size <= 0 || size >= 1002 || ((size % 2) == 0)) {
            throw new IllegalArgumentException();
        }

        matrix = new Cell[size][size];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new HealthyCell();
            }
        }
        int middleSize = (size / 2);
        matrix[middleSize][middleSize] = new InfectedCell();
    }

    public Cell[][] getMatrix() {
        return this.matrix;
    }

    public void clearMatrix() {
        matrix = new Cell[0][0];
    }

    public Cell[][] getMatrixInSomeSteps(int steps) {
        if (steps < 0 || steps > 100) {
            throw new IllegalArgumentException();
        }
        if (steps == 0) {
            return this.matrix;
        }
        Cell[][] copy = this.copyMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Cell cell = matrix[i][j];

                cell.decreaseCounter();

                if (cell.getCounter() <= 0) {
                    cell = this.migrateToAnotherCell(cell);
                }
                matrix[i][j] = cell;

                if (!copy[i][j].getClass().equals(cell.getClass())) {
                    continue;
                }

                if (!(cell instanceof InfectedCell)) {
                    continue;
                }

                if (i > 0) {
                    this.changeIfInfected(i - 1, j);
                    if (j > 0) {
                        this.changeIfInfected(i - 1, j - 1);
                        this.changeIfInfected(i, j - 1);
                    }

                    if (i < (matrix.length - 1)) {
                        this.changeIfInfected(i + 1, j);
                        if (j > 0) {
                            this.changeIfInfected(i + 1, j - 1);
                        }

                        if (j < (matrix.length - 1)) {
                            this.changeIfInfected(i, j + 1);
                            this.changeIfInfected(i + 1, j + 1);
                            this.changeIfInfected(i - 1, j + 1);
                        }
                    } else {
                        if (j < (matrix.length - 1)) {
                            this.changeIfInfected(i, j + 1);
                            this.changeIfInfected(i - 1, j + 1);
                        }
                    }
                } else {
                    this.changeIfInfected(i + 1, j);

                    if (j > 0) {
                        this.changeIfInfected(i + 1, j - 1);
                        this.changeIfInfected(i, j - 1);
                    }

                    if (j < (matrix.length - 1)) {
                        this.changeIfInfected(i + 1, j + 1);
                        this.changeIfInfected(i, j + 1);
                    }
                }
            }
        }
        this.getMatrixInSomeSteps(--steps);
        return matrix;
    }

    private Cell[][] copyMatrix() {
        Cell[][] copy = new Cell[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix.length);
        }
        return copy;
    }

    private void changeIfInfected(int i, int j) {
        Cell cell = matrix[i][j];

        if (cell instanceof HealthyCell) {
            double d = Math.random();

            if (d < 0.5) {
                matrix[i][j] = new InfectedCell();
            }
        }
    }

    private Cell migrateToAnotherCell(Cell cell) {
        if (cell instanceof InfectedCell) {
            return new ImmunityCell();
        } else if (cell instanceof ImmunityCell) {
            return new HealthyCell();
        } else {
            return cell;
        }
    }
}
