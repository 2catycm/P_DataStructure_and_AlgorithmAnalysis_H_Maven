#pragma GCC optimize(2)
#include <stdio.h>
#include <stdlib.h>
struct Node {
    int id;
    int weight;
    struct Node* next /* = NULL */;
    struct Node* previous/*  = NULL */;
};
struct Node* link_after(struct Node* ths, struct Node* node_after){
    ths->next = node_after;
    node_after->previous =ths;
    return node_after;
}
struct Node* searchLastNode(struct Node* ths){//传地址进来
    while(ths->next != NULL){
        ths = ths->next;
    }
    return ths;
}
struct Node* searchQNode(struct Node* ths, int q){
    for (size_t i = 0; i < q-1; i++) //第二大的，是0,1，只用advance一次
    {
        if (ths == NULL)
        {
            return NULL;
        }
        ths = ths->next;
    }
    return ths;
}
int main(int argc, const char** argv) {
    int n, p, q;
    scanf("%d %d %d", &n, &p, &q);
    struct Node *nodes = malloc(n*sizeof(struct Node));
    int newWeight;
    // struct Node newNode;
    for (size_t i = 0; i < n; i++)
    {
        scanf("%d", &newWeight);
        // newNode = {i+1, newWeight};
        // nodes[i] = newNode;
        // nodes[i] = struct Node{i+1, newWeight};
        nodes[i].id = i+1;
        nodes[i].weight = newWeight;
        nodes[i].next = NULL;
        nodes[i].previous = NULL;
    }
    int a, b; struct Node *lastNodeOfA;
    for (size_t i = 0; i < p; i++)
    {
        scanf("%d %d", &a, &b);
        lastNodeOfA = searchLastNode(&nodes[a-1]);
        link_after(lastNodeOfA, &nodes[b-1]);
    }
    for(struct Node* i = nodes; i<(nodes+n); i++){
        if (i->previous==NULL )
        {
            struct Node* q_node = searchQNode(i, q);
            if (q_node!=NULL)
            {
                printf("%d ", q_node->weight);
            }
        }
    }
    return 0;
}