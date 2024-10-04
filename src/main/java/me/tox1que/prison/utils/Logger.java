package me.tox1que.prison.utils;

import me.tox1que.prison.Prison;
import me.tox1que.prison.utils.SQL.SQLUtils;
import me.tox1que.prison.utils.classes.PrisonException;

import java.util.HashMap;
import java.util.Map;

public class Logger{

    public enum Database{
        ;

        private final String name;
        private final String[] keys;

        Database(String name, String... keys){
            this.name = name;
            this.keys = keys;
        }

        public void write(Object... values){
            if(values.length != this.keys.length){
                Console.SEVERE("Lengths of KEYS and VALUES does not match for Log " + this.name());
                return;
            }
            Map<String, Object> data = new HashMap<>();
            for(int i = 0; i < keys.length; i++){
                data.put(this.keys[i], values[i]);
            }
            SQLUtils.insert(this.name, data);
        }
    }

    public static class Console{

        public static void INFO(String msg){
            Prison.getInstance().getLogger().info(msg);
        }

        public static void SEVERE(String msg){
            Prison.getInstance().getLogger().severe(msg);
        }

        public static void WARNING(String msg){
            Prison.getInstance().getLogger().warning(msg);
        }

        public static void ERROR(String msg){
            throw new PrisonException(msg);
        }
    }

}
