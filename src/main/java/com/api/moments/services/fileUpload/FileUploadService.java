package com.api.moments.services.fileUpload;

import com.api.moments.services.aws.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService implements IFileUploadService {

  @Autowired
  private AwsService awsService;

  @Override
  public String upload(MultipartFile file, String fileName) {
    var fileUrl = "";

    try {
      fileUrl = awsService.upload(file, fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileUrl;
  }
}
