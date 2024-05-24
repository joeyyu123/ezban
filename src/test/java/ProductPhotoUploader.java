import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ProductPhotoUploader {
    public static void main(String[] args) throws IOException {
        String url = "jdbc:mysql://localhost:3306/ezban";
        String user = "root";
        String password = "a123456";

        String filePath = "C:/Users/TMP14/Pictures/product";


        File folder = ResourceUtils.getFile(filePath);
        File[] files = folder.listFiles();
        for (File file : files) {

            if (file.isFile() && file.getName().endsWith(".jpg")) {
                // 讀取圖片檔案
                String firstpart = file.getName().split("_")[0];
                int productNo = Integer.parseInt(firstpart.substring("product".length()));
                byte[] imageData = Files.readAllBytes(file.toPath());
                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    String sql = "INSERT INTO product_img (product_no, product_img) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, productNo);
                        pstmt.setBytes(2, imageData);
                        pstmt.executeUpdate();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
