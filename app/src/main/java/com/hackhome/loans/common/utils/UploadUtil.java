package com.hackhome.loans.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * desc:MultipartBody.Part 生成工具类
 * author: aragon
 * date: 2018/1/6 0006.
 */
public class UploadUtil {

    public static MultipartBody.Part prepareFilePart(String partName, File file) {

        RequestBody requestBody = RequestBody.create(MediaType.parse(guessMimeType(file.getName())), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }


    private static String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try
        {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


}
