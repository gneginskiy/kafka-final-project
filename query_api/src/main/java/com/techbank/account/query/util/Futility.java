package com.techbank.account.query.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class Futility {
    private static final Gson gson = new Gson();

    public <K, V> Map<K, V> mapOf(Object... keysAndValues) {
        if (keysAndValues.length % 2 != 0)
            throw new RuntimeException("No values for some of the keys, length: " + keysAndValues.length);
        var map = new HashMap<>();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put(keysAndValues[i], keysAndValues[i + 1]);
        }
        return (Map<K, V>) map;
    }

    @SneakyThrows
    public static void waitForSeconds(double seconds) {
        Thread.sleep((int) (seconds * 1000));
    }


    @SuppressWarnings("unchecked")
    public static <T1, T2> T2 ifNotNull(T1 src, Function<T1, T2> extractValueFunction, T2... defVal) {
        return src != null ? extractValueFunction.apply(src) : defVal == null || defVal.length == 0 ? null : defVal[0];
    }

    public static <T> T ifNull(T src, Supplier<T> supplier) {
        return src == null ? supplier.get() : src;
    }

    public static <T> T deepClone(T dto) {
        return (T) gson.fromJson(gson.toJson(dto), dto.getClass());
    }

    public static <T> T deepClone(Object dto, Class<T> clazz) {
        return gson.fromJson(gson.toJson(dto), clazz);
    }

    public static  <T> T fromJson(String json, Type tClass) {
        return gson.fromJson(json, tClass);
    }

    public static  <T> T fromJsonToCollection(String json, TypeToken<T> typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json,typeOfT);
    }

    public static <T> String toJson(T value) {
        return gson.toJson(value);
    }

    public static String toFtsIndex(Object entity) {
        var sb = new StringBuilder();
        String value = new GsonBuilder()
                .setFieldNamingStrategy(f -> sb.append("`").toString())
                .create()
                .toJson(entity)
                .replace("`", "");
        return StringUtils.substring(value,0,10200);
    }

    public static void tryToSet(Object target, String fieldName, Supplier<Object> valueSupplier) {
        try {
            FieldUtils.readField(target, fieldName, true); //will fail
            FieldUtils.writeField(target, fieldName, valueSupplier.get(), true);
        } catch (Exception ignored){}
    }

    public static boolean allEmpty(String... str) {
        return Arrays.stream(str).allMatch(StringUtils::isEmpty);
    }

    @SneakyThrows
    public static <T> Object readField(T probe, String fieldname) {
        return FieldUtils.readField(probe, fieldname, true);
    }
}
