package com.example.inmoapp.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.inmoapp.Generator.ServiceGenerator;
import com.example.inmoapp.Generator.TipoAutenticacion;
import com.example.inmoapp.Generator.UtilToken;
import com.example.inmoapp.Model.Inmueble;
import com.example.inmoapp.Model.Mine;
import com.example.inmoapp.Model.ResponseContainer;
import com.example.inmoapp.Services.InmuebleService;
import com.example.inmoapp.ViewModel.InmuebleViewModel;
import com.example.inmoapp.ViewModel.MyInmuebleViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EliminarInmuebleDialogFragment extends DialogFragment {

    public static EliminarInmuebleDialogFragment newInstance() {
        return new EliminarInmuebleDialogFragment();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("¿Desea eliminar el proyecto?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InmuebleViewModel mViewModel = ViewModelProviders.of(getActivity()).get(InmuebleViewModel.class);
                String idInmuebleBorrar = mViewModel.getSelectedIdInmuble().getValue();

                deleteInmueble(idInmuebleBorrar, getActivity());

            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    /*public void deleteInmueble(String id, final Context ctx){
        InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(getActivity()), TipoAutenticacion.JWT );
        Call<ResponseContainer<Inmueble>> call = service.deleteProperty(id);

        call.enqueue(new Callback<ResponseContainer<Inmueble>>() {

            @Override
            public void onResponse(Call<ResponseContainer<Inmueble>> call, Response<ResponseContainer<Inmueble>> response) {
                if (response.code() != 204) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                }

                InmuebleViewModel mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(InmuebleViewModel.class);
                String idProyecto = mViewModel.getSelectedIdInmuble().getValue();
                getComentarios(idProyecto, ctx);
            }

            @Override
            public void onFailure(Call<ResponseContainer<Inmueble>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });
    }*/
    public void deleteInmueble(String id, final Context ctx){
        InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(getActivity()), TipoAutenticacion.JWT );
        Call<ResponseContainer<Inmueble>> call = service.deleteProperty(id);

        call.enqueue(new Callback<ResponseContainer<Inmueble>>() {

            @Override
            public void onResponse(Call<ResponseContainer<Inmueble>> call, Response<ResponseContainer<Inmueble>> response) {
                if (response.code() != 204) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                }

                InmuebleViewModel mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(InmuebleViewModel.class);
                String idProyecto = mViewModel.getSelectedIdInmuble().getValue();
                getComentarios(idProyecto, ctx);
            }

            @Override
            public void onFailure(Call<ResponseContainer<Inmueble>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void getComentarios(String idInmueble, final Context ctx) {
        InmuebleService service = ServiceGenerator.createService(InmuebleService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<Mine>> call = service.getMineInmuebles();

        call.enqueue(new Callback<ResponseContainer<Mine>>() {

            @Override
            public void onResponse(Call<ResponseContainer<Mine>> call, Response<ResponseContainer<Mine>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    MyInmuebleViewModel mViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(MyInmuebleViewModel.class);
                    mViewModel.selectInmuebleList(response.body().getRows());

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Mine>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();

            }

        });
    }


}