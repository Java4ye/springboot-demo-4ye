package top.ryzeyang.demo.controller;

import cn.hutool.core.util.StrUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.ryzeyang.demo.model.dto.FileDTO;
import top.ryzeyang.demo.utils.CommonResultUtil;
import top.ryzeyang.demo.utils.MinioUtil;
import top.ryzeyang.demo.common.api.CommonResult;
import top.ryzeyang.demo.common.exceptions.AllMinioException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Java4ye
 * @date 2020/12/23 下午 02:04
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@Controller
@Slf4j
public class MinioController {

    @GetMapping("")
    public String indexPage() {
        return "index";
    }

    /**
     * 上传成功后回显图片
     *
     * @param files
     * @return
     * @throws AllMinioException
     */
    @ResponseBody
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult<String> upload(@RequestParam MultipartFile[] files) throws AllMinioException {
        if (files.length == 0) {
            return CommonResultUtil.clientError("上传文件数量为0");
        }
        for (MultipartFile file : files) {
            uploadFile(file, false);
        }
        return CommonResultUtil.success(files.length + "");
    }

    @ResponseBody
    @PostMapping(value = "/upload/urls", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult<Object> uploadAndShowUrl(@RequestParam MultipartFile[] files) throws AllMinioException {
        if (files.length == 0) {
            return CommonResultUtil.clientError("上传文件数量为0");
        }
        List<FileDTO> list = new ArrayList<>();
        for (MultipartFile file : files) {
            FileDTO fileDTO = uploadFile(file, true);
            list.add(fileDTO);
        }
        return CommonResultUtil.success(list);
    }

    @ResponseBody
    @GetMapping(value = "/url")
    public CommonResult<List<String>> getUrls() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, InvalidExpiresRangeException {
        List<Bucket> buckets = MinioUtil.minioClient.listBuckets();
        List<String> lists = new ArrayList<>();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.name());
            System.out.println("---------------");
            Iterable<Result<Item>> results = MinioUtil.minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucket.name()).recursive(true).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());
                String objectUrl = MinioUtil.minioClient.getObjectUrl(bucket.name(), item.objectName());
                lists.add(objectUrl);
                String presignedObjectUrl = MinioUtil.minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucket.name())
                                .object(item.objectName())
                                .expiry(2, TimeUnit.HOURS)
                                .build());
                lists.add(presignedObjectUrl);
            }
        }
        return CommonResultUtil.success(lists);
    }


    private FileDTO uploadFile(MultipartFile file, boolean showUrl) throws AllMinioException {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new AllMinioException("Upload Failed", e.getCause());
        }
        String fileName = file.getOriginalFilename();
        String fileType = StrUtil.subAfter(fileName, ".", true);
        String contentType = file.getContentType();
        long size = file.getSize();
        MinioUtil.putObject(fileType, fileName, inputStream, size, contentType);
        if (showUrl) {
            String temporaryUrl = MinioUtil.getTemporaryUrl(fileType, fileName);
            return FileDTO.builder().url(temporaryUrl).name(fileName).build();
        }
        return null;
    }


}
