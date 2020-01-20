package com.sundae.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * KryoUtil
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class KryoUtil {

    public static class Config{
        public static boolean REGISTRATION_REQUIRED = false;
        public static boolean WARN_UNREGISTERED_CLASSES = true;
    }

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setRegistrationRequired(Config.REGISTRATION_REQUIRED);
            kryo.setWarnUnregisteredClasses(Config.WARN_UNREGISTERED_CLASSES);
            return kryo;
        }
    };

    private static Kryo getKryo(){
        return KRYO_THREAD_LOCAL.get();
    }

    public static byte[] doSerialize(Object object) {
        Output output = new Output(1024, -1);
        getKryo().writeObject(output, object);
        return output.toBytes();
    }

    public static <T> T doDeserialize(byte[] bytes, Class<T> typeClass) {
        Input input = new Input(bytes);
        return getKryo().readObject(input, typeClass);
    }

}
