
import java.io.IOException;
import java.util.Scanner;

/**
 * ***********************************************************************
 * Author: W1427692, Meirunas Smitas Last update: 22-03-2017
 *
 ************************************************************************
 */
public class PathFindingOnSquaredGrid {

    private static boolean[][] randomlyGenMatrix;
    private static int Ai, Aj, Bi, Bj;
    
    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top 
    public static boolean[][] flow(boolean[][] open) {
      int N = open.length;

        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }

        return full;
    }

    public static int getAi() {
        return Ai;
    }

    public static int getAj() {
        return Aj;
    }

    public static int getBi() {
        return Bi;
    }

    public static int getBj() {
        return Bj;
    }

    public static boolean[][] getRandomlyGenMatrix() {
        return randomlyGenMatrix;
    }

    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
         int N = open.length;

        // base cases
        if (i < 0 || i >= N) {
            return;    // invalid row
        }
        if (j < 0 || j >= N) {
            return;    // invalid column
        }
        if (!open[i][j]) {
            return;        // not an open cell
        }
        if (full[i][j]) {
            return;         // already marked as open
        }
        full[i][j] = true;

        flow(open, full, i + 1, j);   // down
        flow(open, full, i, j + 1);   // right
        flow(open, full, i, j - 1);   // left
        flow(open, full, i - 1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
       int N = open.length;

        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                return true;
            }
        }

        return false;
    }

    // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                // StdOut.println("Hello");
                directPerc = 1;
                int rowabove = N - 2;
                for (int i = rowabove; i >= 0; i--) {
                    if (full[i][j]) {
                        // StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
                        directPerc++;

                    } else {
                        break;
                    }
                }
            }
        }
            
        

        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) {
            return true;
        } else {
            return false;
        }
    }

    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
       int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (a[i][j] == which) {
                    StdDraw.square(j, N - i - 1, .5);
                } else {
                    StdDraw.filledSquare(j, N - i - 1, .5);
                }
            }
        }
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int repetitionNumber) {
      int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (a[i][j] == which) {

                    for (int currentNodeIndex = 0; currentNodeIndex < Dijkstra.getEndNode().getShortestPath().size(); currentNodeIndex++) {
                        if (i == Dijkstra.getEndNode().getShortestPath().get(currentNodeIndex).getX()
                                && j == Dijkstra.getEndNode().getShortestPath().get(currentNodeIndex).getY()
                                || i == Dijkstra.getEndNode().getX()
                                && j == Dijkstra.getEndNode().getY()) {
                            if (repetitionNumber == 0) {

                                StdDraw.setPenColor(StdDraw.GREEN);
                                StdDraw.filledSquare(j, N - i - 1, .5);
                            } else if (repetitionNumber == 1) {
                                StdDraw.setPenColor(StdDraw.RED);
                                StdDraw.filledRectangle(j, N - i - 1, .5, .2);
                            } else if (repetitionNumber == 2) {
                                StdDraw.setPenColor(StdDraw.BLUE);
                                StdDraw.filledRectangle(j, N - i - 1, .2, .5);
                            }

                            //StdDraw.filledSquare(j, N - i - 1, .5);
                            StdDraw.setPenColor(StdDraw.BLACK);
                        } else {
                            StdDraw.square(j, N - i - 1, .5);
                        }
                    }
                    if ((i == Dijkstra.getStartNode().getX() && j == Dijkstra.getStartNode().getY()) || (i == Dijkstra.getEndNode().getX() && j == Dijkstra.getEndNode().getX())) {
                        StdDraw.circle(j, N - i - 1, .5);

                    }

                } else {
                    StdDraw.filledSquare(j, N - i - 1, .5);
                }
            }

        }
    }

    // return a random N-by-N boolean matrix, where each entry is
// true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = StdRandom.bernoulli(p);
            }
        }
        return a;
    }

    // test client
    public static void main(String[] args) {

        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        randomlyGenMatrix = random(10, 0.8);
        int N = randomlyGenMatrix.length;
        StdArrayIO.print(randomlyGenMatrix);
        show(randomlyGenMatrix, true);

        System.out.println();
        System.out.println("The system percolates: " + percolates(randomlyGenMatrix));

        System.out.println();
        System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
        System.out.println();

    	// Reading the coordinates for points A and B on the input squared grid.
        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Start the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis
     

        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A (0," + (N-1) +") > ");
        Ai = in.nextInt();

        System.out.println("Enter j for A (0," + (N-1) +") > ");
        Aj = in.nextInt();
                        
        System.out.println("Enter i for B (0," + (N-1) +") > ");
        Bi = in.nextInt();

        System.out.println("Enter j for B (0," + (N-1) +") > ");
        Bj = in.nextInt();
        
        Dijkstra dijkstra = new Dijkstra();
        for (int repetitionNumber = 0; repetitionNumber < 3; repetitionNumber++) {
             Stopwatch timerFlow = new Stopwatch();
            dijkstra.mainDijkstra(repetitionNumber);
            
//        
//      THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
//         Stop the clock ticking in order to capture the time being spent on inputting the coordinates
//    	// You should position this command accordingly in order to perform the algorithmic analysis
            StdOut.println("Elapsed time = " + timerFlow.elapsedTime());
//        

            show(randomlyGenMatrix, true, repetitionNumber);
            //System.out.println("Click a key to continue");
//            try {
//                System.in.read();
//            } catch (IOException ex) {
//
//            }
        }
    }

}
