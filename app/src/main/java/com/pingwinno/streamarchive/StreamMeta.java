package com.pingwinno.streamarchive;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.TreeMap;

public class StreamMeta implements Serializable {

    @JsonProperty("_id")
    private String uuid;
    private Date date;
    private String title;
    private String game;
    private long duration;
    private LinkedList<AnimatedPreview> animatedPreviews;
    private TreeMap<Long, Preview> timelinePreviews;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @JsonProperty("animated_preview")
    public LinkedList<AnimatedPreview> getAnimatedPreviews() {
        return animatedPreviews;
    }

    @JsonProperty("animated_preview")
    public void setAnimatedPreviews(LinkedList<AnimatedPreview> animatedPreviews) {
        this.animatedPreviews = animatedPreviews;
    }

    @JsonProperty("timeline_preview")
    public TreeMap<Long, Preview> getTimelinePreviews() {
        return timelinePreviews;
    }

    @JsonProperty("timeline_preview")
    public void setTimelinePreviews(TreeMap<Long, Preview> timelinePreviews) {
        this.timelinePreviews = timelinePreviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreamMeta)) return false;

        StreamMeta that = (StreamMeta) o;

        if (duration != that.duration) return false;
        if (!Objects.equals(uuid, that.uuid)) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(game, that.game)) return false;
        if (!Objects.equals(animatedPreviews, that.animatedPreviews))
            return false;
        return Objects.equals(timelinePreviews, that.timelinePreviews);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (animatedPreviews != null ? animatedPreviews.hashCode() : 0);
        result = 31 * result + (timelinePreviews != null ? timelinePreviews.hashCode() : 0);
        return result;
    }
}
