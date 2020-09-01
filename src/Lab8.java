import java.util.HashSet;
import java.util.Set;

public class Lab8 {
    public static void main(String[] args) {
        BipartiteGraph graph = BipartiteGraph.example8();
        System.out.println(graph.maximumMatching());
    }

    private static class BipartiteGraph {
        private final int[][] adjacencyMatrix;
        private final Set<Integer> inDegreeVertices;
        private final Set<Integer> outDegreeVertices;

        private BipartiteGraph(int[][] adjacencyMatrix, Set<Integer> inDegreeVertices, Set<Integer> outDegreeVertices) {
            this.adjacencyMatrix = adjacencyMatrix;
            this.inDegreeVertices = inDegreeVertices;
            this.outDegreeVertices = outDegreeVertices;
        }

        public static BipartiteGraph from(int[][] adjacencyMatrix) {
            assertMatrixIsSquare(adjacencyMatrix);
            assertEdgesAreBinary(adjacencyMatrix);
            BipartiteInfo info = assertGraphIsBipartite(adjacencyMatrix);
            return new BipartiteGraph(adjacencyMatrix, info.inDegreeVertices, info.outDegreeVertices);
        }

        private static void assertMatrixIsSquare(int[][] matrix) {
            int degree = matrix.length;
            for (int[] row : matrix) {
                if (row.length != degree) {
                    throw new RuntimeException("Invalid: The matrix is not a square");
                }
            }
        }

        private static void assertEdgesAreBinary(int[][] adjacencyMatrix) {
            for (int[] row : adjacencyMatrix) {
                for (int element : row) {
                    if (element != 0 && element != 1) {
                        throw new RuntimeException("Invalid: Bipartite Graph must be unweighted and all edge weights " +
                                "must be 0 or 1");
                    }
                }
            }
        }

        private static class BipartiteInfo {
            private final Set<Integer> outDegreeVertices;
            private final Set<Integer> inDegreeVertices;

            BipartiteInfo(Set<Integer> inDegreeVertices, Set<Integer> outDegreeVertices) {
                this.inDegreeVertices = inDegreeVertices;
                this.outDegreeVertices = outDegreeVertices;
            }
        }

        private static BipartiteInfo assertGraphIsBipartite(int[][] adjacencyMatrix) {
            Set<Integer> inDegreeVertices = new HashSet<>();
            Set<Integer> outDegreeVertices = new HashSet<>();
            for (int row = 0 ; row < adjacencyMatrix.length ; row++) {
                for (int column = 0 ; column < adjacencyMatrix.length ; column++) {
                    if (adjacencyMatrix[row][column] > 0) {
                        assertVertexHasNoIndegree(row, inDegreeVertices);
                        assertVertexHashNoOutdegree(column, outDegreeVertices);
                        inDegreeVertices.add(column);
                        outDegreeVertices.add(row);
                    }
                }
            }
            return new BipartiteInfo(inDegreeVertices, outDegreeVertices);
        }

        private static void assertVertexHasNoIndegree(int vertex, Set<Integer> vertices) {
            assertVertexNotIn(vertex, vertices);
        }

        private static void assertVertexHashNoOutdegree(int vertex, Set<Integer> vertices) {
            assertVertexNotIn(vertex, vertices);
        }

        private static void assertVertexNotIn(int vertex, Set<Integer> set) {
            if (set.contains(vertex)) {
                throw new RuntimeException("vertex: " + vertex + " should not be present here. This doesn't " +
                        "satisfy the condition for a Bipartite graph");
            }
        }

        public static BipartiteGraph example3() {
            // maximum matching 1
            return BipartiteGraph.from(new int[][] {
                    {0, 1},
                    {0, 0}
            });
        }

        public static BipartiteGraph example8() {
            return BipartiteGraph.from(new int[][] {
                    {0, 0, 0, 1, 1},
                    {0, 0, 0, 1, 1},
                    {0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            });
        }

        public int maximumMatching() {
            int[][] flowNetworkedMatrix = getFlowMatrix();
            FordFulkerson graph = FordFulkerson.from(flowNetworkedMatrix);
            return graph.maxFlow(0, adjacencyMatrix.length + 1);
        }

        @SuppressWarnings("ManualArrayCopy")
        private int[][] getFlowMatrix() {
            int[][] matrix = new int[adjacencyMatrix.length + 2][adjacencyMatrix.length + 2];
            for (int row = 0 ; row < adjacencyMatrix.length ; row++) {
                for (int column = 0 ; column < adjacencyMatrix.length ; column++) {
                    matrix[row + 1][column + 1] = adjacencyMatrix[row][column];
                }
            }
            for (int vertex : inDegreeVertices) {
                matrix[vertex + 1][matrix.length - 1] = 1;
            }
            for (int vertex : outDegreeVertices) {
                matrix[0][vertex + 1] = 1;
            }
            return matrix;
        }
    }
}
