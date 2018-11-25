package com.akqa.batch.factories;

import com.akqa.batch.FileReader;
import com.akqa.batch.impl.FileReaderImpl;

public class FileReaderFactory {

    public static FileReader newInstance(String fileReader) {

        if (fileReader.equals("StreamFileReader")) {
            return new FileReaderImpl();
        }

        return null;
    }
}
