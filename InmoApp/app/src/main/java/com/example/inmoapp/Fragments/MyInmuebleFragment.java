package com.example.inmoapp.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmoapp.Adapter.MyInmuebleRecyclerViewAdapter;
import com.example.inmoapp.Adapter.MyMyInmuebleRecyclerViewAdapter;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.TipoAutenticacion;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.Listener.InmuebleListener;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.Mine;
import com.example.inmoapp.Model.ResponseContainer;
import com.example.inmoapp.R;
import com.example.inmoapp.Services.InmuebleService;
import com.example.inmoapp.ViewModel.InmuebleViewModel;
import com.example.inmoapp.ViewModel.MyInmuebleViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MyInmuebleFragment extends Fragment {

    // TODO: Customize parameter argument names
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private InmuebleListener mListener;
    private List<Mine> inmuebleList;
    private MyMyInmuebleRecyclerViewAdapter adapter;
    private Context ctx;
    private InmuebleViewModel mViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyInmuebleFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static InmuebleFragment newInstance(int columnCount) {
        InmuebleFragment fragment = new InmuebleFragment();
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
        View view = inflater.inflate(R.layout.fragment_myinmueble_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            ctx = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(ctx, mColumnCount));
            }
            mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(InmuebleViewModel.class);
            inmuebleList = new ArrayList<>();

            /**
             * RELLENAR inmuebleList
             */
            InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
            Call<ResponseContainer<Mine>> call = service.getMineInmuebles();

            call.enqueue(new Callback<ResponseContainer<Mine>>() {

                @Override
                public void onResponse(Call<ResponseContainer<Mine>> call, Response<ResponseContainer<Mine>> response) {
                    if (response.code() != 200) {
                        Toast.makeText(getActivity(), "Error en petición", Toast.LENGTH_SHORT).show();
                    } else {
                        inmuebleList = response.body().getRows();

                        adapter = new MyMyInmuebleRecyclerViewAdapter(
                                ctx,
                                inmuebleList,
                                mListener
                        );
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Mine>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }

            });
            lanzarViewModel(ctx);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InmuebleListener) {
            mListener = (InmuebleListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InmuebleListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        /*void onListFragmentInteraction(DummyItem item);*/
    }

    private void lanzarViewModel(Context ctx) {
        MyInmuebleViewModel inmuebleViewModel = ViewModelProviders.of((FragmentActivity) ctx)
                .get(MyInmuebleViewModel.class);
        inmuebleViewModel.getAll().observe(getActivity(), new Observer<List<Mine>>() {
            @Override
            public void onChanged(@Nullable List<Mine> inmuebles) {
                adapter.setNuevosInmubles(inmuebles);
            }
        });
    }
}

