package id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.R;
import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.model.Comic;
import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.model.Favorite;
import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.model.Price;
import io.realm.Realm;

/**
 * Created by zhe on 14/05/2017.
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    public List<Comic> coms;
    Context context;
    IcomAdapter icomAdapter;
    private Realm realm;

    public ComicAdapter(List<Comic> coms, Context context, Fragment fragment) {
        this.coms = coms;
        this.context = context;
        this.icomAdapter = (IcomAdapter) fragment;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public ComicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ComicAdapter.ViewHolder holder, int position) {
        Comic comic = coms.get(position);
        Price price = null;
        if (comic.prices.size() > 1) {
            price = comic.prices.get(1);
        } else {
            price = comic.prices.get(0);
        }
        holder.nama.setText(comic.title);
        holder.deskripsi.setText(comic.description);
        holder.price.setText("$" + price.price);
        Favorite fav = realm.where(Favorite.class).equalTo("id", comic.id).findFirst();
        if (fav != null) {
            holder.butFav.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        Glide.with(context)
                .load(comic.thumbnail.path + "/landscape_xlarge.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(270, 200)
                .error(R.drawable.def_image_landscape)
                .into(holder.ivCom);
    }

    @Override
    public int getItemCount() {
        if (coms != null) {
            return coms.size();
        }
        return 0;
    }

    public interface IcomAdapter {
        void detailCom(int pos);

        void doSave(int pos, ImageButton fab);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        TextView deskripsi;
        TextView price;
        ImageButton butFav;
        ImageView ivCom;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.textViewJudul);
            deskripsi = (TextView) itemView.findViewById(R.id.textViewDeskripsi);
            price = (TextView) itemView.findViewById(R.id.textViewPrice);
            butFav = (ImageButton) itemView.findViewById(R.id.buttonFavorite);
            ivCom = (ImageView) itemView.findViewById(R.id.imageViewComic);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    icomAdapter.detailCom(getAdapterPosition());
                }
            });
            butFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    icomAdapter.doSave(getAdapterPosition(), butFav);
                }
            });
        }
    }
}
