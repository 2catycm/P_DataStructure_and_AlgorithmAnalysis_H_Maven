#include <iostream>
#include <vector>
// #include <map>
#include <algorithm>
#include <cassert>
#include <set>
using namespace std;
int main(int argc, const char **argv) {
    int N;
    cin >> N;
    assert(N >= 2);
    vector<int> nums(N);
    generate(nums.begin(), nums.end(), []() {
        int num;
        cin >> num;
        return num;// 函数式好玩，但是性能不好玩。
    });
    set<int> tree(std::initializer_list<int>{nums[nums.size() - 1]});
    vector<int> results(N - 1);
    for (int i = N - 2; i >= 0; --i) {
        auto num = nums[i];
        auto left = *tree.lower_bound(num);
        auto right = *tree.upper_bound(num);
        results[i] = min(abs(num - left), abs(num - right));
        tree.emplace(num);
    }
    auto print = [](const int &n) { std::cout << n << " "; };
    for_each(results.begin(), results.end(), print);
	cout<<endl;
    return 0;
}