package ru.ifmo.swfoc;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DATWriterTest {
    @Test
    public void testSaveToFile() throws IOException {
        Map<String, String> records = new HashMap<>();
        records.put("LANGUAGE_ENGLISH", "English");
        DATWriter datWriter = new DATWriter(new File("testData.dat"));
        datWriter.saveToFile(records);
    }

    @Test
    public void testSaveAndReadFromFile() throws IOException {
        Map<String, String> records = new HashMap<>();
        records.put("LANGUAGE_ENGLISH", "English");
        File file = new File("testData.dat");
        DATWriter datWriter = new DATWriter(file);
        datWriter.saveToFile(records);
        DATReader datReader = new DATReader(file);
        Map<String, String> recordsRead = datReader.readFile();
        Assert.assertEquals("English", recordsRead.get("LANGUAGE_ENGLISH"));
    }

    @Test
    public void successfulReadAndSaveOriginalMasterTextFile() throws IOException {
        DATReader datReaderMaster = new DATReader(new File("mastertextfile_english.dat"));
        File file = new File("mastertextfile_test.dat");
        DATWriter datWriter = new DATWriter(file);
        datWriter.saveToFile(datReaderMaster.readFile());
        DATReader datReader = new DATReader(file);
        Map<String, String> recordsRead = datReader.readFile();
        Assert.assertEquals("English", recordsRead.get("LANGUAGE_ENGLISH"));
    }
}
