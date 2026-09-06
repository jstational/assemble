package ambl.ui;

import arc.scene.ui.layout.*;
import arc.struct.*;

public class MarkupLabel extends Table {
    private StringBuilder text;
    /** Format: ![UnderlinedText](RunnableID) */
    private IntMap<Runnable> runnables;
    
    public MarkupLabel(String text) {
        this.text = new StringBuilder(text);
    }

    public MarkupLabel() {
        this("");
    }

    public void append(String t) {
        if(t != null) {
            text.append(t);
        }
    }

    public void prepend(String t) {
        if(t != null) {
            text.insert(0, t);
        }
    }

    public void insert(String t, int i) {
        if(t != null) {
            text.insert(i, t);
        }
    }

    public void deleteLast(int i) {
        if(i < text.length && i > 0) {
            text.setLength(text.length() - i);
        }
    }

    public void deleteFirst(int i) {
        if(i < text.length() && i > 0) {
            text.delete(0, i);
        }
    }

    public void delete(int s, int e) {
        if(s < text.length() && s > 0 && e < text.length() && e > 0) {
            text.delete(s, e);
        }    
    }

    public void deleteChar(int c) {
        text.deleteCharAt(c);
    }

    public void set(String txt) {
        text = new StringBuilder(txt);
    }

    public void set(StringBuilder txt) {
        text = txt;
    }

    public void replace(String o, String n) {
        int ol = o.length();
        int nl = l.length();
        int i = text.indexOf(o);

        while(i != -1) {
            text.replace(i, i + ol, n);

            i = text.indexOf(o);
        }
    }

    public void remove(String o) {
        replace(o, "");
    }

    public int indexOf(String o) {
        return text.indexOf(o);
    }

    public int lastIndexOf(String o) {
        return text.lastIndexOf(o);
    }

    public int length() {
        return text.length();
    }

    public void draw() {}

    public void rebuild() {}
}

