#include <array>
#include <cmath>
#include <iostream>
#include <list>
constexpr int MAX = 3 * 200 * 200 + 3 * 200 + 1;
std::array<std::list<int>, MAX> adj;
inline void addEdge(int u, int v){
    adj[u].push_back(v);
    adj[v].push_back(u);
}
std::array<int, MAX> weights; //node有weight
inline long weightOfEdge(int u, int v){//简单图
    return long(u)*long(v);
}
int main(int argc, char const* argv[]) {
    int n;
    std::cin >> n;
    int prevLineCnt = 0; //上一行有多少个节点
    int currentNode = 1;//当前在输入图里的第几个节点
    for(size_t i = 1; i <= 2 * n - 1; i++) {
        int lineCnt = 2 * n + 1 - std::abs(i - n - 1);


        //不是第一行，触发连边。
        if(prevLineCnt != 0)  
        {
            
        }
        prevLineCnt = lineCnt;
    }

    return 0;
}
