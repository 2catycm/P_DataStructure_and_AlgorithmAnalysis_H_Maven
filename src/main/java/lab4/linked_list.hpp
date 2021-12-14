
#pragma once
//node.h
using namespace std;
using Rank = int; //秩. unsigned int 不行。  秩的差（区间长度）也是秩
template <typename T> struct bidirectional_node;
template <typename T> using b_node_pointer = bidirectional_node<T>*; //节点指针
template <typename T> 
struct bidirectional_node { 
// 公开成员，避免过度封装
   T value; b_node_pointer<T> previous_location; b_node_pointer<T> next_location; //数值、前驱、后继
// 构造函数
   bidirectional_node() {} //针对left_margin和right_margin的构造, 三空
   bidirectional_node ( T e, b_node_pointer<T> p = nullptr, b_node_pointer<T> s = nullptr ) 
      : value ( e ), previous_location ( p ), next_location ( s ) {} //显式的构造函数。
// 操作接口
   b_node_pointer<T> link_front  ( T const& newValue ); //紧靠当前节点之前插入新节点
   b_node_pointer<T> link_back   ( T const& newValue ); //紧随当前节点之后插入新节点
   //注意，这两个方法的职责归属应该是节点，而不是列表。列表负责排序、查找、迭代等，同时也传递出插入的操作。
};
//linked_list.h
template <typename T>
class linked_list { 
//私有成员
private:
//数据存储方式说明：left_margin和right_margin就像游标卡尺的两个针，中间卡着数据。无论数据是否为空，有多大，都是客观存在的。
    Rank size; b_node_pointer<T> left_margin; b_node_pointer<T> right_margin;//b_node_pointer要显式模板
//操作接口
public:
    linked_list();
    // linked_list ( linked_list<T> const& other); //拷贝构造函数
    ~linked_list();
    b_node_pointer<T> push_back(T const& newValue);
    b_node_pointer<T> push_front(T const& newValue);
    Rank size() const { return size;}
    bool empty() const { return size<=0;}
};
//node.cpp
template <typename T>
b_node_pointer<T> bidirectional_node<T>::link_front( T const& newValue ){
    b_node_pointer<T> newNode = new bidirectional_node<T>(newValue, this->previous_location, this);
    this->previous_location->next_location = newNode;
    this->previous_location = newNode;    
    return newNode;
}
template <typename T>
b_node_pointer<T> bidirectional_node<T>::link_back( T const& newValue ){
    b_node_pointer<T> newNode = new bidirectional_node<T>(newValue, this, this->next_location);
    this->next_location->previous_location = newNode;
    this->next_location = newNode;    
    return newNode;
}
//linked_list.cpp
template <typename T>
b_node_pointer<T> linked_list<T>::push_back(T const& newValue){
    
}