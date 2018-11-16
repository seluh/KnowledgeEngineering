package ConstraintSatisfactionProblems;

import org.jacop.constraints.Alldiff;
import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.SumWeight;
import org.jacop.constraints.XplusYeqZ;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.*;

public class CryptoArithmetic {

    public static void main(String[] args) {

        Store store = new Store();
        IntVar W = new IntVar(store, "W",0,9);
        IntVar E = new IntVar(store, "E",0,9);
        IntVar I = new IntVar(store, "I",0,9);
        IntVar B = new IntVar(store, "B",0,9);
        IntVar N = new IntVar(store, "N",0,9);
        IntVar L = new IntVar(store, "L",0,9);

        IntVar [] puzzle = new IntVar[6];
        puzzle[0] = W;
        puzzle[1] = E;
        puzzle[2] = I;
        puzzle[3] = B;
        puzzle[4] = N;
        puzzle [5]= L;

        store.impose(new Alldifferent(puzzle));

        IntVar [] weib = new IntVar[4];
        IntVar [] wein = new IntVar[4];
        IntVar [] result = new IntVar[5];

        weib [0] = W;
        weib [1] = E;
        weib [2] = I;
        weib [3] = B;

        wein [0] = W;
        wein [1] = E;
        wein [2] = I;
        wein [3] = N;

        result [0] = L;
        result [1]  = I;
        result [2] = E;
        result [3] = B;
        result [4] = E;

        int [] weight = new int[4];
        weight[0]= 1000;
        weight [1] = 100;
        weight [2] = 10;
        weight [3] = 1;

        int [] resultWeight = new int [5];
        resultWeight[0]= 10000;
        resultWeight[1]= 1000;
        resultWeight[2]= 100;
        resultWeight[3]= 10;
        resultWeight[4]= 1;

        IntVar sumWeib = new IntVar(store, "sumWeib", 0,9999);
        IntVar sumWein = new IntVar(store, "sumWein", 0, 99999);
        IntVar sumResult = new IntVar(store, "sumResult", 0, 999999);


        store.impose(new SumWeight(wein,weight,sumWein));
        store.impose(new SumWeight(weib, weight, sumWeib));
        store.impose(new SumWeight(result,resultWeight,sumResult));

        store.impose(new XplusYeqZ(sumWein, sumWeib, sumResult));

        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new InputOrderSelect<IntVar>(store,puzzle, new IndomainMax<IntVar>());
        boolean solution = search.labeling(store,select);
    }
}
