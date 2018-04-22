package com.celpa.celpaapp.data;


import java.util.ArrayList;
import java.util.List;

public class SquareMeter {


    public static List<Integer> generateSquareMeters(int min, int max, int interval) {
        List<Integer> hectares = new ArrayList<>(0);

        for(int k = min;k <= max;k += interval) {
            hectares.add(k);
        }
        return hectares;
    }


}
