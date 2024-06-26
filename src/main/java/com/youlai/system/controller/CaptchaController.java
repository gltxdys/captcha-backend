package com.youlai.system.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youlai.system.common.result.Result;
import com.youlai.system.model.dto.FileInfo;
import com.youlai.system.model.entity.CaptchaType;
import com.youlai.system.model.entity.Record;
import com.youlai.system.model.vo.CaptchaDetailVo;
import com.youlai.system.model.vo.CaptchaTypeVo;
import com.youlai.system.model.vo.IdentifyVo;
import com.youlai.system.security.util.SecurityUtils;
import com.youlai.system.service.CaptchaTypeService;
import com.youlai.system.service.OssService;
import com.youlai.system.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RedissonClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;


@RestController
@RequestMapping("/api/v1/captcha")
@RequiredArgsConstructor
public class CaptchaController {
    private static String url="http://127.0.0.1:5000/upload";

    private static String getUrl="http://127.0.0.1:5000/check";

    private final OssService databaseOssService;

    private final RecordService recordService;

    private final RedissonClient redisson;

    private final CaptchaTypeService captchaTypeService;

    @SneakyThrows
    @PostMapping("/geetest3")
    public Result<String> geetest3( @RequestParam(value = "file") MultipartFile file){
        RestTemplate restTemplate = new RestTemplate();

        // 创建临时文件并传输文件内容
        File tempFile = new File(System.getProperty("user.dir")+FileUtil.FILE_SEPARATOR+UUID.randomUUID().toString(true) + ".jpg");
        // 假设file是一个已经存在的MultipartFile对象，包含了要上传的文件内容
        file.transferTo(tempFile);

        // 构建请求体，包含文件和其他参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(tempFile)); // 文件参数

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建请求实体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送 POST 请求
        ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);

        // 处理响应
        System.out.println(response.getBody());
        tempFile.delete();
        // 清理临时文件
        return Result.success(response.getBody());
    }

    @GetMapping("check")
    public Result<IdentifyVo> checkPic(@RequestParam("url") String picUrl,@RequestParam("typeId") int typeId){
        RestTemplate restTemplate =new RestTemplate();
        String req= StrUtil.format("{}?url={}",getUrl,picUrl);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(req, String.class);
        JSONObject object = JSONUtil.parseObj(forEntity.getBody());
        Path imgPath = object.get("img_path", Path.class);
        String result = object.get("data", String.class);
        Double time = Math.floor(object.get("time",Double.class)*1000);
        byte[] data = FileUtil.readBytes(imgPath);
        MultipartFile file =new MockMultipartFile(imgPath.getFileName().toString(),data);
        FileInfo fileInfo = databaseOssService.uploadFile(file);
        CaptchaType captchaType = captchaTypeService.getBaseMapper().selectById(typeId);
        //TODO：保存识别记录为异步
        Record build = Record.builder()
                .picUrl(fileInfo.getUrl())
                .typeId(typeId)
                .score(captchaType.getPrice())
                .status(1)
                .result(result)
                .userId(SecurityUtils.getUserId())
                .spend(time.intValue())
                .build();
        recordService.save(build);
        return Result.success(new IdentifyVo().setResult(result).setPicUrl(fileInfo.getUrl()));
    }

    @GetMapping("all/type")
    public Result<List<CaptchaTypeVo>> getAllCaptchaType(){
        return Result.success(captchaTypeService.getAllCaptchaTypeVoList());
    }

    @GetMapping("/type")
    public Result<CaptchaDetailVo> getCaptchaTypeById(@RequestParam("id") int id){
        CaptchaType byId = captchaTypeService.getById(id);
        if(ObjectUtil.isNull(byId)){
            return Result.failed("类型不存在！");
        }
        return Result.success(new CaptchaDetailVo(byId));
    }

    @GetMapping("all/detail")
    public Result<List<CaptchaDetailVo>> getAllCaptchaDetail(){
        return Result.success(captchaTypeService.getAllCaptchaDetailVoList());
    }

    @PostMapping("add/type")
    public Result addCaptchaType(@RequestBody CaptchaDetailVo captchaTypeVo){
        if(ObjectUtil.isNotNull(captchaTypeService.getById(captchaTypeVo.getTypeId()))){
            return Result.failed("类型已存在");
        }
        captchaTypeService.addCaptchaType(captchaTypeVo);
        return Result.success();
    }
    @PostMapping("update/type")
    public Result updateCaptchaType(@RequestBody CaptchaDetailVo captchaTypeVo){
        captchaTypeService.updateCaptchaType(captchaTypeVo);
        return Result.success();
    }

    @PostMapping("del/type")
    public Result delCaptchaType(@RequestBody List<Integer> typeIds){
        captchaTypeService.getBaseMapper().deleteBatchIds(typeIds);
        return Result.success();
    }
}
