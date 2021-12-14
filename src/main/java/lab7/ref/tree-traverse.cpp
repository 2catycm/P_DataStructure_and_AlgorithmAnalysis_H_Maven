// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



const int N = 100000 + 10; // a constant representing the size of the tree (i.e. the number of nodes)


/* Storing a tree:
 * Use std::vector<> (C++) or ArrayList (Java) to store the tree.
 */

/* The struct that describes each edge.
 * There is no need to store both endpoints.
 */
struct Edge { // (u --w--> v)
	int v; // the other endpoint of this edge
	int w; // the weight of the edge
	// ... (other properties of this edge)
};

std::vector<Edge> out_edge[N]; // out_edge[u] stores the edges connecting node u.



// Add an edge between node u and node v in BOTH direction.
void addEdge(int u, int v, int w) {
	Edge e;
	e.w = w;

	e.v = v;
	out_edge[u].push_back(e);
	e.v = u;
	out_edge[v].push_back(e);

	/* Or, shorter:
	 * out_edge[u].push_back(Edge{v, w});
	 * out_edge[v].push_back(Edge{u, w});
	 */
}

// Read and build the tree.
void readData() {
	scanf("%d", &n);
	for (int i = 1; i <= n - 1; ++i) {
		int u, v, w;
		scanf("%d %d %d", &u, &v, &w);
		addEdge(u, v, w);
	}
}
/*
sample input
5 
1 2 1
2 3 5
3 4 7
5 4 2
*/

// These are some basic arrays that are going to be calculated by DFS.
int size[N]; // size of subtree u
int dep[N];  // depth of node u (define dep[root] = 1)
int max_subtree[N]; // size of the biggest subtree of u

/* Depth-First-Search example
 *   u is the current node.
 *   fa is the father of current node.
 */
void dfs(int u, int fa) {
	// 1. Init some values
	dep[u] = dep[fa] + 1;
	size[u] = 1;
	max_subtree[u] = 0;
	// ... more ...

	/* 2. Begin Depth-First-Search
	 *    Iterate all edges that connect node u and some other nodes.
	 *    For leaves, this step will be automatically skipped (move into the for-loop, then being "continue"ed and jumps out).
	 */
	for (Edge edge : out_edge[u]) {
		int v = edge.v;
		int w = edge.w;
		// Now we are considering an edge from u to v, with weight of w. ( u --w--> v)

		// The edge pointing to the father should be skipped.
		// DO NOT DFS its father.
		if (v == fa) {
			continue;
		}

		dfs(v, u); // DFS the subtree.
		// Now, all basic values within the subtree v should have been calculated correctly.

		// Gather those information coming from the subtree v.
		size[u] += size[v];
		max_subtree[u] = std::max(max_subtree[u], size[v]);
		// ... more ...
	}

	/* 3. Calculate
	 *    Do whatever you want here to calculate the answer of node u or subtree u.
	 *    You can make use of information coming from all subtrees.
	 *    For example, calculate the C.O.G of subtree u. (lab7-G)
	 */
	printf("The max subtree of node %d is %d.\n", u, max_subtree[u]);

	// Now, all basic values within the subtree u should have been calculated correctly.
	// All jobs finished, return.
}

// Entry point.
int main() {
	readData();
	int rt = 1; // Specify the root.
	dfs(rt, -1); // The root has no father.
	return 0;
}


// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!! Warning: Do Not Copy This Template Directly! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
