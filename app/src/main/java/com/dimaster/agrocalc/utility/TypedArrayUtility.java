package com.dimaster.agrocalc.utility;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.dimaster.agrocalc.R;
import com.dimaster.agrocalc.exception.NestedTypedArrayException;
import com.dimaster.agrocalc.model.Crop;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TypedArrayUtility {
    // Makes constructor unaccessible -> Utility  classes shouldn't be instantiated
    private TypedArrayUtility() {
    }

    // Default resource ID value for #getResourceId(...) method
    private static final int DEFAULT_VALUE = 0;

    // Parameters:
    //  - TypedArray typedArray <- TypedArray that contains other arrays
    //  - Integer elementIdx <- index of the nested TypedArray in the "array of arrays"
    // Returns: Android's unique resource ID of the nested array in the typedArray variable
    //   -> found under the elementIdx value index
    // Throws: NestedTypedArrayException if the unique resource ID is not retrieved correctly
    private static final BiFunction<TypedArray, Integer, Integer> getNestedTypedArrayIdFunction =
            (typedArray, elementIdx) -> {
                final int returnValue = typedArray.getResourceId(elementIdx, DEFAULT_VALUE);

                if (returnValue == DEFAULT_VALUE) {
                    throw new NestedTypedArrayException("");
                }

                return returnValue;
            };

    // Indexes for values inside each `Crop` arrays
    private static final int CROP_NAME_INDEX = 0;
    private static final int CROP_N_INDEX = 1;
    private static final int CROP_P_INDEX = 2;
    private static final int CROP_K_INDEX = 3;

    public static List<Crop> getCropsFromResources(final Resources resources) {
        // Get array of `Crop` arrays from the 'crops.xml'
        final TypedArray cropsTypedArray = resources.obtainTypedArray(R.array.crops);

        // Return empty list if 'crops.xml' is empty
        if (Objects.isNull(cropsTypedArray)) {
            return Collections.emptyList();
        }

        // Upper value for iterating over the array of `Crop` array
        final int typedArrayLength = cropsTypedArray.length();

        // Partially apply `TypedArray` parameter to the #getNestedTypedArrayIdFunction(...)
        // This turns BiFunction into IntUnaryOperator, which can be directly passed to
        //   -> the InsStream#map(...) method
        final IntUnaryOperator partiallyAppliedMapToNestedArrayIdFunction = arrayIdx ->
                getNestedTypedArrayIdFunction.apply(cropsTypedArray, arrayIdx);

        // Stream over indexes of the array of `Crop` array -> [0...n)
        return IntStream.range(0, typedArrayLength)
                // Transform each index into the unique ID of the nested `Crop` array
                .map(partiallyAppliedMapToNestedArrayIdFunction)
                // Gets the nested `TypedArray` with the given ID from `Resources`
                .mapToObj(resources::obtainTypedArray)
                // Extracts values from the nested `TypedArray` and builds `Crop` element
                .map(cropElementTypedArray -> new Crop(
                        cropElementTypedArray.getString(CROP_NAME_INDEX),
                        Double.parseDouble(cropElementTypedArray.getString(CROP_N_INDEX)),
                        Double.parseDouble(cropElementTypedArray.getString(CROP_P_INDEX)),
                        Double.parseDouble(cropElementTypedArray.getString(CROP_K_INDEX))
                ))
                // Collects all `Crop` elements into list
                .collect(Collectors.toList());
    }
}
