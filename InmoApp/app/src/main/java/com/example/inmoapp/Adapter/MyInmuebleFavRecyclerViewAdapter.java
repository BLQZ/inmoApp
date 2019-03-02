package com.example.inmoapp.Adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmoapp.Fragments.InmuebleFavFragment.OnListFragmentInteractionListener;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.TipoAutenticacion;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.Generator.UtilUser;
import com.example.inmoapp.Listener.InmuebleListener;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.ResponseContainer;
import com.example.inmoapp.R;
import com.example.inmoapp.Services.InmuebleService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Inmueble} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyInmuebleFavRecyclerViewAdapter extends RecyclerView.Adapter<MyInmuebleFavRecyclerViewAdapter.ViewHolder> {

    private final List<Inmueble> mValues;
    private final InmuebleListener mListener;
    private final Context contexto;

    public MyInmuebleFavRecyclerViewAdapter(Context ctx, List<Inmueble> items, InmuebleListener listener) {
        contexto = ctx;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_inmueblefav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nombre.setText(mValues.get(position).getTitle());
        holder.address.setText(mValues.get(position).getAdress());
        double price = mValues.get(position).getPrice();
        holder.imgPrice.setText(String.valueOf(price));
        holder.imgRooms.setText(String.valueOf(mValues.get(position).getRooms()));
        holder.imgCityProvince.setText(mValues.get(position).getCity() + " (" + mValues.get(position).getProvince() + ")");
        /*holder.isFav.setImageResource(R.drawable.ic_favorite_red_24dp);*/

        if(holder.mItem.getPhotos() == null){
            holder.imageView.setImageResource(R.drawable.ic_home_black_24dp);
        } else {
            Glide
                    .with(holder.mView.getContext())
                    .load(holder.mItem.getPhotos()[0])
                    .into(holder.imageView);
        }

        if(UtilUser.getEmail(holder.mView.getContext()) == mValues.get(position).getOwnerId().getEmail()){
            holder.btnDeleteProperty.show();
        }

        /*holder.isFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(contexto), TipoAutenticacion.JWT);
                Call<ResponseContainer<Inmueble>> call = service.deleteToFavsProperties(holder.mItem.getId());

                call.enqueue(new Callback<ResponseContainer<Inmueble>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<Inmueble>> call, Response<ResponseContainer<Inmueble>> response) {
                        if (response.code() != 200) {
                            Toast.makeText(holder.mView.getContext(), "Error en petición", Toast.LENGTH_SHORT).show();
                        } else {
                            *//*TODO 1: PINTAR CORAZON EN EL MOMENTO
                             * RECARGAR FRAGMENT*//*
                            holder.isFav.setImageResource(R.drawable.ic_favorite_border_red_24dp);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<Inmueble>> call, Throwable t) {

                    }
                });
            }
        });*/

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombre, address, imgCityProvince, imgRooms, imgPrice;
        public final ImageView imageView;
        public final ImageView isFav;
        public Inmueble mItem;
        public CardView cardView_inmueble;
        public final FloatingActionButton btnDeleteProperty;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombre = (TextView) view.findViewById(R.id.textView_nombre);
            address = (TextView) view.findViewById(R.id.textView_address);
            imageView = view.findViewById(R.id.imageViewInmueble);
            cardView_inmueble = view.findViewById(R.id.cardView_inmueble);
            isFav = view.findViewById(R.id.isFav);
            imgCityProvince = view.findViewById(R.id.textView_cityProvince);
            imgRooms = view.findViewById(R.id.textView_rooms);
            imgPrice = view.findViewById(R.id.textView_price);
            btnDeleteProperty = view.findViewById(R.id.btnDeleteProperty);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nombre.getText() + "'";
        }
    }
}
