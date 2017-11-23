/**
 * Representation of a graph vertex
 */
public class Vertex {
	private final String label;   // Label attached to this vertex
	public int dis;
	public boolean known;
	public Vertex pre;
	/**
	 * Construct a new vertex
	 * @param label the label attached to this vertex
	 */
	public Vertex(String label) {
		if(label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
	}

	/**
	 * Get a vertex label
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * A string representation of this object
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	// Auto-generated: hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	// Auto-generated: compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
            return other.label == null;
		} else {
		    return label.equals(other.label);
		}
	}
	public int compareTo(Vertex other) {
		if (this.dis < other.dis) return -1;
		if (this.dis > other.dis) return 1;
		return 0;
	}

	
}
