package lab12;
import java.io.*;
import java.net.*;

public class task4 {
    public static void main(String[] args) throws IOException {
        saveImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9maTWxby8pygNe2xozRNX80y6EjRM8FkPNBVEnVtm&s");
    }

    public static void saveImage(String imageUrl) {
        URL url;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            System.out.println("Wrong URL");
            e.addSuppressed(e);
            return;
        }
        String fileName = url.getFile();
        String destName = "./figures" + fileName.substring(fileName.lastIndexOf("/"));
        System.out.println(destName);
        try (InputStream is = url.openStream()) {
            OutputStream os = new FileOutputStream(destName);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.addSuppressed(e);
        }
        }
}
