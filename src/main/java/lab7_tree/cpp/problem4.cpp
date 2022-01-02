// #pragma GCC optimize(3, "Ofast", "inline")
#include <cstdio>
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
}
// #include <string>
// #include <iostream>
#include <cstring>
int count(char* preOrder, char* postOrder, int low, int high){//low 和 high都能取到
    if(low>=high) return 1;//只剩两个元素应该也是1，而且这两个元素一定是相反的
    if (preOrder[low]==postOrder[high])//独身子情况
        return count(preOrder, postOrder, low+1, high-1)<<1;//为孩子节点的子树的同分异构体数目的两倍
    
    // char* position = strchr(postOrder, )//还是自己写for吧
    char L = preOrder[low];
    size_t i;
    for (i = low+1; i < high; i++)//不可能到high
        if(postOrder[i]==L)
            break;
    return count(preOrder, postOrder, low, i)*count(preOrder, postOrder, i+1, high);
}
int main(int argc, char const *argv[])
{
    int T = oj::nextInt();
    char preOrder[27];
    char postOrder[27];//最多26个字母加一个\0
    for (size_t i = 0; i < T; i++)
    {
        scanf("%s\n%s", preOrder, postOrder);
        int n = strlen(preOrder);
        // printf("%d\n", count(preOrder, postOrder, 1, n-2));
        printf("%d\n", count(preOrder, postOrder, 0, n-1));
    }
    return 0;
}
