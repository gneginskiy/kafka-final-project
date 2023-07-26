package com.techbank.account.query.util;

import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.*;

public class ReflectHelper {

    @SneakyThrows
    public static StringPath getStringPath(String fieldname) {
        var constructor = (Constructor<StringPath>) StringPath.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        return constructor.newInstance(fieldname);
    }

    @SneakyThrows
    public static ComparablePath getComparablePath(Class clazz, String fieldname) {
        var constructor = (Constructor<ComparablePath>) ComparablePath.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        return constructor.newInstance(clazz,fieldname);
    }

    public static Class<?> getEntityClass(JpaRepository<?,?> repo) {
        Type clazzes = getGenericType(repo.getClass())[0];
        Type[] jpaClass = getGenericType(getClass(clazzes));
        return getClass(((ParameterizedType) jpaClass[0]).getActualTypeArguments()[0]);
    }

    private static Type[] getGenericType(Class<?> target) {
        if (target == null) return new Type[0];

        Type[] types = target.getGenericInterfaces();
        if (types.length > 0) return types;

        Type type = target.getGenericSuperclass();
        if (type instanceof ParameterizedType) return new Type[]{type};

        return new Type[0];
    }

    private static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        }
        return null;
    }
}
