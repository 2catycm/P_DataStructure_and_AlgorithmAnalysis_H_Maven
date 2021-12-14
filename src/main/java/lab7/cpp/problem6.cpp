#pragma GCC optimize(3, "Ofast", "inline")
#include <bits/stdc++.h>
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
    inline std::vector<int> nextIntArray(int size){
        std::vector<int> result;
        for (size_t i = 0; i < size; i++)
        {
            result.push_back(nextInt());
        }
        return result;
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

int main(int argc, char const *argv[])
{
    int T = oj::nextInt();
        for (int i = 0; i < T; i++) {
            int n = oj::nextInt();
            int k = oj::nextInt();
            if (k == 1) {
                printf("0\n");
                continue;
            }
            
            vector<int> friends = oj::nextIntArray(k);
        }
    return 0;
}
