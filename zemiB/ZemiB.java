package zemiB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ZemiB {


	public static void main(final String[] args) {
		// hogehoge
		new ZemiB().run("data/demo.txt");

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
