package ConstraintSatisfactionProblems;

import org.jacop.constraints.*;
import org.jacop.core.IntVar;
import org.jacop.core.Store;

import java.util.ArrayList;
import java.util.List;

public class _4Queens {
    private static final int size = 4;

    public static void main(String[] args) {

        Store store = new Store();
        IntVar sum = new IntVar(store, "sum", 1, 1);

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
            store.impose(new Sum(constraints, sum));
            constraints.clear();
        }

        //Constraints for column
        for (int j = 0; j < v.length; j++) {
            for (int i = 0; i < v.length; i++) {
                constraints.add(v[i][j]);
            }
            store.impose(new Sum(constraints, sum));
            constraints.clear();
        }


        //Constraints for diagonal

        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v.length; j++) {
                constraints.add(v[i][j]);
                int k = i;
                for (int l = j; l < v.length; l++, k++) {
                    try {
                        constraints.add(v[k + 1][l + 1]);
                    } catch (Exception E) {
                        break;
                    }

                }
                if (constraints.size() > 1) {
                    store.impose(new Sum(constraints, sum));
                }
                constraints.clear();
            }
        }
        System.out.println(store.toString());
    }



    public static int rowSum(int[][] v, int row) {
        int sum = 0;
        for (int j = 0; j < v.length; j++) {
            sum += v[row][j];
        }
        return sum;
    }
}


