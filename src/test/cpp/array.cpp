#include <cstdio>
struct MyStruct{
    MyStruct(){
        printf("constructor!\n");
        a = 1;
    }
    int a;
};
struct NoDefaultConstructor
{
    NoDefaultConstructor(int i){
        printf("constructor with int i!\n");
        this->i = i;
    }
    int i;
};

int main(int argc, char const *argv[])
{
    // int a[100];
    // for (size_t i = 0; i < 100; i++)
    // {
    //     printf("%d\n", a[i]);
    // }
    // MyStruct b[5];
    // for (size_t i = 0; i < 5; i++)
    // {
    //     printf("%d\n", b[i].a);
    // }
    NoDefaultConstructor c[5] = {1};
    for (size_t i = 0; i < 5; i++)
    {
        printf("%d\n", c[i].i);
    }
    int a[100]{1};
    
    return 0;
}
