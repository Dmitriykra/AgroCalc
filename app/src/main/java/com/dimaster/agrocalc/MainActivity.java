package com.dimaster.agrocalc;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dimaster.agrocalc.exception.MissingDomainObjectException;
import com.dimaster.agrocalc.model.Crop;
import com.dimaster.agrocalc.utility.TypedArrayUtility;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CUSTOM_DEBUG";

    //
    private Map<String, Crop> cropsMappedByName;
    int totalCropNumber;

    private TextView crop, npk; // TODO: Set but not used
    int currentCropIndex = 0; // TODO: Set but not used

    AutoCompleteTextView crop_list;
    TextInputLayout crop_til;

    ArrayAdapter<String> adapterItems;
    Double n, p, k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map each `Crop` element in the received List<Crop> by its name
        //   -> Makes it more comfortable to work with the collection,
        //   -> as well as, make the code more readable
        this.cropsMappedByName = TypedArrayUtility.getCropsFromResources(getResources())
                .stream()
                .collect(Collectors.toMap(Crop::getCropName, Function.identity()));

        crop = findViewById(R.id.crop);
        npk = findViewById(R.id.npk);
        crop_list = findViewById(R.id.crop_list);
        crop_til = findViewById(R.id.crop_til);

        Log.d(TAG, "onCreate: " + cropsMappedByName.values().size());
        Log.d(TAG, "onCreate, the following crops were read from resources: " + cropsMappedByName.values());

        cropList();
    }

    // Drop down list of crop types
    private void cropList() {
        adapterItems = new ArrayAdapter<>(
                this,
                R.layout.list_item,
                cropsMappedByName.values().stream().map(Crop::getCropName).collect(Collectors.toList()));

        crop_list.setAdapter(adapterItems);

        crop_list.setOnItemClickListener((parent, view, position, id) -> {
            final String selectedCropName = parent.getItemAtPosition(position).toString();

            final Optional<Crop> selectedCropOptional = Optional.ofNullable(
                    cropsMappedByName.get(selectedCropName)
            );

            final Crop selectedCrop = selectedCropOptional.orElseThrow(() ->
                    new MissingDomainObjectException(
                            String.format(
                                    "The selected name by user [%s] is not present!",
                                    selectedCropName
                            )));

            n = selectedCrop.getN();
            p = selectedCrop.getP();
            k = selectedCrop.getK();

            Log.d(TAG, "cropList: " + n + " " + p + " " + k);
        });
    }
}