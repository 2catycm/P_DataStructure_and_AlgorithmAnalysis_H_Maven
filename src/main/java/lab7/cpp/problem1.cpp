#pragma GCC optimize(3, "Ofast", "inline")
#include <cstdio>
#include <array>
#include <vector>
// struct Node{
//     int weight;//父节点链接子节点的权重，记作子节点的。
//     int nodeId;
//     std::vector<Node*> childrens;
// };
struct Relation{
    int otherId;
    int weight;
};
#define MAX_LEN 500000
std::array<std::vector<Relation>, MAX_LEN+1> relations;
//实际使用的长度， 0,1...n
std::array<bool, MAX_LEN+1> putValues {false}; //辅助判断父子节点。

int correctCnt = 0, sum = 0;
int n, num;
void solve(int prevId = 0, int currId = 1){
    // if(relations[currId].size()==1){
    if(relations[currId].size()==0){
        if(sum==num)
            correctCnt++;
        return;
    }
    for (const auto& rel : relations[currId])
    {
        // if (rel.otherId==prevId) continue;
        sum += rel.weight;
        solve(currId, rel.otherId);
        sum -= rel.weight;
    }
}
int main(int argc, char const *argv[])
{
    scanf("%d%d", &n, &num);
    // Node* nodes = new Node[n+1];
    // delete[] nodes;
    putValues[1] = true;
    for (size_t i = 0; i < n-1; i++)
    {
        int u, v, w;
        scanf("%d%d%d", &u, &v,&w);
        if(putValues[u]){
            relations[u].push_back({v,w});
            putValues[v] = true;
        }else{
            relations[v].push_back({u,w});
            putValues[u] = true;
        }
    }
    //遍历relations， 通过vector的size判断是否为叶子节点。
    //如果是叶子节点， 往上找root。
    // for (size_t i = 1; i<=n; i++)
    // {
    //     const auto relation_vec = relations[i];
    //     if (relation_vec.size()!=1) continue;
    //     //?似乎判断不了。
    // }
    
    //从root开始搜索
    solve();
    // int sum = 0;
    // for (int prev = 0; ; )
    // {
    //     auto rel_vec = relations[prev+1];
        
    // }
    printf("%d\n", correctCnt);
    return 0;
}
