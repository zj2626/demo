package com.demo.common.service.tools.stream;

import com.demo.common.service.tools.stream.entity.FromEntity;
import com.demo.common.service.tools.stream.entity.ToEntity;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
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
                new FromEntity("ay", 1.0, 7, "c"),
                new FromEntity("HHH", 9.0, 7, "c")
        );

        List<ToEntity> to = from.stream().map(fromEntity -> {
            ToEntity toEntity = ToEntity.builder()
                    .name(fromEntity.getName())
                    .score(fromEntity.getScore())
                    .age(fromEntity.getAge())
                    .type(fromEntity.getType())
                    .longSize(2L)
                    .price(BigDecimal.ONE)
                    .build();

            return toEntity;
        }).collect(Collectors.toList());

        System.out.println("###### 转换类型 打印");
        to.forEach(System.out::println);

        System.out.println("###### list数据 原生简单排序");
        to.sort(Comparator.comparing(ToEntity::getType));
        to.sort(Comparator.comparing(ToEntity::getType).reversed());
        to.sort(Comparator.comparing(ToEntity::getType).thenComparing(ToEntity::getName));
        to.sort(Comparator.comparing(ToEntity::getType).reversed().thenComparing(ToEntity::getName));

        System.out.println("###### list数据 排序");
        to = to.stream()
                .sorted(Comparator.comparing(ToEntity::getType).reversed())
                .collect(Collectors.toList());
        to.forEach(System.out::println);

        Map<String, ToEntity> map;
        System.out.println("###### list转HashMap 在没有重复key情况下");
        try{
            map = to.stream()
                    .collect(Collectors.toMap(
                            ToEntity::getType,
                            toEntity -> toEntity)
                    );
            System.out.println(map);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("###### list转HashMap 在有重复key情况下 (Key冲突, 引入一个合并函数,设定保留哪个条目)");
        // Function.identity() 就等同于 toEntity -> toEntity
        to.add(new ToEntity("eq", 1.0, 2, "c", 1L, BigDecimal.TEN));
        map = to.stream()
                .collect(Collectors.toMap(
                        ToEntity::getType,
                        Function.identity(),
                        (existing, replacement) -> replacement)
                );
        System.out.println(map);

        System.out.println("###### list转HashMap value为另一个属性");
        Map<String, Integer> mapTemp = to.stream()
                .collect(Collectors.toMap(
                        ToEntity::getType,
                        ToEntity::getAge,
                        (k1, k2) -> k1)
                );

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

        System.out.println("###### list数据 求和");
        System.out.println(to.stream().map(ToEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        System.out.println(to.stream().mapToDouble(ToEntity::getScore).sum());

        System.out.println("###### list数据 max");
        System.out.println(to.stream().max(Comparator.comparing(ToEntity::getPrice)).orElse(null));

        System.out.println("###### list数据 根据type分组,求和");
        Map<String, LongSummaryStatistics> volumesMap = to.stream()
                .collect(Collectors.groupingBy(ToEntity::getType, Collectors.summarizingLong(ToEntity::getLongSize)));
        volumesMap.forEach((k, v) -> {
            System.out.println(v.getCount());
            System.out.println(v.getMax());
            System.out.println(v.getMin());
        });

        Map<String, DoubleSummaryStatistics> volumesMap2 = to.stream()
                .collect(Collectors.groupingBy(ToEntity::getType, Collectors.summarizingDouble(ToEntity::getScore)));

        System.out.println("###### list数据 去重");
        System.out.println(to.size());
        to = to.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getType() + ":" + o.getAge()))), ArrayList::new)
        );
        System.out.println(to.size());
    }
}
