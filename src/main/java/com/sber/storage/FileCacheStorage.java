package com.sber.storage;


import com.sber.exception.CacheStorageException;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileCacheStorage implements CacheStorage {
    private final String dir;
    private final boolean isZip;

    public FileCacheStorage(String dir, boolean isZip) {
        this.dir = dir;
        this.isZip = isZip;
        new File(dir).mkdirs();
    }

    @Override
    public void save(String key, Object value) throws CacheStorageException {
        try (ObjectOutputStream outputStream = createObjectOutputStream(key)) {
            outputStream.writeObject(value);
        } catch (IOException e) {
            throw new CacheStorageException("Error saving to file cache");
        }
    }

    @Override
    public Object load(String key) {
        try (ObjectInputStream inputStream = createObjectInputStream(key)) {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    private ObjectOutputStream createObjectOutputStream(String key) throws IOException {
        OutputStream outputStream = createOutputStream(key);
        return new ObjectOutputStream(outputStream);
    }

    private ObjectInputStream createObjectInputStream(String key) throws IOException {
        InputStream inputStream = createInputStream(key);
        return new ObjectInputStream(inputStream);
    }

    private OutputStream createOutputStream(String key) throws IOException {
        OutputStream outputStream = new FileOutputStream(new File(dir, key));
        if (isZip) {
            outputStream = new GZIPOutputStream(outputStream);
        }
        return outputStream;
    }

    private InputStream createInputStream(String key) throws IOException {
        InputStream inputStream = new FileInputStream(new File(dir, key));
        if (isZip) {
            inputStream = new GZIPInputStream(inputStream);
        }
        return inputStream;
    }
}
