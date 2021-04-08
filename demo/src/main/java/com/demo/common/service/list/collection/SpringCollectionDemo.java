package com.demo.common.service.list.collection;

import com.demo.common.service.list.abs.ListInfo;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

public class SpringCollectionDemo extends ListInfo {

    @Test
    public void test() {
        System.out.println("判断空:\t" + CollectionUtils.isEmpty(listA));
        System.out.println("判断空:\t" + CollectionUtils.isEmpty(listC));
        System.out.println("判断空:\t" + CollectionUtils.isEmpty(listD));
        System.out.println("判断空:\t" + CollectionUtils.isEmpty(listE));

        System.out.println("得到第一个:\t" + Optional.ofNullable(listA).map(a -> a.get(0)).orElse("default"));
        System.out.println("得到第一个:\t" + Optional.ofNullable(listC).map(a -> a.get(0)).orElse("default"));
        System.out.println("得到第一个:\t" + Optional.ofNullable(listD).filter(a -> !a.isEmpty()).map(a -> a.get(0)).orElse("default"));
        System.out.println("得到第一个:\t" + Optional.ofNullable(listE).filter(a -> !a.isEmpty()).map(a -> a.get(0)).orElse("default"));

        System.out.println("数组转List:\t" + CollectionUtils.arrayToList(arrayE));

        CollectionUtils.mergeArrayIntoCollection(arrayE, listB);
        System.out.println("数组加入List:\t" + listB);
    }

}
