#pragma GCC optimize(3, "Ofast", "inline")
//这题Java过不了是因为常数问题。都是O（n）。现在这个也是O（n）
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
#include <array>
#define MAX_LEN 1000000
std::array<int, MAX_LEN+1> counts {0};
int main(int argc, char const *argv[])
{
    int n = oj::nextInt();
    // putValues[1] = true;
    for (size_t i = 0; i < n-1; i++)
    {
        int u = oj::nextInt();
        int v = oj::nextInt();
        counts[u]++;
        counts[v]++;
    }
    for (size_t i = 1; i <= n; i++)
    {
        if(counts[i]==1)
            printf("%d ", i);
    }
    return 0;
}

