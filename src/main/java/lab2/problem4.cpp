#include <iostream>
#include <vector>
using namespace std;
size_t num_count = 0;
size_t binary_search_index(vector<size_t>& vec, size_t start, size_t target){
    size_t left = start, right = vec.size(), mid;
    while (left<right)
    {
        mid = left+ (right-left)/2;
        if (vec[mid]==target)
            return mid;
        else if (vec[mid]<target)
            left = mid+1;    
        else
            right = mid;  
    }
    return -1;
}
size_t binary_search(vector<size_t>& vec, size_t start, size_t target){
    size_t index = binary_search_index(vec, start, target);
    if (index==-1) return 0;
    size_t result = 1;

    //想的过于简单，这里有可能重复：
    // 4 1 1 1 1
    //应该是6个
    // 第一个1 不应该算,所以应该最多到start

    //短路设计，防止数组越界
    for (size_t i = index+1; i<vec.size()&&vec[i]==target ; i++)//先运行第一句，然后进入循环前运行第二句，结束循环运行第三句
    {
        result++;
    }
    for (size_t i = index-1; i>=start&&vec[i]==target ; i--)
    {
        result++;
    }
    return result;
}
int main(int argc, char const *argv[])
{
    ios_base::sync_with_stdio(false);cin.tie(nullptr);cout.tie(nullptr);
    size_t n; cin>>n;
    // int* a = new int[n];     // array<int, n> 
    vector<size_t> vec_a;
    for (size_t i = 0, temp; i < n; i++) {
        cin >> temp;
        vec_a.push_back(temp);
    }

//    // 我和前面一样，那么我就不算了，我看看我和前面一不一样。
//    size_t previous_result;
    for (size_t i = 0; i < n-1; i++)
    {
    //搜索范围，最大加起来是2的28次方, 最小是一次方
    //想的太过简单：有可target<0怎么办
    //算法复杂度过高，需要减少枚举，需要判断一下可能性。
    // 如果我比temp的一半要大，那么后面不可能找到东西

//        if (i>0&&vec_a[i]==vec_a[i-1])
//        {
//            num_count+=previous_result;
//            for (size_t j = 1, temp=2; j <= 28; j++, temp<<=1)
//            {
//                if (vec_a[i]+vec_a[i-1]==temp)
//                 num_count--;
//            }
//            continue;
//        }
        for (size_t j = 1, temp=2; j <= 28; j++, temp<<=1)
        {
            if ((temp>>1)<vec_a[i])
                continue;
//            num_count+= (previous_result= binary_search(vec_a, i + 1, temp - vec_a[i]) );
            num_count+=  binary_search(vec_a, i + 1, temp - vec_a[i]) ;
        }
    }
    cout << num_count;
    return 0;
}
