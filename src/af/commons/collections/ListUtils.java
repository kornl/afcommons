package af.commons.collections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static String[] toStringArray(List xs) {
        String[] res = new String[xs.size()];
        for (int i=0; i<xs.size(); i++)
            res[i] = xs.toString();
        return res;
    }

    public static <E> List<String> toString(List<E> xs) {
        return transform(xs, "toString");
    }

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


    public static <E> List<E> copyAndAdd(List<E> xs, E x) {
        List<E> result = new ArrayList<E>();
        result.addAll(xs);
        result.add(x);
        return result;
    }
}
