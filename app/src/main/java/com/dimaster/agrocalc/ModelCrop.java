package com.dimaster.agrocalc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ModelCrop {

    public static String cropArray[]  = {
            //Эти строки нужно записать в виде строки в файле strings, in app>java>res>values>strings
            "Пшениця",
            "Кукурудза",

    };


    public static double npkUsesFromSoil [][] = {
            {26, 8.5, 20}, //Пшениця
            {21, 12, 28},   //Кукурудза
    };
}
