package model.tasks;
import model.Matrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixTask extends Thread{

    private Matrix matrix1;
    private Matrix matrix2;
    public Matrix finalMatrix;

    public MatrixTask(Matrix givenM1, Matrix givenM2, Matrix finalMatrix){
        this.matrix1 = givenM1;
        this.matrix2 = givenM2;
        // matrix1.columns should be equal to matrix2.rows

        this.finalMatrix = finalMatrix;
    }

    public void computeElement(int row, int column){
        Integer finalElement = 0;

        List<Integer> rowFactor = matrix1.elements.get(row);

        // we add into columnFactor the column we'll have to multiply to rowFactor in order to compute the desired element
        List<Integer> columnFactor = new ArrayList<Integer>();

        for (int i = 0; i < matrix2.rows; ++i)
            columnFactor.add(matrix2.elements.get(i).get(column));

        for (int i = 0; i < rowFactor.size(); ++i)
            finalElement += rowFactor.get(i) * columnFactor.get(i);

        finalMatrix.setElement(row, column, finalElement);
    }

    public void computeMatrix(){
        for (int i = 0; i < finalMatrix.rows; ++i)
            for (int j = 0; j < finalMatrix.columns; ++j)
                computeElement(i, j);
    }

}
