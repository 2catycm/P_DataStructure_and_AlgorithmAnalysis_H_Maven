#pragma GCC optimize(3, "Ofast", "inline")
#include <cstdio>
#include <vector>
#include <string>
#include <iostream>
namespace oj
{
    // #include <cstdio>//居然可以包起来
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
    template <typename T> inline
    void printlnVector(const std::vector<T>& vec, const std::string& elementSeparator = " "){
        for(const auto& v : vec){
            std::cout << v << elementSeparator;
        }
        std::cout<<std::endl;
    }
    template <typename T, std::size_t N> inline
    void printlnArray(const std::array<T, N>& vec, const std::string& elementSeparator = " "){
        for(const auto& v : vec){
            std::cout << v << elementSeparator;
        }
        std::cout<<std::endl;
    }
}
#include <array>
#include <list>
#include <vector>
#define MAX_LEN 5000
// #define MAX_LEN 6
std::array<std::list<int>, MAX_LEN+1> childrenOfNodes /* {{}} */;

// std::array<int, MAX_LEN+1> parentOfNodes;
// std::array<int, MAX_LEN+1> depthOfNodes;
// //根据children确定父子关系和深度
// void confirmInformation(int parent = 0, int current = 1, int depth = 0){
//     depthOfNodes[current] = depth;
//     parentOfNodes[current]= parent;
//     for (const auto& child : childrenOfNodes[current])
//     {
//         if(child==parent) continue;
//         confirmInformation(current, child, depth+1);
//     }
// }

std::array<std::vector<int>, MAX_LEN+1> pathOfNodes;
int leavesCnt = 0;
void pathInference(int parent = 0, int current = 1){
    for (const auto& int_path : pathOfNodes[parent])
    {
        pathOfNodes[current].push_back(int_path);
    }
    pathOfNodes[current].push_back(current);//把自己也加上去。
    if(childrenOfNodes[current].size()==1){
        leavesCnt++;
        return;
    }
    for (const auto& child :childrenOfNodes[current]){
        if(child==parent) continue;
        pathInference(current, child);
    }
}
int main(int argc, char const *argv[])
{
    int n = oj::nextInt();
    for (size_t i = 0; i < n-1; i++) //n-1个边，不是n个
    {
        int u = oj::nextInt();
        int v = oj::nextInt();
        childrenOfNodes[u].push_back(v);
        childrenOfNodes[v].push_back(u);
    }
    // confirmInformation();
    
    //每个叶子都会给出有且只有一次。
    //path可能太长，但是绝对不会太少
    pathInference();
    
    oj::printlnArray(pathOfNodes);
    exit(-1);


    std::vector<int> path {n<<1};
    int prevLeaf = 1 /* oj::nextInt() */; //先从1开始走
    // for(int currLeaf/* , k = 0 */;scanf("%d", &currLeaf)==1;/* k++, */ prevLeaf = currLeaf){
    for(int currLeaf, k = 0;k<leavesCnt;k++, prevLeaf = currLeaf){
        currLeaf = oj::nextInt();
        const auto& prevPath = pathOfNodes[prevLeaf];
        const auto& currPath = pathOfNodes[currLeaf];
        const int m = std::min(prevPath.size(), currPath.size());
        size_t leastCommonAncestorIndex = 0;//不是说从0开始编号，意思是list的下标
        for (; leastCommonAncestorIndex < m; leastCommonAncestorIndex++)
        {
            if(prevPath[leastCommonAncestorIndex]!=currPath[leastCommonAncestorIndex])
                break;
        }
        //现在leastCommonAncestorIndex是最近公共祖先的下一个
        //第一步，给定prevLeaf到达leastCommonAncestorIndex的路径。包括“root到公共祖先（就是root）没有路径” 这一情况。
        for (int i = prevPath.size()-1; i >= leastCommonAncestorIndex; i--)
        {
            path.push_back(prevPath[i]);
        }
        //第二步。刚才为了兼容根，没有吧最近公共祖先写上。现在从最近公共祖先开始，到达最下方。
        for (size_t i = leastCommonAncestorIndex-1; i < currPath.size(); i++)
        {
            path.push_back(currPath[i]);
        }
    }
    //最后，从最后一个得到的currLeaf（也就是prevLeaf）开始，加入回根路径。
    const auto& prevPath = pathOfNodes[prevLeaf];
    for (int i = prevPath.size()-1; i >= 0; i--) //size_t坑
    {
        path.push_back(prevPath[i]);
    }
    if(path.size()==(n<<1-1)){
        for (const auto& p:path)
        {
            printf("%d ", p);
        }
    }else{
        puts("-1");
    }

    getchar();
    return 0;
}
