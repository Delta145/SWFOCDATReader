package ru.ifmo.swfoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DATWriter {
    private File processingFile;
    private byte[] byteBufferReverse = new byte[4];

    public DATWriter(File file) {
        processingFile = file;
    }

    public void saveToFile(Map<String, String> map) throws IOException {
        FileOutputStream out = new FileOutputStream(processingFile);
        byte[] size = intToByte(map.size());
        for (int i = 0; i < size.length; i++)
            size[size.length - 1 - i] = byteBufferReverse[i];
        Object[] keys = map.keySet().toArray();
        Object[] values = map.values().toArray();
        byte[] fish = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        out.write(size);
        for (int i = 0; i < map.size(); i++) {
            out.write(fish);
            out.write(intToByte(((String) values[i]).getBytes(StandardCharsets.UTF_16LE).length/2));
            out.write(intToByte(((String) keys[i]).getBytes(StandardCharsets.US_ASCII).length));
        }

        for (int i = 0; i < map.size(); i++)
            out.write(((String) values[i]).getBytes(StandardCharsets.UTF_16LE));
        for (int i = 0; i < map.size(); i++)
            out.write(((String) keys[i]).getBytes(StandardCharsets.US_ASCII));
    }

    private byte[] intToByte(int integer) {
        byteBufferReverse = ByteBuffer.allocate(4).putInt(integer).array();
        byte[] buffer = new byte[4];
        for (int i = 0; i < 4; i++)
            buffer[3 - i] = byteBufferReverse[i];
        return buffer;
    }
}
