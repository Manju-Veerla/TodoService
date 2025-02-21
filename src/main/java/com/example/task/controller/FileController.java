package com.example.task.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/fileapi/v1")
@Slf4j
public class FileController {

  private final String UPLOAD_DIR = "C:\\Learning\\";

  @PostMapping("/upload")
  public boolean upload(@RequestParam("file")MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    log.info("File name is " + fileName);
    file.transferTo(new File(UPLOAD_DIR+fileName));
  return true;
}

@GetMapping("/download/{fileName}")
public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) throws IOException {
  byte[] fileData = Files.readAllBytes(new File(UPLOAD_DIR+fileName).toPath());
  HttpHeaders headers = new HttpHeaders();
  headers.setContentType(MediaType.IMAGE_PNG);
  return new ResponseEntity<byte[]>(fileData,headers, HttpStatus.OK);

}

}
