package com.pingwinno.streamarchive;

import java.io.Serializable;
import java.util.Objects;

public class AnimatedPreview implements Serializable {
    private int index;
    private String src;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimatedPreview)) return false;

        AnimatedPreview that = (AnimatedPreview) o;

        if (index != that.index) return false;
        return Objects.equals(src, that.src);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (src != null ? src.hashCode() : 0);
        return result;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
