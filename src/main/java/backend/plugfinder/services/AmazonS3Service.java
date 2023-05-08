package backend.plugfinder.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class AmazonS3Service {

    private final AmazonS3 amazonS3Client;

    private final String bucketName;

    public AmazonS3Service(AmazonS3 amazonS3Client, @Value("${aws.s3.bucket-name}") String bucketName) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }

    /**
     * Uploads a file to the S3 bucket.
     *
     * @param name The name of the file to upload.
     * @param file_base64 The file to upload in base 64.
     */
    public String upload_file(String name, String file_base64) {
        /* Decodifiquem el contingut del fitxer */
        byte[] content = DatatypeConverter.parseBase64Binary(file_base64);

        PutObjectResult result = amazonS3Client.putObject(new PutObjectRequest(bucketName, name + "-photo", new ByteArrayInputStream(content), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        /* Retornem la URL pública del fitxer */
        return  String.valueOf(amazonS3Client.getUrl(bucketName, name + "-photo"));
    }

    /**
     * Deletes a file from the S3 bucket.
     *
     * @param file_name The name of the file to delete.
     */
    public void delete_file(String file_name) {
        amazonS3Client.deleteObject(bucketName, file_name + "-photo");
    }
}

