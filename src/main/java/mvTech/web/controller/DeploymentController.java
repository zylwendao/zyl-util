package mvTech.web.controller;

import mvTech.UserRepository;
import mvTech.enumDemo.bean.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "zzz")
public class DeploymentController {
    public static final MediaType APPLICATION_MP3 = MediaType.valueOf("audio/mp3");


    @Autowired
    UserRepository userRepository;
    @GetMapping("/love")
    @ResponseBody
    public String love() throws IOException {
        List<User> all = userRepository.findAll();
        List<User> res = userRepository.find1();
        return "大萌子 你好";
    }

    @GetMapping("/playMusic")
    public ResponseEntity<Resource> playMusic() throws IOException {
//        String mp3Path = "/Users/zyl/Downloads/music.mp3";
        String mp3Path = "/Users/zyl/Desktop/123.mp3";
        File mp3File = new File(mp3Path);
        ByteArrayResource resource = new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(mp3File)));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_MP3);
        headers.setContentLength(resource.contentLength());
        headers.set("Content-Disposition", "inline; filename=" + mp3File.getName());
//        headers.set("Content-Disposition", "attachment; filename=" + mp3File.getName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(mp3File.length())
                .body(resource);
    }

}