package com.deka.milleyouthexpo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    // Mendefinisikan Atribut Layout
    private EditText txtNamaLengkap;
    private String namaLengkap;
    private EditText inputtglLahir;
    private String tglLahir;
    private SeekBar seekBar;
    private TextView lbl_umur;
    private String nilaiUmur;
    private String umur;
    private Button btnDaftar;
    private RadioGroup radioGroup;
    private String gender;
    private CheckBox mKerajinan, mTeknologi;
    private RadioButton rblaki;
    private RadioButton rbperempuan;
    private String expo;

    private DataHelper db;
    private User user;

    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Menyesuaikan ID
        txtNamaLengkap = findViewById(R.id.input_namalengkap);
        inputtglLahir = findViewById(R.id.input_tgl_lahir);
        seekBar = findViewById(R.id.seekbar);
        lbl_umur = findViewById(R.id.lbl_umur);
        btnDaftar = findViewById(R.id.daftar);
        radioGroup = findViewById(R.id.radioGroupGender);
        rblaki = findViewById(R.id.laki_laki);
        rbperempuan = findViewById(R.id.perempuan);
        mKerajinan = findViewById(R.id.kerajinan);
        mTeknologi = findViewById(R.id.teknologi);

        db = new DataHelper(this);

        // Membuat Kalender
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        inputtglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        inputtglLahir.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                nilaiUmur = String.valueOf(i);
                lbl_umur.setText("Umur : " + nilaiUmur);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.input_namalengkap,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        awesomeValidation.addValidation(this,R.id.input_tgl_lahir,
                RegexTemplate.NOT_EMPTY,R.string.invalid_date);


        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                namaLengkap = txtNamaLengkap.getText().toString();
                tglLahir = inputtglLahir.getText().toString();
                umur = nilaiUmur;
                gender = getGenderSelected();
                expo = getExpoSelected();

                if(awesomeValidation.validate()){
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AddActivity.this);
                    builder.setIcon(R.drawable.warning);
                    builder.setTitle("Form Daftar");
                    builder.setMessage(
                            "Apakah anda sudah yakin dengan data anda ?\n\n" +
                                    "Nama Lengkap : \n" + namaLengkap + "\n\n" +
                                    "Tanggal Lahir : \n" + tglLahir + "\n\n" +
                                    "Umur : \n" + umur + "\n\n" +
                                    "Gender :\n" + gender + "\n\n" +
                                    "Jenis Expo Yang di Pilih :\n" + expo + ""
                    );

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Data anda berhasil terdaftarkan !", Toast.LENGTH_SHORT).show();

                            Intent layout2 = new Intent(AddActivity.this, MainActivity.class);

                            user = new User();
                            user.setNamaLengkap(namaLengkap);
                            user.setTglLahir(tglLahir);
                            user.setUmur(umur);
                            user.setGender(gender);
                            user.setExpo(expo);

                            db.insert(user);

                            startActivity(layout2);
                            finish();

                        }
                    });

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.show();
                }

                else {

                    Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_SHORT).show();

                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AddActivity.this);
                    builder.setIcon(R.drawable.warning);
                    builder.setTitle("Peringatan");
                    builder.setMessage(
                            "Tolong isi Form dengan Benar !!!"
                    );

                    builder.show();


                }
            }
        });


    }
    // Memilih Jenis Expo
    private String getExpoSelected(){
        String expo = "";

        if (mKerajinan.isChecked()) {
            expo += mKerajinan.getText().toString() + "\n";
        }
        if (mTeknologi.isChecked()) {
            expo += mTeknologi.getText().toString() + "\n";
        }

        return expo;

    }
    // Memilih Gender
    private String getGenderSelected(){
        String gender = "";

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == rblaki.getId()){
            gender = "Laki-Laki";
        }
        else if (selectedId == rbperempuan.getId()){
            gender = "Perempuan";
        }

        return gender;
    }

}