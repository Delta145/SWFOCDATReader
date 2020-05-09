package ru.ifmo.swfoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class DATReader {
    private File processingFile;
    private byte[] byteBuffer = new byte[4];
    private byte[] byteBufferReverse = new byte[4];
    public DATReader(File datFile) {
        processingFile = datFile;
    }

    public Map<String, String> readFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(processingFile);

        int recordsNumber = readInt(fileInputStream);
        String[] keys = new String[recordsNumber];
        String[] values = new String[recordsNumber];
        int[] keyLengths = new int[recordsNumber];
        int[] valueLengths = new int[recordsNumber];

        for (int i = 0; i < recordsNumber; i++) {
            readInt(fileInputStream);
            valueLengths[i] = readInt(fileInputStream)*2;
            keyLengths[i] = readInt(fileInputStream);
        }

        for (int i = 0; i < recordsNumber; i++) {
            values[i] = readString(fileInputStream, valueLengths[i], "UTF-16LE");
        }

        for (int i = 0; i < recordsNumber; i++) {
            keys[i] = readString(fileInputStream, keyLengths[i], "US-ASCII");
        }

        Map<String, String> records = new HashMap<>((int) (recordsNumber * 1.6));
        for (int i = 0; i < recordsNumber; i++) {
            records.put(keys[i], values[i]);
        }

        return records;
    }

    private String readString(FileInputStream inputStream, int length, String encoding) throws IOException {
        byte[] bytes = new byte[length];
        inputStream.read(bytes);
        return new String(bytes, encoding);
    }

    private int readInt(FileInputStream inputStream) throws IOException {
        inputStream.read(byteBuffer);
        for (int i = 0; i < byteBuffer.length; i++) {
            byteBufferReverse[byteBufferReverse.length-1-i] = byteBuffer[i];
        }
        ByteBuffer wrapped = ByteBuffer.wrap(byteBufferReverse);
        return wrapped.getInt();
    }
}
