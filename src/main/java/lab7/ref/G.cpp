#include <iostream>
#include <queue>
#include <vector>
#include <cstring>
using namespace std;

struct TreeNode
{
    int val;
    TreeNode *father;
    vector<TreeNode *> edges;
    TreeNode() : val(0), edges(vector<TreeNode *>()){};
};

int main()
{

// #ifndef NDEBUG
//     printf("-----DEBUG-----\n");
// #endif
    int n;
    scanf("%d", &n);

    TreeNode **nodes = new TreeNode *[n + 1];
    int *point = new int[n + 1];
    // for(int i = 0; i < n + 1; i++){
    //     cout << point[i] << " ";
    // }
    // cout << endl;
    int *other = new int[n + 1];
    // memset(point, 0, (n + 1) * sizeof(int));    
    // memset(other, 0, (n + 1) * sizeof(int));

    int *lmq_size(new int[n + 1]);

    for (int i = 1; i <= n; i++)
    {
        TreeNode *node = new TreeNode();
        node->val = i;
        nodes[i] = node;
    }

    for (int i = 0; i < n - 1; i++)
    {
        int num1, num2;
        scanf("%d%d", &num1, &num2);
        nodes[num1]->edges.push_back(nodes[num2]);
        nodes[num2]->edges.push_back(nodes[num1]);
    }
    int *visited = new int[n + 1];
    // memset(visited, 0, (n+1) * sizeof(int));
    int *edge_times = new int[n + 1];
    // memset(edge_times, 0, (n+1) * sizeof(int)); 

    queue<TreeNode *> q;
    queue<TreeNode *> queue_node;

    q.push(nodes[1]);
    nodes[1]->father = new TreeNode();
    nodes[1]->father->val = -1;
    visited[1] = 1;
    while (!q.empty())
    {
        int size = q.size();
        for (int i = 0; i < size; i++)
        {
            TreeNode *node = q.front();
            q.pop();
            int edge_size = node->edges.size();
            if (edge_size == 1 && node != nodes[1])
            {
                queue_node.push(node);
                edge_times[node->val] = 1;
            }
            for (int j = 0; j < edge_size; j++)
            {
                if (visited[node->edges[j]->val] == 0)
                {
                    q.push(nodes[node->edges[j]->val]);
                    visited[node->edges[j]->val] = 1;
                    nodes[node->edges[j]->val]->father = node;
                }
            }
        }
    }

    while (!queue_node.empty())
    {
        TreeNode *node = queue_node.front();
        queue_node.pop();
// #ifndef NDEBUG
//         printf("lmq_size[%d]=1\n", node->val);
// #endif
        lmq_size[node->val] = 1;
        if (node != nodes[1] && node->edges.size() == 1)
        {
            point[node->val] = node->val;
// #ifndef NDEBUG
//             printf("point[%d] = %d\n", node->val, node->val);
// #endif
        }
        else
        {
            TreeNode *great_subtree(nullptr);
// #ifndef NDEBUG
//             printf("Begin find the largest subtree of node %d. \n", node->val);
// #endif
            for (TreeNode *sub_node : node->edges)
            {
                if (sub_node == node->father)
                    continue;
                lmq_size[node->val] += lmq_size[sub_node->val];
                if (!great_subtree || lmq_size[great_subtree->val] < lmq_size[sub_node->val]) //寻找子树最大的节点使得子树最大
                    great_subtree = sub_node;
// #ifndef NDEBUG
//                 printf("[node%d]Now search the subnode %d, size=%d. \n",
//                        node->val, sub_node->val, lmq_size[sub_node->val]);
// #endif
            }
            if (lmq_size[great_subtree->val] * 2 < lmq_size[node->val])
            {
                point[node->val] = node->val; //自己就是自己的重心
// #ifndef NDEBUG
//                 printf("point[%d] = %d\n", node->val, node->val);
// #endif
            }
            else
            {

                TreeNode *great_point(nodes[point[great_subtree->val]]);
                while (lmq_size[great_point->val] * 2 < lmq_size[node->val])
                    great_point = great_point->father;
                if (lmq_size[great_point->val] * 2 == lmq_size[node->val])
                    other[node->val] = great_point->val, great_point = great_point->father;
                point[node->val] = great_point->val;
            }
        }
        if (node != nodes[1])
            edge_times[node->father->val] += 1;
        if (edge_times[node->father->val] == node->father->edges.size() - 1 && node->father->val != nodes[1]->val)
        {
        do_push:
            queue_node.push(node->father);
        }
        else if (node->father == nodes[1] && node->father->edges.size() == edge_times[1])
        {
            goto do_push;
        }
    }

    // for (int k = 1; k <= n; k++)
    // {
    //     printf("point[%d] = %d", k, point[k]);
    //     printf(" and other[%d] = %d\n", k, other[k]);
    // }

    for (int i = 1; i <= n; i++)
    {
        printf("%d", point[i]);
        if (other[i] != 0)
        {
            printf(" %d\n", other[i]);
        }
        else
        {
            printf("\n");
        }
    }
}