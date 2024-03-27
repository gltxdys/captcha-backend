package com.youlai.system.controller;

import com.youlai.system.common.result.Result;
import com.youlai.system.mapper.StorageMapper;
import com.youlai.system.model.dto.FileInfo;
import com.youlai.system.service.OssService;
import com.youlai.system.service.impl.oss.DatabaseOssService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation; 
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Tag(name = "07.文件接口")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final OssService ossService;
    @Autowired
    StorageMapper storageMapper;
    @PostMapping
    @Operation(summary = "文件上传")
    public Result<FileInfo> uploadFile(
            @Parameter(description ="表单文件对象") @RequestParam(value = "file") MultipartFile file
    ) {
        FileInfo fileInfo = ossService.uploadFile(file);
        return Result.success(fileInfo);
    }

    @GetMapping("/pic")
    @Operation(summary = "文件获取")
    @SneakyThrows
    public ResponseEntity<byte[]> getFile(@RequestParam(value = "id") Long id) throws IOException {
        byte[] file = storageMapper.selectById(id).getStorageData();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    @DeleteMapping
    @Operation(summary = "文件删除")
    @SneakyThrows
    public Result deleteFile(@Parameter(description ="文件路径") @RequestParam String filePath) {
        boolean result = ossService.deleteFile(filePath);
        return Result.judge(result);
    }
}
