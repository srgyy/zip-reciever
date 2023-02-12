package com.example.zipreciever.service;

import java.io.OutputStream;

public interface LogService {
    String getLog();

    void getLogsAsZip(OutputStream outputStream);
}
