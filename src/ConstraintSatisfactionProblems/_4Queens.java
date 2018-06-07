package ConstraintSatisfactionProblems;

import org.jacop.constraints.*;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.*;

import java.util.ArrayList;
import java.util.List;

public class _4Queens {
    private static final int size = 4;

    public static void main(String[] args) {

        Store store = new Store();

        IntVar[][] v = new IntVar[size][size];
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v.length; j++) {
                v[i][j] = new IntVar(store, "v" + i + j, 0, 1);
            }
        }

        List<IntVar> constraints = new ArrayList<>();

        //Constraints for row
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v.length; j++) {
                constraints.add(v[i][j]);
            }
            store.impose(new Sum(constraints, new IntVar(store, "sum1", 1, 1)));
            constraints.clear();
        }

        //Constraints for column
        for (int j = 0; j < v.length; j++) {
            for (int i = 0; i < v.length; i++) {
                constraints.add(v[i][j]);
            }
            store.impose(new Sum(constraints, new IntVar(store, "sum2", 1, 1)));
            constraints.clear();
        }

        //Constraints for diagonals \
        for (int j = 0; j < v.length; j++) {
            int row = 0;
            for (int l = j; l < v.length; l++) {
                constraints.add(v[row][l]);
                row++;
            }
            if (constraints.size() > 1) {
                store.impose(new Max(constraints, new IntVar(store, "sum3", 1, 1)));
            }
            constraints.clear();
        }
        for (int i = 1; i < v.length; i++) {
            int column = 0;
            for (int k = i; k < v.length; k++) {
                constraints.add(v[k][column]);
                column++;
            }
            if (constraints.size() > 1) {
                store.impose(new Max(constraints, new IntVar(store, "sum4", 1, 1)));
            }
            constraints.clear();
        }
            //TO-DO: Constraints for diagonals /
        for (int j = v.length-1; j >=0 ; j--) {
            int row = 0;
            for (int l = j; l >=0; l--) {
                constraints.add(v[row][l]);
                row++;
            }
            if (constraints.size() > 1) {
                store.impose(new Max(constraints, new IntVar(store, "sum5", 1, 1)));
            }
            constraints.clear();
        }
        for (int i = 1; i < v.length-1; i++) {
            int column = v.length-1;
            for (int k = i; k < v.length; k++) {
                constraints.add(v[k][column]);
                column--;
            }
            if (constraints.size() > 1) {
                store.impose(new Max(constraints, new IntVar(store, "sum6", 1, 1)));
            }
            constraints.clear();
        }
        System.out.println(store.toString());

        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new SimpleMatrixSelect<IntVar>(v, new SmallestMax<IntVar>(), new IndomainMin<IntVar>());
        boolean result = search.labeling(store, select);
        System.out.println(result);

    }



    public static int rowSum(int[][] v, int row) {
        int sum = 0;
        for (int j = 0; j < v.length; j++) {
            sum += v[row][j];
        }
        return sum;
    }
}


