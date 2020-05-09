package ru.ifmo.swfoc;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DATReaderTest {
    @Test
    public void ableToReadSomeRecord() throws IOException {
        File file = new File("mastertextfile_english.dat");
        DATReader datReader = new DATReader(file);
        Map<String, String> records = datReader.readFile();
        assertEquals("English", records.get("LANGUAGE_ENGLISH"));
    }
}
