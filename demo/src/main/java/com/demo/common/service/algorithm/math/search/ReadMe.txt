查找:
1.顺序查找: 从头到尾一个一个比较

2.折半查找: (二分法) 要求:必须采用顺序存储结构,数据必须有序

二叉查找树(二叉排序树):
    1.左子树所有结点都小于当前结点
    2.右子树所有结点都大于当前结点
    3.左右子树都是二叉查找树

平衡二叉树(AVL树):
    1.首先是二叉查找树
    2.每个结点的左右子树的层数相差不超过1

红黑树: (TreeSet, TreeMap)
    1.首先是平衡二叉树
    2.每个结点要么是黑色要么是红色
    3.根节点是黑色
    4.每个叶子结点(NIL)是黑色 (这里的叶子结点是为空的叶子结点)
    5.从一个结点到其子孙结点的所有路径上包含相同数目的黑结点

B树:
    1.平衡树 是多叉的

B+树:
    1.在B树的基础上,为叶子结点增加链表指针,所有的关键字都在叶子结点中,非叶子结点上存放叶子结点的索引

B*树:
    1.在B+树的基础上,在非根和非叶子结点上增加指向兄弟的指针

Hash表: (HashMap, HashSet, HashTable)
    1.由顺序表和链表组成,顺序表不存放数据只存放链表引用 (HashMap在1.8之后,当链表长度大于8时会把链表转化为红黑树)
    2.没有重复数据
    3.无序
    4.快
    负载因子: 表中记录数 / 哈希表的长度 (HashMap使用的是0.75)
    哈希函数: 除留余数法, 直接定址法, 平方取中法, 折叠法
    处理冲突: 链地址法, 开放地址法, 再散列法, 公共溢出区

