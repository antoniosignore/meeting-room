package com.akqa.batch.impl;

import com.akqa.batch.FileReader;
import com.akqa.batch.MeetingRequestsCollector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReaderImpl implements FileReader {

    @Override
    public MeetingRequestsCollectorImpl process(String fileName) throws IOException {

        return Files.lines(Paths.get(fileName))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(MeetingRequestsCollectorImpl::new,
                        MeetingRequestsCollector::accept,
                        MeetingRequestsCollector::combine);

    }
}
