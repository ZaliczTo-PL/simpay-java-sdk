package pl.zaliczto.simpay.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ArrayFlattener {
    private ArrayFlattener() {}

    public static List<String> flattenValues(Object obj) {
        List<String> out = new ArrayList<>();
        flattenInto(obj, out);
        return out;
    }

    private static void flattenInto(Object obj, List<String> out) {
        if (obj == null) return;
        if (obj instanceof Map<?, ?> map) {
            for (Map.Entry<?, ?> e : map.entrySet()) {
                flattenInto(e.getValue(), out);
            }
        } else if (obj instanceof Iterable<?> it) {
            for (Object v : it) flattenInto(v, out);
        } else if (obj.getClass().isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) flattenInto(Array.get(obj, i), out);
        } else {
            out.add(String.valueOf(obj));
        }
    }
}

