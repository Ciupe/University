package model.tasks;

import model.Matrix;

public class KthElementTask extends MatrixTask{

    private int rowStart, columnStart, elementsCount, k;

    public KthElementTask(int rowStart, int columnStart, int elementsCount, Matrix matrix1, Matrix matrix2, Matrix finalMatrix, int k){
        super(matrix1, matrix2, finalMatrix);
        this.rowStart = rowStart;
        this.columnStart = columnStart;
        this.elementsCount = elementsCount;
        this.k = k;
        this.computeMatrix();
    }

    @Override
    public void computeMatrix(){
        int row = rowStart, column = columnStart;

        while (elementsCount > 0 && row < finalMatrix.rows){
            computeElement(row, column);

            elementsCount--;

            row += (row + k) / finalMatrix.columns;
            column = (column + k) % finalMatrix.columns;
        }
    }
}
