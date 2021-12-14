//
// Created by zhangliyu on 2021/11/21.
//

//
// Created by zhangliyu on 2021/11/19.
//

//
// Created by zhangliyu on 2021/11/17.
//
#include"iostream"
#include "vector"
#include "stack"
#include "queue"
using namespace std;
class node{
public:
    int index;
    int path_depth;
    int size_of_subtree;

    vector<struct node *> *around;
    node * father;
    node * core_first;
    node * core_second;
    node(int num){
        index=num;
        around=new vector<node*>;
        path_depth=0;
        father= nullptr;
        size_of_subtree=0;
        core_first= nullptr;core_second= nullptr;
    }
};
bool find_balance(node * current_root,int all_elements, node * start_node){//答案一定在最重的子树的重心到
    auto * temp_queue=new queue<node *>;
    current_root->core_first=start_node;//先假设在这
    node * cur_buff=current_root->core_first;
    while (cur_buff!=current_root){
        temp_queue->push(cur_buff);
        cur_buff=cur_buff->father;
    }
    temp_queue->push(current_root);//这个为了对冲最后一个入队的没法被检验

    while (!temp_queue->empty()){
        int size_left=current_root->core_first->size_of_subtree;//left是当前已经平衡的
        int size_right=all_elements-size_left;//
        if(size_left>size_right){
            return true;

        }else if(size_left < size_right){
            current_root->core_first=temp_queue->front();
            temp_queue->pop();
        } else {
            current_root->core_second=temp_queue->front();
            return true;
        }
    }
    return false;
}
void find_core(node * current_root){
    for(int i=0;i<current_root->around->size();i++){
        if(current_root->around->at(i)!=current_root->father){
            find_core(current_root->around->at(i));
        }
    }
    if(current_root->around->size()==1){
        current_root->core_first=current_root;
    }else{
        int first_not_father;
        for(int i=0;i<current_root->around->size();i++){
            if(current_root->around->at(i)!=current_root->father){
                first_not_father=i;
                break;
            }
        }
        node * max_size_subtree=current_root->around->at(first_not_father);
        for(int i=0;i<current_root->around->size();i++){
            if(current_root->around->at(i)!=current_root->father
            && current_root->around->at(i)->size_of_subtree > max_size_subtree->size_of_subtree){
                max_size_subtree=current_root->around->at(i);
            }
        }
        //if(max_size_subtree->size_of_subtree*2>)
        current_root->core_first=max_size_subtree->core_first;//先是已经假设在某一个树根
        while (true){
            if(current_root->core_first==current_root){
                break;
            }

            int size_left=current_root->core_first->size_of_subtree;
            int size_right=current_root->size_of_subtree-size_left;//1代表先把根节点加上
            if(size_left>size_right){
                break;
            }else if(size_left < size_right){
                current_root->core_first=current_root->core_first->father;
            } else {
                current_root->core_second=current_root->core_first->father;
                break;
            }
        }


    }


}

int find_size_of_subtree(node* current_root){//深搜找根节点路径,也可以像dongtingzhen那样传输father进去
    if(current_root->around->size()==1){
        current_root->size_of_subtree=1;
    }else{
        for(int i=0;i<current_root->around->size();i++){
            if(current_root->around->at(i)!=current_root->father){
                current_root->around->at(i)->father=current_root;
                current_root->size_of_subtree+= find_size_of_subtree(current_root->around->at(i));
            }
        }
        current_root->size_of_subtree+=1;
    }
    return current_root->size_of_subtree;
}
int main(){
    int num_nodes;
    scanf("%d",&num_nodes);
    auto * node_vector=new vector<node*>;
    node_vector->resize(num_nodes+4);
    for(int i=0;i<=num_nodes;i++){
        (*node_vector)[i]=new node{i};
    }
    int left,right;
    for(int i=0;i<num_nodes-1;i++){
        scanf("%d %d",&left,&right);
        (*node_vector)[left]->around->push_back((*node_vector)[right]);
        (*node_vector)[right]->around->push_back((*node_vector)[left]);
    }
    (*node_vector)[1]->father=(*node_vector)[0];
    (*node_vector)[1]->around->push_back((*node_vector)[0]);//防止第一个直接返回
    find_size_of_subtree(node_vector->at(1));
    find_core(node_vector->at(1));
    for(int i=1;i<=num_nodes;i++){
        if(node_vector->at(i)->core_second!= nullptr){
            printf("%d %d\n",min(node_vector->at(i)->core_first->index,node_vector->at(i)->core_second->index)
                   ,max(node_vector->at(i)->core_first->index,node_vector->at(i)->core_second->index));
        }else{
            printf("%d\n",node_vector->at(i)->core_first->index);
        }
    }

}

