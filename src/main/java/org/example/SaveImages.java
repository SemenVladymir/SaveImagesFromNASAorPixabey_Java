package org.example;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SaveImages {

    public static void saveImage(String imageUrl, String filePath) throws Exception {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        File outputFile = new File(filePath);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
    }

    public static String getJsonResponse(String requestUrl) throws Exception {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");
        String response = "";
        if (scanner.hasNext()) {
            response = scanner.next();
        }
        inputStream.close();
        scanner.close();
        return response;
    }


    public static String[] getImageUrls(String jsonResponse) {
        String[] imageUrls = new String[0];
        if (jsonResponse != null && !jsonResponse.isEmpty()) {
            String[] tokens = jsonResponse.split(",");
            int count = 0;
            for (String token : tokens) {
                if (token.contains("\"webformatURL\"")) {
                    count++;
                }
            }
            imageUrls = new String[count];
            int index = 0;
            for (String token : tokens) {
                if (token.contains("\"webformatURL\"")) {
                    String url = token.substring(token.indexOf(":") + 2, token.length() - 1);
                    imageUrls[index] = url;
                    index++;
                }
            }
        }
        return imageUrls;
    }
}
