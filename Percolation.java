import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

     int N;
     private boolean[][] open;

     private static final int TOP = 0;

     private int bottom;

    // int [][] id;
    private int numOfOpenSites = 0;
    private QuickFindUF full;
    WeightedQuickUnionUF perc;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int N)
    {

        this.N = N;
        this.open = new boolean[N][N];
        full = new QuickFindUF(N*N + 2);
        perc = new WeightedQuickUnionUF(N*N);
        bottom = (N*N) +1;
        numOfOpenSites = 0;

       /* if(N <1 )
        {
            throw new IllegalArgumentException ("outside its prescribed range");
        } else
        {
           // id = new int[N][N];
            for(int i=0; i<N; i++)
            {
                for(int j=0; j<N; j++)
                {
                    this.open[i][j] = false;
                   // id[i][j]= i*N +j;
                    System.out.println("["+(i*N)+"]["+j+"] : "+open[i][j]);
                }
            }
        }*/



    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        checkEdgeCases(row, col);
        return open[row-1][col-1];
    }

    private void checkEdgeCases(int row, int col) {

        if (row <= 0 || row >N || col <= 0 || col > N)
        {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        checkEdgeCases(row,col);
        open[row - 1][col - 1] = true;
        numOfOpenSites++;

        if(row == 1)
        {
            full.union(getCorrectIndex(row, col), TOP);
        }
        if(row == N)
        {
            full.union(getCorrectIndex(row, col), bottom);
        }


        if(row > 1 && isOpen(row-1, col))
        {
            full.union(getCorrectIndex(row, col), getCorrectIndex(row-1, col));
        }
        if(row <N && isOpen(row + 1, col))
        {
            full.union(getCorrectIndex(row, col), getCorrectIndex(row+1, col));
        }

        if(col > 1 && isOpen(row, col-1))
        {
            full.union(getCorrectIndex(row, col), getCorrectIndex(row, col-1));
        }
        if(col < N && isOpen(row, col+1))
        {
            full.union(getCorrectIndex(row, col), getCorrectIndex(row, col+1));
        }

    }

    private int getCorrectIndex(int row, int col)
    {
        return ((row-1)*N +col);
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
       return (full.find(TOP) == full.find(getCorrectIndex(row,col)));

    }

    // does the system percolate?
    public boolean percolates()
    {
        return (full.find(TOP) == full.find(bottom));
    }

    public int numberOfOpenSites()
    {
        return  numOfOpenSites;
    }
}
