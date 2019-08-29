package sausages_material.util;

import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;

public class SMUtils {
    public static <T, R> R checkAndCast(Class<R> retClz, T from) {
        if(retClz.isInstance(from)){
            return retClz.cast(from);
        }
        throw new ReportedException(CrashReport.makeCrashReport(
                new RuntimeException(String.format(
                        "Cannot cast from class[Class:%s,value:%s] to class[Class:%s],it is not assignable",
                        from.getClass().toString(),
                        from.toString(),
                        retClz.toString()
                )),"Failure casting class!"
        ));
    }
    public static <T, R> T castOrElse(Class<T> retClz, R $, T defaultValue) {
        if ($.getClass().isAssignableFrom(retClz)) {
            return retClz.cast($);
        }
        return defaultValue;
    }
}
