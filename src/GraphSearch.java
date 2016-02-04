import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class GraphSearch {
	
	public static int[] mazeDFS(Graph G, int s){
		boolean[] visited = new boolean[G.numVerts()];
		int[] parent = new int[G.numVerts()];
		ArrayList<ArrayList<Integer>> neighbors = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();
		Random rnd = new Random(1234);
		int visitCount = 0;
		
		visited[s] = true;
		stack.push(s);
		parent[s] = -1;
		visitCount = 1;
		
		// create a list of each node with all it neighbors
		for(int i=0; i<G.numVerts(); i++)
			neighbors.add(G.adjacents(i));
		
		while(visitCount != G.numVerts()){
	
			if(!neighbors.get(s).isEmpty()){
				// randomly visit a neighbor
				int index = Math.abs(rnd.nextInt() % neighbors.get(s).size());
				int w = neighbors.get(s).remove(index); // remove visited neighbors from the list 
				
				if(!visited[w]){
					visited[w] = true;
					visitCount++;
					parent[w] = s;
					stack.push(w);
					s = w;
				}
			}
			else{
				s = stack.pop();
			}
		}
		return parent;
	}
	
}
