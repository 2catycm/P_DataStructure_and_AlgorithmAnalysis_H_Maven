#pragma GCC optimize(3, "Ofast", "inline")
#include <cstdio>
#include <cstring>
#include <vector>
#include <iostream>
using std::vector;
#define MAXLEN 2000001
// #define MAXLEN 100
// vector<int> buildNextArray(const char (&pattern)[MAXLEN]){
vector<int> buildNextArray(const char (*pattern)){
    std::vector<int> result;
    result.push_back(0);
    for (size_t i = 1, j = 0; pattern[i]!='\0';)
    {
        if (pattern[i] == pattern[j])
        {
            result.push_back(j+1);
            i++;j++;
        }else if (j==0){
            result.push_back(0);
            i++;
        }else{
            j = result[j-1];
        }
    }
    
    return result;//moving
}
int main(int argc, char const *argv[])
{
    // char buffer1[MAXLEN], buffer2[MAXLEN];//栈的大小是有限的。
    char* buffer1 = new char[MAXLEN];
    char* buffer2 = new char[MAXLEN];
    scanf("%s\n%s\n", buffer1, buffer2);
    int N = strlen(buffer1);
    int M = strlen(buffer2);
    if(N!=M){
        puts("No");
        return 0;
    }
    buffer1 = strcat(buffer1, buffer1);
    const auto nextArray = buildNextArray(buffer2);
    // std::cout << to_string(nextArray)<<std::endl;
    // for(const auto& i : nextArray){
    //     printf("%d ", i);
    // }
    for (size_t i = 0, j = 0; buffer1[i] !='\0';i++)
    {
        while (buffer1[i+j] !='\0' && buffer1[i+j] == buffer2[j]) j++;
        if(j==M){
            puts("Yes");
            return 0;
        }else{
            i+= j==0?0:nextArray[j-1];//然后再i++
            j = j==0?0:nextArray[j-1];
        }
    }
    puts("No");
    return 0;
}
