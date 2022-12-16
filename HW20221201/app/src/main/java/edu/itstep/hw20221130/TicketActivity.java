package edu.itstep.hw20221130;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import edu.itstep.hw20221130.models.ConstantsStore;
import edu.itstep.hw20221130.models.OrderTicket;

public class TicketActivity extends AppCompatActivity {

    // поля - змінні класу, що відповідають активним елементам Activity
    private TextView tvDirection;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvNumberOfSeats;
    private TextView tvTotalPrice;

//    // модель
//    private OrderTicket orderTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        initView(); // ініціалізація даних
        initData(); // ініціалізація первинних даних
    }

    private void initView() {
        tvDirection = findViewById(R.id.tvDirection);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvNumberOfSeats = findViewById(R.id.tvNumberOfSeats);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
    }

    private void initData() {
        Intent intent = getIntent();

        OrderTicket orderTicket = (OrderTicket) intent.getSerializableExtra(ConstantsStore.KEY_ORDER_TICKET);
        tvDirection.setText(orderTicket.getDirection());
        tvDate.setText(orderTicket.getDateOfDirection());
        tvTime.setText(orderTicket.getDepartureTime());
        tvNumberOfSeats.setText(orderTicket.getCountPlaces());

        // розрахунок загальної вартості
        int onePlacePrice = Integer.parseInt(orderTicket.getOneTicketCost());
        int numberOfSeats = Integer.parseInt(orderTicket.getCountPlaces());
        tvTotalPrice.setText(String.valueOf(onePlacePrice * numberOfSeats));
    }
}