#pragma GCC optimize("Ofast")
#include <array>
#include <cstdint>
#include <cstdio>
#include <iostream>
#include <limits>
#include <list>
#include <queue>
#include <bitset>
#include <cassert>
using namespace std;
constexpr int MAXV = 100005;
//有向图
struct Edge{
    int other;
    int weight;
};
list<Edge> adj[MAXV]{ {} };  // index是节点，第一个是连接的节点，第二个是权值。

static void addEdge(int u, int v, int w) {
    adj[u].push_back({ v, w });
}
struct Offer{
    int vertex;
    // int distance;
    size_t distance;
    bool operator<(const Offer& other) const {
        return distance < other.distance;
    }
    bool operator>(const Offer& other) const {
        return distance > other.distance;
    }
};
std::array<int, MAXV> parent{};  //最短路径树
// std::array<int, MAXV> distances{ /* std::numeric_limits<int32_t>::max() */ }; //垃圾c++，居然是0！
std::array<size_t, MAXV> distances{ /* std::numeric_limits<int32_t>::max() */ }; //垃圾c++，居然是0！
std::bitset<MAXV> isAwayFromTable;
void dijkstra(int src) {
    distances[src] = 0;
    std::priority_queue<Offer, std::vector<Offer>, std::greater<Offer>> pq;
    pq.push({src, distances[src]});
    while(!pq.empty()) {
        const Offer top = pq.top();
        pq.pop();
        if(isAwayFromTable[top.vertex]) {
            continue;
        }
        isAwayFromTable.set(top.vertex); //这个offer使得节点被拉了起来。
        assert (distances[top.vertex] == top.distance); 
        for (const auto& edge:adj[top.vertex]){
            int relative = edge.other;
            size_t newCost = edge.weight+top.distance;
            if (newCost < distances[relative]){
                distances[relative] = newCost;
                pq.push({relative, newCost});
            }
        }
    }
}
int main(int argc, char const* argv[]) {
    int n, m;
    scanf("%d %d", &n, &m);
    for(int i = 0; i < m; i++) {
        int u, v, w;
        scanf("%d %d %d", &u, &v, &w);
        addEdge(u, v, w);
    }
    // std::cout << distances[n];
    std::fill(distances.begin(), distances.end(), std::numeric_limits<size_t>::max());
    dijkstra(1);
    // printf("%d\n", distances[n]);
    std::cout << distances[n]; //<< std::cout;
    return 0;
}
