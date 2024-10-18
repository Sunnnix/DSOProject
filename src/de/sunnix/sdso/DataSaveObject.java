package de.sunnix.sdso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

@SuppressWarnings("unchecked")
public class DataSaveObject {

    private static final byte TYPE_BYTE = 0;
    private static final byte TYPE_SHORT = 1;
    private static final byte TYPE_INT = 2;
    private static final byte TYPE_LONG = 3;
    private static final byte TYPE_FLOAT = 4;
    private static final byte TYPE_DOUBLE = 5;
    private static final byte TYPE_BOOL = 6;
    private static final byte TYPE_CHAR = 7;
    private static final byte TYPE_STRING = 8;
    private static final byte TYPE_OBJECT = 9;
    private static final byte TYPE_OBJECT_START = 10;
    private static final byte TYPE_OBJECT_END = 11;
    private static final byte TYPE_ARRAY = 12;
    private static final byte TYPE_DATA = 13;

    private final Map<String, Object> data = new HashMap<>();

    public boolean print_ms;

    public DataSaveObject(){}

    public DataSaveObject(InputStream stream) throws IOException {
        load(stream);
    }

    public DataSaveObject removeValue(String key){
        data.remove(key);
        return this;
    }

    // ========================================================
    // PUT
    // ========================================================

    private <T> DataSaveObject _put(String key, T value) {
        if (key.length() > 255)
            throw new IllegalArgumentException("The length of the Key can't be larger then 255 chars");
        if (value != null)
            data.put(key, value);
        else
            data.remove(key);
        return this;
    }

    public DataSaveObject put(String key, byte b){
        return _put(key, b);
    }

    public DataSaveObject putByte(String key, int b) {
        return _put(key, (byte) b);
    }

    public DataSaveObject put(String key, short s){
        return _put(key, s);
    }

    public DataSaveObject putShort(String key, int s) {
        return _put(key, (short) s);
    }

    public DataSaveObject put(String key, int i){
        return _put(key, i);
    }

    public DataSaveObject putInt(String key, int i) {
        return _put(key, i);
    }

    public DataSaveObject put(String key, long l){
        return _put(key, l);
    }

    public DataSaveObject putLong(String key, long l) {
        return _put(key, l);
    }

    public DataSaveObject put(String key, float f){
        return _put(key, f);
    }

    public DataSaveObject putFloat(String key, double f) {
        return _put(key, (float) f);
    }

    public DataSaveObject put(String key, double d){
        return _put(key, d);
    }

    public DataSaveObject putDouble(String key, double d) {
        return _put(key, d);
    }

    public DataSaveObject put(String key, boolean b){
        return _put(key, b);
    }

    public DataSaveObject putBool(String key, boolean b) {
        return _put(key, b);
    }

    public DataSaveObject put(String key, char c){
        return _put(key, c);
    }

    public DataSaveObject putChar(String key, char c) {
        return _put(key, c);
    }

    public DataSaveObject put(String key, String s){
        return _put(key, s);
    }

    public DataSaveObject putString(String key, String s) {
        return _put(key, s);
    }

    public DataSaveObject put(String key, DataSaveObject o){
        return _put(key, o);
    }

    public DataSaveObject putObject(String key, DataSaveObject o) {
        return _put(key, o);
    }

    public <T> DataSaveObject put(String key, List<T> l){
        return putList(key, l);
    }

    public <T> DataSaveObject putList(String key, List<T> l) {
        if (l != null && !l.isEmpty())
            return _put(key, new DataSaveArray<>(l));
        return this;
    }

    public <T> DataSaveObject put(String key, T[] a){
        return putArray(key, a);
    }

    public <T> DataSaveObject putArray(String key, T[] array) {
        return putList(key, Arrays.asList(array));
    }

    public DataSaveObject put(String key, byte[] a){
        return putArray(key, a);
    }

    public DataSaveObject putArray(String key, byte[] array) {
        var wA = new Byte[array.length];
        for (int i = 0; i < wA.length; i++)
            wA[i] = array[i];
        return putList(key, Arrays.asList(wA));
    }

    public DataSaveObject put(String key, short[] a){
        return putArray(key, a);
    }

    public DataSaveObject putArray(String key, short[] array) {
        var wA = new Short[array.length];
        for (int i = 0; i < wA.length; i++)
            wA[i] = array[i];
        return putList(key, Arrays.asList(wA));
    }

    public DataSaveObject put(String key, int[] a){
        return putArray(key, a);
    }

    public DataSaveObject putArray(String key, int[] array) {
        return putList(key, Arrays.stream(array).boxed().toList());
    }

    public DataSaveObject put(String key, long[] a){
        return putArray(key, a);
    }

    public DataSaveObject putArray(String key, long[] array) {
        return putList(key, Arrays.stream(array).boxed().toList());
    }

    public DataSaveObject put(String key, float[] a){
        return putArray(key, a);
    }

    public DataSaveObject putArray(String key, float[] array) {
        var wA = new Float[array.length];
        for (int i = 0; i < wA.length; i++)
            wA[i] = array[i];
        return putList(key, Arrays.asList(wA));
    }

    public DataSaveObject put(String key, double[] a){
        return putArray(key, a);
    }

    public DataSaveObject putArray(String key, double[] array) {
        return putList(key, Arrays.stream(array).boxed().toList());
    }

    public DataSaveObject put(String key, boolean[] a){
        return putArray(key, a);
    }

    public DataSaveObject putArray(String key, boolean[] array) {
        var wA = new Boolean[array.length];
        for (int i = 0; i < wA.length; i++)
            wA[i] = array[i];
        return putList(key, Arrays.asList(wA));
    }

    public DataSaveObject put(String key, char[] a){
        return putArray(key, a);
    }

    public DataSaveObject putArray(String key, char[] array) {
        var wA = new Character[array.length];
        for (int i = 0; i < wA.length; i++)
            wA[i] = array[i];
        return putList(key, Arrays.asList(wA));
    }

    // ========================================================
    // GET
    // ========================================================

    public <T> Object getRaw(String key, T def){
        if (key.length() > 255)
            throw new IllegalArgumentException("The length of the Key can't be larger then 255 chars");
        var value = data.get(key);
        return value == null ? def : value;
    }

    public <T> T get(String key, T def, Class<T> clazz) {
        var value = getRaw(key, def);
        if(value != null && value.getClass().equals(clazz))
            return (T) value;
        return def;
    }

    public Number getNumber(String key, Number def){
        var value = getRaw(key, def);
        if(value instanceof Number num)
            return num;
        return def;
    }

    public byte get(String key, byte b){
        return getByte(key, b);
    }

    public byte getByte(String key, int defaultValue) {
        return getNumber(key, defaultValue).byteValue();
    }

    public short get(String key, short s){
        return getShort(key, s);
    }

    public short getShort(String key, int defaultValue) {
        return getNumber(key, defaultValue).shortValue();
    }

    public int get(String key, int i){
        return getInt(key, i);
    }

    public int getInt(String key, int defaultValue) {
        return getNumber(key, defaultValue).intValue();
    }

    public long get(String key, long l){
        return getLong(key, l);
    }

    public long getLong(String key, long defaultValue) {
        return getNumber(key, defaultValue).longValue();
    }

    public float get(String key, float f){
        return getFloat(key, f);
    }

    public float getFloat(String key, double defaultValue) {
        return getNumber(key, defaultValue).floatValue();
    }

    public double get(String key, double d){
        return getDouble(key, d);
    }

    public double getDouble(String key, double defaultValue) {
        return getNumber(key, defaultValue).doubleValue();
    }

    public boolean get(String key, boolean b){
        return getBool(key, b);
    }

    public boolean getBool(String key, boolean defaultValue) {
        return get(key, defaultValue, Boolean.class);
    }

    public char get(String key, char c){
        return getChar(key, c);
    }

    public char getChar(String key, char defaultValue) {
        return get(key, defaultValue, Character.class);
    }

    public String get(String key, String s){
        return getString(key, s);
    }

    public String getString(String key, String defaultValue) {
        var value = getRaw(key, defaultValue);
        return value == null ? null : String.valueOf(value);
    }

    public DataSaveObject getObject(String key) {
        return get(key, null, DataSaveObject.class);
    }

    public DataSaveObject getObjectOrEmpty(String key){
        var obj = getObject(key);
        if(obj == null)
            return new DataSaveObject();
        return obj;
    }

    public <T> List<T> getList(String key) {
        DataSaveArray<T> dsa = get(key, null, DataSaveArray.class);
        if (dsa == null)
            return new ArrayList<>();
        else
            return new ArrayList<T>(dsa.list);
    }

    public <T> T[] getArray(String key, IntFunction<T[]> generator) {
        return getList(key).toArray(generator);
    }

    public byte[] getByteArray(String key) {
        var wA = getList(key).toArray(Byte[]::new);
        var pA = new byte[wA.length];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public short[] getShortArray(String key) {
        var wA = getList(key).toArray(Short[]::new);
        var pA = new short[wA.length];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public int[] getIntArray(String key) {
        var wA = getList(key).toArray(Integer[]::new);
        var pA = new int[wA.length];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public long[] getLongArray(String key) {
        var wA = getList(key).toArray(Long[]::new);
        var pA = new long[wA.length];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public float[] getFloatArray(String key) {
        var wA = getList(key).toArray(Float[]::new);
        var pA = new float[wA.length];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public double[] getDoubleArray(String key) {
        var wA = getList(key).toArray(Double[]::new);
        var pA = new double[wA.length];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public boolean[] getBoolArray(String key) {
        var wA = getList(key).toArray(Boolean[]::new);
        var pA = new boolean[wA.length];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public char[] getCharArray(String key) {
        var wA = getList(key).toArray(Character[]::new);
        var pA = new char[wA.length];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public byte[] getByteArray(String key, int minimumSize) {
        var wA = getList(key).toArray(Byte[]::new);
        var pA = new byte[Math.max(minimumSize, wA.length)];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public short[] getShortArray(String key, int minimumSize) {
        var wA = getList(key).toArray(Short[]::new);
        var pA = new short[Math.max(minimumSize, wA.length)];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public int[] getIntArray(String key, int minimumSize) {
        var wA = getList(key).toArray(Integer[]::new);
        var pA = new int[Math.max(minimumSize, wA.length)];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public long[] getLongArray(String key, int minimumSize) {
        var wA = getList(key).toArray(Long[]::new);
        var pA = new long[Math.max(minimumSize, wA.length)];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public float[] getFloatArray(String key, int minimumSize) {
        var wA = getList(key).toArray(Float[]::new);
        var pA = new float[Math.max(minimumSize, wA.length)];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public double[] getDoubleArray(String key, int minimumSize) {
        var wA = getList(key).toArray(Double[]::new);
        var pA = new double[Math.max(minimumSize, wA.length)];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public boolean[] getBoolArray(String key, int minimumSize) {
        var wA = getList(key).toArray(Boolean[]::new);
        var pA = new boolean[Math.max(minimumSize, wA.length)];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    public char[] getCharArray(String key, int minimumSize) {
        var wA = getList(key).toArray(Character[]::new);
        var pA = new char[Math.max(minimumSize, wA.length)];
        for (int i = 0; i < wA.length; i++)
            pA[i] = wA[i];
        return pA;
    }

    // ========================================================
    // FUNCTIONS
    // ========================================================

    public OutputStream save() throws IOException {
        try (var stream = new ByteArrayOutputStream()) {
            save(stream);
            return stream;
        }
    }

    public DataSaveObject save(OutputStream stream) throws IOException {
        for (var e : data.entrySet()) {
            var msStart = System.currentTimeMillis();
            var k = e.getKey();
            var o = e.getValue();
            // write value
            if (o instanceof Byte b) {
                stream.write(TYPE_BYTE);
                stream.write(b);
            } else if (o instanceof Short s) {
                stream.write(TYPE_SHORT);
                stream.write(ByteBuffer.allocate(Short.BYTES).putShort(s).array());
            } else if (o instanceof Integer i) {
                stream.write(TYPE_INT);
                stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(i).array());
            } else if (o instanceof Long l) {
                stream.write(TYPE_LONG);
                stream.write(ByteBuffer.allocate(Long.BYTES).putLong(l).array());
            } else if (o instanceof Float f) {
                stream.write(TYPE_FLOAT);
                stream.write(ByteBuffer.allocate(Float.BYTES).putFloat(f).array());
            } else if (o instanceof Double d) {
                stream.write(TYPE_DOUBLE);
                stream.write(ByteBuffer.allocate(Double.BYTES).putDouble(d).array());
            } else if (o instanceof Boolean b) {
                stream.write(TYPE_BOOL);
                stream.write(b ? 1 : 0);
            } else if (o instanceof Character c) {
                stream.write(TYPE_CHAR);
                stream.write(ByteBuffer.allocate(Character.BYTES).putChar(c).array());
            } else if (o instanceof String s) {
                stream.write(TYPE_STRING);
                var stringData = s.getBytes();
                stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(stringData.length).array());
                stream.write(stringData);
            } else if (o instanceof DataSaveObject dso) {
                stream.write(TYPE_OBJECT_START);
                dso.save(stream, k + "->");
            } else if (o instanceof DataSaveArray<?> dsa) {
                dsa.save(stream);
            }
            // write key
            var kb = k.getBytes();
            stream.write(kb.length);
            stream.write(kb);
            if (print_ms)
                System.out.println("saving " + k + " done in " + (System.currentTimeMillis() - msStart) + " ms.");
        }
        stream.write(TYPE_OBJECT_END);
        return this;
    }

    private DataSaveObject save(OutputStream stream, String parent) throws IOException {
        for (var e : data.entrySet()) {
            var msStart = System.currentTimeMillis();
            var k = e.getKey();
            var o = e.getValue();
            // write value
            if (o instanceof Byte b) {
                stream.write(TYPE_BYTE);
                stream.write(b);
            } else if (o instanceof Short s) {
                stream.write(TYPE_SHORT);
                stream.write(ByteBuffer.allocate(Short.BYTES).putShort(s).array());
            } else if (o instanceof Integer i) {
                stream.write(TYPE_INT);
                stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(i).array());
            } else if (o instanceof Long l) {
                stream.write(TYPE_LONG);
                stream.write(ByteBuffer.allocate(Long.BYTES).putLong(l).array());
            } else if (o instanceof Float f) {
                stream.write(TYPE_FLOAT);
                stream.write(ByteBuffer.allocate(Float.BYTES).putFloat(f).array());
            } else if (o instanceof Double d) {
                stream.write(TYPE_DOUBLE);
                stream.write(ByteBuffer.allocate(Double.BYTES).putDouble(d).array());
            } else if (o instanceof Boolean b) {
                stream.write(TYPE_BOOL);
                stream.write(b ? 1 : 0);
            } else if (o instanceof Character c) {
                stream.write(TYPE_CHAR);
                stream.write(ByteBuffer.allocate(Character.BYTES).putChar(c).array());
            } else if (o instanceof String s) {
                stream.write(TYPE_STRING);
                var stringData = s.getBytes();
                stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(stringData.length).array());
                stream.write(stringData);
            } else if (o instanceof DataSaveObject dso) {
                stream.write(TYPE_OBJECT_START);
                dso.save(stream, k + "->");
            } else if (o instanceof DataSaveArray<?> dsa) {
                dsa.save(stream);
            }
            // write key
            var kb = k.getBytes();
            stream.write(kb.length);
            stream.write(kb);
            if (print_ms)
                System.out.println(
                        "saving " + parent + k + " done in " + (System.currentTimeMillis() - msStart) + " ms.");
        }
        stream.write(TYPE_OBJECT_END);
        return this;
    }

    public DataSaveObject load(InputStream stream) throws IOException {
        while (stream.available() > 0) {
            var msStart = System.currentTimeMillis();
            var type = (byte) stream.read();
            if (type == TYPE_OBJECT_END)
                break;
            Object o = switch (type) {
                case TYPE_BYTE: {
                    yield (byte) stream.read();
                }
                case TYPE_SHORT: {
                    yield ByteBuffer.allocate(Short.BYTES).put(stream.readNBytes(Short.BYTES)).position(0).getShort();
                }
                case TYPE_INT: {
                    yield ByteBuffer.allocate(Integer.BYTES).put(stream.readNBytes(Integer.BYTES)).position(0).getInt();
                }
                case TYPE_LONG: {
                    yield ByteBuffer.allocate(Long.BYTES).put(stream.readNBytes(Long.BYTES)).position(0).getLong();
                }
                case TYPE_FLOAT: {
                    yield ByteBuffer.allocate(Float.BYTES).put(stream.readNBytes(Float.BYTES)).position(0).getFloat();
                }
                case TYPE_DOUBLE: {
                    yield ByteBuffer.allocate(Double.BYTES).put(stream.readNBytes(Double.BYTES)).position(0).getDouble();
                }
                case TYPE_BOOL: {
                    yield stream.read() != 0;
                }
                case TYPE_CHAR: {
                    yield ByteBuffer.allocate(Character.BYTES).put(stream.readNBytes(Character.BYTES)).position(0)
                            .getChar();
                }
                case TYPE_STRING: {
                    yield new String(stream.readNBytes(ByteBuffer.wrap(stream.readNBytes(Integer.BYTES)).getInt()));
                }
                case TYPE_OBJECT_START: {
                    var object = new DataSaveObject();
                    object.load(stream);
                    yield object;
                }
                case TYPE_ARRAY: {
                    var array = new DataSaveArray<>(stream);
                    yield array;
                }
                default:
                    throw new IOException("Unknown Type: " + type);
            };
            var k = new String(stream.readNBytes(stream.read()));
            data.put(k, o);
            if (print_ms)
                System.out.println("saving " + k + " done in " + (System.currentTimeMillis() - msStart) + " ms.");
        }
        return this;
    }

    public static DataSaveObject genTestObject() {
        var inner = new DataSaveObject();
        inner.putInt("In object Test 0", 809030);
        inner.putString("In object Test 1", "Test 123");
        var o = new DataSaveObject();
        o.putByte("Test 1", (byte) 20);
        o.putShort("Test 2", (short) 20391);
        o.putInt("Test 3", 333999666);
        o.putLong("Test 4", 1234567890123456789L);
        o.putFloat("Test 5", 12.85f);
        o.putDouble("Test 6", 912.6388);
        o.putBool("Test 7", true);
        o.putChar("Test 8", 'Z');
        o.putString("Test 9", "Test 456");
        o.putObject("Test 10", inner);
        o.putArray("Test 11", new Byte[] { 20, 40, 80, 120, (byte) 160, (byte) 200, (byte) 240 });
        o.putByte("Test 12", (byte) -64);
        o.putShort("Test 13", (short) 38000);
        o.putInt("Test 14", -333999666);
        o.putLong("Test 15", -1234567890123456789L);
        o.putFloat("Test 16", -12.85f);
        o.putDouble("Test 17", -912.6388);
        o.putBool("Test 18", false);
        o.putChar("Test 19", 'Y');
        o.putList("Test 20", List.of(200, 300, 400));
        o.putString("Test 21", null);
        o.putArray("Test 22", new String[] {});
        o.putArray("Test 23", new String[] { "Test Eins", "Test zwo", "Test drei" });
        o.putArray("Test 24", new byte[] { 8, 12, 78, 16, 7 });
        o.putArray("Test 25", new float[] { 2f, .3f, 1.4f, .9f });
        o.putList("Test 27", List.of(new byte[] { 0, 1, 2 }, new byte[] { 3, 4, 5 }, new byte[] { 7, 8, 9 }));
        return o;
    }

    @Override
    public String toString() {
        var b = new StringBuilder("DataSaveObject:\n");
        data.forEach((k, v) -> b.append(k + ": " + (v instanceof byte[] ? "(data)" : v) + "\n"));
        b.append("====================");
        return b.toString();
    }

    private static class DataSaveArray<T> {

        private final byte DATATYPE;
        private List<T> list;

        private DataSaveArray(List<T> list) {
            if (list.isEmpty())
                DATATYPE = -1;
            else {
                var f = list.get(0);
                if (f instanceof Byte)
                    DATATYPE = TYPE_BYTE;
                else if (f instanceof Short)
                    DATATYPE = TYPE_SHORT;
                else if (f instanceof Integer)
                    DATATYPE = TYPE_INT;
                else if (f instanceof Long)
                    DATATYPE = TYPE_LONG;
                else if (f instanceof Float)
                    DATATYPE = TYPE_FLOAT;
                else if (f instanceof Double)
                    DATATYPE = TYPE_DOUBLE;
                else if (f instanceof Boolean)
                    DATATYPE = TYPE_BOOL;
                else if (f instanceof Character)
                    DATATYPE = TYPE_CHAR;
                else if (f instanceof String)
                    DATATYPE = TYPE_STRING;
                else if (f instanceof DataSaveObject)
                    DATATYPE = TYPE_OBJECT;
                else if (f instanceof DataSaveArray<?>) // currently unused
                    DATATYPE = TYPE_ARRAY;
                else if (f instanceof byte[])
                    DATATYPE = TYPE_DATA;
                else
                    throw new IllegalArgumentException("Type of list is invalid: " + f.getClass().getName());
            }
            this.list = new ArrayList<>(list);
        }

        private void save(OutputStream stream) throws IOException {
            if (DATATYPE == -1)
                return;
            stream.write(TYPE_ARRAY);
            stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(list.size()).array());
            if (DATATYPE != TYPE_ARRAY)
                stream.write(DATATYPE);
            for (T t : list)
                switch (DATATYPE) {
                    case TYPE_BYTE:
                        stream.write((byte) t);
                        break;
                    case TYPE_SHORT:
                        stream.write(ByteBuffer.allocate(Short.BYTES).putShort((short) t).array());
                        break;
                    case TYPE_INT:
                        stream.write(ByteBuffer.allocate(Integer.BYTES).putInt((int) t).array());
                        break;
                    case TYPE_LONG:
                        stream.write(ByteBuffer.allocate(Long.BYTES).putLong((long) t).array());
                        break;
                    case TYPE_FLOAT:
                        stream.write(ByteBuffer.allocate(Float.BYTES).putFloat((float) t).array());
                        break;
                    case TYPE_DOUBLE:
                        stream.write(ByteBuffer.allocate(Double.BYTES).putDouble((double) t).array());
                        break;
                    case TYPE_BOOL:
                        stream.write((boolean) t ? 1 : 0);
                        break;
                    case TYPE_CHAR:
                        stream.write(ByteBuffer.allocate(Character.BYTES).putChar((char) t).array());
                        break;
                    case TYPE_STRING:
                        var stringData = ((String) t).getBytes();
                        stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(stringData.length).array());
                        stream.write(stringData);
                        break;
                    case TYPE_OBJECT:
                        ((DataSaveObject) t).save(stream);
                        break;
                    case TYPE_ARRAY: // currently unused
                        ((DataSaveArray<?>) t).save(stream);
                        break;
                    case TYPE_DATA:
                        var data = (byte[]) t;
                        stream.write(ByteBuffer.allocate(Integer.BYTES).putInt(data.length).array());
                        stream.write(data);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + DATATYPE);
                }
        }

        private DataSaveArray(InputStream stream) throws IOException {
            var count = ByteBuffer.allocate(Integer.BYTES).put(stream.readNBytes(Integer.BYTES)).position(0).getInt();
            list = new ArrayList<>(count);
            DATATYPE = (byte) stream.read();
            for (int i = 0; i < count; i++) {
                switch (DATATYPE) {
                    case TYPE_BYTE:
                        list.add((T) Byte.valueOf((byte) stream.read()));
                        break;
                    case TYPE_SHORT:
                        list.add((T) Short.valueOf(ByteBuffer.allocate(Short.BYTES).put(stream.readNBytes(Short.BYTES))
                                .position(0).getShort()));
                        break;
                    case TYPE_INT:
                        list.add((T) Integer.valueOf(ByteBuffer.allocate(Integer.BYTES)
                                .put(stream.readNBytes(Integer.BYTES)).position(0).getInt()));
                        break;
                    case TYPE_LONG:
                        list.add((T) Long.valueOf(
                                ByteBuffer.allocate(Long.BYTES).put(stream.readNBytes(Long.BYTES)).position(0).getLong()));
                        break;
                    case TYPE_FLOAT:
                        list.add((T) Float.valueOf(ByteBuffer.allocate(Float.BYTES).put(stream.readNBytes(Float.BYTES))
                                .position(0).getFloat()));
                        break;
                    case TYPE_DOUBLE:
                        list.add((T) Double.valueOf(ByteBuffer.allocate(Double.BYTES).put(stream.readNBytes(Double.BYTES))
                                .position(0).getDouble()));
                        break;
                    case TYPE_BOOL:
                        list.add((T) Boolean.valueOf(stream.read() != 0));
                        break;
                    case TYPE_CHAR:
                        list.add((T) Character.valueOf(ByteBuffer.allocate(Character.BYTES)
                                .put(stream.readNBytes(Character.BYTES)).position(0).getChar()));
                        break;
                    case TYPE_STRING:
                        list.add((T) new String(
                                stream.readNBytes(ByteBuffer.wrap(stream.readNBytes(Integer.BYTES)).getInt())));
                        break;
                    case TYPE_OBJECT:
                        var object = new DataSaveObject();
                        object.load(stream);
                        list.add((T) object);
                        break;
                    case TYPE_ARRAY: // currently unused
                        var array = new DataSaveArray<>(stream);
                        list.add((T) array);
                        break;
                    case TYPE_DATA:
                        list.add((T) stream.readNBytes(ByteBuffer.wrap(stream.readNBytes(Integer.BYTES)).getInt()));
                        break;
                    default:
                        throw new IOException("Readed datattype is unknown: " + DATATYPE);
                }
            }
        }

        @Override
        public String toString() {
            return list.toString();
        }

    }

}
