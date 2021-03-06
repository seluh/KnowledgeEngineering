package ConstraintSatisfactionProblems;

import org.jacop.constraints.Alldiff;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.*;
import java.util.ArrayList;
import java.util.List;

public class Sudoku {

    private static final int size = 4; //choose the size of the sudoku

    public static void main(String[] args) {

        Store store = new Store();
        //building vars for every cell of the sudoku
        IntVar[][] v = new IntVar[size][size];
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v.length; j++) {
                v[i][j] = new IntVar(store, "v" + i + j, 1, size);
            }
        }
        //specific sudoku----------------------------------------

        v[0][1] = new IntVar(store, "0|1=1",1,1);
        v[1][1] = new IntVar(store, "1|1=2",2,2);
        v[1][3] = new IntVar(store, "1|3=4",4,4);
        v[3][2] = new IntVar(store, "3|2=2",2,2);

        //-------------------------------------------------------

        int blocksize = (int) (Math.sqrt(size));

        List<IntVar> constraints = new ArrayList<>();
        //building constraints for rows i=rows j=columns; expected: v00 v01 v02 v03
        for (int i = 0; i < v.length; i++) {

            for (int j = 0; j < v.length; j++) {
                constraints.add(v[i][j]);
            }
            store.impose(new Alldiff(constraints));
            constraints.clear();

        }
        store.impose(new Alldiff(constraints));
        constraints.clear();
        //building constraints for columns expected: v00 v10 v20 v30
        for (int j = 0; j < v.length; j++) {
            for (int i = 0; i < v.length; i++) {
                constraints.add(v[i][j]);
            }
            store.impose(new Alldiff(constraints));
            constraints.clear();

        }
        store.impose(new Alldiff(constraints));
        constraints.clear();
        //constraints for block
        //selecting the block
        for (int i = 0; i < v.length; i = i + blocksize) {
            for (int j = 0; j < v.length; j = j + blocksize) {
                //building constraints for values inside a block; expected: v00,v01,v10,v11
                for (int k = i; k <= i + blocksize - 1; k++) {
                    for (int l = j; l <= j + blocksize - 1; l++) { //2x2  i+1
                        System.out.print(k);
                        System.out.print(l);
                        System.out.printf(" ");
                        constraints.add(v[k][l]);

                    }
                }
                store.impose(new Alldiff(constraints));
                constraints.clear();
            }
        }
        store.impose(new Alldiff(constraints));
        System.out.println(store.toString());
        constraints.clear();

        //running the libary
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new SimpleMatrixSelect<IntVar>(v, new SmallestMax<IntVar>(), new IndomainMin<IntVar>());
        boolean result = search.labeling(store, select);
        System.out.println(result);
    }
}


