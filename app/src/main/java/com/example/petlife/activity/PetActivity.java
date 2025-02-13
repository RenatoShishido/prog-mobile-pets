package com.example.petlife.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petlife.dao.PetDAO;
import com.example.petlife.dao.UsuarioDAO;
import com.example.petlife.entities.Pet;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.petlife.R;
import com.example.petlife.entities.Usuario;

public class PetActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView nomePET, idadePET, castradoPET, vacinadoPET, sexoPET, tipoPET, racaPET;
    Button btnAdotar;
    ImageView imageView2;
    Pet pet;
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent it = getIntent();
        pet  = (Pet)  it.getSerializableExtra("pet");

        inicializarItens();

        btnAdotar.setOnClickListener(v -> {showDialog();});
        usuarioDAO = new UsuarioDAO(this);

    }

    public void inicializarItens() {
        nomePET = findViewById(R.id.nomePET);
        idadePET = findViewById(R.id.idadePET);
        castradoPET = findViewById(R.id.castradoPET);
        vacinadoPET = findViewById(R.id.vacinadoPET);
        tipoPET = findViewById(R.id.tipoPET);
        sexoPET = findViewById(R.id.sexoPET);
        racaPET = findViewById(R.id.racaPET);
        btnAdotar = findViewById(R.id.btnAdotar);
        imageView2 = findViewById(R.id.imageView2);

        nomePET.setText(pet.getNome() != null? pet.getNome():nomePET.getText());

        if(pet.getPetPictureUrl() != null && !pet.getPetPictureUrl().isEmpty() ) {
            byte[] imagemBytes = Base64.decode(pet.getPetPictureUrl(),Base64.DEFAULT);
            Bitmap imagemDecodificada = BitmapFactory.decodeByteArray(imagemBytes, 0, imagemBytes.length);
            imageView2.setImageBitmap(imagemDecodificada);

        }
        idadePET.setText(pet.getIdade() != null? pet.getIdade().toString(): idadePET.getText());


        castradoPET.setText(pet.getCastrado() == null? "não": "sim");

        vacinadoPET.setText(pet.getVacinado() == null? "não": "sim");


        tipoPET.setText(pet.getTipo() != null? pet.getTipo(): tipoPET.getText());
        sexoPET.setText(pet.getSexo() != null? pet.getSexo(): sexoPET.getText());
        racaPET.setText(pet.getRaca() != null? pet.getRaca(): racaPET.getText());


    }

    public void showDialog() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.getById(pet.getUserId());

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Contatce o Doador para adotar o Pet! ");
        builder1.setMessage(
                "\n Doador: " +usuario.getNome()
                +  "\n Telefone: " +usuario.getTelefone()
                +"\n Email: "+usuario.getEmail());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}