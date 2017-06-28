public class Percolation
{
    private int size;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF qu;
    private boolean[][] sites;
    private int count;

//create n-by-n grid with all sitess closed
    public Percolation (int N) 
    {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = N ;
        top = 0;
        count = 0;
        bottom = size * size + 1;
        qu = new WeightedQuickUnionUF (bottom +1);
        sites = new boolean [size][size];
    }
// open sites (row, col) if it is not open already
    public void open(int row, int col)
    {
        validateInput(row,col);
        sites [row-1][col-1]=true;
        count++;
        if (row == 1) {
            qu.union(getQFIndex(row, col), top);
        }
        if (row == size) {
            qu.union(getQFIndex(row, col), bottom);
        }
         if (col > 1 && isOpen(row, col - 1)) {
            qu.union(getQFIndex(row, col), getQFIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            qu.union(getQFIndex(row, col), getQFIndex(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            qu.union(getQFIndex(row, col), getQFIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            qu.union(getQFIndex(row, col), getQFIndex(row + 1, col));
        }
    }
// is sites (row, col) open?    
    public boolean isOpen(int row, int col)
    {
        validateInput(row,col);
        return sites[row - 1][col - 1];
    }
// is sites (row, col) full?
   public boolean isFull(int row, int col)
   {
       validateInput(row,col);
       if (0 < row && row <= size && 0 < col && col <= size) {
            return qu.connected(top, getQFIndex(row , col));
        } else {
            throw new IndexOutOfBoundsException();
        }
   }

// number of open sitess
   public int numberOfOpenSites()
   {
       return count;
   }
// does the system percolate?
   public boolean percolates()
   {
       return qu.connected(top, bottom);
   }
private int getQFIndex(int row, int col) {
        return size * (row - 1) + col;
    }
private void validateInput(int row, int col) {
        if (1 > row || size < row) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (1 > col || size < col) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }
}
        
    