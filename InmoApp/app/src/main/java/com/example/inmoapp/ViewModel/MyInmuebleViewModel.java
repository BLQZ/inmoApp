package com.example.inmoapp.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.inmoapp.Model.Mine;

import java.util.List;

public class MyInmuebleViewModel extends ViewModel {

    private final MutableLiveData<String> idInmueble = new MutableLiveData<>();
    private final MutableLiveData<List<Mine>> inmuebles = new MutableLiveData<>();

    public void selectIdInmueble(String id) { idInmueble.setValue(id);}

    public void selectInmuebleList(List<Mine> inmueblesList) {
        inmuebles.setValue(inmueblesList);
    }

    public MutableLiveData<String> getSelectedIdInmuble() {
        return idInmueble;
    }


    public MutableLiveData<List<Mine>> getAll() { return inmuebles; }
}
