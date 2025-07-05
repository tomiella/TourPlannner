package at.bif.swen.tourplanner.utils;

import java.text.DecimalFormat;

public class formatUtils {

    private static final DecimalFormat DISTANCE_FORMAT = new DecimalFormat("#0.0");


    public static String formatDistance(double meter) {
        double kilometers = meter/1000;
        return DISTANCE_FORMAT.format(kilometers);
    }



    public static String formatTime(long time){

        long hours = time / 3600;
        long minutes = time % 3600 / 60;

        if(time > 0){
            return String.format("%02d:%02d", hours, minutes);
        }else {
            return String.format("%d km", minutes);
        }
        }
    }


