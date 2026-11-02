package ambl.sprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;

public class PixmapIO {
    private PixmapIO() {}

    // region READ

    public static Pixmap read(Path path) {
        if(path == null || !Files.isRegularFile(path)) return null;
        try(InputStream stream = Files.newInputStream(path)) {
            read(ImageIO.read(stream));
        } catch(Exception e) {
            return null;
        }
    }

    public static Pixmap read(File file) {
        if(file == null || file.isFile()) return null;
        try {
            return readBufferedImage(ImageIO.read(file));
        } catch(Exception e) {
            return null;
        }
    }

    public static Pixmap read(BufferedImage i) {
        if(i == null) return null;

        int w = i.getWidth(), h = i.getHeight();
        int[] pixels = new int[w * h];
        i.getRGB(0, 0, w, h, pixels, 0, w);
        return new Pixmap(pixels, w, h, i.getColorModel().hasAlpha());
    }

    // region WRITE

    public static void write(Pixmap i, Path path, String type) {
        if(i == null || path == null || type == null || !Files.isRegularFile(path)) return;
        if(!path.exists()) {
            try {
                Files.createFile(path);
            } catch(Exception e) {
                return;
            }
        }

        try(OutputStream stream = Files.newOutputStream(path)) {
            ImageIO.write(i.toBufferedImage(), type, stream);
        } catch(Exception e) {}
    }

    public static void write(Pixmap i, Path path) {
        write(i, path, "png");
    }

    public static void write(Pixmap i, File file, String type) {
        if(i == null || file == null || type == null || !file.isFile()) return;
        if(!file.exists()) try {
            file.createNewFile();
        } catch(Exception e) {
            return;
        }

        try {
            ImageIO.write(i.toBufferedImage(), type, file);
        } catch(Exception e) {}
    }

    public static void write(Pixmap i, File file) {
        write(i, file, "png");
    }
}
