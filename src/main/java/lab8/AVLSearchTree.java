package lab8;

import java.util.Objects;
import java.util.function.Predicate;

class AVLSearchTree<Key extends Comparable<Key>, Value> extends BinarySearchTree<Key, Value> {
    @Override
    protected Predicate<Entry<Key, Value>> getLegalTreeEntryCondition() {
        return super.getLegalTreeEntryCondition().and(entry->Math.abs(unbalanceFactor(entry)) <= 1);
    }

    @Override
    protected Entry<Key, Value> removeEntry(Entry<Key, Value> entry) {
        if (entry.left!=null){
            if (entry.right!=null){ //左右孩子都有
                final var successorEntry = getSuccessorEntry(entry.right, entry.key);
                assert (successorEntry.left==null); //由后继的性质可得。
                assert (heightOf(successorEntry.right)<=0); //由平衡树性质可得。
                entryValueCopy(successorEntry, entry);
                return removeEntry(successorEntry);//^//这时候predecessor.right要不是一个null，要不是一个叶子（新版的定义，null也是叶子，可以被removeLeaf方法删除），直接删除。
            } else{//有且只有左孩子。
                assert (isLeaf(entry.left)); //由avl树的性质可得。
                entryValueCopy(entry.left, entry);
                return removeLeaf(entry.left); //which must be a leaf.
            }
        }else if(entry.right!=null){ //有且只有右孩子。
            assert (isLeaf(entry.right)); //由avl树的性质可得。
            entryValueCopy(entry.right, entry);
            return removeLeaf(entry.right); //which must be a leaf.
        }else{
            assert (isLeaf(entry)); //叶子节点， base case。 //! 这是方法的核心， 需要对树调平衡。
            return removeLeaf(entry);
        }
    }
    protected Entry<Key, Value> removeLeaf(Entry<Key, Value> entry){
        if (entry==null)
            return null; //递归调用的时候可能遇到null.
        assert (isLeaf(entry));
        assert (isLegalTree()); //只有这个remove会改变树的结构（链接、高度、大小、旋转）, 前面通过巧妙的变换，让删除只在叶子上进行。
        if (entry.parent==null){ //删除root节点的情况
            assert (entry==root); //只有root的parent可以是null。
            return root = null;
        }
        //正常情况。
        if (entry.parent.left==entry) {
            entry.parent.left = null;
        }
        else{
            assert (entry.parent.right==entry);
            entry.parent.right = null;
        }
        //avl树的维护。
        pathToRootBalance((Entry<Key, Value>) entry);
        //收尾工作
        entry.parent = null; //防止给出去之后被外界修改了树的结构。
        return entry; //还没有真的成为孤儿，再成为孤儿之前把值给出去，如果没有人领养，就真的是垃圾回收掉了。
    }



    @Override
    protected Entry<Key, Value> putEntry(Entry<Key, Value> subTree, Key key, Value value) {
        final var putLocation = super.putEntry(subTree, key, value);
        if (putLocation==noNewNodeCreated) {
            assert(isLegalTree());
            return putLocation; //不需要改树的结构。
        }
        pathToRootBalance((Entry<Key, Value>) putLocation);
        return putLocation;
    }

    /**
     * @param subTree
     * @return 返回左边减去右边。大于零表示左高。
     */
    private int unbalanceFactor(Entry<Key, Value> subTree){
        return heightOf(subTree.left) - heightOf(subTree.right);
    }

    private void pathToRootBalance(Entry<Key, Value> entry) {
        for(var upIt = entry.parent; upIt!=null; upIt = upIt.parent){
            //先更新这一层高度，再说平不平衡。
            upIt.size = newSizeOf(upIt);
            upIt.height = newHeightOf(upIt);
            if (Math.abs(unbalanceFactor(upIt))<=1)
                continue;
            balance(upIt);
            assert(Math.abs(unbalanceFactor(upIt))<=1);
            assert(upIt.parent==null || (upIt.parent.left==upIt || upIt.parent.right==upIt) );
        }
        assert(isLegalTree());
    }
    /**
     * // deprecated: 返回新的子树, 对外保证，新的子树是平衡的，而且每个点的高度和大小都更新好了。
     * @param subTree
     */
    private /*Entry<Key, Value>*/ void balance(Entry<Key, Value> subTree) {
        final var subTreeFactor = unbalanceFactor(subTree);
        if (subTreeFactor==2){ //左边h+2,右边h的情况
            final var leftFactor = unbalanceFactor(subTree.left);
            if (leftFactor==-1){ //右边h+1，左边h的情况
//                subTree.left = rotateLeft(subTree.left); //rotateLeft方法没有修改parent对他们新的子树根的指向。所以这个赋值是很有必要的。
//                subTree = rotateRight(subTree);
//                return subTree;
                rotateLeft(subTree.left);
                rotateRight(subTree);
            }else {
                assert (leftFactor==1 || leftFactor==0); //左边是h+1，右边h+1或者h。
//                subTree = rotateRight(subTree); //对subTree节点右旋即可修正。 得到了新的x，目的是返回x。
//                return subTree;
                rotateRight(subTree);
            }
//            throw new RuntimeException("SubTree of the first unbalance tree is also unbalanced, which contradicts the presumption that it is the first. ");
        }else /*if (subTreeFactor<-1)*/{
            assert (subTreeFactor==-2);
            final var rightFactor = unbalanceFactor(subTree.right);
            if (rightFactor==1){ //左边h+1，右边h的情况
                /*subTree.right = rotateRight(subTree.right); //rotateLeft方法没有修改parent对他们新的子树根的指向。所以这个赋值是很有必要的。
                subTree = rotateLeft(subTree);*/
//                return subTree;
                rotateRight(subTree.right);
                rotateLeft(subTree);
            }else {
                assert (rightFactor==-1 || rightFactor==0); //右边是h+1，左边h+1或者h。
//                subTree = rotateLeft(subTree); //对subTree节点左旋即可修正。 得到了新的x，目的是返回x。
//                return subTree;
                rotateLeft(subTree);
            }
        }
//        throw new RuntimeException("Balance factor has been to big to be balanced. ");
    }

    /**
     * 重任之方法，国之重器。
     * 1.为了防止父子关系混乱，我们决定原本的upRight，作为链接父之工具，不改变其地址指向，没有破坏性赋值。（独家方法）
     * 2.这个方法还负责旋转之后，子树的高度。
     * @param upRight
     * @return
     */
    private /*Entry<Key, Value>*/ void rotateRight(Entry<Key, Value> upRight) {
        assert (Objects.nonNull(upRight));
        //生成新的节点，复制upRight.
        final var downLeft = upRight.left;
        final var newDownRightLeft = downLeft.right; //B
        final var newDownRightRight = upRight.right; //C
        //更新upRight节点为"upLeft"节点。
        upRight.right = copyUpsideDown((Entry<Key, Value>) upRight, (Entry<Key, Value>) newDownRightLeft, (Entry<Key, Value>) newDownRightRight); //拉拢刚才创建的新节点


        entryValueCopy(downLeft, upRight);
        //这一步删除很容易漏。相当于双向链表的删除，两边都要删。
        if(downLeft.left!=null)
            downLeft.left.parent = upRight;
        upRight.left = downLeft.left; //隐含的让左下节点被垃圾回收。
        //链接建完了，不能忘了高度和size的更新。
        upRight.height = newHeightOf(upRight);
        upRight.size = newSizeOf(upRight);
        //upRight唯一没有改的就是和父亲节点的相互链接关系。 父亲节点的size不会更新，
        // 但是父亲节点的高度发生更新：
        if(upRight.parent!=null)
            upRight.parent.height = newHeightOf(upRight.parent);
    }

    private void entryValueCopy(Entry<Key, Value> src, Entry<Key, Value> dst){
        dst.key = src.key;
        dst.value = src.value;
    }
    private Entry<Key, Value> copyUpsideDown(Entry<Key, Value> upRight, Entry<Key, Value> newDownRightLeft, Entry<Key, Value> newDownRightRight) {
        final var newDownRight = new Entry<Key, Value>(upRight.key, upRight.value, newDownRightLeft, newDownRightRight, upRight
                , sizeOf(newDownRightLeft) + sizeOf(newDownRightRight) + 1, Math.max(heightOf(newDownRightLeft), heightOf(newDownRightRight)) + 1);
        if (parentOf(newDownRightLeft)!=null)
            newDownRightLeft.parent = newDownRight;
        if (parentOf(newDownRightRight)!=null)
            newDownRightRight.parent = newDownRight;//这两步很容易漏了。
        return newDownRight;
    }

    private /*Entry<Key, Value>*/ void rotateLeft(Entry<Key, Value> upLeft) {
        assert (Objects.nonNull(upLeft)); //a
        //生成新的节点，复制upLeft.
        final var downRight = upLeft.right; //b
        final var newDownLeftLeft = upLeft.left; //A
        final var newDownLeftRight = downRight.left; //B
        //更新 upLeft 节点为"upRight"节点。
        upLeft.left = copyUpsideDown((Entry<Key, Value>) upLeft, (Entry<Key, Value>) newDownLeftLeft, (Entry<Key, Value>) newDownLeftRight); //拉拢刚才创建的新节点
        entryValueCopy(downRight, upLeft);
        //这一步删除很容易漏。相当于双向链表的删除，两边都要删。
        if(downRight.right!=null)
            downRight.right.parent = upLeft;
        upLeft.right = downRight.right; //隐含的让 downRight 节点被垃圾回收。
        //链接建完了，不能忘了高度和size的更新。
        upLeft.height = newHeightOf(upLeft);
        upLeft.size = newSizeOf(upLeft);
        //upLeft 唯一没有改的就是和父亲节点的相互链接关系。 父亲节点的size不会更新，
        // 但是父亲节点的高度发生更新：
        if(upLeft.parent!=null)
            upLeft.parent.height = newHeightOf(upLeft.parent);
    }
}
