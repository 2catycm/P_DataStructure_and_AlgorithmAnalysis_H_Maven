package util.data_structure;

import java.util.ArrayList;

//维护当前窗口，保证有序、可以得知中位数、
// 可以去除其中的某个元素、加入某个元素（通过BinarySearch实现）
class OrderedArrayList<E extends Comparable<E>> extends ArrayList<E> {
    public boolean orderedRemove(E element){
        final int index = binarySearch(element, false);
        if (index==-1)return false;
        this.remove(index);
        return true;
    }
    public void orderedAdd(E element){ //原本的add保留，不过增加一种新的add
        final int index = binarySearch(element, true);
        this.add(index,element); //这个居然返回值是void，震惊了，为什么？
    }
    public E getMedian(){
        return this.get((this.size()-1)/2);
    }
    private int binarySearch(E target, boolean forInsertion){ //找到了
        int left = 0, right = size(), mid;//不取右边
        while (left < right) {
            mid = left + (right - left) / 2;
            final int comparison = this.get(mid).compareTo(target);
            if (comparison == 0)
                return mid;             //找到了就返回任何一个index（不需要区分第一个还是最后一个）
            else if (comparison < 0)
                left = mid + 1;
            else
                right = mid;
        }
        return forInsertion?left:-1;                      //找不到就返回一个合适的index
    }
}