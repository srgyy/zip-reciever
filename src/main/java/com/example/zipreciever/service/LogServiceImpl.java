package com.example.zipreciever.service;

import com.example.zipreciever.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
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


//    @Override
//    public List<String> getLog(){
//        File json = new File("/home/anon/IdeaProjects/zip-reciever/src/main/resources/people.json");
//        ObjectMapper mapper = new ObjectMapper();
//        List<String> list = null;
//        try {
//            list = mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//    }

    @Override
    public List<Person> getLog() {
        ObjectMapper mapper = new ObjectMapper();
        List<Person> personList = null;
        File json = new File("/home/anon/IdeaProjects/zip-reciever/src/main/resources/people.json");
        try {
            personList = mapper.readValue(
                    json,
                    mapper.getTypeFactory().constructCollectionType(List.class, Person.class)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return personList;
    }

//    @Override
//    public Map<String, String> getLog(){
//        ObjectMapper mapper = new ObjectMapper();
//        List<Person> personList = null;
//        File json = new File("/home/anon/IdeaProjects/zip-reciever/src/main/resources/people.json");
//        try {
//            personList = mapper.readValue(
//                    json,
//                    mapper.getTypeFactory().constructCollectionType(List.class, Person.class)
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Map<String, String> personMap = new HashMap<>();
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//
//        personList.forEach(person -> {
//            String key = null;
//            String value = null;
//            if (person.getGender().contains("Male")) key = "Male";
//            else if (person.getGender().contains("Female")) key = "Female";
//            else key = person.getGender();
//            try {
//                value = ow.writeValueAsString(person);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            personMap.put(key, value);
//        });

//        StringBuilder stringBuilder = new StringBuilder();
//        personList.forEach(person -> {
//            try {
//                stringBuilder.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(person));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        return personMap;
//    }

//    @Override
//    public String getLog() {
//        try {
//            File file = new File("/home/anon/IdeaProjects/zip-reciever/src/main/resources/log.txt");
//            BufferedReader br;
//            br = new BufferedReader(new FileReader(file));
//            StringBuilder stringBuilder = new StringBuilder();
//            String st;
//            while ((st = br.readLine()) != null) {
//                stringBuilder.append("\n" + st);
//            }
//            return stringBuilder.toString();
//        }
//        catch(IOException e){
//                throw new RuntimeException(e);
//        }
//    }
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
