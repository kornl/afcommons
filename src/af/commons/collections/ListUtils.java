package af.commons.collections;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <E> List<String> toString(List<E> xs) {
        List<String> result = new ArrayList<String>();
        for (E x:xs)
            result.add(xs.toString());
        return result;
    }

    public static <E> List<E> copyAndAdd(List<E> xs, E x) {
        List<E> result = new ArrayList<E>();
        result.addAll(xs);
        result.add(x);
        return result;
    }

}
