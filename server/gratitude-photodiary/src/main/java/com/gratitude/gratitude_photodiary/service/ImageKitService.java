package com.gratitude.gratitude_photodiary.service;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class ImageKitService {

    @Value("${imagekit.private_key}")
    private String privateKey;

    private static final String IMAGEKIT_API_URL = "https://upload.imagekit.io/api/v1/files/upload";

    public String uploadImageToImageKit(String base64File, String fileName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String authHeader = "Basic " + Base64.getEncoder().encodeToString((privateKey + ":").getBytes());

        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", base64File)
                .addFormDataPart("fileName", fileName)
                .addFormDataPart("useUniqueFileName", "true")
                .build();

        Request request = new Request.Builder()
                .url(IMAGEKIT_API_URL)
                .post(body)
                .addHeader("Authorization", authHeader)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body() != null ? response.body().string() : null;
                return new JSONObject(responseBody).getString("url");
            } else {
                throw new IOException("Failed to upload image: " + response.message());
            }
        }
    }
}
