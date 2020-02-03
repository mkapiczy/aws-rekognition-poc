package pl.grapeup.awsrekognitionpoc.facecomparison;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.grapeup.awsrekognitionpoc.facecomparison.exception.FaceComparisonException;

import java.io.IOException;
import java.nio.ByteBuffer;

@Log4j2
@RestController
@AllArgsConstructor
public class FaceComparisonController {

    private FaceComparisonService faceComparisonService;

    @PostMapping("/")
    public Float handleCompareFaces(@RequestParam("photo1") MultipartFile photo1, @RequestParam("photo2") MultipartFile photo2) throws IOException {
        log.info("FaceComparisonController called");
        try {
            return faceComparisonService.compareFaces(ByteBuffer.wrap(photo1.getBytes()), ByteBuffer.wrap(photo2.getBytes()));
        } catch (IOException | FaceComparisonException e) {
            log.error(e);
            throw e;
        }
    }
}
