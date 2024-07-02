package mvTech.web.controller.seeScreen;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenshotController {

    @PostMapping("/process-screenshot")
    public String processScreenshot(@RequestBody String imageData) {
        // Here you can handle the image data (e.g., save it to a file, process it further)
        System.out.println("Received screenshot data. Length: " + imageData.length());
        return "Screenshot processed successfully!";
    }
}