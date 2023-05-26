package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.RevistaAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceRevista;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaConsultaActivity extends NewAppCompatActivity {

    Button btnListar;

    GridView gridRevista;

    ArrayList<Revista> data = new ArrayList<Revista>();

    RevistaAdapter adapter;

    ServiceRevista serviceRevista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_consulta);

        btnListar = findViewById(R.id.btnLista);
        gridRevista = findViewById(R.id.gridRevista);
        adapter = new RevistaAdapter( this, R.layout.activity_item_revista, data);
        gridRevista.setAdapter(adapter);

        gridRevista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Revista objRevista = data.get(position);

                Intent intent = new Intent( RevistaConsultaActivity.this, RevistaConsultaDetalleActivity.class);
                intent.putExtra("VAR_OBJETO", objRevista);
                startActivity(intent);
            }
        });

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { listaRevistas();}
        });
    }
    public void listaRevistas(){
        Call<List<Revista>> call = serviceRevista.listaRevista();

        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                if(response.isSuccessful()){
                    List<Revista> lista = response.body();
                    data.clear();
                    data.addAll(lista);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {

            }
        });

    }




}