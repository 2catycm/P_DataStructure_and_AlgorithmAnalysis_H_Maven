#include<iostream>
#include<vector>
#include<queue>
#include<cstring>

using namespace std;

const int N = 1000000007;

typedef long long ll;

struct Node{
    ll val;
    vector<Node*> edges;
    Node(): val(0), edges(vector<Node*>(0)){};
};

int main(){
    int T;
    scanf("%d", &T);
    for(int t = 0; t < T; t++){
        int n, m;
        scanf("%d%d", &n, &m);
        Node** nodes = new Node*[n + 1];

        int *degree = new int[n + 1];
        ll *f = new ll[n + 1];
        int * a = new int[n + 1];
        int * b = new int[n + 1];

        memset(a, 0, sizeof(int) * (n + 1));
        memset(b, 0, sizeof(int) * (n + 1));
        memset(degree, 0, sizeof(int) * (n + 1));
        memset(f, 0, sizeof(ll) * (n + 1));

        for(int i = 1 ; i <= n; i++){
            scanf("%d%d", &a[i], &b[i]);
        }

        for(int i = 1; i <= n + 1; i++){
            nodes[i] = new Node();
            nodes[i] -> val = i;
        }

        for(int i = 0; i < m; i++){
            int n1, n2;
            scanf("%d%d", &n1, &n2);
            nodes[n1] -> edges.push_back(nodes[n2]);
            degree[n2] ++;
        }
        queue<Node*> q;
        for(int i = 1; i <= n; i++){
            if(degree[i] == 0){
                q.push(nodes[i]);
            }
        }

        while(!q.empty()){
            int size = q.size();
            for(int i = 0; i < size; i++){
                Node* node = q.front();
                q.pop();
                int edge_size = node -> edges.size();
                for(int j = 0; j < edge_size; j++){
                    degree[node -> edges[j] -> val]--;
                    if(degree[node -> edges[j] -> val] == 0){
                        q.push(node -> edges[j]);
                    }

                    f[node -> edges[j] -> val] = (((f[node -> edges[j] -> val] + f[node -> val] % N) + a[node -> val]) % N);
                    f[node -> edges[j] -> val] %= N;

                }
            }
        }

        ll res = 0;
        for(int i = 1; i <= n; i++){
            res = (res + (f[i] * b[i] % N) % N);
            res %= N;
        }
        printf("%lld\n", res);


    }
}