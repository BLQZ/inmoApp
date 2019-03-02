package com.example.inmoapp.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.inmoapp.Model.Inmueble;

import java.util.List;

public class InmuebleViewModel extends ViewModel {

    private final MutableLiveData<String> idInmueble = new MutableLiveData<>();
    private final MutableLiveData<List<Inmueble>> inmuebles = new MutableLiveData<>();

    public void selectIdInmueble(String id) { idInmueble.setValue(id);}

    public void selectInmuebleList(List<Inmueble> inmueblesList) {
        inmuebles.setValue(inmueblesList);
    }

    public MutableLiveData<String> getSelectedIdInmuble() {
        return idInmueble;
    }


    public MutableLiveData<List<Inmueble>> getAll() { return inmuebles; }
}
