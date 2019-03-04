package com.example.inmoapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.TipoAutenticacion;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.Generator.UtilUser;
import com.example.inmoapp.Model.User;
import com.example.inmoapp.R;
import com.example.inmoapp.Services.InmuebleService;
import com.example.inmoapp.Services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AjustesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AjustesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AjustesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tvName, tvEmail;
    private Button btnNamePicture, btnCambiarPassword;
    private EditText etName, etPassword, etRepeatPassword;
    private ImageView security, photo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AjustesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AjustesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AjustesFragment newInstance(String param1, String param2) {
        AjustesFragment fragment = new AjustesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);

        btnNamePicture = view.findViewById(R.id.btnCambiarNombrePicture);
        btnCambiarPassword = view.findViewById(R.id.btnCambiarPassword);
        /*btnCambiarPassword.setEnabled(false);*/

        etName = view.findViewById(R.id.editTextName);
        etName.setVisibility(View.INVISIBLE);
        etPassword = view.findViewById(R.id.etPassword);
        etRepeatPassword = view.findViewById(R.id.etRepeatPassword);

        photo = view.findViewById(R.id.imageViewPhoto);
        security = view.findViewById(R.id.imageView7);

        Glide
                .with(view.getContext())
                .load(R.drawable.ic_security_black_24dp)
                .into(security);


        UserService service = ServiceGenerator.createService(UserService.class, UtilToken.getToken(view.getContext()), TipoAutenticacion.JWT);
        Call<User> call = service.getDataUser(UtilUser.getId(view.getContext()));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Glide
                            .with(view.getContext())
                            .load(response.body().getPicture())
                            .into(photo);

                    tvName.setText(response.body().getName());
                    tvEmail.setText(response.body().getEmail());

                    etName.setText(response.body().getName());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        /*if(etPassword.getText().equals(etRepeatPassword.getText())){
            btnCambiarPassword.setEnabled(true);
        } else {
            btnCambiarPassword.setEnabled(false);
        }*/

        btnCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Requerir contraseña actual, para asi mander un @Header con Basi Authorization
                 *          String credentials = Credentials.basic(email, password);
                 */
                updatePassword(view.getContext());
            }
        });



        return  view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            /*mListener.onFragmentInteraction(uri);*/
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>getDataUser
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        /*void onFragmentInteraction(Uri uri);*/
    }

    public void updatePassword(Context ctx){
        UserService service = ServiceGenerator.createService(UserService.class, UtilToken.getToken(ctx), TipoAutenticacion.BASIC);
        Call<User> call = service.updatePassword(etPassword.getText().toString());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Log.d("el cambio pass", "flama killo");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
