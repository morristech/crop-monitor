package midien.kheldiente.cma.utils;


import java.util.ArrayList;
import java.util.List;

public class SquareMeterUtils {


    public static List<String> generateSquareMeters(int min, int max, int interval) {
        List<String> hectares = new ArrayList<>(0);

        for(int k = min;k <= max;k += interval) {
            hectares.add(String.valueOf(k));
        }
        return hectares;
    }

    public static double toHectares(double squareMeters) {
        return squareMeters / 10000;
    }


}
