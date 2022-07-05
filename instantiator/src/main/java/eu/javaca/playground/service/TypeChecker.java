package eu.javaca.playground.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

import static eu.javaca.playground.Utils.CONSTANTS;

public class TypeChecker {

    public TypeChecker() {
    }

    public boolean isCollection(Field field) {
        return Collection.class.isAssignableFrom(field.getType());
    }

    public boolean isMap(Field field) {
        return Map.class.isAssignableFrom(field.getType());
    }

    public boolean isPrimitive(Field field) {
        return field.getType().isPrimitive();
    }

    public boolean isSimpleType(Class<?> type) {
        return CONSTANTS.containsKey(type.getSimpleName());
    }

    public boolean isEnum(Field field) {
        return field.getType().isEnum();
    }

    public boolean isGenericClass(Field field) {
        try {
            String className = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName();
            Class.forName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isClass(Field field) {
        try {
            Class.forName(field.getGenericType().getTypeName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isClass(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
