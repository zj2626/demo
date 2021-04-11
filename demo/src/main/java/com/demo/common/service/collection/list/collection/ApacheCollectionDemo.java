package com.demo.common.service.collection.list.collection;

import com.demo.common.service.collection.list.abs.ListInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

public class ApacheCollectionDemo extends ListInfo {

    @Test
    public void test() {
        System.out.println(CollectionUtils.isNotEmpty(listA));
        System.out.println(CollectionUtils.isSubCollection(listA, listF));
    }
}
