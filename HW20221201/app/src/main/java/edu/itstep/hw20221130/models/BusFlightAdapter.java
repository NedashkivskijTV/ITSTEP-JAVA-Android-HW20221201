package edu.itstep.hw20221130.models;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.itstep.hw20221130.R;

public class BusFlightAdapter extends ArrayAdapter<BusFlight> {

    // збереження значень – поля екземпляра класу
    private int resource;
    private List<BusFlight> busFlights;
    private Direction direction;
    private String directionDate;

    // створення парсера
    private LayoutInflater inflater;


    //public BusFlightAdapter(@NonNull Context context, int resource, @NonNull List<BusFlight> busFlights) {
    public BusFlightAdapter(@NonNull Context context, int resource, Direction direction, String directionDate) {
        super(context, resource, direction.getBusFlightList());

        // збереження даних, що прийшли в конструктор у полях екземпляра класу
        this.resource = resource;
        this.busFlights = direction.getBusFlightList();
        this.direction = direction;
        this.directionDate = directionDate;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // створення елементів списка за допомогою inflater
        // параметри
        // 1 - як саме виглядатиме елемент списка
        // 2 - інформація про батьківська група елементів - parent - контейнер для усіх елементів списка
        // надходить самостійно у параметрах методу
        // 3 - false (параметр вказує чи потрібно доєднувати корньовий елемент до батька-контейнера, зазвичай встановлюється false)
        // item - контейнер для розміщення елементів, що відображатимуться у кількості, що залежить від розміру колекції
        View item = inflater.inflate(resource, parent, false);

        // Отримання елементів xml-шаблона
        TextView tvTimeStart = item.findViewById(R.id.tvTimeStart);
        TextView tvTimeFinish = item.findViewById(R.id.tvTimeFinish);
        TextView tvDateStart = item.findViewById(R.id.tvDateStart);
        TextView tvDateFinish = item.findViewById(R.id.tvDateFinish);
        TextView tvTravelTime = item.findViewById(R.id.tvTravelTime);
        TextView tvPlacePrice = item.findViewById(R.id.tvPlacePrice);
        TextView tvTownStart = item.findViewById(R.id.tvTownStart);
        TextView tvTownFinish = item.findViewById(R.id.tvTownFinish);
        TextView tvAddressStart = item.findViewById(R.id.tvAddressStart);
        TextView tvAddressFinish = item.findViewById(R.id.tvAddressFinish);

        // отримання поточного елемента із загальної колекції за позицією
        BusFlight busFlight = busFlights.get(position);

//        // Отримання доступу до ресурсів (варіанти)
//        Resources resources = Resources.getSystem();
//        String moneyTitle = Resources.getSystem().getString(R.string.listUkrMoney);
//        String moneyTitle = getContext().getString(R.string.listUkrMoney);

        // Передача потрібних даних для відображення у відповідних полях шаблона
        tvTimeStart.setText(busFlight.getDepartureTime());

        // встановлення часу прибуття
        //tvTimeFinish.setText("---");
        tvTimeFinish.setText(TimeCalculator.getDatePlusInterval(
                directionDate,
                busFlight.getDepartureTime(),
                (int) busFlight.getDirectionTime(),
                getContext().getString(R.string.pattern_time)
        ));

        // встановлення дати відправлення
        //tvDateStart.setText(directionDate);
        tvDateStart.setText(TimeCalculator.getDatePlusInterval(
                directionDate,
                busFlight.getDepartureTime(),
                0,
                getContext().getString(R.string.pattern_date_short)
        ));

        // встановленння дати прибуття
        //tvDateFinish.setText("---");
        tvDateFinish.setText(TimeCalculator.getDatePlusInterval(
                directionDate,
                busFlight.getDepartureTime(),
                (int) busFlight.getDirectionTime(),
                getContext().getString(R.string.pattern_date_short)
        ));

        // встановлення часу (год. хв.) в дорозі
        String h = String.valueOf(TimeCalculator.longToHoursOrMinutes(busFlight.getDirectionTime(), true));
        String m = String.valueOf(TimeCalculator.longToHoursOrMinutes(busFlight.getDirectionTime(), false));
        String hourStr = getContext().getString(R.string.hourStr);
        String minStr = getContext().getString(R.string.minStr);
        String onWayStr = getContext().getString(R.string.onWayStr);
        //tvTravelTime.setText(h + " h " + m + " m on the way" );
        tvTravelTime.setText(h + " " + hourStr + " " + m + " " + minStr + " " + onWayStr);

        // встановлення вартості 1 квитка
        String moneyTitle = getContext().getString(R.string.listUkrMoney); // отримання назви валюти з системного ресурсу string
        tvPlacePrice.setText(busFlight.getDirectionPrice() + " " + moneyTitle);

        tvTownStart.setText(direction.getDirectionTownStart());
        tvTownFinish.setText(direction.getDirectionTownFinish());
        tvAddressStart.setText(busFlight.getAddressDeparture());
        tvAddressFinish.setText(busFlight.getAddressArrival());

        return item;
        // return super.getView(position, convertView, parent);
    }
}
