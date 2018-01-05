package zemiB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Trie {
	private List<Node> nodes;
	public Trie() {
		nodes = new ArrayList<Node>();
		nodes.add(new Node(0, nodes));
	}
	
	public void add(List<Integer> s) {
		nodes.get(0).add(s, 0);
	}
	
	public boolean isContain(List<Integer> s) {
		return nodes.get(0).isContain(s, 0);
	}
	
	public void debug() {
		
	}
}

class Node {
	private Map<Integer, Node> nextNodes;
	private List<Node> nodes;
	private int number;
	private boolean isEnd;
	
	public Node(int number, List<Node> nodes) {
		this.nodes = nodes;
		nextNodes = new HashMap<Integer, Node>();
		this.number = number;
		this.isEnd = false;
	}
	
	public void add(List<Integer> s, int i) {
		if(i == s.size()){
			this.isEnd = true;
			return;
		}
		Node next = nextNodes.get(s.get(i));
		if(next == null) {
			next = new Node(nodes.size(), nodes);
			nextNodes.put(s.get(i), next);
			nodes.add(next);
		}
		next.add(s, i+1);
	}
	
	public boolean isContain(List<Integer> s, int i) {
		if(s.size() == i) {
			return this.isEnd;
		}
		Node next = nextNodes.get(s.get(i));
		if(next != null) {
			return next.isContain(s, i+1);
		}
		return false;
	}
}

// ビームサーチのノード
class BNode {
	static final int height = 30;
	static final int width = 30;
	// このdx, dyで方向に番号をつける
	// たとえば (-1, 0)方向は0
	static final int dx[] = {-1, 0, 1, 0};
	static final int dy[] = {0, -1, 0, 1};
	// boolean[x][y][方向の番号] ですでに使った辺を管理する
	boolean[][][] usedFlag;
	
	
	public BNode() {
		usedFlag = new boolean[height][width][4];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Arrays.fill(usedFlag[i][j], false);
			}
		}
	}
	
	public BNode(boolean[][][] usedFlag) {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				this.usedFlag[i][j] = usedFlag[i][j].clone();
			}
		}
	}
	
	public int eval() {
		// 評価値を返す
		return -1;
	}
	
	public List<BNode> generateNext() {
		// 次のステップのノードを返す
		List<BNode> ret = new ArrayList<BNode>();
		// hogehoge
		return ret;
	}
}

class BNodeComparator implements Comparator<BNode> {

	@Override
	public int compare(BNode o1, BNode o2) {
		int val1 = o1.eval();
		int val2 = o2.eval();
		if(val1 < val2) {
			return 1;
		} else if(val1 == val2) {
			return 0;
		} else {
			return -1;
		}
	}
	
}

public class ZemiB {

	
	static final int dx[] = {-1, 0, 1, 0};
	static final int dy[] = {0, -1, 0, 1};
	final int height = 30;
	final int width = 30;
	int[][] field;
	final int NONE = -1;
	
	public ZemiB() {
		field = new int[height][width];
	}
	
	
	
	public static void main(final String[] args) throws FileNotFoundException {
		(new ZemiB()).run("input.txt");
	}


	public void run(final String inputFileName) throws FileNotFoundException{
		for(int i = 0; i < height; i++) {
			Arrays.fill(field[i], NONE);
		}
		File file = new File(inputFileName);
		Scanner scan = new Scanner(file);
		while(scan.hasNext()) {
			int c = Integer.parseInt(scan.next());
			int x = Integer.parseInt(scan.next());
			int y = Integer.parseInt(scan.next());
			c--; x--; y--;
			field[x][y] = c;
		}
		BNode answer = search();
		// anser.printLog();
	}
	
	
	private BNode search() {
		final int BeamWidth = 100;
		List<BNode> nodes = new ArrayList<BNode>();
		nodes.add(new BNode());
		boolean endFlag = true;
		do {
			List<BNode> nextNodes = new ArrayList<BNode>();
			for(BNode node : nodes) {
				nextNodes.addAll(node.generateNext());
			}
			// 大きい順にソート
			Collections.sort(nextNodes, new BNodeComparator());
			// 枝を刈る
			nodes.clear();
			for(int i = 0; i < BeamWidth && i < nextNodes.size(); i++) {
				nodes.add(nextNodes.get(i));
			}
		}while(endFlag);
		return nodes.get(0);
	}


	private int[][] createTopology(int x_max,int y_max) {

		int allnode=x_max*y_max;

		int[][] toporogy = new int[allnode][allnode];

		for(int i=0;i<x_max;i++) {
			for(int j=0;j<y_max;j++) {

				if(i!=0) {
					toporogy[i+x_max*j][i-1+x_max*j]=1;
				}

				if(i!=(x_max-1)) {
					toporogy[i+x_max*j][i+1+x_max*j]=1;
				}

				if(j!=0) {
					toporogy[i+x_max*j][i+x_max*(j-1)]=1;
				}

				if(j!=(y_max-1)) {
					toporogy[i+x_max*j][i+x_max*(j+1)]=1;
				}

			}
		}


		return toporogy;
	}

}
