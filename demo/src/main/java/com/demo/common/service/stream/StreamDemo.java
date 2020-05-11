package com.demo.common.service.stream;

import com.demo.common.service.stream.entity.ToEntity;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.demo.common.service.stream.entity.FromEntity;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
    
    @Test
    public void testStream() {
        List<String> list = new ArrayList<>();
        list.add("AAC");
        list.add("AAC");
        list.add(new String("AAC"));
        list.add("KKKKKkk");
        list.add("aaaaaAA");
        list.add("aaaaAA");
        list.add("fffFF");
        list.add("444441");
        list.add("FFf");
        list.add("zzzzzZzZz");
        list.add("DDDDDdDdD");
        list.add("44444");
        list.add("hhhhhhhhhh");
        list.add("zzzzz");
        
        // 排序
//        Collections.sort(list, (o1, o2) -> o1.compareTo(o2));
//        System.out.println("#######################");
        
        System.out.println("############# 是否同一个String对象, 使用intern方法得到字符串常量池的引用");
        System.out.println(list.get(0) == list.get(1));
        System.out.println(list.get(0) == list.get(2));
        // intern()得到的是字符串常量池的引用,也就是list.get(0)或者list.get(1)的字符串对象
        System.out.println(list.get(0) == list.get(2).intern());
        System.out.println(list.get(2) == list.get(2).intern());
        
        System.out.println("############# 全部小写");
        list = list.stream().map(String::toLowerCase).collect(Collectors.toList());
        list.forEach(System.out::print);
        System.out.println();
        
        
        System.out.println("############# 尾部添加字符串");
        String[] array = {"aa", "BB", "cc"};
        Stream.of(array).map(item -> Strings.padEnd(item, 4, 's')).forEach(System.out::println);
        Stream.of(array).map(item -> Strings.padEnd(item, item.length() + 4, 's')).forEach(System.out::println);
        
        System.out.println("############# String数组");
        Function<Integer, String[]> fun = String[]::new;
        String[] strs = fun.apply(10);
        System.out.println(Arrays.toString(strs));
        System.out.println(strs.length);
        
        Function<Integer, String[]> fun1 = String[]::new;
        strs = fun1.apply(15);
        System.out.println(Arrays.toString(strs));
        System.out.println(strs.length);
    }
    
    @Test
    public void testStreamList() {
        List<FromEntity> from = Lists.newArrayList(
                new FromEntity("zj", 1.2, 5, "b"),
                new FromEntity("eq", 1.1, 2, "a"),
                new FromEntity("ay", 1.0, 7, "c")
        );
        
        List<ToEntity> to = from.stream().map(fromEntity -> {
            ToEntity toEntity = ToEntity.builder()
                    .name(fromEntity.getName())
                    .score(fromEntity.getScore())
                    .age(fromEntity.getAge())
                    .type(fromEntity.getType())
                    .build();
            
            return toEntity;
        }).collect(Collectors.toList());
        
        System.out.println("###### 转换类型 打印");
        to.forEach(System.out::println);
        
        System.out.println("###### list数据 排序");
        to = to.stream()
                .sorted(Comparator.comparing(ToEntity::getType).reversed())
                .collect(Collectors.toList());
        to.forEach(System.out::println);
        
        System.out.println("###### list转HashMap 在没有重复key情况下");
        Map<String, ToEntity> map = to.stream()
                .collect(Collectors.toMap(
                        ToEntity::getType,
                        toEntity -> toEntity)
                );
        System.out.println(map);
        
        System.out.println("###### list转HashMap 在有重复key情况下 (Key冲突, 引入一个合并函数,设定保留哪个条目)");
        // Function.identity() 就等同于 toEntity -> toEntity
        to.add(new ToEntity("eq", 1.0, 2, "c"));
        map = to.stream()
                .collect(Collectors.toMap(
                        ToEntity::getType,
                        Function.identity(),
                        (existing, replacement) -> replacement)
                );
        System.out.println(map);
        
        System.out.println("###### list转ConcurrentMap");
        map = to.stream()
                .collect(Collectors.toMap(
                        ToEntity::getType,
                        Function.identity(),
                        (existing, replacement) -> replacement,
                        ConcurrentHashMap::new)
                );
        System.out.println(map);
        
        System.out.println("###### list转TreeMap+排序");
        map = to.stream()
                .sorted(Comparator.comparing(ToEntity::getType))
                .collect(Collectors.toMap(
                        ToEntity::getType,
                        Function.identity(),
                        (existing, replacement) -> replacement,
                        TreeMap::new)
                );
        System.out.println(map);

        System.out.println("###### list数据 根据type分组");
        Map<String, List<ToEntity>> listMap = to.stream()
                .collect(Collectors.groupingBy(ToEntity::getType));
        System.out.println(listMap);

        System.out.println("###### set数据 根据type分组");
        Map<String, Set<ToEntity>> setMap = to.stream()
                .collect(Collectors.groupingBy(ToEntity::getType, Collectors.toSet()));
        System.out.println(setMap);

        System.out.println("###### set数据 根据type分组");
        Map<String, Set<Integer>> listAgeMap = to.stream()
                .collect(Collectors.groupingBy(ToEntity::getType, Collectors.mapping(ToEntity::getAge, Collectors.toSet())));
        System.out.println(listAgeMap);

        System.out.println("###### 数据 合计个数");
        Map<String, Long> countMap = to.stream()
                .collect(Collectors.groupingBy(ToEntity::getType, Collectors.counting()));
        System.out.println(countMap);

        System.out.println("###### list数据 根据type分组,然后根据name分组");
        Map<String, Map<String, List<ToEntity>>> mapMap = to.stream()
                .collect(Collectors.groupingBy(ToEntity::getType, Collectors.groupingBy(ToEntity::getName)));
        System.out.println(mapMap);

    }
}
