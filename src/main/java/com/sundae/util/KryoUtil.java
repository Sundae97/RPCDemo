package com.sundae.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sundae.TestBean;

/**
 * KryoUtil
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class KryoUtil {

    static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            return new Kryo();
        }
    };

    private static Kryo getKryo(){
        return KRYO_THREAD_LOCAL.get();
    }

    public static byte[] doSerialize(Object object) {
        getKryo().register(object.getClass());
        getKryo().register(Object[].class);
        Output output = new Output(1024, -1);
        getKryo().writeObject(output, object);
        return output.toBytes();
    }

    public static <T> T doDeserialize(byte[] bytes, Class<T> typeClass) {
        getKryo().register(typeClass);
        getKryo().register(Object[].class);
        Input input = new Input(bytes);
        return getKryo().readObject(input, typeClass);
    }

}
