package com.example.demo.AWSController;

import com.example.demo.Service.AWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AWSController {

    @Autowired
    private AWSService awsService;

    //Create list in the bucket
    @GetMapping("createAllFilesBucket")
    public String createAllFilesBucket() {
        List<String> files = awsService.listAllObjects(awsService.getS3BucketName());
        StringBuilder allFiles = new StringBuilder();
        allFiles.append("List of files available in bucket").append(awsService.getS3BucketName());
        for (String filename :files)
            allFiles.append(files).append("\n");
        return allFiles.toString();
    }

    //Delete list  in the bucket
    @GetMapping("/delete/{fileToDelete}")
    public String deleteFileBucket(@PathVariable String fileToDelete) {
        awsService.deleteObject(awsService.getS3BucketName(), fileToDelete);
        return fileToDelete + " has been deleted successfully from " + awsService.getS3BucketName();
    }
}
