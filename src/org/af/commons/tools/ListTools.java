package org.af.commons.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListTools {

    /**
     * Converts a List to an array of Strings by calling toString on every entry.
     * @param xs The list.
     * @return String array.
     */
    public static String[] toStringArray(List xs) {
        String[] res = new String[xs.size()];
        for (int i=0; i<xs.size(); i++)
            res[i] = xs.toString();
        return res;
    }

    /**
     * Converts a list to a list of strings by calling toString on every entry.
     * @param xs The list.
     * @return List of strings.
     */
    public static <E> List<String> toString(List<E> xs) {
        return transform(xs, "toString");
    }

    /**
     * Transforms a list by calling a method on every element of the list by using reflection.
     * The method is specified by its name, must have no arguments and return an object.
     * Otherwise a runtime exception might be thrown.
     * @param xs The list.
     * @param method The name of the method to call.
     * @param <E> Base type of the list.
     * @param <F> Type that the call to method returns
     * @return Transformed list of base type F.
     */
    public static <E, F> List<F> transform(List<E> xs, String method) {
        List<F> result = new ArrayList<F>();
        if (xs.isEmpty())
            return result;
        try {
            Method m = xs.get(0).getClass().getMethod(method);
            for (E x : xs)
                result.add((F) m.invoke(x));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Copies a list (shallow copy) and adds an element at the end.
     * @param xs The list.
     * @param x The Element to add.
     * @param <E> Type for the list.
     * @return Copied list with added element.
     */
    public static <E> List<E> copyAndAdd(List<E> xs, E x) {
        List<E> result = new ArrayList<E>();
        result.addAll(xs);
        result.add(x);
        return result;
    }
}
