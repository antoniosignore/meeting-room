package com.akqa.batch;

import java.io.IOException;

public interface FileReader {

    MeetingRequestsCollector process(String fileName) throws IOException;

}
