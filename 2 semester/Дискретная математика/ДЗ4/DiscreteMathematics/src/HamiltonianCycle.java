/**
 * The HamiltonianCycle class is used to find Hamiltonian cycle
 *
 * @author Vladimir Kartashev
 * @version 1.0
 * @since 04-06-2023
 */
public class HamiltonianCycle {
    /**
     * The numberOfVertices field is used to contain number of vertices
     * @param numberOfVertices
     */
    private int numberOfVertices;


    /**
     * The path massive is used to contain current path
     * @param path
     */
    private int[] path;


    /**
     * The graph massive is used to contain matrix of graph
     * @param graphMatrix
     */
    private int[][] graphMatrix;


    /**
     * Main hamiltonianCycle() method
     *
     * @param graphMatrix
     */
    public void hamiltonianCycle(int[][] graphMatrix) {
        numberOfVertices = graphMatrix.length;
        path = new int[numberOfVertices];

        for (int i = 0; i < numberOfVertices; i++) {
            path[i] = -1;
        }

        this.graphMatrix = graphMatrix;

        path[0] = 0;
        if (findCycle(1)) {
            printCycle();
        } else {
            System.out.println("No Cycle");
        }
    }



    /**
     * Helper findCycle() method
     *
     * @param position
     * @return BOOLEAN value about whether the cycle is found or not
     */
    private boolean findCycle(int position) {
        if (position == numberOfVertices) {
            if (graphMatrix[path[position - 1]][path[0]] == 1) {
                return true;
            }
            return false;
        }

        for (int i = 1; i < numberOfVertices; i++) {
            if (isSafe(i, position)) {
                path[position] = i;

                if (findCycle(position + 1)) {
                    return true;
                }

                path[position] = -1;
            }
        }

        return false;
    }


    /**
     * Helper isSafe() method
     *
     * @param value
     * @param position
     * @return BOOLEAN value that value can be added to the cycle
     */
    private boolean isSafe(int value, int position) {
        if (graphMatrix[path[position - 1]][value] == 0) {
            return false;
        }

        for (int i = 0; i < position; i++) {
            if (path[i] == value) {
                return false;
            }
        }

        return true;
    }


    /**
     * Helper printCycle() method
     */
    private void printCycle() {
        System.out.println("Hamiltonian Cycle:");
        for (int i = 0; i < numberOfVertices; i++) {
            System.out.print(path[i] + " ");
        }
        System.out.print("| " + path[0] + " ...");
    }
}
