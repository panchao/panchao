package com.aoxiu.meta.photo;

/**
 * Created by panchao on 15/5/17.
 */
public class Appraisal {
    private int id;
    private String appraisal;
    private int appraisalId;
    private int appraisalStar;

    public Appraisal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public int getAppraisalId() {
        return appraisalId;
    }

    public void setAppraisalId(int appraisalId) {
        this.appraisalId = appraisalId;
    }

    public int getAppraisalStar() {
        return appraisalStar;
    }

    public void setAppraisalStar(int appraisalStar) {
        this.appraisalStar = appraisalStar;
    }
}
