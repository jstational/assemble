package ambl.sprites;

import java.awt.image.BufferedImage;

public class Pixmap {
    int[] pixels;
    int width, height;
    boolean alpha;

    Pixmap(int[] pixels, int width, int height, boolean alpha) {
        this.pixels = pixels == null ? new int[0] : pixels;
        this.width = width;
        this.height = height;
        this.alpha = alpha;
    }

    public Pixmap(int width, int height, boolean alpha) {
        this(new int[width * height], width, height, alpha);
    }

    public Pixmap(int width, int height) {
        this(w, h, false);
    }

    public Pixmap(boolean alpha) {
        this(0, 0, alpha);
    }

    public Pixmap() {
        this(0, 0);
    }

    public Pixmap(Pixmap other) {
        pixels = other.pixels;
        width = other.width;
        height = other.height;
        alpha = other.alpha;
    }

    public BufferedImage toBufferedImage() {
        return new BufferedImage(width, height, alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB).setRGB(0, 0, width, height, pixels, 0, width);
    }

    public int[] getPixelArray() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasAlpha() {
        return alpha;
    }

    public int getRawPixel(int x, int y) {
        if(x > width || y > height || x < 0 || y < 0) return 0;
        return pixels[x + (y * width)];
    }
}
