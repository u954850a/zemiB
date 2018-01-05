package zemiB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

public class ZemiB {


	public static void main(final String[] args) {
		try{
			File file = new File("input.txt");
			Scanner scan = new Scanner(file);
			Trie trie = new Trie();
			int n = Integer.parseInt(scan.next());
			for(int i = 0; i < n; i++) {
				int m = Integer.parseInt(scan.next());
				List<Integer> tmp = new ArrayList<Integer>();
				for(int j = 0; j < m; j++) {
					tmp.add(Integer.parseInt(scan.next()));
				}
				System.out.println(tmp);
				trie.add(tmp);
			}
			int k = Integer.parseInt(scan.next());
			for(int i = 0; i < k; i++) {
				int m = Integer.parseInt(scan.next());
				List<Integer> tmp = new ArrayList<Integer>();
				for(int j = 0; j < m; j++) {
					tmp.add(Integer.parseInt(scan.next()));
				}
				System.out.println(trie.isContain(tmp));
			}
		}catch(FileNotFoundException e){
			System.out.println(e);
		}
	}


	public void run(final String inputFileName){
		try {
			List<String> buffer = Files.readAllLines(Paths.get(inputFileName));

			for(int i=0;i<buffer.size();i++) {
				System.out.println(buffer.get(i));
			}

			int [][] toporogy = createTopology(20,20);

			for(int i=0;i<400;i++) {
				if(toporogy[39][i]==1) {
					System.out.println(i);
				}
			}


		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
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
