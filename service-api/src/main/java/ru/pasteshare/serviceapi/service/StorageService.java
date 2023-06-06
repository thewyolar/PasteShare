package ru.pasteshare.serviceapi.service;

import java.io.IOException;

public interface StorageService {
    void savePaste(String filename, String content) throws IOException;
}
