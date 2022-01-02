
#pragma GCC optimize("Ofast")
#include <cstdio>
#include <list>
#include <array>
#include <vector>
#include <bitset>
#include <stack>
struct Edge
{
	int relative;
	int order;
};
namespace
{
	constexpr int maxindex = 100000;
	std::array<std::list<Edge>, maxindex> adj;
	std::array<std::list<Edge>, maxindex> rev;
	std::bitset<maxindex> isVisited; //自动都是0
	std::stack<int> reversePost;
	int n, m;
} // namespace

inline void addEdge(int u, int v, int order)
{
	adj[u].push_back({v, order});
	rev[v].push_front({u, order});
}
void dfs_order(int current, const int& roadCnt){
	isVisited[current] = true;
	for (const auto& edge:rev[current])
		if (edge.order<=roadCnt && !isVisited[edge.relative])
			dfs_order(edge.relative, roadCnt);
	reversePost.push(current);	
}
inline void depth_first_order(const int& roadCnt)
{
	for (int i = 1; i <= n; i++)
		if (!isVisited[i])
			dfs_order(i, roadCnt);
}
void dfs(int current, const int& roadCnt){
	isVisited[current] = true;
	for(const auto& edge:adj[current])
		if (edge.order<=roadCnt && !isVisited[edge.relative])
			dfs(edge.relative, roadCnt);
}
inline bool check(int roadCnt)
{
	isVisited = 0; //下一次check，还是要清零。
	depth_first_order(roadCnt);
	isVisited = 0;
	int count = 0;
	while (!reversePost.empty())
	{
		const auto& top = reversePost.top();
		reversePost.pop();
		if (isVisited[top])
			continue;
		dfs(top, roadCnt);
		count++;
	}
	return count==1;
}
int main()
{
	scanf("%d%d", &n, &m);
	for (int i = 1; i <= m; i++)
	{
		int u, v;
		scanf("%d%d", &u, &v);
		addEdge(u, v, i);
	}
	//当要求建设x条路时，x = 1...m, 小于等于x的order可以启用。
	int left = 1, right = m, mid;
	while (left <= right)
	{
		mid = left + (right - left) / 2;
		if (check(mid))
		{
			right = mid - 1;
		}
		else
		{
			left = mid + 1;
		}
	}
	// left是保守指针，left是取得到的（除非上调到很大，否则会一直上调进行尝试）。现在是想要尽可能少。
	printf("%d\n", left > m ? -1 : left);
}
