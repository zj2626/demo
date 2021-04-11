package com.demo.common.service.collection.map.source;


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

    final void split(HashMapDemo<K,V> map, MyNode<K,V>[] tab, int index, int bit) {

    }

    final void treeify(MyNode<K, V>[] tab) {

    }
}