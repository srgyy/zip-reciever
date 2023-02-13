package com.example.zipreciever.service;

import com.example.zipreciever.model.Person;

import java.io.OutputStream;
import java.util.List;

public interface LogService {
    List<Person> getLog();

    void getLogsAsZip(OutputStream outputStream);
}
