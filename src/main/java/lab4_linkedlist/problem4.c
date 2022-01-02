#include <stdlib.h>
#include <stdio.h>
struct Node {
    int weight;
    struct Node* previous/*  = NULL */;
    struct Node* next /* = NULL */;
};
//} fake_head = {-1, NULL, NULL}, fake_tail = {-1, NULL, NULL};
struct Node* link_after(struct Node* ths, struct Node* node_after){
    ths->next = node_after;
    node_after->previous =ths;
    return node_after;
}
int nodes_comparing_weights(const void* a, const void* b)
{
    struct Node arg1 = *(const struct Node*)a;
    struct Node arg2 = *(const struct Node*)b;
	int w1 = arg1.weight;
	int w2 = arg2.weight;
	return (w1>w2) - (w1<w2);
}
int main(){
	int n;scanf("%d", &n);
	struct Node fake_head = {-1, NULL, NULL};
	struct Node *previous = &fake_head/* , *current */;
	struct Node* nodes = malloc(sizeof(struct Node)*n);
	for(int i=0;i<n;i++){
		//wrong
		// current = malloc(sizeof(struct Node));
		// scanf("%d", &current->weight);
		// nodes[i] = *(previous = link_after(previous, current));
		//fix:
		scanf("%d", &(nodes[i].weight));
		link_after(previous, &nodes[i]);
		nodes[i].next = NULL;//这句很重要，如果没有，后面遍历的时候就爆炸了
		previous = &nodes[i];
	}
	qsort(nodes,n,sizeof(struct Node), nodes_comparing_weights);
	for (struct Node* i = fake_head.next; i->next!=NULL; i = i->next)//遍历到倒数第二个。倒数第一格不进去
	{
		printf("%d ", (i+1)->weight - i->weight);
	}
	
	return 0;
} 
