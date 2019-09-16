package com.kinteg.todo;

import java.util.Calendar;
import java.util.Objects;

public class Item {

    private String name;
    private String description;
    private Calendar created;
    private Calendar closed;
    private boolean done;
    private int tag;
    private String shortDesc;
    private final int SHORT_DESC_LENGTH = 20;

    public Item(String name, String description, Calendar created, int tag) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.tag = tag;
        setShortDesc();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        setShortDesc();
    }

    public String getShortDesc() {
        return shortDesc;
    }

    private void setShortDesc() {
        if (description.length() > SHORT_DESC_LENGTH)
            shortDesc = description.substring(0, SHORT_DESC_LENGTH) + "...";
        else
            shortDesc = description;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getClosed() {
        return closed;
    }

    public void setClosed(Calendar closed) {
        this.closed = closed;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return tag == item.tag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
