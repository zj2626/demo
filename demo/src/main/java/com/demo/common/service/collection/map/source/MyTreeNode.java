package com.demo.common.service.collection.map.source;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;

public class MyTreeNode<K, V> extends MyNode<K, V> {
    MyTreeNode<K, V> parent;  // red-black tree links
    MyTreeNode<K, V> left;
    MyTreeNode<K, V> right;
    MyTreeNode<K, V> prev;

    MyTreeNode(int hash, K key, V value, MyNode<K, V> next) {
        super(hash, key, value, next);
    }

    public MyNode<K, V> getTreeNode(int hash, Object key) {
        return null;
    }

    final MyTreeNode<K, V> putTreeVal(HashMapDemo<K, V> map, MyNode<K, V>[] tab,
                                      int h, K k, V v) {
        return null;
    }

    /**
     * @see HashMap.TreeNode#split(java.util.HashMap, java.util.HashMap.Node[], int, int)
     */
    final void split(HashMapDemo<K,V> map, MyNode<K,V>[] tab, int index, int bit) {

    }

    final void treeify(MyNode<K, V>[] tab) {

    }

    public final MyTreeNode<K, V> getLeft()        { return left; }
    public final MyTreeNode<K, V> getRight()      { return right; }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}