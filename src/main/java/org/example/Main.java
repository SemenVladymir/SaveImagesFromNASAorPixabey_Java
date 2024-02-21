package org.example;
import java.util.Scanner;

import static org.example.SaveImages.*;
import static org.example.SaveImages.saveImage;

public class Main {
    public static void main(String[] args) {
        try {

            String startDate, endDate, item;
            String requestUrl = "";
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input number which do you want to get imeges:\n1. NASA\n2. Pixabey\n");
            int choose = System.in.read();
            if (choose==49) {
                System.in.skip(System.in.available());
                System.out.println("For get images from Pixabey");
                System.out.println("Input the start date in format YYYY-MM-DD");
                startDate = scanner.nextLine();
                System.out.println("Input the end date in format YYYY-MM-DD");
                endDate = scanner.nextLine();
                if (startDate.length() > 8 && endDate.length() > 8)
                    requestUrl = "https://api.nasa.gov/planetary/apod?start_date=" + startDate + "&end_date=" + endDate +
                        "&api_key=TV6SywKlBGFqDU13ZkC5drdqKnskqlyUE5H9eTZW";
                else
                    System.out.println("You inputed wrong data!");
            }
            else {
                System.in.skip(System.in.available());
                System.out.println("For get images from Pixabey");
                System.out.println("Input text for searching");
                item = scanner.nextLine();
                System.out.println("Input number which images you want to search");
                choose = scanner.nextInt();
                if (item.length() > 3 && choose > 0 && choose < 100)
                    requestUrl = "https://pixabay.com/api/?key=39206396-a3d0261b98314ee7c13677bfd&q="+ item +
                            "&image_type=photo&per_page=" + choose;
                else
                    System.out.println("You inputed wrong data!");
            }

            String jsonResponse = getJsonResponse(requestUrl);
            String[] imageUrls = getImageUrls(jsonResponse);
            if (imageUrls.length > 0) {
                System.out.println("Found " + imageUrls.length + " images.");
                for (int i = 0; i < imageUrls.length; i++) {
                    System.out.println("Saving image from " + imageUrls[i]);
                    String filePath = "D:\\Images\\" + (i + 1) + ".jpg";
                    saveImage(imageUrls[i], filePath);
                    System.out.println("Image saved to " + filePath);
                }
            } else {
                System.out.println("No images found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}