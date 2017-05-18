package id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.adapter.FavAdapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.model.Favorite;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements FavAdapter.IFavAdapter {
    public List<Favorite> favs = new ArrayList<>();
    public FavAdapter adapter;
    Favorite fav = null;
    Realm realm;
    TextView status;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FavAdapter(favs, getActivity(), this);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.favlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        status = (TextView) view.findViewById(R.id.textStatus);
        if (favs.isEmpty()) {
            status.setVisibility(View.VISIBLE);
        } else {
            status.setVisibility(View.GONE);
        }
        fillData();

        return view;
    }

    private void fillData() {
        realm = Realm.getDefaultInstance();
        favs.clear();
        favs.addAll(realm.where(Favorite.class).findAll());
//        Log.d("FavFragment",favs.get(0).toString());
        if (favs.isEmpty()) {
            status.setVisibility(View.VISIBLE);
        } else {
            status.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void detFav(int pos) {
        fav = favs.get(pos);
        Intent intent = new Intent(getActivity(), FavoriteActivity.class);
        intent.putExtra("Favdet", fav.id);
        startActivity(intent);
    }

    @Override
    public void delete(int pos) {
        fav = favs.get(pos);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Favorite> result = realm.where(Favorite.class).equalTo("id", fav.id).findAll();
                result.deleteAllFromRealm();
                fillData();
            }
        });
    }
}
