package com.dimaster.agrocalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "we";
    TextView crop, npk;
    int currentCropIndex = 0;
    int totalCropNumber = ModelCrop.cropArray.length;
    AutoCompleteTextView crop_list;
    ArrayAdapter<String> adapterItems;
    TextInputLayout crop_til;
    String selectedCrop;
    Double n, p, k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        crop = findViewById(R.id.crop);
        npk = findViewById(R.id.npk);
        crop_list = findViewById(R.id.crop_list);
        crop_til = findViewById(R.id.crop_til);
        Log.d(TAG, "onCreate: "+totalCropNumber);
        cropList();
    }

    //Drop down list of crops types
    private void cropList(){
        adapterItems = new ArrayAdapter<>(
                this,
                R.layout.list_item,
                getResources().getStringArray(R.array.crop_type));
        crop_list.setAdapter(adapterItems);
        crop_list.setOnItemClickListener((parent, view, position, id) -> {
            selectedCrop = parent.getItemAtPosition(position).toString();

            int i = Arrays.asList(ModelCrop.cropArray).indexOf(selectedCrop);

            if(selectedCrop.equals(ModelCrop.cropArray[i])) {
                n = ModelCrop.npkUsesFromSoil[i][0];
                p = ModelCrop.npkUsesFromSoil[i][1];
                k = ModelCrop.npkUsesFromSoil[i][2];
                Log.d(TAG, "cropList: " + n + " " + p + " " + k);
            }
        });
    }
}