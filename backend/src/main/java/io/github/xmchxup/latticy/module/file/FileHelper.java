package io.github.xmchxup.latticy.module.file;

import java.io.File;
import java.io.*;

public class FileHelper {
    private static final String BASE_PATH = System.getProperty("user.dir") + "/assets";
    public String getFileName(String path) {
        String[] splitted = path.split("/");
        if(splitted.length == 0) {
            return "";
        }
        return splitted[splitted.length - 1];
    }

    public String getDirPath(String relativePath) {
        String path = BASE_PATH + File.separator + relativePath;
        return path;
    }

    public String getRelativePath(String path) {
        if(path.startsWith(BASE_PATH)) {
            return path.substring(BASE_PATH.length());
        }
        return path;
    }

    public void makeDirsIfNotExists(String dirPath) {
        java.io.File file = new java.io.File(dirPath);
        if (file.exists() && file.isDirectory()) {
            return;
        }

        file.mkdirs();
    }

    public void guaranteeDir(String relativeDir) {
        String dirPath = getDirPath(relativeDir);
        makeDirsIfNotExists(dirPath);
    }

    public boolean fileExists(String path) {
        java.io.File file = new File(path);
        if(file.isFile() && file.exists()) {
            return true;
        }
        return false;
    }

    public boolean writeBytes(String path, byte[] bytes, boolean append) throws IOException {
        java.io.File file = new File(path);
        if(fileExists(path) && !append) {
            throw new IOException("文件已存在");
        }

        if(!fileExists(path) && !file.createNewFile()) {
            throw new IOException("保存失败");
        }

        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        return true;
    }

    public byte[] readBytes(String path) throws IOException {
        java.io.File file = new File(path);
        if(!fileExists(path)) {
            throw new IOException("文件不存在");
        }
        InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        byte[] buffer = new byte[1024];
        int length = 0;
        while( (length = inputStream.read(buffer, 0, 1024)) > 0) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toByteArray();
    }

}
