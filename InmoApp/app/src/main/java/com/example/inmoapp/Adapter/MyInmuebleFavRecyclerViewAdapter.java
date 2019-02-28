package com.example.inmoapp.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inmoapp.Fragments.InmuebleFavFragment.OnListFragmentInteractionListener;
import com.example.inmoapp.Listener.InmuebleListener;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.R;

import java.util.List;

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

        if(holder.mItem.getPhotos() == null){
            holder.imageView.setImageResource(R.drawable.ic_home_black_24dp);
        } else {
            Glide
                    .with(this.contexto)
                    .load(holder.mItem.getPhotos()[0])
                    .into(holder.imageView);
        }

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
        public final TextView nombre, address;
        public final ImageView imageView;
        public Inmueble mItem;
        public CardView cardView_inmueble;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombre = (TextView) view.findViewById(R.id.textView_nombre);
            address = (TextView) view.findViewById(R.id.textView_address);
            imageView = view.findViewById(R.id.imageViewInmueble);
            cardView_inmueble = view.findViewById(R.id.cardView_inmueble);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nombre.getText() + "'";
        }
    }
}
