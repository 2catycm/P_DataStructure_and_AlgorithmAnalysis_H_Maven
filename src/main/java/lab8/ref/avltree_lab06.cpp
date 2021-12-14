//
// Created by zhangliyu on 2021/11/28.
//


#include <iostream>
#include "stack"
#include "algorithm"
#include "vector"

using namespace std;
class node{//竞赛通过修改源代码直接修改实现泛型,distinct保证
public:
    int data;
    int balance_factor;//1代表左边高
    int height;
    node * son_left;
    node * son_right;
    //node * parent;
    explicit node(int in_data){
        height=0;
        data=in_data;
        balance_factor=0;
        son_left= nullptr;
        son_right= nullptr;
        //parent= nullptr;
    }
};

class AVLTree{
public:
     node * root;
     AVLTree(){
         root= nullptr;
     }
     static int get_height(node * in){
         if(in== nullptr){
             return 0;
         } else{
             return in->height;
         }
     }
     static int get_balance(node * root){
         return get_height(root->son_left)- get_height(root->son_right);
     }
     /*void insert(node * in){//迭代在平衡扭转以上的高度是肯定不会变的，但是不是那么确定，先写递归
         node * current=root;
         if(current== nullptr){
             root=in;return;//在这一行就结束了
         }
         while (true){
             if(in->data > current->data){
                 if(current->son_right== nullptr){
                     current->son_right=in;break;
                 }else{ current = current->son_right;}

             }else if (in->data < current->data){
                 if(current->son_left== nullptr){
                     current->son_left=in;break;
                 } else{current = current->son_left;}
             }
         }
     }*/
     static node * ll_rotate(node * current_root){
        node * temp=current_root->son_left->son_right;
        node * temp_left_son=current_root->son_left;
        current_root->son_left=temp;
        temp_left_son->son_right=current_root;
        current_root->height=max(get_height(current_root->son_left), get_height(current_root->son_right))+1;
        temp_left_son->height=max(get_height(temp_left_son->son_left), get_height(temp_left_son->son_right))+1;
        return temp_left_son;

     }
     static node * rr_rotate(node * current_root){
         node * temp=current_root->son_right->son_left;
         node * temp_right_son=current_root->son_right;
         current_root->son_right=temp;
         temp_right_son->son_left=current_root;
         current_root->height=max(get_height(current_root->son_left), get_height(current_root->son_right))+1;
         temp_right_son->height=max(get_height(temp_right_son->son_left), get_height(temp_right_son->son_right))+1;
         return temp_right_son;
     }
     static node * lr_rotate(node * current_root){//有时间完成lr完全不靠ll和rr直接扭
         current_root->son_left= rr_rotate(current_root->son_left);
         return ll_rotate(current_root);

     }
     static node * rl_rotate(node * current_root){//有时间完成lr完全不靠ll和rr直接扭
         current_root->son_right= ll_rotate(current_root->son_right);
         return rr_rotate(current_root);
     }
     static node * insert(node * current,node * in){//这个不能返回空，你必须要知道parent才能rotate连上整个tree，有时间可以写写
         if(current== nullptr){
             current=in;
         }
         if(in->data > current->data){
//             if(current->son_right== nullptr){//这个还有要判断平衡的
//                 current->son_right=in;
//                 //in->parent=current;
//             }else{
                 current->son_right=insert(current->son_right,in);
//             }

         }else if (in->data < current->data){
//             if(current->son_left== nullptr){
//                 current->son_left=in;
//                 //in->parent=current;
//             } else{
                 current->son_left=insert(current->son_left,in);
//             }
         }
         //插入需要先更新高度
         current->height=max(get_height(current->son_left), get_height(current->son_right))+1;
         current->balance_factor= get_balance(current);//回到这一步，儿子的balance都计算过了
         if(current->balance_factor<=-2){

            if(current->son_right->data < in->data){
                current=rr_rotate(current);
            } else if(current->son_right->data >in->data){
                current= rl_rotate(current);
            }

         }else if(current->balance_factor>=2){

             if(current->son_left->data > in->data){
                 current= ll_rotate(current);
             } else if(current->son_left->data < in->data){
                 current= lr_rotate(current);
             }

         }
         //不用更新高度，rotate的时候更新了
         return current;
     }
     static node * delete_node(node * current,node * to_delete){//传进来都是根节点
         //但是这里只使用了data
         //递归的每次都要把替代的节点返回
         if(current== nullptr){
             return nullptr;
         }
        if(current->data < to_delete->data){//删除右边节点，和插入左节点相似
            current->son_right=delete_node(current->son_right,to_delete);
            if(get_height(current->son_left)- get_height(current->son_right)>=2){
                node * temp=current->son_left;
                if(get_height(temp->son_left) < get_height(temp->son_right)){
                    current=lr_rotate(current);
                } else{
                    current=ll_rotate(current);
                }
            }
        } else if(current->data > to_delete->data){
            current->son_left=delete_node(current->son_left,to_delete);
            if(get_height(current->son_right)- get_height(current->son_left)>=2){
                node * temp=current->son_right;
                if(get_height(temp->son_right) < get_height(temp->son_left)){
                    current=rl_rotate(current);
                } else{
                    current=rr_rotate(current);
                }
            }
        } else{
            if(current->son_left!= nullptr && current->son_right!= nullptr){
                if(get_height(current->son_left) > get_height(current->son_right)){
                    node * temp= find_max(current->son_left);
                    current->data=temp->data;
                    current->son_left=delete_node(current->son_left,temp);
                } else{
                    node * temp= find_min(current->son_right);
                    current->data=temp->data;//先把这个值改了,如果交换的话不是很清楚交换了啥，
                    current->son_right=delete_node(current->son_right,temp);
                }
            } else{
                //delete current;理论需要释放空间，但是这是竞赛
//                if(current->son_left){
//                    current=current->son_left;
//                } else{
//                    current=current->son_right;
//                }
                if(current->son_left== nullptr){
                    current=current->son_right;
                } else if(current->son_right== nullptr){
                    current=current->son_left;
                }
            }
        }
        if(current!= nullptr){//这个地方陈张杰没写就是对的，但是就是不清楚差别在哪里
            current->height=max(get_height(current->son_left), get_height(current->son_right))+1;
        }

         return current;
     }
     static node * find_min(node * root){//传进来不可能空指针
         node * to_return= root;
         while (to_return->son_left!= nullptr){
             to_return=to_return->son_left;
         }
         return to_return;

     }
    static node * find_max(node * root){//传进来不可能空指针
        node * to_return= root;
        while (to_return->son_right!= nullptr){
            to_return=to_return->son_right;
        }
        return to_return;

    }
    static node * pre_query(node * root,int target){//传回去data 最近的node,找前继最大的
         node * to_return= nullptr;
         int answer=INT32_MIN;
         node * current=root;
        while (current!= nullptr){
            if(current->data < target){
                if(current->data > answer){
                    to_return=current;answer=current->data;
                }
                current=current->son_right;
            }
            else if(current->data > target){
                current=current->son_left;
            }
            else{
                to_return=current;
                return to_return;
            }

        }
        if (to_return== nullptr){
            to_return=new node{INT32_MIN};
        }
        return to_return;
     }
    static node * successor_query(node * root,int target){//传回去data 最近的node
        node * to_return= nullptr;
        int answer=INT32_MAX;
        node * current=root;
        while (current!= nullptr){
            if(current->data < target){
                current=current->son_right;
            }
            else if(current->data > target){
                if(current->data < answer){
                    to_return=current;answer=current->data;
                }
                current=current->son_left;
            }
            else{
                to_return=current;
                return to_return;
            }

        }
        if (to_return== nullptr){
            to_return=new node{INT32_MAX};
        }
        return to_return;

    }
    static int find_closest_suc_pre(node *& root_in,int data){
//        long long bigger=successor_query(root_in,data)->data;
//        long long smaller= pre_query(root_in,data)->data;
//        return (int)min(abs(smaller-data), abs(bigger-data));
        node* bigger=successor_query(root_in,data);
        node * smaller=pre_query(root_in,data);
        long long  smaller_int=(long long)data-(long long)smaller->data;
        long long bigger_int=(long long)bigger->data-(long long)data;
        if( bigger_int < smaller_int ){
            root_in=delete_node(root_in,bigger);
            return (int)bigger_int;
        } else{
            root_in=delete_node(root_in,smaller);//优先删除小的
            return (int )smaller_int;
        }
     }
};
     /*static int find_closest(node * root_in,int data){//root不是空,都是把对象的根节点传进去
         node * to_return;
         node * current=root_in;
         while (true){
             if(data > current->data){
                 if(current->son_right== nullptr){
                     to_return=current;//                     delete_node(root_in,current);
                     break;
                 }
             } else{
                 if(current->son_left== nullptr){
                     to_return=current;//                     delete_node(root_in,current);
                     break;
                 }
             }
             if(current->son_right!= nullptr){
                 if(data > current->data && data< current->son_right->data){
                     if(data-current->data > current->son_right->data-data){
                         to_return=current->son_right;//delete_node(root_in,current->son_right);
                         break;
                     } else if(data-current->data < current->son_right->data-data) {
                         to_return=current;//delete_node(root_in,current);
                         break;
                     } else{
                         to_return=current;//delete_node(root_in,current);
                         break;
                     }
                 } else if( data>max(current->data,current->son_right->data)){
                     current=current->son_right;
                 }
             }

             if(current->son_left!= nullptr){

                 if(data < current->data && data> current->son_left->data){
                     if(current->data-data > data-current->son_left->data){
                         to_return=current->son_left;//delete_node(root_in,current->son_left);
                         break;
                     } else if(current->data-data < data-current->son_left->data) {
                         to_return=current;//delete_node(root_in,current);
                         break;
                     } else{
                         to_return=current->son_left;//delete_node(root_in,current->son_left);
                         break;
                     }
                 } else if(data <min(current->data,current->son_left->data)){
                     current=current->son_left;
                 }
             }

         }
         delete_node(root_in,to_return);
         return abs(to_return->data-data);
     }

};*/
int main(){
    int situation=0;//正数代表girl多
    int cases;
    auto * property= new vector<int>{};
    auto * money=new vector<int>{};
    auto * avltree=new AVLTree{};
    scanf("%d",&cases);
    property->resize(cases);
    money->resize(cases);

    for(int i=0;i<cases;i++){
        scanf("%d",&(*property)[i]);
        scanf("%d",&(*money)[i]);
    }
    long long answer=0;
    for(int i=0;i<cases;i++){

        if((*property)[i]==1){
            if(situation>=0){
                node * temp=new node{(*money)[i]};
                avltree->root=AVLTree::insert(avltree->root,temp);
                situation++;
            } else{
                answer+=AVLTree::find_closest_suc_pre(avltree->root,(*money)[i]);
                situation++;
            }
        } else{
            if(situation<=0){
                node * temp=new node{(*money)[i]};
                avltree->root=AVLTree::insert(avltree->root,temp);
                situation--;
            } else{
                answer+=AVLTree::find_closest_suc_pre(avltree->root,(*money)[i]);
                situation--;
            }
        }
    }
    printf("%lld\n",answer);

}
