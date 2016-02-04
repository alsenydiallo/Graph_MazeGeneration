import java.util.ArrayList;

public class MazeGraph implements Graph {

	private int width, height;
	public MazeGraph(int H, int W){
		width = W;
		height = H;
	}

	@Override
	public int numVerts() {
		return width*height;
	}

	private int computeV(int r, int c){
		assert(r > 0);
		assert(c > 0);
		return r * width + c;
	}
	@Override
	public ArrayList<Integer> adjacents(int v) {
		assert(v >= 0);
		ArrayList<Integer> adjacentsNode = new ArrayList<>();
		int r = v / width;
		int c = v % width;
		
		if(c > 0)
			adjacentsNode.add(computeV(r, c-1));
		if(c < width-1)
			adjacentsNode.add(computeV(r, c+1));
		if(r > 0)
			adjacentsNode.add(computeV(r-1, c));
		if(r < height-1)
			adjacentsNode.add(computeV(r+1, c));
		
		return adjacentsNode;
	}

}
