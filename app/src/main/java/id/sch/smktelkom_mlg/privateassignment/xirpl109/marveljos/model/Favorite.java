package id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.model;

import io.realm.RealmObject;

/**
 * Created by zhe on 18/05/2017.
 */

public class Favorite extends RealmObject {
    public int id;
    public String title;
    public String description;
    public byte[] picture = new byte[102400];
    public float price;

    public Favorite() {

    }

    public Favorite(int id, String title, String description, byte[] picture, float price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.price = price;
    }
}
