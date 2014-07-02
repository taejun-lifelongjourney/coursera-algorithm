/**
 * Created with IntelliJ IDEA.
 * User: colin
 * Date: 2014. 6. 25.
 * Time: 오후 1:20
 * To change this template use File | Settings | File Templates.
 */
public class QuickUnionUF {
	private int[] id;

	public QuickUnionUF(int N){
		id = new int[N];

		for(int i = 0; i < N; i++)
			id[i]=1;
	}

	public int root(int i){
		while(i != id[i]) i = id[i];
		return i;
	}

	public boolean connected(int p, int q){
		return root(p) == root(q);
	}

	public void union(int p, int q){
		if (connected(p, q))return;

		int qRoot = root(q);
		int pRoot = root(p);
		id[pRoot] = qRoot;
	}
}
