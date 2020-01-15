package com.sundae.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * ReflectUtil
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class ReflectUtil {

    public static String getMethodDescription(Method method){
        return method.toGenericString();
//        String parameterTypesStr = Arrays.stream(method.getParameterTypes())
//                .map(typeClz -> typeClz.getCanonicalName())
//                .collect(Collectors.joining("_"));
//
//        StringBuilder methodDescription = new StringBuilder();
//        methodDescription.append(method.getDeclaringClass().getCanonicalName());
//        methodDescription.append("_");
//        methodDescription.append(method.getReturnType().getCanonicalName());
//        methodDescription.append("_");
//        methodDescription.append(method.getName());
//        methodDescription.append("(");
//        methodDescription.append(parameterTypesStr);
//        methodDescription.append(")");
//
//        return methodDescription.toString();
    }
}
