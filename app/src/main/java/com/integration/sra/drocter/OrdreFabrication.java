package com.integration.sra.drocter;

import android.support.annotation.Nullable;

import com.orm.SugarRecord;



public class OrdreFabrication extends SugarRecord<OrdreFabrication> {
    private String article;
    private String of;
    private String matricule;
    private String idpalette;
    private String capacite;
    private String lot;
    private String qty;
    private  String batch;
    private  Flag flg=Flag.Sent;



    public OrdreFabrication(
             String article, String of, String matricule
            , String idpalette, String capacite, String lot
            ,String qty,String batch){
        System.out.println(this);
        this.article = article;
        this.of = of;
        this.matricule = matricule;
        this.idpalette = idpalette;
        this.capacite = capacite;
        this.lot = lot;
        this.qty=qty;
        this.batch=batch;
    }

    public OrdreFabrication(){



    }

    public Flag getFlg() {
        return flg;
    }

    public void setFlg(Flag flg) {
        this.flg = flg;
    }

    public String getOf() {
        return of;
    }

    public void setOf(String of) {
        this.of = of;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getIdpalette() {
        return idpalette;
    }

    public void setIdpalette(String idpalette) {
        this.idpalette = idpalette;
    }

    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
