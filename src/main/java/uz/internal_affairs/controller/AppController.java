package uz.internal_affairs.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.io.OutputStream;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/app")
public class AppController {

    @GetMapping("/logo")
    public void getAppLogo(HttpServletResponse response){
        response.setContentType("image/png");
        //Browser tries to open it
        response.addHeader("Content-Disposition", "attachment;filename=" + "logo");

        try {
            OutputStream outputStream = response.getOutputStream();
            byte[] read = Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")).readAllBytes();
            outputStream.write(read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
