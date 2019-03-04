package com.example.inmoapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmoapp.Adapter.MyMyInmuebleRecyclerViewAdapter;
import com.example.inmoapp.Adapter.MyPhotoRecyclerViewAdapter;
import com.example.inmoapp.Adapter.ViewPagerAdapter;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.InmuebleDetalladoActivity;
import com.example.inmoapp.Listener.InmuebleListener;
import com.example.inmoapp.Model.PropertyResponseOne;
import com.example.inmoapp.Model.Rows;
import com.example.inmoapp.PhotoActivity;
import com.example.inmoapp.R;
import com.example.inmoapp.Services.InmuebleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<String> photos;
    private MyPhotoRecyclerViewAdapter adapter;
    private Context ctx;
    private String idProyec;
    /*private InmuebleViewModel mViewModel;*/

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PhotoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PhotoFragment newInstance(int columnCount) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);

        idProyec = getArguments().getString("idB");

        // Set the adapter
        if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            photos = new ArrayList<>();

            /*PhotoActivity p = new PhotoActivity();
            idProyec = p.mandarId();*/

            /*Bundle b = getIntent().getExtras();
            String id = b.getString("id");*/

            /*Bundle bundle = this.getArguments();
            if (bundle != null) {
                idProyec = bundle.getString("id");
            }*/

            /*idProyec = (String) getArguments().getSerializable("id").toString();*/

            InmuebleService service = ServiceGenerator.createService(InmuebleService.class);
            Call<PropertyResponseOne> call = service.getInmueble(idProyec);


            call.enqueue(new Callback<PropertyResponseOne>() {

                /*private Inmueble inmueble;*/

                @Override
                public void onResponse(Call<PropertyResponseOne> call, Response<PropertyResponseOne> response) {
                    if (response.code() != 200) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Rows inmueble = response.body().getRows();

                        photos = Arrays.asList(inmueble.getPhotos());

                        adapter = new MyPhotoRecyclerViewAdapter(
                                ctx,
                                photos
                        );
                        recyclerView.setAdapter(adapter);



                    }
                }

                @Override
                public void onFailure(Call<PropertyResponseOne> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                }


            });
        }
        return view;
    }

    public void getPhotos(){

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
