package pl.grapeup.awsrekognitionpoc.facecomparison;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.grapeup.awsrekognitionpoc.facecomparison.exception.FaceComparisonException;

import java.nio.ByteBuffer;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class FaceComparisonService {

    private AmazonRekognition amazonRekognition;

    public Float compareFaces(ByteBuffer face1, ByteBuffer face2) {
        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(new Image().withBytes(face1))
                .withTargetImage(new Image().withBytes(face2));

        CompareFacesResult result = amazonRekognition.compareFaces(request);
        List<CompareFacesMatch> faceMatches = result.getFaceMatches();
        List<ComparedFace> unmatchedFaces = result.getUnmatchedFaces();
        log.info("Unmatched faces {}", unmatchedFaces.size());
        if (faceMatches.size() > 1) {
            throw new FaceComparisonException("Too many faces detected!");
        } else if (CollectionUtils.isNullOrEmpty(faceMatches)) {
            return (float) 0;
        } else {
            Float similarity = faceMatches.get(0).getSimilarity();
            log.info("Similarity {}", similarity);
            return similarity;
        }
    }
}
