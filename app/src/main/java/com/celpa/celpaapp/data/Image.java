package com.celpa.celpaapp.data;


import java.io.Serializable;

public class Image implements Serializable {
    public String imgPath;
    public byte[] img;

    public Image(String path, byte[] img) {
        this.imgPath = path;
        this.img = img;
    }

    public Image(String imgPath) {
        this.imgPath = imgPath;
    }

    public Image(byte[] img) {
        this.img = img;
    }
}
