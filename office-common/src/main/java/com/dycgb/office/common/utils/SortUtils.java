package com.dycgb.office.common.utils;

import java.lang.reflect.Field;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 自定义排序工具类
 */
public class SortUtils {


    /**
     * 中文排序
     *
     * @param lists 待排序列表
     * @param field 排序字段
     * @param <K>
     * @return
     */
    public static <K> List<K> streamSortByChinese(List<K> lists, String field) {
        return lists.stream().sorted((k1, k2) -> {
            Collator collator = Collator.getInstance(Locale.CHINA);
            try {
                Field k1f = k1.getClass().getDeclaredField(field);
                Field k2f = k2.getClass().getDeclaredField(field);
                k1f.setAccessible(true);
                k2f.setAccessible(true);
                int res = collator.compare(k1f.get(k1), k2f.get(k2));
                k1f.setAccessible(false);
                k2f.setAccessible(false);
                return res;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            return 1;
        }).collect(Collectors.toList());
    }
}
