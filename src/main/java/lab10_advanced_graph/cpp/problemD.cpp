#pragma GCC optimize("Ofast")
#include <array>
#include <bitset>
#include <cassert>
#include <cmath>
#include <iostream>
#include <list>
#include <queue>
constexpr size_t nodesForN(int n) {
    return 3 * n * n + 3 * n + 1;
}
constexpr size_t MAX = nodesForN(200) + 1;
std::array<std::list<int>, MAX> adj;
inline void addEdge(int u, int v) {
    assert(u != v);
    adj[u].push_back(v);
    adj[v].push_back(u);
}
std::array<int, MAX> weights;               // node有weight
inline size_t weightOfEdge(int u, int v) {  //简单图, 节点可以确定一条边
    assert(u != v);
    return size_t(weights[u]) * size_t(weights[v]);
}
//输入一个初始在生成树中的节点，然后返回最小生成树的cost
inline size_t prim(int start = 1) {
    size_t cost = 0;
    struct Edge {
        int u;
        int v;
    };
    auto edgeCmp = [](const Edge& e1, const Edge& e2) { return weightOfEdge(e1.u, e1.v) > weightOfEdge(e2.u, e2.v); };
    std::bitset<MAX> isVisited;
    std::priority_queue<Edge, std::vector<Edge>, decltype(edgeCmp)> pq(edgeCmp);  //横切边们
    auto visit = [&](const int& vertex) {
        isVisited.set(vertex);
        for(const auto& relative : adj[vertex])
            if(!isVisited[relative])
                // pq.emplace(vertex, relative);
                pq.push({ vertex, relative });
    };
    visit(start);
    while(!pq.empty()) {
        const Edge top = pq.top();
        pq.pop();
        //幽灵边
        if(isVisited[top.u] && isVisited[top.v])
            continue;
        cost += weightOfEdge(top.u, top.v);
        if(isVisited[top.u])
            visit(top.v);
        if(isVisited[top.v])
            visit(top.u);
    }
    return cost;
}
int main(int argc, char const* argv[]) {
    int n;
    std::cin >> n;
    int prevLineCnt = 0;     //上一行有多少个节点
    size_t currentNode = 1;  //当前在输入图里的第几个节点
    for(int i = 1; i <= 2 * n + 1; i++) {
        const int lineCnt = 2 * n + 1 - std::abs(i - n - 1);  //这一行有几个节点。
        for(int j = 1; j <= lineCnt; j++) {
            std::cin >> weights[currentNode];
            //不是第一行，触发连边。
            if(i != 1) {
                const int& myPositionAtThisRow = j;
                int adjNodeAtLastRow = currentNode - prevLineCnt;
                //中间这行，是n+1行，在第二行到n+1行
                //比如，第二个节点，对应的是上一行的一和二、
                //在第n+2行到2n+1行，对应的是二和三。
                if(i <= n + 1)
                    adjNodeAtLastRow--;
                //如果合法就链接
                //错误代码：adjNodeAtLastRow >= 1  这个是总index
                // if(adjNodeAtLastRow >= 1)
                if(i > n + 1 || myPositionAtThisRow > 1)
                    addEdge(currentNode, adjNodeAtLastRow);
                adjNodeAtLastRow++;
                if(i > n + 1 || myPositionAtThisRow < lineCnt)
                    addEdge(currentNode, adjNodeAtLastRow);
            }
            //还有横的边要连。 第一行也要连。
            if(j != 1)
                addEdge(currentNode, currentNode - 1);
            currentNode++;
        }
        prevLineCnt = lineCnt;
    }
    #ifndef NDEBUG
    for (size_t i = 1; i <= nodesForN(n); i++)
    {
        std::cout<<"node "<<i<<": ";
        const auto& l = adj[i];
        for (const auto& j:l)
            std::cout<<j<<" ";
        std::cout<<std::endl;
    }
    #endif // !NDEBUG
    std::cout << prim() << std::endl;
    return 0;
}
