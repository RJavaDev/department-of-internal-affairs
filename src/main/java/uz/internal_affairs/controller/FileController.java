package uz.internal_affairs.controller;

import jakarta.activation.MimetypesFileTypeMap;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.sevice.FileService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Value("${upload.folder.img}")
    private String IMG_FOLDER_PATH;

    @Value("${upload.folder.file}")
    private String FILE_FOLDER_PATH;

    @Autowired
    private FileService fileService;

    @GetMapping("/citizen/img/{id}")
    public void img(HttpServletResponse response,
                    @PathVariable(value = "id") Long fileId) throws FileNotFoundException {

        FileDto imgFile = fileService.getCitizenFile(fileId);
        if(imgFile == null) return;

        File folder = new File(IMG_FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(IMG_FOLDER_PATH, imgFile.getName());
        if (!file.exists()) throw new FileNotFoundException();

        /*Finding MIME type for explicitly setting MIME*/
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        response.setContentType(mimeType);

        //Browser tries to open it
        response.addHeader("Content-Disposition", "attachment;filename=" + imgFile.getOriginalName());

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/citizen/file/{id}")               // fingerprint file
    public void file(HttpServletResponse response,
                    @PathVariable(value = "id") Long fileId) throws FileNotFoundException {

        FileDto fPrintFile = fileService.getCitizenFile(fileId);
        if(fPrintFile == null) return;

        File folder = new File(FILE_FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(FILE_FOLDER_PATH, fPrintFile.getName());
        if (!file.exists()) throw new FileNotFoundException();

        /*Finding MIME type for explicitly setting MIME*/
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        response.setContentType(mimeType);

        //Browser tries to open it
        response.addHeader("Content-Disposition", "attachment;filename=" + fPrintFile.getOriginalName());

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
