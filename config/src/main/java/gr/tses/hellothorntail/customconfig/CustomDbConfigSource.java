package gr.tses.hellothorntail.customconfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;
public class CustomDbConfigSource implements ConfigSource {
    @Override
    public int getOrdinal() {
        return 350;
    }
    @Override
    public Set<String> getPropertyNames() {
        Set<String> s = new HashSet<String>();
        s.add("DBP1");
        return s;
    }
    @Override
    public Map<String, String> getProperties() {
        Map<String,String> m = new HashMap<String,String>();
        m.put("DBP1", "DBVAL");
        return m;
    }
    @Override
    public String getValue(String key) {
        System.out.println("Fake config source" + key);
        if ("DBP1".equals(key) )
            return "DBVAL";
        else 
            return null;
    }
    @Override
    public String getName() {
        return "customDbConfig";
    }

}