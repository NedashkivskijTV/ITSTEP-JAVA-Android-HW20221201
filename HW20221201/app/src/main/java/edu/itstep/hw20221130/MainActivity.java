package edu.itstep.hw20221130;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.itstep.hw20221130.models.ConstantsStore;
import edu.itstep.hw20221130.models.DataBase;
import edu.itstep.hw20221130.models.DirectionDB;
import edu.itstep.hw20221130.models.OrderTicket;

public class MainActivity extends AppCompatActivity {

    // поля - змінні класу, що відповідають активним елементам Activity
    // забезпечення роботи спінера по вибору напрямку
    private Spinner spDirection;
    private DirectionDB directionDB; // колекція напрямків (елементи містять колекцію рейсів автобусів)
    //private String[] directionNamesArr = directionDB.getDirectionsNamesArr(); // колекція назв напрямків у форматі String[] - отримується викликом методу
    private ArrayAdapter<String> adapterDirection; // адаптер - оскільки має місце використання масива String[], використовується ArrayAdapter<>
    private int directionPosition = 0; // позиція обраного користувачем напрямку в масиві напрямків (за замовчуванням = 0)

    // забезпечення роботи спінера по вибору часу відправлення
    private Spinner spDepartureTime;
    // забезпечення роботи спінера по вибору часу відправлення
    private ArrayAdapter<String> adapterDepartureTime; // адаптер - оскільки має місце використання масива String[], використовується ArrayAdapter<>
    private String timeSelected; // обраний користувачем час відправлення
    private int timePosition = 0; // позиція обраного користувачем часу відправлення в масиві автобусних рейсів (за замовчуванням = 0)

    private boolean isRotate = false; // змінна, що використовується у якості індикатора, щодо зміни положення пристрою
    private boolean directionChanged = false;

    // лічильник кількості викликів методу створення адаптера для спінера вибору часу виїзду
    // використовується для забезпеченні коректної роботи при переносі даних між Актівіті внаслідок
    // зміни положення екрана
    private int counterTime;

    private Button btnCalendar;
    private TextView tvDate;
    private TextView tvOneTicketCost;

    // зміна лічильника кількості білетів
    private ImageButton ibMinusPlaceCounter;
    private ImageButton ibPlusPlaceCounter;
    private TextView tvPlaceCount;

    private OrderTicket orderTicket; // модель для передачі даних між Актівіті

    private Button btnLaunchTicketActivity; // кнопка переходу на TicketActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseRequest(); // запит до БД на отримання колекції напрямків
        initView(); // ініціалізація даних

        initData(); // ініціалізація первинних даних
        makeAdapters(); // створення адаптерів на елементів Spinner
        setListener(); // підключення слухачів

        dataLoadAfterScreenRotation(savedInstanceState); // завантаження даних після зміни положення екрана
    }

    // Перевизначення методу для відображення меню (Spinner/List)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        // Отримання об'єкта MenuInflater - об'єкт для наповнення/надування меню
        MenuInflater inflater = getMenuInflater();

        // звернення до наповнювача меню через метод inflate() - наповни меню наступними об'єктами
        // приймає 2 параметри
        // 1 - макет меню - підготовлений заздалегідь файл (spinner_to_list.xml)
        // 2 - об'єкт menu, що отримується в параметрах поточного методу
        inflater.inflate(R.menu.spinner_to_list, menu);

        // при поверненні true - меню відображатиметься, згенерований код повертає false
        return true;
    }

    // Перевизначення методу для підключення слухачів по натисканню на елементи меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // отриманн id обраного елемента меню (приходить у параметрах)
        int id = item.getItemId();

        // реалізація алгоритмів відповідно до обраного пункту меню 
        switch (id) {
            case R.id.menuItemSpinner: {
                Toast.makeText(this, "The application works in spinner mode", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.menuItemList: {
                //startActivity(new Intent(this, MainActivityList.class));
                // створення об'єкта intent
                Intent intent = new Intent(MainActivity.this, ListActivity.class);

                setDataToOrderTicket(); // наповнення моделі даними

                // передача моделі з даними до intent
                intent.putExtra(ConstantsStore.KEY_ORDER_TICKET, orderTicket);
                startActivity(intent); // запуск Актівіті

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // збереження даних при зміні положення екрана
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // відправка моделі до об'єкта Bundle
        // використовується метод .putSerializable() оскільки OrderTicket імплементує інтерфейс Serializable
        setDataToOrderTicket(); // наповнення моделі даними
        outState.putSerializable(ConstantsStore.KEY_ORDER_TICKET, orderTicket);
    }

    // завантаження даних після зміни положення екрана
    private void dataLoadAfterScreenRotation(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // отримання даних - моделі OrderTicket з об'єкта Bundle
            // використовується метод .getSerializable() оскільки OrderTicket імплементує інтерфейс Serializable
            orderTicket = (OrderTicket) savedInstanceState.getSerializable(ConstantsStore.KEY_ORDER_TICKET);

            // оновлення стану активних елементів Актівіті після зміни положення екрану
            isRotate = true;
            //++counterRotate;
            directionPosition = orderTicket.getDirectionPosition();
            timePosition = orderTicket.getTimePosition();
            timeSelected = orderTicket.getDepartureTime();

            tvDate.setText(orderTicket.getDateOfDirection());
            tvPlaceCount.setText(orderTicket.getCountPlaces());

            directionChanged = orderTicket.isDirectionChanged();
        }

    }

    // запит до БД на отримання колекції напрямків
    private void databaseRequest() {
        directionDB = DataBase.getDirectionsList();
    }

    // ініціалізація даних
    private void initView() {
        // спіннер вибору напрямку
        spDirection = findViewById(R.id.spDirection);

        // спіннер вибору часу відправлення
        //spDirectionTime = findViewById(R.id.spDirectionTime);
        spDepartureTime = findViewById(R.id.spDepartureTime);

        btnLaunchTicketActivity = findViewById(R.id.btnLaunchTicketActivity);
        btnCalendar = findViewById(R.id.btnCalendar);
        tvDate = findViewById(R.id.tvDate);
        tvOneTicketCost = findViewById(R.id.tvOneTicketCost);

        // лічильник кількості білетів
        ibMinusPlaceCounter = findViewById(R.id.ibMinusPlCounter);
        ibPlusPlaceCounter = findViewById(R.id.ibPlusPlCounter);
        tvPlaceCount = findViewById(R.id.tvPlaceCount);
    }

    // створення адаптерів та елементів Spinner
    private void makeAdapters() {
        // створення адаптера для Спінера вибору напрямку
        adapterDirection = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, directionDB.getDirectionsNamesArr()); // ініціалізація адаптера
        spDirection.setSelection(directionPosition); // встановлення елемента, що відображатиметься при завантаженні Актівіті
        spDirection.setAdapter(adapterDirection); // встановлення Adapter у елемент Spinner

        // створення адаптера для Спінера вибору часу відправлення
        //makeAdapterDepartureTime();
    }

    // створення адаптера для Спінера вибору часу відправлення -
    // запускається в коді слухача по вибіру напрямку - метод setListener()
    private void makeAdapterDepartureTime() {

        // конструкція для забезпеченя коректної роботи при переносі даних між спінерами
        // в результаті зміни положення екрана
        ++counterTime; // збільшення лічильника (кількість випадків виклитку методу)
        if (!isRotate) {
            timePosition = 0;
        } else if (!directionChanged && counterTime > 1) {
            timePosition = 0;
        } else if (counterTime > 2) {
            timePosition = 0;
        }

        // створення адаптера для Спінера вибору конкретного рейма автобуса (вибір часу виїзду)
        // ініціалізація адаптера
        adapterDepartureTime = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, directionDB.getDepartureTimeArr(directionPosition));
        spDepartureTime.setAdapter(adapterDepartureTime); // встановлення елемента, що відображатиметься при завантаженні Актівіті
        spDepartureTime.setSelection(timePosition); // встановлення адаптера на спінер

        // виведення контрольних даних при тестуванні роботи спінерів
        //Toast.makeText(this, "counterDirection=" + counterDirection + " counterTime=" + counterTime, Toast.LENGTH_LONG).show();
    }

    // перевірка коректності введеної дати та часу - якщо час минув виводить повідомлення
    //private boolean checkSelectedTime(String departureTime) {
    private boolean checkSelectedTime() {
        Calendar calendar = Calendar.getInstance();
        Date dateTimeNaw = new GregorianCalendar(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
        ).getTime();

        String date = tvDate.getText().toString();
        String[] dateToArr = date.split("\\.");

        //Toast.makeText(this, "date=" + date, Toast.LENGTH_SHORT).show();
        String[] departureTimeToArr = timeSelected.split(":");
        Date dateTimeSelected = new GregorianCalendar(
                Integer.parseInt(dateToArr[2]),
                Integer.parseInt(dateToArr[1]) - 1,
                Integer.parseInt(dateToArr[0]),
                Integer.parseInt(departureTimeToArr[0]),
                Integer.parseInt(departureTimeToArr[1])
        ).getTime();

        return !(dateTimeSelected.getTime() < dateTimeNaw.getTime());
    }

    // виведення повідомлення про обрану застарілу дату/час
    private void showOutOfDateAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.Attention))
                .setMessage(getResources().getString(R.string.AttentionText))
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    // функціонал по натисненню на кнопку
                    // TODO
                    // при натисненні кнопки дія відсутня
                });
        builder.show();
    }

    // перевірка коректності дати та виведення попереджувального повідомлення у разі потреби
    // (для зменшення рядків коду)
    private void checkSelectedTimeAndShowOutOfDateAlertDialog() {
        if (!checkSelectedTime()) {
            //showOutOfDateAlertDialog();
            Toast.makeText(this, getResources().getString(R.string.ToastPastDate), Toast.LENGTH_SHORT).show();
        }
    }

    // підключення слухачів
    private void setListener() {

        // встановлення слухача/події на об’єкт Spinner (вибір напрямку) – обробника по натисканню на кнопку/елемент списка
        spDirection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                directionPosition = position; // збереження обраної користувачем позиції у масиві напрямків (використовується для отримання масиву часу відправлення)

                // виклик методу створення адаптера для спінера вибору часу відправлення
                // дозволяє відображати дані, пов'язані з обраним напрямком -
                // колекція часу відправлення змінюється в залежності від обраного напрямку
                makeAdapterDepartureTime();

                // збереження даних до моделі
                // дані зберігіються в моделі у окремому методі
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // встановлення слухача/події на об’єкт Spinner (вибір часу виїзду) – обробника по натисканню на кнопку/елемент списка
        spDepartureTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedDepartureTime = adapterView.getItemAtPosition(position).toString();
                timePosition = position; // збереження обраної користувачем позиції у масиві автобусних рейсів (використовується для передачі обраної позиції у модель при повороті екрана)
                timeSelected = selectedDepartureTime; // збереження обраного користувачем часу відправлення (використовується при перевірці коректності обраних дати та часу (має бути не пізніше поточного часу),  а також для передачі даних у модель при переході до наступного Актівіті)

                // перевірка коректності обраного часу (має бути не раніше поточного часу)
                checkSelectedTimeAndShowOutOfDateAlertDialog();

                // виведення вартості 1 квитка (залежить від напрямку та часу виїзду (виїзди в різний час здійснюють різні перевізники ...))
                showOneTicketCost(position);

                // збереження даних до моделі
                // дані зберігіються в моделі у окремому методі
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // встановлення слухача/події на кнопку зміни дати виїзду та поле виведення дати
        btnCalendar.setOnClickListener(view -> {
            changeDirectionDate();
        });
        tvDate.setOnClickListener(view -> {
            changeDirectionDate();
        });

        // підключення слухачів до кнопок зміни лічильника кількості мусць
        ibMinusPlaceCounter.setOnClickListener(view -> {
            changePlaceCounter(false);
        });
        ibPlusPlaceCounter.setOnClickListener(view -> {
            changePlaceCounter(true);
        });

        // підключення слухача на кнопку ОК - перехід на TicketActivity
        btnLaunchTicketActivity.setOnClickListener(view -> {
            // перевірка коректності обраного часу (має бути не раніше поточного часу)
            if (!checkSelectedTime()) {
                showOutOfDateAlertDialog();
            } else {
                // створення об'єкта intent
                Intent intent = new Intent(this, TicketActivity.class);

                setDataToOrderTicket(); // наповнення моделі даними

                // передача моделі з даними до intent
                intent.putExtra(ConstantsStore.KEY_ORDER_TICKET, orderTicket);
                startActivity(intent); // запуск Актівіті
            }
        });
    }

    // наповнення моделі даними
    private void setDataToOrderTicket() {
        // запис даних до моделі -
        // оскільки сторінка при завантаженні відображає поточні дані (може бути натиснута кнопка ОК)
        // наповнення моделі даними відбувається перед відкриванням нового Актівіті
        orderTicket.setDirection(directionDB.getDirectionList().get(directionPosition).getDirectionName());
        orderTicket.setDateOfDirection(tvDate.getText().toString());
        orderTicket.setCountPlaces(tvPlaceCount.getText().toString());
        orderTicket.setDepartureTime(timeSelected);
        orderTicket.setOneTicketCost(tvOneTicketCost.getText().toString());

        if (!directionChanged && orderTicket.getDirectionPosition() != directionPosition) {
            orderTicket.setDirectionChanged(true);
        }
        orderTicket.setDirectionPosition(directionPosition);

        orderTicket.setTimePosition(timePosition);
    }

    // виведення вартості одного квитка
    private void showOneTicketCost(int departureTimePosition) {
        int oneTicketCost = directionDB
                .getDirectionList()
                .get(directionPosition)
                .getBusFlightList()
                .get(departureTimePosition)
                .getDirectionPrice();

        tvOneTicketCost.setText(String.valueOf(oneTicketCost));

        // збереження даних у моделі
        // дані зберігаються до моделі в окремому методі
    }

    private void changeDirectionDate() {
        // отримання поточної дани для виділення у ДатаПікер
        Calendar calendar = Calendar.getInstance();
        int presentYear = calendar.get(Calendar.YEAR);
        int presentMonth = calendar.get(Calendar.MONTH);
        int presentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Демонстрація календаря по натисканню на кнопку
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, year, month, dayOfMonth) -> {
                    Date dateNaw = new GregorianCalendar(presentYear, presentMonth, presentDay).getTime();
                    Date d = new GregorianCalendar(year, month, dayOfMonth).getTime();
                    if (d.getTime() < dateNaw.getTime()) { // перевірка на введення дати, що минула
                        //Toast.makeText(this, "You cannot select a past date", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, getResources().getString(R.string.ToastPastDate), Toast.LENGTH_SHORT).show();
                    } else {
                        String date = new SimpleDateFormat(getResources().getString(R.string.pattern_date)).format(d);
                        tvDate.setText(date);

                        // перевірка коректності обраного часу (має бути не раніше поточного часу)
                        checkSelectedTimeAndShowOutOfDateAlertDialog();

                        // збереження даних у моделі (дата виїзду)
                        // дані зберігаються до моделі в окремому методі
                    }
                },
                presentYear,
                presentMonth,
                presentDay
        );
        datePickerDialog.show(); // виклик метода show() для відображення діалогового вікна
    }

    // ініціалізація первинних даних
    private void initData() {
        // ініціалізація моделі для передачі даних між Актівіті
        orderTicket = new OrderTicket();

        // первинне відображення дати елементом tvDate
        Date d = Calendar.getInstance().getTime();
        //String date = new SimpleDateFormat("dd.MM.yyyy").format(d);
        String date = new SimpleDateFormat(getResources().getString(R.string.pattern_date)).format(d);
        tvDate.setText(date);
        // збереження даних до моделі
        // дані зберігаються до моделі в окремому методі

        // первинне значення лічильника кількості білетів
        tvPlaceCount.setText(String.valueOf(1));
        // збереження даних до моделі
        // дані зберігаються до моделі в окремому методі

        // Ініціалізація даних у разі переходу дане Актівіті через натиснення пункта меню Spinner
        Intent intent = getIntent();
        OrderTicket tempOrderTicket = (OrderTicket) intent.getSerializableExtra(ConstantsStore.KEY_ORDER_TICKET);
        if (tempOrderTicket != null) {
            //Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show();
            directionPosition = tempOrderTicket.getDirectionPosition();
            timePosition = tempOrderTicket.getTimePosition();
            timeSelected = tempOrderTicket.getDepartureTime();

            tvOneTicketCost.setText(tempOrderTicket.getOneTicketCost());
            tvDate.setText(tempOrderTicket.getDateOfDirection());
            tvPlaceCount.setText(tempOrderTicket.getCountPlaces());

            directionChanged = tempOrderTicket.isDirectionChanged();
        }

    }

    // зміна значення лічильника кількості мість при натисненні на кнопку
    private void changePlaceCounter(boolean isPlus) {
        int counter = Integer.parseInt(tvPlaceCount.getText().toString());
        String newCounter = isPlus ? String.valueOf(counter + 1) : (counter > 1 ? String.valueOf(counter - 1) : "1");
        tvPlaceCount.setText(newCounter);

        // збереження даних до моделі
        // дані зберігаються до моделі в окремому методі
    }

}