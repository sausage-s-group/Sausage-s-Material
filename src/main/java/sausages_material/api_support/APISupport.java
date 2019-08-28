package sausages_material.api_support;

import com.google.common.collect.Maps;
import sausage_core.api.annotation.LoadClass;

import java.util.Map;

@LoadClass(when = LoadClass.When.PRE_INIT)
public class APISupport {
    static {

    }

    public static Map<String, String> getOreGenConfiguration() {
        return Maps.newHashMap();
    }
}
