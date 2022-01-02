#pragma GCC optimize(3, "Ofast", "inline")
#include <array>
#include <cstdio>
#include <list>
#include <bitset>
#define MAX_LEN 1000000
#include <cstdio>
namespace oj
{
    inline int nextInt()
    {
        int x = 0, f = 1;
        char ch = getchar();
        while (ch < '0' || ch > '9')
        {
            if (ch == '-')
                f = -1;
            ch = getchar();
        }
        while (ch >= '0' && ch <= '9')
        {
            x = x * 10 + ch - 48;
            ch = getchar();
        }
        return x * f;
    }
}

std::array<std::list<int>, MAX_LEN+1> relations;
// std::array<bool, MAX_LEN+1> putValues {false}; //辅助判断父子节点。 //这得假定输入是按顺序来的。
std::bitset<MAX_LEN+1> isLeaves; 

void solve(int parent = 0, int currId = 1){
    if(relations[currId].size()==1&& relations[currId].front()==parent){
    // if(relations[currId].size()==0){
        isLeaves[currId] = true;
        return;
    }
    for (const auto& rel : relations[currId])
    {
        if (rel==parent)
                continue;
        solve(currId, rel);
    }
}
int main(int argc, char const *argv[])
{
    int n = oj::nextInt();
    // putValues[1] = true;
    for (size_t i = 0; i < n-1; i++)
    {
        int u = oj::nextInt();
        int v = oj::nextInt();
        // if(putValues[u]){
            relations[u].push_back(v);
            // putValues[v] = true;
        // }else{
            relations[v].push_back(u);
            // putValues[u] = true;
        // }
    }
    solve();
    for (size_t i = 1; i <= n; i++)
    {
        if(isLeaves[i])
            printf("%d ", i);
    }
    // for(size_t pos = isLeaves._Find_First(); pos < isLeaves.size(); pos = bs._Find_Next(pos)){
    
    // }
    return 0;
}

