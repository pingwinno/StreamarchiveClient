package com.pingwinno.streamarchive;

import java.io.Serializable;
import java.util.Objects;

public class Preview implements Serializable {
    private String src;

    public Preview() {
    }

    public Preview(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Preview)) return false;

        Preview preview = (Preview) o;

        return Objects.equals(src, preview.src);
    }

    @Override
    public int hashCode() {
        return src != null ? src.hashCode() : 0;
    }
}
