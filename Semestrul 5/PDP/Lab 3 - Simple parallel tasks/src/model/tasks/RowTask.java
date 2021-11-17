package model.tasks;

import model.Matrix;

public class RowTask extends MatrixTask{
    private int rowStart, columnStart, elementsCount;

    public RowTask(int rowStart, int columnStart, int elementsCount, Matrix matrix1, Matrix matrix2, Matrix finalMatrix){
        super(matrix1, matrix2, finalMatrix);
        this.rowStart = rowStart;
        this.columnStart = columnStart;
        this.elementsCount = elementsCount;
        this.computeMatrix();
    }

    @Override
    public void computeMatrix(){
        int row = rowStart, column = columnStart;

        while (elementsCount > 0 && row < finalMatrix.rows && column < finalMatrix.columns){
            computeElement(row, column);

            elementsCount--;
            column++;
            if (column == finalMatrix.columns){
                row++;
                column = 0;
            }
        }
    }
}
