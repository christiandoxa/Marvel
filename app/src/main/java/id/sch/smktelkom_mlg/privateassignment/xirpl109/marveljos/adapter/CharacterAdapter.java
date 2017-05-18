package id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.R;
import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.model.Character;

/**
 * Created by zhe on 14/05/2017.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    Context context;
    List<Character> chars = new ArrayList<>();
    charListener charListener;

    public CharacterAdapter(List<Character> chars, Context context, Fragment fragment) {
        this.chars = chars;
        this.context = context;
        if (fragment != null) {
            charListener = (charListener) fragment;
        } else {
            charListener = (charListener) context;
        }
    }

    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.characteritem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CharacterAdapter.ViewHolder holder, int position) {
        Character charing = chars.get(position);
        holder.txtCharName.setText(charing.name);
        holder.txtCharDesc.setText(charing.description);
        try {
            Glide.with(context)
                    .load(charing.thumbnail.path + "/portrait_xlarge.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(150, 225)
                    .error(R.drawable.def_image)
                    .into(holder.ivCharPict);
        } catch (Exception ex) {
            Log.e("CHAR ADAPTER", ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (chars != null) {
            return chars.size();
        }
        return 0;
    }

    public interface charListener {
        void detail(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCharName;
        TextView txtCharDesc;
        ImageView ivCharPict;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCharName = (TextView) itemView.findViewById(R.id.charName);
            txtCharDesc = (TextView) itemView.findViewById(R.id.charDesc);
            ivCharPict = (ImageView) itemView.findViewById(R.id.imageChar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    charListener.detail(getAdapterPosition());
                }
            });
        }
    }
}
