#pragma GCC optimize(3, "Ofast", "inline")
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
#include <cmath>
#include <cstdint>
int main(int argc, char const *argv[])
{
    int T = oj::nextInt();
    for (size_t i = 0; i < T; i++)
    {
        double num;
        scanf("%lf", &num);
        // printf("%d", (int)log2(num)+1);//(int)log2(0)=int.min
        printf("%d\n", num==0?0:(int)floor(log2(num))+1); 
    }
    return 0;
}

//发现这题用c交比c++更慢。
//发现这题不用判断0其实。
