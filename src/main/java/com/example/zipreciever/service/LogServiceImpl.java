package com.example.zipreciever.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class LogServiceImpl implements LogService {

//    "/home/anon/IdeaProjects/zip-reciever/src/main/resources/logs.zip"

    List<String> filesListInDir = new ArrayList<String>();

    @Override
    public String getLog() {
        try {
            File file = new File(
                    "/home/anon/IdeaProjects/zip-reciever/src/main/resources/log.txt");
            BufferedReader br;
            br = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null) {
                stringBuilder.append("\n" + st);
            }
            return stringBuilder.toString();
        }
        catch(IOException e){
                throw new RuntimeException(e);
        }
    }
        @Override
        public void getLogsAsZip (OutputStream outputStream){
            try {
                File dir = new File("/home/anon/IdeaProjects/zip-reciever/src/main/resources/logs/folder");
                populateFilesList(dir);
                ZipOutputStream zos = new ZipOutputStream(outputStream);
                for (String filePath : filesListInDir) {
                    ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1));
                    zos.putNextEntry(ze);
                    FileInputStream fis = new FileInputStream(filePath);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                    fis.close();
                }
                zos.finish();
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void populateFilesList (File dir) throws IOException {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) filesListInDir.add(file.getAbsolutePath());
                else populateFilesList(file);
            }
        }
    }
