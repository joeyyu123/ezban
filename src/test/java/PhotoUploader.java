import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PhotoUploader {
    public static void main(String[] args) throws IOException {
        String url = "jdbc:mysql://localhost:3306/ezban";
        String user = "root";
        String password = "a123456";

        String filePath = "C:/Users/TMP14/Downloads/event-photo";


        File folder = ResourceUtils.getFile(filePath);
        File[] files = folder.listFiles();
        for (File file : files) {

            if (file.isFile() && file.getName().endsWith(".jpg")) {
                // 讀取圖片檔案
                int eventNo = Integer.parseInt(file.getName().split("\\.")[0]);

                byte[] imageData = Files.readAllBytes(file.toPath());
                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    String sql = "UPDATE event SET event_img = ? WHERE event_no = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setBytes(1, imageData);
                        pstmt.setInt(2, eventNo);
                        pstmt.executeUpdate();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
}