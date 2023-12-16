package com.aws.awss3.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model;

import io.lettuce.core.dynamic.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AWSService {
    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @Autowired
    private AmazonS3 amazonS3;

    public List<String> listAllObjects (String s3BucketName) {

        List<String> listOfObjects = new ArrayList<>();
        ObjectListing objectListing = amazonS3.listObjects(s3BucketName);
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            listOfObjects.add("Filename: " + objectSummary.getKey() + "LastModified: " + objectSummary.getLastModified() +
                    "Size: " + objectSummary.getSize());
        }
        return listOfObjects;
    }
}
