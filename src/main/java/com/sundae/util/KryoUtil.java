package com.sundae.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sundae.TestBean;
import com.sundae.server.Response;
import com.sundae.service.ServiceRemoteInvokeBean;
import com.sundae.service.ServiceResultBean;

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
            Kryo kryo = new Kryo();
            kryo.register(ServiceRemoteInvokeBean.class);
            kryo.register(ServiceResultBean.class);
            kryo.register(Response.class);
            kryo.register(Object[].class);
            kryo.register(Object.class);
            kryo.register(byte[].class);
            kryo.register(TestBean.class);
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
