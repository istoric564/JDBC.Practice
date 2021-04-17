package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;

public class BlobRunner {
    public static void main(String[] args) throws SQLException, IOException {
//        blob - bytea
//        clob - TEXT
//        saveImage();
        getImage();
    }

    private static void getImage() throws SQLException, IOException {
        var sql = """
                SELECT image
                FROM aircraft
                WHERE id = ?
                """;
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setInt(1,1);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()){
                var image = resultSet.getBytes("image");
                Files.write(Path.of("resources", "boeingImageNew.jpg"), image, StandardOpenOption.CREATE);
            }
        }

    }

    private static void saveImage() throws SQLException, IOException {
        var sql = """
                UPDATE aircraft
                SET image = ?
                WHERE id = 1
                """;

        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setBytes(1, Files.readAllBytes(Path.of("resources","boeingImage.jpg")));
            prepareStatement.executeUpdate();
        }
    }
}
//    private static void saveImage() throws SQLException, IOException {
//        var sql = """
//                UPDATE aircraft
//                SET image = ?
//                WHERE id = 1
//                """;
//
//        try (var connection = ConnectionManager.open();
//             var prepareStatement = connection.prepareStatement(sql)) {
//            var blob = connection.createBlob();
//            blob.setBytes(1, Files.readAllBytes(Path.of("resources","boeingImage.jpg")));
//
//            prepareStatement.setBlob(1, blob);
//            prepareStatement.executeUpdate();
//        }
//    }
