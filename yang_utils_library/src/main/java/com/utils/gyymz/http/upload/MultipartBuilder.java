package com.utils.gyymz.http.upload;


import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
* @作者    Yang
* @创建时间 2018/12/16 17:24
*
*/

public class MultipartBuilder {

    /**
     * 单文件上传构造.
     *
     * @param file 文件
     * @return MultipartBody
     */
    public static MultipartBody fileToMultipartBody(String mImagerId,File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("id",mImagerId);
        builder.addFormDataPart("uploadFile",file.getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }


    //    /*多图片上传*/
    public static  List<MultipartBody.Part> uploadFilesWithParts( Map<String, String> parameters, List<File> fileList) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String _key = entry.getKey();
            String _value = entry.getValue();
            builder.addFormDataPart(_key, _value);
        }
        for (int i = 0; i < fileList.size(); i++) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileList.get(i));
            builder.addFormDataPart("uploadFile", fileList.get(i).getName(), imageBody);//"shareImg"+i 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        return parts;
    }


}
