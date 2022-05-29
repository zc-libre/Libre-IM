//package com.libre.im.core.codec;
//
//import io.protostuff.LinkedBuffer;
//import io.protostuff.ProtostuffIOUtil;
//import io.protostuff.Schema;
//import io.protostuff.runtime.RuntimeSchema;
//
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//
//
//
///**
// * 具备缓存功能的序列化工具类，基于Protostuff实现（其基于Google Protobuf实现）
// *
// */
//public class SerializationUtil {
//
//    /**
//     * 避免每次序列化都重新申请Buffer空间
//     */
//    private static final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//    /**
//     * 缓存Schema
//     */
//    private static final Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();
//
//    /**
//     * 序列化方法，把指定对象序列化成字节数组
//     *
//     * @param obj /
//     * @param <T> /
//     * @return /
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> byte[] serialize(T obj) {
//        Class<T> clazz = (Class<T>) obj.getClass();
//        Schema<T> schema = getSchema(clazz);
//        byte[] data;
//        try {
//            data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        } finally {
//            buffer.clear();
//        }
//
//        return data;
//    }
//
//    /**
//     * 反序列化方法，将字节数组反序列化成指定Class类型
//     *
//     * @param data /
//     * @param clazz /
//     * @param <T> /
//     * @return /
//     */
//    public static <T> T deserialize(byte[] data, Class<T> clazz) {
//        Schema<T> schema = getSchema(clazz);
//        T obj = schema.newMessage();
//        ProtostuffIOUtil.mergeFrom(data, obj, schema);
//        return obj;
//    }
//
//    @SuppressWarnings("unchecked")
//    private static <T> Schema<T> getSchema(Class<T> clazz) {
//        Schema<T> schema = (Schema<T>) schemaCache.get(clazz);
//        if (Objects.isNull(schema)) {
//            schema = RuntimeSchema.getSchema(clazz);
//            if (Objects.nonNull(schema)) {
//                schemaCache.put(clazz, schema);
//            }
//        }
//        return schema;
//    }
//}