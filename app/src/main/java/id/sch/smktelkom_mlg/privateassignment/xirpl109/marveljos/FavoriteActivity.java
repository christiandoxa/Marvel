package id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.model.Favorite;
import io.realm.Realm;

public class FavoriteActivity extends AppCompatActivity {

    TextView txtDeskripsi, txtPrice;
    ImageView ivFav;

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Realm realm = Realm.getDefaultInstance();
        Favorite fav = realm.where(Favorite.class).equalTo("id", getIntent().getIntExtra("Favdet", 0)).findFirst();
        txtDeskripsi = (TextView) findViewById(R.id.deskripsiCoyFav);
        txtPrice = (TextView) findViewById(R.id.detPriceFav);
        ivFav = (ImageView) findViewById(R.id.imageFotoFav);
        setTitle(fav.title);
        txtDeskripsi.setText(fav.description);
        txtPrice.setText("Price : " + fav.price);
        Bitmap bitmap = getImage(fav.picture);
        ivFav.setImageBitmap(bitmap);
    }
}
