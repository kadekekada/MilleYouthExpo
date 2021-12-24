package com.deka.milleyouthexpo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewActivity extends AppCompatActivity {
    TextView txtNamaLengkap;
    TextView txtTglLahir;
    TextView txtUmur;
    TextView txtGender;
    TextView txtExpo;
    String namaLengkap;
    String tglLahir;
    String umur;
    String gender;
    String expo;

    private DataHelper db;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        db = new DataHelper(this);
        userList = db.selectUserData();


        txtNamaLengkap = findViewById(R.id.isi_namalengkap);
        txtTglLahir = findViewById(R.id.isi_tgl_lahir);
        txtUmur = findViewById(R.id.isi_umur);
        txtGender = findViewById(R.id.isi_gender);
        txtExpo = findViewById(R.id.isi_expo);

        namaLengkap = userList.get(userList.size()-1).getNamaLengkap();
        tglLahir = userList.get(userList.size()-1).getTglLahir();
        umur = userList.get(userList.size()-1).getUmur();
        gender = userList.get(userList.size()-1).getGender();
        expo = userList.get(userList.size()-1).getExpo();

        txtNamaLengkap.setText(namaLengkap);
        txtTglLahir.setText(tglLahir);
        txtUmur.setText(umur);
        txtGender.setText(gender);
        txtExpo.setText(expo);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Menampilkan Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Menjeda Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(this," Memulai Activity Kembali", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Menghancurkan activity", Toast.LENGTH_SHORT).show();
    }


}