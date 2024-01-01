package com.example.mycar.Enum;

import com.example.mycar.R;

public enum CarType {

    DUMP_TRUCK,
    BULLDOZER,
    EXCAVATOR,
    TRUCK_CRANE,
    TRUCK_LOADER;

    public static String valueOfStr(CarType value){
        String pick = "Бульдозеры";
        switch (value){
            case EXCAVATOR:
                pick = "Экскаваторы";
                break;
            case DUMP_TRUCK:
                pick = "Самосвалы";
                break;
            case TRUCK_CRANE:
                pick = "Строительные краны";
                break;
            case TRUCK_LOADER:
                pick = "Погрузчики";
                break;
        }
        return pick;
    }

    public static CarType valueOfCar(String value){
        CarType pick = BULLDOZER;
        switch (value){
            case "Экскаваторы":
                pick = EXCAVATOR;
                break;
            case "Самосвалы":
                pick = DUMP_TRUCK;
                break;
            case "Строительные краны":
                pick = TRUCK_CRANE;
                break;
            case "Погрузчики":
                pick = TRUCK_LOADER;
                break;
        }
        return pick;
    }

    public static String getTextForType(String[] arrayText, String value){
        String pick = arrayText[1];

        switch (value){
            case "Экскаваторы":
                pick = arrayText[2];
                break;
            case "Самосвалы":
                pick = arrayText[0];
                break;
            case "Строительные краны":
                pick = arrayText[3];
                break;
            case "Погрузчики":
                pick = arrayText[4];
                break;
        }
        return pick;
    }
}
