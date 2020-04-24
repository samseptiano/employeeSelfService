package com.enseval.samuelseptiano.hcservice.Model.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserEventsRealm extends RealmObject {
    public UserEventsRealm() {
    }

    public UserEventsRealm(int id, String userId, String newsId, String dateJoin, String newsName, String newsPoster, String status, String newsDetail) {
        this.id = id;
        this.userId = userId;
        this.newsId = newsId;
        this.dateJoin = dateJoin;
        this.newsName = newsName;
        this.newsPoster = newsPoster;
        this.status = status;
        this.newsDetail = newsDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsPoster() {
        return newsPoster;
    }

    public void setNewsPoster(String newsPoster) {
        this.newsPoster = newsPoster;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNewsDetail() {
        return newsDetail;
    }

    public void setNewsDetail(String newsDetail) {
        this.newsDetail = newsDetail;
    }

    @PrimaryKey
    int id;
    String userId;
    String newsId;
    String dateJoin;
    String newsName;
    String newsPoster;
    String status;
    String newsDetail;

}
