/**
 * This program is used to find Hamiltonian Path and Hamiltonian Cycle
 *
 * @author Vladimir Kartashev
 * @version 1.0
 * @since 04-06-2023
 */
public class Main {
    public static void main(String[] args) {
        /**
         * The INT graphMatrix massive graph is used to contain the graph
         *
         * @param graphMatrix
         */
        int[][] graphMatrix = new int[][]{
//               Variant: 20
//               1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12
                {0, 0, 3, 3, 0, 5, 1, 0, 1, 0, 0, 0}, // 1
                {0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0}, // 2
                {3, 0, 0, 0, 0, 0, 5, 2, 4, 0, 0, 3}, // 3
                {3, 0, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0}, // 4
                {0, 0, 0, 1, 0, 4, 0, 0, 0, 3, 0, 0}, // 5
                {5, 2, 0, 0, 4, 0, 2, 3, 2, 0, 0, 0}, // 6
                {1, 1, 5, 3, 0, 2, 0, 0, 2, 0, 1, 0}, // 7
                {0, 0, 2, 0, 0, 3, 0, 0, 4, 5, 0, 0}, // 8
                {1, 0, 4, 0, 0, 2, 2, 4, 0, 5, 4, 0}, // 9
                {0, 0, 0, 0, 3, 0, 0, 5, 5, 0, 0, 1}, // 10
                {0, 0, 0, 0, 0, 0, 1, 0, 4, 0, 0, 0}, // 11
                {0, 0, 3, 0, 0, 0, 0, 0, 0, 1, 0, 0}  // 12
        };

        /**
         * The STRING graphVerticesNames massive vertices is used to contain graph vertex names
         *
         * @param graphVerticesNames
         */
        String[] graphVerticesNames = {"e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "e10", "e11", "e12"};

        
        HamiltonianPath hamiltonianPath = new HamiltonianPath();
        hamiltonianPath.hamiltonianPath(graphMatrix, graphVerticesNames);

        System.out.println();

        HamiltonianCycle hamiltonianCycle = new HamiltonianCycle();
        hamiltonianCycle.hamiltonianCycle(graphMatrix);
    }
}