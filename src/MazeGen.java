import java.io.PrintStream;
import java.util.Random;

public class MazeGen {

	final static int EAST = 1; // 0001
	final static int NORTH = 2; // 0010
	final static int WEST = 4; // 0100
	final static int SOUTH = 8; // 1000	

	public static void printCells(String fname, int W, int H, int cells[][]){
		//Output textfile where cells[][] encodes the maze
		try {
			PrintStream ps = new PrintStream(fname);
			ps.println(W + " " + H);
			for (int r = 0; r < H; r++) {
				for (int c = 0; c < W; c++) {
					ps.print(cells[r][c] + " ");
				}
				ps.println();
			}
			ps.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static int[][] encodeMaze(int H, int W, int[] parents){
		// initialize each cell with four walls
		int cells[][] = new int[H][W];

		for(int r=0; r < H; r++)
			for(int c=0; c < W; c++)
				cells[r][c] = 0xF; // bit code 1111

		for(int r=0; r < H; r++){
			for(int c=0; c < W; c++){
				int v = r * W + c;
				int p = parents[v];

				if(p >= 0){// v has a parent
					int r0 = p / W;
					int c0 = p % W;

					if(c0 == c+1){
						cells[r][c] &= ~EAST;
						cells[r0][c0] &= ~WEST;
					}
					else if(c0 == c-1){
						cells[r][c] &= ~WEST;
						cells[r0][c0] &= ~EAST;
					}
					else if(r0 == r+1){
						cells[r][c] &= ~SOUTH;
						cells[r0][c0] &= ~NORTH;
					}
					else if(r0 == r-1){
						cells[r][c] &= ~NORTH;
						cells[r0][c0] &= ~SOUTH;
					}
				}
			}
		}	
		//Randomly choose start point and end point
		Random rnd = new Random(1234);
		int index = Math.abs(rnd.nextInt());
		cells[index%(H-1)][0] &= ~WEST; // start point
		index = Math.abs(rnd.nextInt());
		cells[index%(H-1)][W-1] &= ~EAST; // end point
		
		return cells;
	}

	public static void main(String[] args) {
		int W, H;
		String fname;
		if (args.length != 3) {
			W = 16;
			H = 16;
			fname = "maze16x16.txt";
		} else {
			W = Integer.parseInt(args[0]);
			H = Integer.parseInt(args[1]);
			if (W < 5 || H < 5) {
				System.err.println("bogus size!");
				return;
			}
			fname = args[2];
		}

		MazeGraph g = new MazeGraph(H, W);

		int[] parents = GraphSearch.mazeDFS(g, 7);
		int[][] cells = encodeMaze(H, W, parents);
		printCells(fname, H, W, cells);

	}
}


