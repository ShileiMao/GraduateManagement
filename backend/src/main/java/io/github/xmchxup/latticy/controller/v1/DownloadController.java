package io.github.xmchxup.latticy.controller.v1;

import io.github.xmchxup.latticy.module.file.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    ServletContext servletContext;

    @GetMapping("")
    public StreamingResponseBody downloadFile(HttpServletResponse response, @RequestParam String filePath) {
        FileHelper fileHelper = new FileHelper();
        String fileName = fileHelper.getFileName(filePath);
        String path = fileHelper.getDirPath(filePath);

        File file = new File(path);

        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"");

        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[1024];
            InputStream inputStream = new FileInputStream(file);
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        };
    }
}
