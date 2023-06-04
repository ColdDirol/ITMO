/**
 * The HamiltonianPath class is used to find Hamiltonian path
 *
 * @author Vladimir Kartashev
 * @version 1.0
 * @since 04-06-2023
 */
public class HamiltonianPath {
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
     * The visitedVertices massive is used to contain visited vertices information
     * @param visitedVertices
     */
    private boolean[] visitedVertices;


    /**
     * The graphVerticesNames massive is used to contain graph vertex names
     * @param graphVerticesNames
     */
    private String[] graphVerticesNames;




    /**
     * Main hamiltonianPath() method
     * @param graphMatrix
     * @param graphVerticesNames
     */
    public void hamiltonianPath(int[][] graphMatrix, String[] graphVerticesNames) {
        numberOfVertices = graphMatrix.length;
        path = new int[numberOfVertices];
        visitedVertices = new boolean[numberOfVertices];
        this.graphVerticesNames = graphVerticesNames;
        this.graphMatrix = graphMatrix;

        for (int i = 0; i < numberOfVertices; i++) {
            visitedVertices[i] = false;
            path[i] = -1;
        }

        path[0] = 0;
        visitedVertices[0] = true;

        if (findPath(1)) {
            printPath();
        } else {
            System.out.println("No Path Found");
        }
    }



    /**
     * Helper findPath() method
     *
     * @param vertexIndex
     * @return BOOLEAN value about whether the path is found or not
     */
    private boolean findPath(int vertexIndex) {
        if (vertexIndex == numberOfVertices) {
            return graphMatrix[path[vertexIndex - 1]][path[0]] == 1;
        }

        for (int i = 0; i < numberOfVertices; i++) {
            if (isSafe(vertexIndex, i)) {
                path[vertexIndex] = i;
                visitedVertices[i] = true;

                if (findPath(vertexIndex + 1)) {
                    return true;
                }

                visitedVertices[i] = false;
            }
        }
        return false;
    }



    /**
     * Helper isSafe() method
     *
     * Method isSafe() check that vertexIndex can be added to the path
     *
     * @param vertexIndex
     * @param vertex
     * @return BOOLEAN value that vertexIndex can be added to the path
     */
    private boolean isSafe(int vertexIndex, int vertex) {
        if (graphMatrix[path[vertexIndex - 1]][vertex] == 0) {
            return false;
        }

        for (int i = 0; i < vertexIndex; i++) {
            if (path[i] == vertex) {
                return false;
            }
        }

        return true;
    }



    /**
     * Helper printPath() method
     *
     * Method printPath() print the found Hamiltonian Path
     */
    private void printPath() {
        System.out.println("Hamiltonian Path: ");

        for (int i = 0; i < path.length; i++) {
            System.out.print(graphVerticesNames[path[i]] + " ");
        }

        System.out.println("| " + graphVerticesNames[path[0]] + " ...");
    }
}
