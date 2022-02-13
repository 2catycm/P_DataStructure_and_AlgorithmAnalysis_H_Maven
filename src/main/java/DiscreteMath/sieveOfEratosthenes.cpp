#pragma GCC optimize("Ofast")
#include <ctime>
#include <iostream>
#include <vector>
// solving:
// 1到n的质数数量，质数是只有1和自己是因子，且恰好有这两个因子的数，不包括1.
inline int solve(int n) {
  std::vector<bool> bitSet(n + 1);
  int count = 0, i = 2;
  for (; i <= n; i++)
    bitSet[i] = true; //首先假定所有非1数都是质数
  for (i = 2; i * i <= n;
       i++) { //一个合数可以被一个不超过它的平方根的素数整除。
    if (bitSet[i]) { //如果i是素数，检查一下后面所有数，会不会被发现是合数。
      count++;       //此时可以完全确定i是素数。
      for (int k = 2 * i; k <= n; k += i) { // k是1到n之间，i这个素数的倍数
        bitSet[k] = false;
      }
    }
  }
  //继续使用刚才的i，进入下一个阶段：计量根号n下界 以上的素数。
  for (; i <= n; i++) {
    if (bitSet[i])
      count++;
  }
  return count;
}
int main(int argc, char const *argv[]) {
  int n;
  std::cin >> n;
  clock_t cstart = clock();
  int result = solve(n); // c++中变量和函数不能重名，因为函数指针也是变量。
  clock_t cend = clock();
  std::cout << result << " primes" << std::endl;
  std::cout << (cend - cstart) << " milliseconds" << std::endl;
  return 0;
}
