package ru.ifmo.swfoc;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class DATReaderTest {
    @Test
    public void readNumberOfRecords() throws IOException {
        File file = new File("mastertextfile_english.dat");
        DATReader datReader = new DATReader(file);
        int i = datReader.readFile();
        System.out.println(i);
    }
}
