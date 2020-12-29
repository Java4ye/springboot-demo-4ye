package top.ryzeyang.demo.utils;

import cn.hutool.core.util.StrUtil;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.ryzeyang.demo.common.exceptions.AllMinioException;
import top.ryzeyang.demo.config.MinioConfig;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author Java4ye
 * @date 2020/12/23 下午 03:30
 * @微信公众号： Java4ye
 * @GitHub https://github.com/RyzeYang
 * @博客 https://blog.csdn.net/weixin_40251892
 */
@Component
@Slf4j
public class MinioUtil {

    public static MinioClient minioClient;

    @Autowired
    public MinioUtil(MinioConfig minioConfig) {
        minioClient= MinioClient.builder()
                .endpoint(minioConfig.getUrl())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }

    private MinioUtil(){
        throw new AssertionError(" Tool classes are not allowed to be created ");
    }

    /**
     * 判断bucket存不存在
     * @param bucketName
     * @return
     */
    public static boolean bucketExist(String bucketName) throws AllMinioException {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (MinioException|InvalidKeyException|IOException|NoSuchAlgorithmException e) {
            throw new AllMinioException("Call bucketExist method failed with arg: "+bucketName,e.getCause());
        }
    }

    /**
     * 创建bucket
     * @param bucketName
     */
    public static void makeBucket(String bucketName) throws AllMinioException {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (MinioException|InvalidKeyException|IOException|NoSuchAlgorithmException e) {
            throw new AllMinioException("Call makeBucket method failed with arg: "+bucketName,e.getCause());
        }
    }

    /**
     * 存储数据
     * @param bucketName
     * @param objectName
     * @param stream
     * @param size
     * @param contextType
     */
    public static void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws AllMinioException {
        if(bucketName!=null&&!bucketExist(bucketName)){
            makeBucket(bucketName);
        }
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                   stream, size, -1)
                   .contentType(contextType)
                   .build());
        } catch (MinioException|InvalidKeyException|IOException|NoSuchAlgorithmException e) {

            throw new AllMinioException("Upload Failed",e.getCause());
        }
    }

    public static String putAndGetUrl(String bucketName, String objectName, InputStream stream, long size, String contextType) throws AllMinioException {
        putObject(bucketName,objectName,stream,size,contextType);
//        getTemporaryUrl
        return "";
    }

    /**
     * 获取 临时的 图片链接
     * @param bucketName
     * @param objectName
     * @return
     * @throws AllMinioException
     */
    public static String getTemporaryUrl(String bucketName, String objectName) throws AllMinioException {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(2, TimeUnit.HOURS)
                            .build());
        }catch (MinioException|InvalidKeyException|IOException|NoSuchAlgorithmException e) {
            throw new AllMinioException(String.format("Call getTemporaryUrl method failed with arg: %s , %s",bucketName,objectName),e.getCause());
        }
    }

}
