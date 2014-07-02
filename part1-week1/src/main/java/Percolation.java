/**
 * it uses array's index starting from 1 not 0.
 */
public class Percolation {

	private boolean landscapeGrid[];
	private int squareSize = 0;
	private WeightedQuickUnionUF weightedQuickUnionUF;

	// create N-by-N grid, with all sites blocked
	public Percolation(int n)
	{
		if(n<1)
			throw new IllegalArgumentException("lower than one");

		squareSize = n ;
		weightedQuickUnionUF = new WeightedQuickUnionUF(n*n);
		landscapeGrid = new boolean[n*n];
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j)
	{
	 	if(i<1||i>squareSize||j<1||j>squareSize)
			throw new IndexOutOfBoundsException("out of range");

		if(isOpen(i,j))
			return ;


		int newOpenSiteIndex = getIndex(i,j);
		//check and open specified site
		landscapeGrid[newOpenSiteIndex] = true;


		//check left site and connect
		if(j>1){
			if(isOpen(i, j-1)){
				//if(!weightedQuickUnionUF.connected(newOpenSiteIndex, getIndex(i,j-1)))
				weightedQuickUnionUF.union(newOpenSiteIndex, getIndex(i,j-1));
			}
		}

		//check right site and connect
		if(j<squareSize){
			if(isOpen(i, j+1)){
				//if(!weightedQuickUnionUF.connected(newOpenSiteIndex, getIndex(i,j+1)))
				weightedQuickUnionUF.union(newOpenSiteIndex, getIndex(i,j+1));
			}
		}

		//check upper site and connect
		if(i>1){
			if(isOpen(i-1, j)){
				//if(!weightedQuickUnionUF.connected(newOpenSiteIndex, getIndex(i-1,j)))
				weightedQuickUnionUF.union(newOpenSiteIndex, getIndex(i-1,j));
			}
		}

		//check lower site and connect
		if(i<squareSize){
			if(isOpen(i+1, j)){
				//if(!weightedQuickUnionUF.connected(newOpenSiteIndex, getIndex(i+1,j)))
				weightedQuickUnionUF.union(newOpenSiteIndex, getIndex(i+1,j));
			}
		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j)
	{
		if(i<1||i>squareSize||j<1||j>squareSize)
			throw new IndexOutOfBoundsException("out of range");

		int idx = getIndex(i,j);
		return landscapeGrid[idx];
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j)
	{
		if(i<1||i>squareSize||j<1||j>squareSize)
			throw new IndexOutOfBoundsException("out of range");

		int targetIndex = getIndex(i,j);

		//all columns in top row
		for(int eachTopCol=1;eachTopCol<=squareSize;eachTopCol++){
			if(!isOpen(1, eachTopCol))continue;
			int currentTopColIndex = getIndex(1, eachTopCol);
			if(currentTopColIndex==targetIndex)return true;

			//is the specified site connected?
			if(weightedQuickUnionUF.connected(currentTopColIndex, targetIndex)){
				return true;
			}
		}
		return false;
	}

	// does the system percolate?
	public boolean percolates()
	{
		//System.out.println(displayPercolation());
		//all columns in top row
		for(int eachTopCol=1;eachTopCol<=squareSize;eachTopCol++){
			if(!isOpen(1, eachTopCol))continue;
			//all columns in bottom row
			for(int eachBottomCol=1;eachBottomCol<=squareSize;eachBottomCol++){
				if(!isOpen(squareSize, eachBottomCol))continue;
				if(weightedQuickUnionUF.connected(getIndex(1, eachTopCol), getIndex(squareSize, eachBottomCol))){
					return true;
				}
			}
		}
		return false;
	}

	//change coordinates to one dimension array index
	private int getIndex(int i, int j){
		i+=-1;j+=-1;
		return i * squareSize + j;
	}

	private String displayPercolation(){

		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		for(int i=1; i<=squareSize;i++){
			for(int j=1; j<=squareSize;j++){
				builder.append(landscapeGrid[getIndex(i,j)]?"+":" ");
			}
			builder.append("\n");
		}

		return builder.toString();
	}
}