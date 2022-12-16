package edu.itstep.hw20221130.models;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static String[] getDirections() {
        return new String[]{
                "Zhytomyr - Kyiv",
                "Kyiv - Zhytomyr",
                "Zhytomyr - Vinnitsa",
                "Vinnitsa - Zhytomyr",
                "Zhytomyr - Zaporozhye",
                "Zaporozhye - Zhytomyr",
                "Zhytomyr - Khmelnitsky",
                "Khmelnitsky - Zhytomyr",
                "Zhytomyr - Lviv",
                "Lviv - Zhytomyr",
        };
    }

    public static DirectionDB getDirectionsList() {
        DirectionDB directionDB = new DirectionDB();

        List<BusFlight> busFlightList_ZhytomyrKyiv = new ArrayList<>();
        busFlightList_ZhytomyrKyiv.add(
                new BusFlight(2, 0, 200, "09:20",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93",
                        "Автостанція Київ (кафе Бургер Хаус), вулиця Симона Петлюри; будинок 32", "7-Time"));
        busFlightList_ZhytomyrKyiv.add(
                new BusFlight(1, 50, 240, "09:25",
                        "Зупинка \"АЗС БРСМ\" (навпроти центрального автовокзалу), проспект Незалежності; будинок 95",
                        "Автостанція Київ (кафе Бургер Хаус), вулиця Симона Петлюри; будинок 32", "ПП Павлюк Л.В."));
        busFlightList_ZhytomyrKyiv.add(
                new BusFlight(1, 53, 200, "17:57",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93",
                        "Зупинка \"Автостанція-5 Дачна\" (міська зупинка перед в'їздом на автостанцію), проспект Перемоги; будинок 142", "Бус Тайм"));
        busFlightList_ZhytomyrKyiv.add(
                new BusFlight(1, 55, 270, "18:10",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93",
                        "Зупинка \"Автостанція-5 Дачна\" (міська зупинка перед в'їздом на автостанцію), проспект Перемоги; будинок 142", "Бус Тайм"));
        busFlightList_ZhytomyrKyiv.add(
                new BusFlight(2, 15, 200, "23:25",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93",
                        "Зупинка \"Автостанція-5 Дачна\" (міська зупинка перед в'їздом на автостанцію), проспект Перемоги; будинок 142", "ПП Пацюк П.П."));
        directionDB.getDirectionList().add(new Direction( "Zhytomyr", "Kyiv", 138, busFlightList_ZhytomyrKyiv));


        List<BusFlight> busFlightList_KyivZhytomyr = new ArrayList<>();
        busFlightList_KyivZhytomyr.add(
                new BusFlight(2, 10, 135, "07:00",
                        "Автостанція Київ (центральний залізничний вокзал), метро Вокзальна; вулиця Симона Петлюри; будинок 32",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93", "Нашбус"));
        busFlightList_KyivZhytomyr.add(
                new BusFlight(1, 25, 150, "14:00",
                        "Автостанція \"Дачна\", проспект Перемоги; будинок 142",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93", "СХІДНО-ЄВРОПЕЙСЬКА ПОДОРОЖ"));
        busFlightList_KyivZhytomyr.add(
                new BusFlight(3, 50, 200, "17:00",
                        "Автовокзал \"Центральний\", метро Деміївська; проспект Науки; будинок 1/2",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93", "Стецик Т.В."));
        directionDB.getDirectionList().add(new Direction( "Kyiv", "Zhytomyr", 138, busFlightList_KyivZhytomyr));


        List<BusFlight> busFlightList_ZhytomyrLviv = new ArrayList<>();
        busFlightList_ZhytomyrLviv.add(
                new BusFlight(5, 50, 400, "08:50",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93",
                        "Автостанція №8, Двірцева площа; будинок 1", "Меньок Н.І."));
        busFlightList_ZhytomyrLviv.add(
                new BusFlight(6, 10, 480, "13:50",
                        "Зупинка \"АЗС БРСМ\" (навпроти центрального автовокзалу), проспект Незалежності; будинок 95",
                        "Зупинка \"Піцерія Наполетана\" (біля залізничного вокзалу), вулиця Чернівецька; будинок 21", "Павлюк Л.В."));
        busFlightList_ZhytomyrLviv.add(
                new BusFlight(6, 20, 500, "16:40",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93",
                        "Автовокзал \"Центральний\", вулиця Стрийська; будинок 109", "EAST WEST EUROLINES/ТзОВ \"Гал-Всесвіт\""));
        directionDB.getDirectionList().add(new Direction( "Zhytomyr", "Lviv", 411, busFlightList_ZhytomyrLviv));


        List<BusFlight> busFlightList_LvivZhytomyr = new ArrayList<>();
        busFlightList_LvivZhytomyr.add(
                new BusFlight(5, 53, 480, "06:45",
                        "Центральний залізничний вокзал (платна парковка), вулиця Черновицька; будинок 21",
                        "Зупинка \"АЗС БРСМ\" (навпроти центрального автовокзалу), проспект Незалежності; будинок 95", "Павлюк Л.В."));
        busFlightList_LvivZhytomyr.add(
                new BusFlight(7, 30, 500, "11:30",
                        "Автовокзал \"Центральний\", вулиця Стрийська; будинок 109",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93\n", "EAST WEST EUROLINES/ТзОВ \"Гал-Всесвіт\""));
        busFlightList_LvivZhytomyr.add(
                new BusFlight(5, 55, 600, "13:00",
                        "Автовокзал \"Центральний\", вулиця Стрийська; будинок 109",
                        "Автовокзал \"Центральний\", вулиця Київська; будинок 93", "KLR Bus"));
        directionDB.getDirectionList().add(new Direction( "Lviv", "Zhytomyr", 411, busFlightList_LvivZhytomyr));


        return directionDB;
    }
}
