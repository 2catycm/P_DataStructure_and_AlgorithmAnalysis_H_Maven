#pragma GCC optimize(3, "Ofast", "inline")
#include <iostream>
// #include <cstdlib>
#include <vector>
#include <stack>
#include <cmath>
using namespace std;
// long solve(int* arrayH, int l, int r);
long solve(vector<int> &arrayH, int l, int r);
void init(vector<int> &arrayH);
class IntDoublingSTTable
{
private:
    int **st;
    int st_r, st_c;
    vector<int> *source;

public:
    int indexOfMaxAmong(int startInclusive, int endExclusive)
    { //查询source[startInclusive-endExclusive] 中最大值的index。
        //区间长度在2的k次方和k+1次方之间（可取2的k次方，不可以取k+1次方），比如，2^2<=4<2^3;
        int k = (int)(log2(endExclusive - startInclusive));
        int leftHalfMaxIndex = st[startInclusive][k];
        int rightHalfMaxIndex = st[endExclusive - (1 << k)][k]; //比如，长度为5。 0 1 2 3 4    excluded:5. 应该为5-2^2=1开始的.
        if ((*source)[leftHalfMaxIndex] > (*source)[rightHalfMaxIndex])
            return leftHalfMaxIndex;
        else
            return rightHalfMaxIndex;
    }
    IntDoublingSTTable(vector<int> *source) : source(source)
    {
        int jMax = (int)(log2(source->size())); //我们不需要让每一个st[i]覆盖数组，而是要保证查询时需要且仅需要两个st[i][j]来定位。比如，长度为7，那么j只需要做到2, 2**2的长度足够了。
        st_r = source->size();
        st_c = jMax + 1;
        st = new int *[st_r]; //st的意义。 st[i][j] 表示source[i]开始，长度为2**j的区间中最大值在source中的下标。 jMax本身也要取到，所以+1
        for (int i = 0; i < st_r; i++)
        { //j=0的情况，每一个st[i]都可以很轻松的得到，因为区间长度为2**0=1, 有且只有一个元素。
            st[i] = new int[st_c];
            st[i][0] = i;
        }
        for (int j = 1; j <= jMax; j++)
        { //j=1-jMax的情况
            for (int i = 0; (i + (1 << j)) <= st_r; i++)
            { //比如，j=2， i=3， 数组是0 1 ... 6， 长度为7. 那么，i现在可以进去，下一次不可以进去。
                int leftHalfMaxIndex = st[i][j - 1];
                int rightHalfMaxIndex = st[i + (1 << (j - 1))][j - 1]; //比如，现在是i=0， j=2，   右边的一半，从2开始到3.从0跃迁到2，需要加上2的j-1次方。
                if ((*source)[leftHalfMaxIndex] > (*source)[rightHalfMaxIndex])
                    st[i][j] = leftHalfMaxIndex;
                else
                    st[i][j] = rightHalfMaxIndex;
            }
        }
    }
    ~IntDoublingSTTable()
    {
        for (int i = 0; i < st_r; i++)
        {
            delete st[i];
            st[i] = NULL;
        }
        delete st;
        st = NULL;
    }
};
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
int main(int argc, char const *argv[])
{
    int n = nextInt();
    int q = nextInt();
    // int* arrayH = (int*)calloc(n, sizeof(int));
    vector<int> arrayH(n);
    for (size_t i = 0; i < n; i++)
    {
        arrayH[i] = nextInt();
    }
    init(arrayH);
    for (size_t i = 0; i < q; i++)
    {
        int l = nextInt();
        int r = nextInt();
        cout << solve(arrayH, l, r) << endl;
    }
    // free(arrayH);
    return 0;
}
int *asLeftCount;
int *asRightCount;
IntDoublingSTTable *stTable;
void init(vector<int> &arrayH)
{
    stTable = new IntDoublingSTTable(&arrayH);
    asLeftCount = new int[arrayH.size()]{};
    asRightCount = new int[arrayH.size()]{};
    stack<int> rightCountStack;
    for (int i = 0; i < arrayH.size(); i++)
    {
        //right
        int temp = -1; //最后一个被打败的元素
        //7 4 4 3 2 4,  4打败3 2， 打败4，但是不再往前走了
        //不，不会有连续的4，因为是严格单调递减的，所以左边必定还会更大，所以继续走。
        while (!rightCountStack.empty() && arrayH[rightCountStack.top()] <= arrayH[i])
        {
            temp = rightCountStack.top();
            rightCountStack.pop();
            asRightCount[i]++;
        }
        if (!rightCountStack.empty() && (temp == -1 || arrayH[temp] != arrayH[i])) //弹掉不是因为相等的情况
            asRightCount[i]++;
        rightCountStack.push(i);
    }
    stack<int> leftCountStack;
    for (int i = arrayH.size() - 1; i >= 0; i--)
    {
        int temp = -1;
        while (!leftCountStack.empty() && arrayH[leftCountStack.top()] <= arrayH[i])
        {
            temp = leftCountStack.top();
            leftCountStack.pop();
            asLeftCount[i]++;
        }
        if (!leftCountStack.empty() && (temp == -1 || arrayH[temp] != arrayH[i]))
            asLeftCount[i]++;
        leftCountStack.push(i);
    }
}
long solve(vector<int> &arrayH, int l, int r)
{
    l--;
    r--; //从0开始是基本共识，国际社会不能为违背这一共识。
    int indexOfMax = stTable->indexOfMaxAmong(l, r + 1);
    long pairCount = 0;
    for (int i = l; i < indexOfMax; i++)
    {
        pairCount += asLeftCount[i];
    }
    for (int i = indexOfMax + 1; i <= r; i++)
    {
        //            pairCount+=asLeftCount[i];//写错了
        pairCount += asRightCount[i];
    }
    return pairCount;
}

