package com.example.inmoapp.Adapter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmoapp.Dialog.EliminarInmuebleDialogFragment;
import com.example.inmoapp.Fragments.MyInmuebleFragment.OnListFragmentInteractionListener;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.TipoAutenticacion;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.InmuebleDetalladoActivity;
import com.example.inmoapp.Listener.InmuebleListener;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.Mine;
import com.example.inmoapp.Model.ResponseContainer;
import com.example.inmoapp.R;
import com.example.inmoapp.Services.InmuebleService;
import com.example.inmoapp.ViewModel.InmuebleViewModel;
import com.example.inmoapp.ViewModel.MyInmuebleViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Inmueble} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMyInmuebleRecyclerViewAdapter extends RecyclerView.Adapter<MyMyInmuebleRecyclerViewAdapter.ViewHolder> {

    private List<Mine> mValues;
    private final InmuebleListener mListener;
    private Context contexto;
    private MyInmuebleViewModel mViewModel;
    private Inmueble inmueble;

    public MyMyInmuebleRecyclerViewAdapter(Context ctx, List<Mine> items, InmuebleListener listener) {
        contexto = ctx;
        mValues = items;
        mListener = listener;
    }

    public void setNuevosInmubles(List<Mine> nuevosInmubles) {
        this.mValues = nuevosInmubles;
        notifyDataSetChanged();
    }

    /*public void getOne(String id){
        InmuebleService service = ServiceGenerator.createService(InmuebleService.class);
        Call<Inmueble> call = service.getInmueble(id);


        call.enqueue(new Callback<Inmueble>() {

            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.code() != 200) {
                    Toast.makeText(contexto, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    inmueble = response.body();

                    Intent i = new Intent(contexto , InmuebleDetalladoActivity.class);
                    i.putExtra("inmueble", );
                    contexto.startActivity(i);

                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(contexto, "Error de conexión", Toast.LENGTH_SHORT).show();
            }


        });
    }*/

    @Override
    public MyMyInmuebleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_myinmueble, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final MyMyInmuebleRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.nombre.setText(mValues.get(position).getTitle());
        holder.address.setText(mValues.get(position).getAddress());
        double price = mValues.get(position).getPrice();
        holder.imgPrice.setText(String.valueOf(price));
        holder.imgRooms.setText(String.valueOf(mValues.get(position).getRooms()));
        holder.imgCityProvince.setText(mValues.get(position).getCity() + " (" + mValues.get(position).getProvince() + ")");

        holder.mView.findViewById(R.id.cardView_inmueble).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, InmuebleDetalladoActivity.class);
                i.putExtra("id", mValues.get(position).getId());
                contexto.startActivity(i);

            }
        });



        holder.btnDeleteProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel = ViewModelProviders.of((FragmentActivity) contexto).get(MyInmuebleViewModel.class);
                mViewModel.selectIdInmueble(holder.mItem.getId());
                EliminarInmuebleDialogFragment dialogoEliminar = EliminarInmuebleDialogFragment.newInstance();
                dialogoEliminar.show(((FragmentActivity) contexto).getSupportFragmentManager(), "dialog");
            }
        });


    }

    /*public void getComentarios(final Context ctx) {
        InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<Inmueble>> call = service.getListInmuebleAuth();

        call.enqueue(new Callback<ResponseContainer<Inmueble>>() {

            @Override
            public void onResponse(Call<ResponseContainer<Inmueble>> call, Response<ResponseContainer<Inmueble>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    InmuebleViewModel mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(InmuebleViewModel.class);
                    mViewModel.selectInmuebleList(response.body().getRows());

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Inmueble>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();

            }

        });
    }*/

    @Override
    public int getItemCount() {
        if(mValues != null)
            return mValues.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombre, address, imgCityProvince, imgRooms, imgPrice;
        public final ImageView imageView;
        public final ImageView isFav;
        public Mine mItem;
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
