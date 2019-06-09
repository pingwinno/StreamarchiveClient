package com.pingwinno.streamarchive;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class StreamMeta {

    @JsonProperty("_id")
    private String uuid;
    private Date date;
    private String title;
    private String game;
    private long duration;
    private LinkedList<AnimatedPreview> animatedPreviews;
    private LinkedHashMap<String, Preview> timelinePreviews;

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
    public LinkedHashMap<String, Preview> getTimelinePreviews() {
        return timelinePreviews;
    }

    @JsonProperty("timeline_preview")
    public void setTimelinePreviews(LinkedHashMap<String, Preview> timelinePreviews) {
        this.timelinePreviews = timelinePreviews;
    }

}
