package util.syntax;

import java.util.Objects;

class ParentImplicitTree<Item>{
    public class PublicInner{
        public void message(){
            System.out.println("public inner class method is invoked.");
            System.out.println(ParentImplicitTree.this.it().toString());
        }
    }
    private class ParentImplicitTreeIterator<Item>{
        public void message(){
            System.out.println("private inner class method is invoked.");
        }

        @Override
        public String toString() {
            return "toString is accessible however.";
        }
    }
    public ParentImplicitTreeIterator<Item> it(){return new ParentImplicitTreeIterator<>();}
}
public class InnerClassTest {
    public static void main(String[] args) {
        final var integerParentImplicitTree = new ParentImplicitTree<Integer>();
        final var it = integerParentImplicitTree.it();
//        it.message();//java: ParentImplicitTreeIterator.message() 是在不可访问的类或接口中定义的
        System.out.println(it);//可以打印，很神奇
//        System.out.println(it.toString());//不，错误
        System.out.println(((Object) it).toString());
        final var publicInner = integerParentImplicitTree.new PublicInner();
        publicInner.message();
    }
}
