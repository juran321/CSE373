import java.util.*;

/**
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph {
    // You will need some private fields to represent the graph.
    // You are also likely to want some private helper methods.
    private Collection<Vertex> v;
    private Collection<Edge> e;
    private final Map<Vertex, Set<Edge> > map;
    // YOUR CODE HERE

    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
    	map = new HashMap<Vertex, Set<Edge>>();
		for (Vertex vertex : v) {
			if (!map.containsKey(vertex)) map.put(vertex, new HashSet<Edge>());
		}

		for (Edge edge : e) {
			if (edge.getWeight() < 0||!map.containsKey(edge.getSource()) ||!map.containsKey(edge.getDestination())) {
				throw new IllegalArgumentException();
			}

			for (Edge each : map.get(edge.getSource())) {
				if (each.getDestination().equals(edge.getDestination()) && each.getWeight() != edge.getWeight()) {
					throw new IllegalArgumentException();
				}
			}

			map.get(edge.getSource()).add(edge);
		}
		
		this.e =e;
		this.v =v;
		
}

    

	
    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
	public Collection<Vertex> vertices() {
		return v;
	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	public Collection<Edge> edges() {

		return e;
	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	public Collection<Vertex> adjacentVertices(Vertex v) {
		if (!map.containsKey(v)) {
			throw new IllegalArgumentException();
		}
		
		List<Vertex> nodes = new ArrayList<Vertex>();
		for (Edge edge : map.get(v)) {
			nodes.add(edge.getDestination());
		}
		return nodes;
	
	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	public int edgeCost(Vertex a, Vertex b) {
		if (a == null || b == null) {
			throw new IllegalArgumentException();
		}
		for (Edge edge : map.get(a)) {
			if (edge.getDestination().equals(b))
				return edge.getWeight();
		}
		return -1;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {
        List<Vertex> path = new ArrayList<>();
        if (a.equals(b)) {
            path.add(a);
            return new Path(path, 0);
        }
        Vertex terminal = terminal(a, b);
        if (terminal == null) return null;
        // keep track previous vertex of this vertex, so we can get all vertices in this path
        // but it is back order
        Vertex pre = terminal;
        while (pre != null) {
            path.add(pre);
            pre = pre.pre;
        }
        // reverse the list, make it correct order
        Collections.reverse(path);
        return new Path(path, terminal.dis);
        // YOUR CODE HERE

    }

    // Helper function to find two edges that they have the same start vertex and end vertex, but different weight
    

    // Helper method to find shortest path form start vertex to end vertex
    // return null if there is not such path, return the terminal vertex which contains the total cost since
    // start point and its previous in this path
    private Vertex terminal(Vertex a, Vertex b) {
        Queue<Vertex> priorityVertex = new PriorityQueue<>();
        // initialize all cost to be infinite, all previous vertex to be null
        // initialize cost of start point to be 0
        // insert all vertices to priority queue
        for (Vertex v : v) {
            v.dis = Integer.MAX_VALUE;
            v.pre = null;
            if (v.equals(a)) v.dis = 0;
            priorityVertex.add(v);
        }

        // body of Dijkstra's algorithm
        while (!priorityVertex.isEmpty()) {
            Vertex u = priorityVertex.poll();
            // u is the terminal vertex
            if (u.equals(b)) return u;

            // no such path from a to b, return null
            if (u.dis == Integer.MAX_VALUE) break;

            // for every edge (u, v) outgoing from u
            for (Edge e : e) {
                if (e.getSource().equals(u)) {
                    for (Vertex node : v) {
                        if (node.equals(e.getDestination())) {
                            // new cost is less than old one
                            if (node.dis > u.dis + e.getWeight()) {
                                // reset its cost and previous vertex
                                priorityVertex.remove(node);
                                node.dis =u.dis + e.getWeight();
                                node.pre = u;
                                priorityVertex.add(node);
                            }
                        }
                    }
                }
            }
        }
        return null;
    
    }	
	

}