package com.example.hp.firebaseconnection;

public class Prop_Model {

    public String getPropType() {
        return propType;
    }

    public String getPropTitle() {
        return propTitle;
    }

    public void setPropTitle(String propTitle) {
        this.propTitle = propTitle;
    }

    public String getPropDesc() {
        return propDesc;
    }

    public void setPropDesc(String propDesc) {
        this.propDesc = propDesc;
    }

    public String getPropPrice() {
        return propPrice;
    }

    public void setPropPrice(String propPrice) {
        this.propPrice = propPrice;
    }

    public String getPropArea() {
        return propArea;
    }

    public void setPropArea(String propArea) {
        this.propArea = propArea;
    }

    public String getPropEmail() {
        return propEmail;
    }

    public void setPropEmail(String propEmail) {
        this.propEmail = propEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPropNmbr() {
        return propNmbr;
    }

    public void setPropNmbr(int propNmbr) {
        this.propNmbr = propNmbr;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    String propType,propTitle,propDesc,propPrice,propArea,propEmail,userId;
    int propNmbr;
    String  uri;

   public Prop_Model()
    {

    }

    public Prop_Model(String propType, String propTitle, String propDesc, String propPrice, String propArea, String propEmail, String userId, int propNmbr, String uri)
    {
        this.propType = propType;
        this.propTitle = propTitle;
        this.propDesc = propDesc;
        this.propPrice = propPrice;
        this.propArea = propArea;
        this.propEmail= propEmail;
        this.userId = userId;
        this.propNmbr = propNmbr;
        this.uri = uri;

    }
}
