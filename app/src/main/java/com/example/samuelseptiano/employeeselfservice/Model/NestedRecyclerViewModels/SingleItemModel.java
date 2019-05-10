package com.example.samuelseptiano.employeeselfservice.Model.NestedRecyclerViewModels;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class SingleItemModel {


    private String name;
    private String type;
    private String id;
    private String description;
    private String posterPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public SingleItemModel(String name, String type, String id, String description, String posterPath) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.description = description;
        this.posterPath = posterPath;
    }

    public SingleItemModel() {
    }
}
