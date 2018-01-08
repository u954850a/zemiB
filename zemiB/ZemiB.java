package zemiB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
		System.out.println(nodes.size());
	}
	
	public Node getRoot() {
		return nodes.get(0);
	}
	
	public Node getNode(int i) {
		return nodes.get(i);
	}
}

class Node {
	private Map<Integer, Node> nextNodes;
	private List<Node> nodes;
	private int number;
	private boolean endFlag;
	
	public Node(int number, List<Node> nodes) {
		this.nodes = nodes;
		nextNodes = new HashMap<Integer, Node>();
		this.number = number;
		this.endFlag = false;
	}
	
	public void add(List<Integer> s, int i) {
		if(i == s.size()){
			this.endFlag = true;
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
			return this.endFlag;
		}
		Node next = nextNodes.get(s.get(i));
		if(next != null) {
			return next.isContain(s, i+1);
		}
		return false;
	}
	
	public boolean isEnd() {
		return this.endFlag;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Node getNextNode(int character) {
		return nextNodes.get(character);
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

class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
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
	Trie dictionary;
	final int NONE = -1;
	
	public ZemiB() {
		field = new int[height][width];
	}
	
	
	
	public static void main(final String[] args) throws FileNotFoundException {
		try {
			(new ZemiB()).run("input.txt", "dictionary.txt");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void run(final String inputFileName, final String dictFileName) throws IOException{
		dictionary = new Trie();
		FileReader fr = new FileReader(dictFileName);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while ((line = br.readLine()) != null) {
			String[] hoge = line.split(" ", 0);
//			System.out.println(hoge[0]);
			List<Integer> tmp = new ArrayList<Integer>();
			for(int i = 0; i < hoge.length; i++) {
				tmp.add(Integer.parseInt(hoge[i])-1);
			}
			dictionary.add(tmp);
		}
		dictionary.debug();
		
		br.close();
		
		
		for(int i = 0; i < height; i++) {
			Arrays.fill(field[i], NONE);
		}
		
		fr = new FileReader(inputFileName);
		br = new BufferedReader(fr);
		while((line = br.readLine()) != null) {
			String[] tmp = line.split(" ", 0);
			int c = Integer.parseInt(tmp[2]);
			int x = Integer.parseInt(tmp[0]);
			int y = Integer.parseInt(tmp[1]);
			c--; x--; y--;
			field[x][y] = c;
		}
		br.close();
		{
			Set<Integer> s = new HashSet<Integer>();
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					if(field[i][j] == NONE) continue;
					Node next = dictionary.getRoot().getNextNode(field[i][j]);
					if(next == null) continue;
					System.out.println(field[i][j]);
					usedEdgeFlag = new boolean[height][width][4];
					List<Integer> list = enumerate(i, j, next);
					s.addAll(list);
					System.out.println(s.size());
//					sum += s.size();
				}
			}
			System.out.println("sum : " + s.size());
		}
//		BNode answer = search();
		// anser.printLog();
	}
	
	private boolean checkRange(int x, int y) {
		return 0 <= x && x < height && 0 <= y && y < width;
	}
	
	boolean[][][] usedEdgeFlag;
	private List<Integer> enumerate(int x, int y, Node current) {
		// まずBFS
		System.out.println("x : " + x);
		System.out.println("y : " + y);
		Queue<Point> qu = new ArrayDeque<Point>();
		qu.add(new Point(x, y));
		int[][] preDirection = new int[height][width];
		for(int[] a : preDirection) {
			Arrays.fill(a, NONE);
		}
		while(!qu.isEmpty()) {
			Point currentPoint = qu.poll();
			for(int k = 0; k < 4; k++) {
				if(usedEdgeFlag[currentPoint.getX()][currentPoint.getY()][k]) {
					continue;
				}
				int nx = x+dx[k];
				int ny = y+dy[k];
				if(!checkRange(nx, ny) || preDirection[nx][ny] != NONE) continue;
				preDirection[nx][ny] = (k+2)%4;
			}
		}
		
		System.out.println("unnko");
		
		// そして次のノードを決める
		List<Integer> res = new ArrayList<Integer>();
		if(current.isEnd()) {
			res.add(current.getNumber());
		}
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(preDirection[i][j] == NONE || field[i][j] == NONE) continue;
				Node next = current.getNextNode(field[i][j]);
				if(next == null) continue;
				
				System.out.println("i : " + i);
				System.out.println("j : " + j);
				int curi = i, curj = j;
				while(preDirection[curi][curj] != NONE) {
					int dir = preDirection[curi][curj];
					System.out.println("curi : " + curi);
					System.out.println("curj : " + curj);
					
					assert(!usedEdgeFlag[curi][curj][dir]);
					usedEdgeFlag[curi][curj][dir] = true;
					assert(!usedEdgeFlag[curi+dx[dir]][curj+dy[dir]][(dir+2)%4]);
					usedEdgeFlag[curi+dx[dir]][curj+dy[dir]][(dir+2)%4] = true;
					curi += dx[dir];
					curj += dy[dir];
				}
				assert(curi == x); assert(curj == y);
				
				List<Integer> tmp = enumerate(i, j, next);
				
				curi = i;
				curj = j;
				while(preDirection[curi][curj] != NONE) {
					int dir = preDirection[curi][curj];
					usedEdgeFlag[curi][curj][dir] = false;
					usedEdgeFlag[curi+dx[dir]][curj+dy[dir]][(dir+2)%4] = false;
					curi += dx[dir];
					curj += dy[dir];
				}
				
				if(tmp.size() > res.size()) {
					List<Integer> a = tmp;
					tmp = res;
					res = a;
				}
				res.addAll(tmp);
			}
		}
		return res;
	}
	
	private BNode search() {
		final int BeamWidth = 100;
		List<BNode> nodes = new ArrayList<BNode>();
		nodes.add(new BNode());
		do {
			List<BNode> nextNodes = new ArrayList<BNode>();
			for(BNode node : nodes) {
				nextNodes.addAll(node.generateNext());
			}
			if(nextNodes.isEmpty()) break;
			// 大きい順にソート
			Collections.sort(nextNodes, new BNodeComparator());
			// 枝を刈る
			nodes.clear();
			for(int i = 0; i < BeamWidth && i < nextNodes.size(); i++) {
				nodes.add(nextNodes.get(i));
			}
		}while(true);
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
