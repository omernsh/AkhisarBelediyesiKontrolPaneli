package com.n.akhisarbelediyesikontrolpaneli;



public class ProjeVeriler {

    String proje_adı;
    String proje_özeti;
    String proje_açıklaması;
    String foto_url;



    public ProjeVeriler() {
    }

    public ProjeVeriler(String proje_adı, String proje_özeti, String proje_açıklaması, String foto_url) {
        this.proje_adı = proje_adı;
        this.proje_özeti = proje_özeti;
        this.proje_açıklaması = proje_açıklaması;
        this.foto_url = foto_url;

    }

    public String getProje_adı() {
        return proje_adı;
    }

    public void setProje_adı(String proje_adı) {
        this.proje_adı = proje_adı;
    }



    public String getProje_özeti() {
        return proje_özeti;
    }

    public void setProje_özeti(String proje_özeti) {
        this.proje_özeti = proje_özeti;
    }




    public String getProje_açıklaması() {
        return proje_açıklaması;
    }

    public void setProje_açıklaması(String proje_açıklaması) {
        this.proje_açıklaması = proje_açıklaması;
    }





    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }





}
