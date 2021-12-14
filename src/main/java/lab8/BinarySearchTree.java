package lab8;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Predicate;

class BinarySearchTree<Key extends Comparable<Key>, Value> extends AbstractBinarySearchTree<Key, Value> {
    @Override
    protected Predicate<Entry<Key, Value>> getLegalTreeEntryCondition() {
        return entry ->
                (entry.parent == null || (entry.parent.left == entry || entry.parent.right == entry))
                        && newSizeOf(entry) == sizeOf(entry)
                        && newHeightOf(entry) == heightOf(entry);
    }

    @Override
    protected Entry<Key, Value> removeEntry(Entry<Key, Value> entry) {
        assert (entry!=null);
        return putEntry(entry, entry.key, null); //TODO, 先去写AVL树的删除。BST的放过。
    }

    @Override
    /**
     *     这个方法的语义： 从一个非空子树（null不是合法的子树，但是leaf是合法的子树）开始，把key, value放在子树合适的位置，
     *     并且把这个位置返回。
     *     注意，返回的是位置，以便于操作。
     *     这里有一部分是为了avl树的继承。avl树得到位置之后，可以进行平衡。或者，avl树直接重新写。
     */
    protected Entry<Key, Value> putEntry(Entry<Key, Value> subTree, Key key, Value value) {
        assert(subTree!=null);
        for (var curr = subTree;curr!=null;){
            final var comparison = curr.key.compareTo(key);
            if (comparison>0){//tree node curr has bigger key than the to be put key.
                //情况1， 左边就是要放的位置了
                if (curr.left==null){
                    //第一步
                    curr.left = new Entry<>(key, value, null, null, curr, 1, 0);
                    //第二步，上溯修改高度和大小。
//                    for (var upIt = curr;upIt!=null;upIt = upIt.parent){
//                        upIt.size = sizeOf(upIt.left) + sizeOf(upIt.right) + 1;
//                        upIt.height = Math.max(heightOf(upIt.left), heightOf(upIt.right))+1;
//                    }
                    return curr.left;//返回新的叶子的位置，以便平衡。
                }
                //情况2， 需要在左子树进行进一步判断。
                curr = curr.left;
            }else if(comparison<0){
                //情况1， 右边就是要放的位置了
                if (curr.right==null){
                    //第一步
                    curr.right = new Entry<>(key, value, null, null, curr, 1, 0);
                    //第二步，上溯修改高度和大小。
//                    for (var upIt = curr;upIt!=null;upIt = upIt.parent){
//                        upIt.size = sizeOf(upIt.left) + sizeOf(upIt.right) + 1;
//                        upIt.height = Math.max(heightOf(upIt.left), heightOf(upIt.right))+1;
//                    }
                    return curr.right;//返回新的叶子的位置，以便平衡。
                }
                //情况2， 需要在右子树进行进一步判断。
                curr = curr.right;

            }else /*if(comparison==0)*/{
                //只需要更新值，不需要修改树的结构。
                curr.value =  value;
                //                return curr;
                noNewNodeCreated.key = curr.key;
                noNewNodeCreated.value = curr.value;
                return noNewNodeCreated;
            }
        }
        throw new AssertionError("putEntry() method: must not reach here.");
    }

    @Override
    protected Entry<Key, Value> getEntry(Entry<Key, Value> subTree, Key key) {
        assert (Objects.nonNull(subTree));
        for (var curr = subTree; curr!=null;){
            final var comparison = curr.key.compareTo(key);
            if (comparison<0) //当前节点的key太小了
                curr = curr.right; //去右边找key
            else if (comparison>0)
                curr = curr.left;
            else
                return curr;
        }
        return null;//没有找到。对于删除来说（调用了get），没有找到就不删了。
    }

    @Override
    protected Entry<Key, Value> getPredecessorEntry(Entry<Key, Value> subTree, Key key) {
        assert (Objects.nonNull(subTree));
        Entry<Key, Value> answer = null;//注意，不能设置为subTree。 subTree未必是后继。没有后继就是返回null。
        for (var curr = subTree; curr!=null; ){ //如果碰到null，那说明没法查找了，那就返回存过的answer。
            final var comparison = curr.key.compareTo(key);
            if (comparison>0) {//当前节点的key太大了。
                //我现在要找前继，那curr这么大绝对不可能是前继。
                curr = curr.left;
            } else if (comparison<0) {
                //虽然有可能有更大更接近key的前继，但是也不能忘了我哦
                answer = curr;
                curr = curr.right;
            } else
                return curr; //我到了，我就是答案。前面的answer都是渣渣。
        }
        return answer;
    }

    @Override
    protected Entry<Key, Value> getSuccessorEntry(Entry<Key, Value> subTree, Key key) {
        assert (Objects.nonNull(subTree));
        Entry<Key, Value> answer = null;//注意，不能设置为subTree。 subTree未必是后继。没有后继就是返回null。
        for (var curr = subTree; curr!=null; ){ //如果碰到null，那说明没法查找了，那就返回存过的answer。
            final var comparison = curr.key.compareTo(key);
            if (comparison<0) {//当前节点的key太小了。
                //我现在要找后继，那curr绝对不可能是后继。
                curr = curr.right; //去右边找后继咯。
            } else if (comparison>0) {
                //虽然有可能有更小的后继，但是也不能忘了我哦
                answer = curr;
                curr = curr.left;
            } else
                return curr; //我到了，我就是答案。前面的answer都是渣渣。
        }
        return answer;
    }

    @Override
    protected Entry<Key, Value> minEntry(Entry<Key, Value> subTree) {
        assert (Objects.nonNull(subTree));
        Entry<Key, Value> curr = subTree;
        while (curr.left!=null){
            curr = curr.left;
        }
        return curr;
    }

    @Override
    protected Entry<Key, Value> maxEntry(Entry<Key, Value> subTree) {
        assert (Objects.nonNull(subTree));
        Entry<Key, Value> curr = subTree;
        while (curr.right!=null){
            curr = curr.right;
        }
        return curr;
    }
}
