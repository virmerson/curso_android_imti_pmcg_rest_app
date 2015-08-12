package com.htcursos.webservicesapp.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Course implements Serializable {

    @SerializedName("remote_id")
    private long id;

    @SerializedName("title")
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        return !(title != null ? !title.equals(course.title) : course.title != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
