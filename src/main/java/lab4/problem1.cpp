//
// Created by 叶璨铭 on 2021/10/3.
//
#pragma GCC optimize(2) //works both gcc and g++, except for some version of g++.
#include <iostream>
#include "linked_list.hpp"
using namespace std;
struct Term {
    int coefficient;
    int exponent;
};
linked_list<Term>* readTerms(int length){
        linked_list<Term>* terms = new linked_list<Term>;
        int n,m;
        for (int i = 0; i < length; i++) {
            cin>>n>>m;
            terms->push_back({n, m});
        }
        return terms;
    }
int main(){
    int n,m;cin>>n>>m;
    linked_list<Term>* list1 = readTerms(n);
    linked_list<Term>* list2 = readTerms(m);
    // cout<<list1.head()->value;
}
