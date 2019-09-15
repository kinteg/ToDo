package com.kinteg.todo;

import java.util.Calendar;
import java.util.Objects;

public class Item {

    private String name;
    private Calendar created;
    private Calendar closed;
    private boolean done;
    private int id;

    public Item(String name, Calendar created, int id) {
        this.name = name;
        this.created = created;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
