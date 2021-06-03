package com.demo.common.service.tools;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CopyUtils {
    public static <T> T copy(Object sorce, T target) {
        if (null == sorce) {
            return target;
        }

        BeanUtils.copyProperties(sorce, target);
        return target;
    }

    public static <T> List<T> listCopy(List<?> sourse, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sourse)) {
            return Collections.emptyList();
        }

        List<T> target = ObjectArrayUtil.listCopy(sourse, clazz);
        if (CollectionUtils.isEmpty(target)) {
            return Collections.emptyList();
        }
        return target;
    }

    static class ObjectArrayUtil {
        static <T> List<T> listCopy(List<?> sourceList, Class<T> target) {
            if (sourceList != null && !sourceList.isEmpty()) {
                ArrayList targetList = new ArrayList(sourceList.size());

                try {
                    for (Object o : sourceList) {
                        T targetObj = target.newInstance();
                        BeanUtils.copyProperties(o, targetObj);
                        targetList.add(targetObj);
                    }
                    return targetList;
                } catch (Exception var5) {
                    throw new RuntimeException(var5);
                }
            } else {
                return null;
            }
        }
    }
}
