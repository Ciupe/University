package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Matrix{

    public ArrayList<ArrayList<Integer>> elements;
    public int rows;
    public int columns;

    public Matrix(){
        this.rows = new Random().nextInt(10);
        this.columns = new Random().nextInt(10);
        this.elements = new ArrayList<>(rows);
        this.initializeMatrix();
    }

    public Matrix(int givenRows, int givenColumns){
        this.rows = givenRows;
        this.columns = givenColumns;
        this.elements = new ArrayList<>(rows);
        this.initializeMatrix();
    }

    private void initializeMatrix(){
        for (int i = 0; i < rows; ++i)
            this.elements.add(new ArrayList<>());
    }

    public void populate(){
        var rand = new Random();

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j)
                elements.get(i).add(rand.nextInt(10));
        }
    }

    public void neutralizeMatrix(){
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j)
                elements.get(i).add(0);
        }
    }

    public void setElement(int row, int column, Integer newValue){
        elements.get(row).set(column, newValue);
    }

    @Override
    public String toString(){
        var sb = new StringBuilder();

        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < columns; ++j)
                sb.append(elements.get(i).get(j) + " ");
            sb.append('\n');
        }

        return sb.toString();
    }

}