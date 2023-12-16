package com.aws.awss3.Controller;

import com.aws.awss3.Service.AWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AWSController {

    @Autowired
    private AWSService awsService;
    @GetMapping("createAllFilesBucket")
    public String createAllFilesBucket() {
        List<String> files = awsService.listAllObjects(awsService.getS3BucketName());
        StringBuilder allFiles = new StringBuilder();
        allFiles.append("List of files available in bucket").append(awsService.getS3BucketName());
        for (String filename :files)
            allFiles.append(files).append("\n");
        return allFiles.toString();
    }

    @GetMapping("/delete/{fileToDelete}")
    public String deleteFileBucket(@PathVariable String fileToDelete) {
        awsService.deleteObject(awsService.getS3BucketName(), fileToDelete);
        return fileToDelete + " has been deleted successfully from " + awsService.getS3BucketName();
    }
}
