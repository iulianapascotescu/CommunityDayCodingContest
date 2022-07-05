package eu.javaca.playground.service;

import com.google.common.base.Defaults;
import eu.javaca.playground.structure.CompoundNode;
import eu.javaca.playground.structure.GenericNode;
import eu.javaca.playground.structure.ObjectNode;
import eu.javaca.playground.structure.SimpleNode;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static eu.javaca.playground.Utils.CONSTANTS;
import static eu.javaca.playground.Utils.getCamelCaseClassName;

public class FieldManager {

    private final TypeChecker typeChecker;

    public FieldManager() {
        typeChecker = new TypeChecker();
    }

    public GenericNode instantiateClass(Class<?> any, List<Class<?>> actualTypeArgs, List<String> genericTypeArgs)
            throws ClassNotFoundException, IllegalAccessException {
        CompoundNode rootNode = new CompoundNode.CompoundNodeBuilder().setName(getCamelCaseClassName(any)).getNode();
        List<Field> fields = FieldUtils.getAllFieldsList(any);
        if (fields.isEmpty()) {
            return new SimpleNode.SimpleNodeBuilder().setValue("null").getNode();
        }
        List<GenericNode> nodes = processFields(any, fields, actualTypeArgs, genericTypeArgs);
        rootNode.setNodes(nodes);
        return rootNode;
    }

    private List<GenericNode> processFields(Class<?> any, List<Field> fields, List<Class<?>> actualTypeArgs,
                                            List<String> genericTypeArgs)
            throws ClassNotFoundException, IllegalAccessException {
        List<GenericNode> genericNodes = new ArrayList<>();
        for (Field field : fields) {
            if (field.getType().getName().equals(any.getName())) {
                genericNodes.add(new SimpleNode.SimpleNodeBuilder().setName(field.getName())
                        .setValue("null").getNode());
                continue;
            }
            genericNodes.add(processField(field, actualTypeArgs, genericTypeArgs));
        }
        return genericNodes;
    }

    private GenericNode processField(Field field, List<Class<?>> actualTypeArgs, List<String> genericTypeArgs)
            throws ClassNotFoundException, IllegalAccessException {
        if (typeChecker.isPrimitive(field)) {
            return processPrimitive(field);
        }
        if (typeChecker.isSimpleType(field.getType())) {
            return processSimpleType(field);
        }
        if (typeChecker.isEnum(field)) {
            return processEnum(field);
        }
        if (typeChecker.isCollection(field)) {
            return processCollection(field, actualTypeArgs, genericTypeArgs);
        }
        if (typeChecker.isMap(field)) {
            return processMap(field, actualTypeArgs, genericTypeArgs);
        }
        if (typeChecker.isGenericClass(field)) {
            return processGenericClass(field);
        }
        if (!typeChecker.isClass(field)) {
            return processGenericType(field, actualTypeArgs, genericTypeArgs);
        }
        if (hasNoFields(field)) {
            return new SimpleNode.SimpleNodeBuilder().setName(field.getName()).setValue("null").getNode();
        }
        return new ObjectNode.ObjectNodeBuilder().setName(field.getName())
                .setGenericNode(instantiateClass(field.getType(), new ArrayList<>(), new ArrayList<>())).getNode();
    }

    private boolean hasNoFields(Field field) {
        return FieldUtils.getAllFieldsList(field.getType()).isEmpty();
    }

    private ObjectNode processGenericClass(Field field) throws ClassNotFoundException, IllegalAccessException {
        List<Class<?>> actualArguments = getActualTypeArguments(field);
        List<String> genericArguments = getGenericTypeArguments(field);
        return new ObjectNode.ObjectNodeBuilder().setName(field.getName())
                .setGenericNode(instantiateClass(field.getType(), actualArguments, genericArguments)).getNode();
    }

    private GenericNode processEnum(Field field) {
        Object[] enumConstants = field.getType().getEnumConstants();
        if (enumConstants.length == 0) {
            return new SimpleNode.SimpleNodeBuilder().setName(field.getName()).setValue("null").getNode();
        }
        return new SimpleNode.SimpleNodeBuilder().setName(field.getName())
                .setValue(enumConstants[0].toString()).getNode();
    }

    private GenericNode processGenericType(Field field, List<Class<?>> actualTypeArgs, List<String> genericTypeArgs)
            throws ClassNotFoundException, IllegalAccessException {
        if (actualTypeArgs.isEmpty()) {
            return new SimpleNode.SimpleNodeBuilder().setName(field.getName()).setValue("null").getNode();
        }
        String typeName = field.getGenericType().getTypeName();
        Class<?> actualArg = getActualArgOfGeneric(actualTypeArgs, genericTypeArgs, typeName);
        if (typeChecker.isSimpleType(actualArg)) {
            return new SimpleNode.SimpleNodeBuilder().setName(field.getName())
                    .setValue(CONSTANTS.get(actualArg.getSimpleName())).getNode();
        }
        return new ObjectNode.ObjectNodeBuilder().setName(field.getName())
                .setGenericNode(instantiateClass(actualArg, new ArrayList<>(), new ArrayList<>())).getNode();
    }

    private List<Class<?>> getActualTypeArguments(Field field) throws ClassNotFoundException {
        ParameterizedType genericArg = (ParameterizedType) field.getGenericType();
        List<String> argNames = Arrays.stream(genericArg.getActualTypeArguments())
                .map(Type::getTypeName)
                .collect(Collectors.toList());
        List<Class<?>> actualTypeArguments = new ArrayList<>();
        for (String name : argNames) {
            actualTypeArguments.add(Class.forName(name));
        }
        return actualTypeArguments;
    }

    private List<String> getGenericTypeArguments(Field field) {
        return Arrays.stream(field.getType().getTypeParameters())
                .map(TypeVariable::getName)
                .collect(Collectors.toList());
    }

    private GenericNode processMap(Field field, List<Class<?>> actualTypeArgs, List<String> genericTypeArgs)
            throws ClassNotFoundException {
        Type[] arguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        String firstArgName = arguments[0].getTypeName();
        String secondArgName = arguments[1].getTypeName();

        if (genericTypeArgs.contains(firstArgName)) {
            firstArgName =
                    getActualArgOfGeneric(actualTypeArgs, genericTypeArgs, firstArgName).getSimpleName();
        }

        if (genericTypeArgs.contains(secondArgName)) {
            secondArgName =
                    getActualArgOfGeneric(actualTypeArgs, genericTypeArgs, secondArgName).getSimpleName();
        }

        if (typeChecker.isClass(firstArgName) && typeChecker.isClass(secondArgName)) {
            firstArgName = Class.forName(firstArgName).getSimpleName();
            secondArgName = Class.forName(secondArgName).getSimpleName();
        }

        if (CONSTANTS.containsKey(firstArgName) && CONSTANTS.containsKey(secondArgName)) {
            return new CompoundNode.CompoundNodeBuilder().setName(field.getName())
                    .setNodes(List.of(new SimpleNode.SimpleNodeBuilder().setName(CONSTANTS.get(firstArgName))
                            .setValue(CONSTANTS.get(secondArgName)).setAsMapElement()
                            .getNode()))
                    .isList().getNode();
        }
        return new SimpleNode.SimpleNodeBuilder().setName(field.getName()).setValue("null").getNode();
    }

    private GenericNode processCollection(Field field, List<Class<?>> actualTypeArgs, List<String> genericTypeArgs)
            throws ClassNotFoundException, IllegalAccessException {
        Type argType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        String argName = argType.getTypeName();
        if (genericTypeArgs.contains(argName)) {
            Class<?> actualArg = getActualArgOfGeneric(actualTypeArgs, genericTypeArgs, argName);
            return new CompoundNode.CompoundNodeBuilder().setName(field.getName())
                    .setNodes(List.of(instantiateClass(actualArg, new ArrayList<>(), new ArrayList<>())))
                    .isList().getNode();
        }

        if (typeChecker.isClass(argName)) {
            Class<?> fieldClass = Class.forName(argName);
            if (CONSTANTS.containsKey(fieldClass.getSimpleName())) {
                return new CompoundNode.CompoundNodeBuilder().setName(field.getName())
                        .setNodes(List.of(new SimpleNode.SimpleNodeBuilder()
                                .setValue(CONSTANTS.get(fieldClass.getSimpleName())).getNode()))
                        .isList().getNode();
            }
            return new CompoundNode.CompoundNodeBuilder().setName(field.getName())
                    .setNodes(List.of(instantiateClass(fieldClass, actualTypeArgs, genericTypeArgs)))
                    .isList().getNode();
        }
        return new SimpleNode.SimpleNodeBuilder().setName(field.getName()).setValue("null").getNode();
    }

    private Class<?> getActualArgOfGeneric(List<Class<?>> actualTypeArgs, List<String> genericTypeArgs,
                                           String argName) {
        return actualTypeArgs.get(genericTypeArgs.indexOf(argName));
    }

    private GenericNode processSimpleType(Field field) {
        return new SimpleNode.SimpleNodeBuilder().setName(field.getName())
                .setValue(CONSTANTS.get(field.getType().getSimpleName())).getNode();
    }

    private GenericNode processPrimitive(Field field) {
        return new SimpleNode.SimpleNodeBuilder().setName(field.getName())
                .setValue(Objects.requireNonNull(Defaults.defaultValue(field.getType())).toString()).getNode();
    }
}
