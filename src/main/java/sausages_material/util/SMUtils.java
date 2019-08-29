package sausages_material.util;

public class SMUtils {
    public static <T, R> T checkAndCast(Class<T> retClz, R $) {
        if ($.getClass().isAssignableFrom(retClz)) {
            return retClz.cast($);
        }
        throw new RuntimeException("Failure casting class[class=" + $.getClass().getName() + "," +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "")
    }
}
