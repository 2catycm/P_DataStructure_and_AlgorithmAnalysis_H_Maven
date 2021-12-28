#pragma GCC optimize("Ofast")
#include <array>
#include <bitset>
#include <cassert>
#include <cstdio>
#include <iostream>
#include <list>
#include <memory>
#include <queue>
constexpr int MAXROADS = 5e5;
struct Road {
    int cityA;
    int cityB;
    int cost;
    bool operator<(const Road& other) const {
        return cost < other.cost;
    }
    bool operator>(const Road& other) const {
        return cost > other.cost;
    }
    int otherThan(int city) {
        if(city == cityA)
            return cityB;
        else {
            assert(city == cityB);
            return cityA;
        }
    }
};
std::array<std::list<std::shared_ptr<Road>>, MAXROADS + 1> graph;  //联通无向有权图。
inline void addEdge(int u, int v, int w) {
    auto newRoad = std::shared_ptr<Road>(new Road{ u, v, w });
    graph[u].push_back(newRoad);
    graph[v].push_back(newRoad);
}
std::bitset<MAXROADS + 1> isVisited;
inline void visit(int vertex, std::priority_queue<Road, std::vector<Road>, std::greater<Road>>& transverseEdges) {
    isVisited.set(vertex);
    for(const auto& road : graph[vertex]) {
        // transverseEdges.push(*road);  //把新节点的所有边复制进去。
        if(!isVisited[road.get()->otherThan(vertex)])  //如果已经在树内，就不加入优先队列。
            transverseEdges.push(*road);
    }
}
//返回cost
size_t prim(int start) {
    size_t cost = 0;
    std::priority_queue<Road, std::vector<Road>, std::greater<Road>> transverseEdges;  //小顶堆
    visit(start, transverseEdges);
    while(!transverseEdges.empty()) {
        // 1.选择最小横切边
        // const auto& road = transverseEdges.top(); 
        const auto road = transverseEdges.top(); //不能引用，因为被删掉了
        transverseEdges.pop();
        if(isVisited[road.cityA] && isVisited[road.cityB])  //边的两端都在树内。
            continue;
        // 2.把最小横切边加入树中. 这里只是把cost记上，下一步还要访问新的节点。
        cost += road.cost;
        // 3.访问新的树内节点。
        if(isVisited[road.cityA])
            visit(road.cityB, transverseEdges);  // cityB是新的城市。
        else
            visit(road.cityA, transverseEdges);
    }
    return cost;
}
int main(int argc, char const* argv[]) {
    int n, m;
    scanf("%d%d", &n, &m);
    for(size_t i = 0; i < m; i++) {
        int u, v, w;
        scanf("%d%d%d", &u, &v, &w);
        addEdge(u, v, w);
    }
    std::cout << prim(1) << std::endl;
    return 0;
}
