package com.youlai.system.service.impl.oss;

import com.youlai.system.mapper.StorageMapper;
import com.youlai.system.model.dto.FileInfo;
import com.youlai.system.model.entity.Storage;
import com.youlai.system.service.OssService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;

/**
* @description:  <p>使用数据库存储数据在storage表</p>
* @author Lovejj
* @date  2024/3/25 下午3:11
* @version 1.0
*/
@Component
@ConditionalOnProperty(value = "oss.type", havingValue = "database")
@ConfigurationProperties(prefix = "oss.database")
@RequiredArgsConstructor
@Data
public class DatabaseOssService implements OssService {
    @Autowired
    private StorageMapper storageMapper;


    @SneakyThrows
    public FileInfo uploadFile(MultipartFile file) {
        Storage storage = null;
        storage = Storage.builder().storageData(file.getBytes()).build();
        storageMapper.insert(storage);
        String id= String.valueOf(storage.getId());
        FileInfo fileInfo=new FileInfo();
        fileInfo.setName(id);
        fileInfo.setUrl("http://127.0.0.1:8989/api/v1/files/pic?id="+id);
        return fileInfo;
    }

    @SneakyThrows
    public byte[] getFile(Long id){
        return storageMapper.selectById(id).getStorageData();
    }
    public boolean deleteFile(String filePath) {
        return false;
    }
}
