package io.github.xmchxup.latticy.common.util;

import io.github.xmchxup.latticy.module.file.FileHelper;

import java.io.File;
import java.io.IOException;

public class FileSaver {
    public String saveContentToPath(String dirPath, byte[] fileContent, String fileName) throws IOException {
        FileHelper fileHelper = new FileHelper();
        fileHelper.guaranteeDir(dirPath);
        String storagePath = fileHelper.getDirPath(dirPath);

        String filePath = storagePath + File.separator + fileName;

        if (fileHelper.writeBytes(filePath, fileContent, false)) {
            return dirPath + File.separator + fileName;
        }

        return null;
    }
}
