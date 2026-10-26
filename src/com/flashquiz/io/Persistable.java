package com.flashquiz.io;

// Unit 4: Packages and interfaces - contract for entities that can be serialized/persisted
public interface Persistable {

    // Saves entity representation to destination path
    void save(String destinationPath) throws Exception;

    // Loads entity state from source path
    void load(String sourcePath) throws Exception;

    // Converts object to raw data record line
    String toDataRecord();
}
