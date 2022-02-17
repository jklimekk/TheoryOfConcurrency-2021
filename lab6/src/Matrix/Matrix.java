package Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Matrix {
    private final int size;
    private final double[][] matrix;

    public Matrix(String inputFile) throws FileNotFoundException {
        File input = new File(inputFile);
        Scanner scanner = new Scanner(input);

        int size = Integer.parseInt(scanner.next());
        this.size = size;

        matrix = new double[size][size + 1];

        // macierz
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double element = Double.parseDouble(scanner.next());
                matrix[i][j] = element;
            }
        }

        // wektor wyrazow wolnych
        for(int j = 0; j < size; j++){
            double element = Double.parseDouble(scanner.next());
            matrix[j][size] = element;
        }
    }

    public int getRows() {
        return size;
    }

    public int getColumns() {
        return size + 1;
    }

    public double getElement(int i, int j) {
        if(i < this.size && i >= 0 && j < this.size + 1 && j >= 0) {
            return this.matrix[i][j];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void setElement(int i, int j, double element) {
        if(i < this.size && i >= 0 && j < this.size + 1 && j >= 0) {
            this.matrix[i][j] = element;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void solve() {
        // backward substitution

        for(int i = this.size - 1; i >= 0; i--) {
            double div = this.matrix[i][i];

            for(int j  = i; j < this.size + 1; j++) {
                this.matrix[i][j] = this.matrix[i][j] / div;
            }

            this.matrix[i][i] = 1.0;

            for(int j = i + 1; j < this.size; j++){
                double mul = this.matrix[i][j];
                this.matrix[i][j] = 0.0;
                double newElement = this.matrix[i][this.size] - mul * this.matrix[j][this.size];

                this.matrix[i][this.size] = newElement;
            }
        }
    }

    private String buildString() {
        StringBuilder result = new StringBuilder();

        // macierz
        for(int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                result.append(matrix[i][j]);

                if (j == this.size - 1) {
                    result.append("\n");
                } else {
                    result.append(" ");
                }
            }
        }

        // wektor wyrazow wolnych
        for(int i = 0; i < this.size; i++) {
            result.append(matrix[i][this.size]);

            if (i != this.size - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    public void save(String outputFile) throws IOException {
        FileWriter myWriter = new FileWriter(outputFile);

        myWriter.write(size + "\n");
        myWriter.write(buildString());

        myWriter.close();
    }

    @Override
    public String toString() {
        return "\n" + buildString() + "\n";
    }
}
