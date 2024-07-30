package com.example.likonirestaurante.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.Domain.OrderDatabaseDomain;
import com.example.likonirestaurante.Helper.ManagementCart;
import com.example.likonirestaurante.Helper.TinyDB;
import com.example.likonirestaurante.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TrackOrder extends AppCompatActivity {
    private static final String MESSAGE_ID = "phoneno_prefs";

    // Declare global variables
    private ManagementCart managementCart;

    private Button confirmButton, MealReceipt, TrackOrder;
    private DatabaseReference mDatabase;

    TextView OrderDate, Status;
    TextView OrderId, MpesaId, UserName, phoneNo;
    TextView Price;
    TextView Cookingtime;
    TextView CurrentTime, textViewETA;
    TextView PaymentTime;
    TextView ProcessingTime;
    TextView PickUpTime, ready_info;
    ProgressBar PB1, PB2, PB3, PB4;
    View view1, view3, view4;
    ImageView checkPB1, checkPB2, checkPB3, checkPB4;

    private String Id;
    private String Amount;
    private String orderId;


    String Date;
    private String phoneNumber;
    private String eta;
    private int Time;
    private OrderDatabaseDomain object;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        // Instantiate variables
        OrderDate = findViewById(R.id.Date);
        UserName = findViewById(R.id.Username);
        phoneNo = findViewById(R.id.phone_number);
        OrderId = findViewById(R.id.order_id);
        Price = findViewById(R.id.order_ammount);
        Cookingtime = findViewById(R.id.eta);
        PaymentTime = findViewById(R.id.payment_time);
        CurrentTime = findViewById(R.id.current_time);
        ProcessingTime = findViewById(R.id.process_time);
        PickUpTime = findViewById(R.id.pickup_time);
        textViewETA = findViewById(R.id.textViewETA);
        ready_info = findViewById(R.id.Pickup_ready_info);

        Status = findViewById(R.id.status);
        MpesaId = findViewById(R.id.mpesa_id);

        PB1 = findViewById(R.id.progressBar7);
        PB2 = findViewById(R.id.progressBar4);
        PB3 = findViewById(R.id.progressBar5);
        PB4 = findViewById(R.id.progressBar6);

        checkPB1 = findViewById(R.id.imageView15);
        checkPB2 = findViewById(R.id.imageView18);
        checkPB3 = findViewById(R.id.imageView20);
        checkPB4 = findViewById(R.id.imageView22);

        view1 = findViewById(R.id.view);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);

        confirmButton = findViewById(R.id.confirm_button);
        MealReceipt = findViewById(R.id.meal_reciept);
        TrackOrder = findViewById(R.id.TrackOrder);

        managementCart = new ManagementCart(this);

        object = (OrderDatabaseDomain) getIntent().getSerializableExtra("object");


        // Get the current time AM/PM format
        SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
        String currentTime = time.format(Calendar.getInstance().getTime());
        CurrentTime.setText(currentTime);
        PaymentTime.setText(currentTime);
        ProcessingTime.setText(currentTime);
        PickUpTime.setText(currentTime);


        if (object == null) {

            ArrayList<FoodDomain> CartItems = managementCart.getListCart();
            if (CartItems.isEmpty()) {
                NoitemPrompt();
            } else {
                getAccountDetails();
                getIntentBundle();
                setTextViews();
                database();
            }
        } else {
            getOrderDetails();
        }

        bottomNavigationView();

    }


    // Method to get any IntentBundle plus shared preference
    private void getIntentBundle() {
        // Get Phone Number from Shared Preferences
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        Id = getSharedPrefs.getString("PhoneNumber", "Login With Phone Number");


        // To retrieve the total amount and ETA from the cart activity
        Amount = String.valueOf(getIntent().getDoubleExtra("total_amount", 0));
        Price.setText("KES " + Amount);

        Time = getIntent().getIntExtra("total_time", 0);
        Cookingtime.setText("Cooking Time: " + Time + " Mins");

    }


    private void setTextViews() {
        Calendar calendar = Calendar.getInstance();
        // Code creates a SimpleDateFormat object with the LLLL, dd, yy pattern,
        // which specifies a date format with the full month name, day, and year.
        SimpleDateFormat sdf = new SimpleDateFormat("LLLL dd,yyyy");
        Date = sdf.format(calendar.getTime());
        OrderDate.setText(Date);

        // This code generates an ID with the format timestamp-randomNumber
        orderId = calendar.getTimeInMillis() + "-" + new Random().nextInt(10000);
        OrderId.setText(orderId);

// Setting up the Ready Info
        ready_info.setText("Order ID : " + orderId + " is ready for pick up");

    }

    private void getAccountDetails() {

//Retrieving data from the Profile Database to set them as Account Details

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Ds : dataSnapshot.getChildren()) {

                    phoneNumber = Ds.child("phoneNumber").getValue((String.class));


                    if (Ds.child("phoneNumber").getValue().equals(Id)) {
                        UserName.setText(Ds.child("firstName").getValue(String.class) + " " + Ds.child("lastName").getValue(String.class));
                        phoneNo.setText(Ds.child("phoneNumber").getValue(String.class));
                    }

                    PB1.setVisibility(View.GONE);
                    checkPB1.setVisibility(View.VISIBLE);

                    view1.setVisibility(View.VISIBLE);
                    PB2.setVisibility(View.VISIBLE);

                    MpesaId.setVisibility(View.VISIBLE);
                    confirmButton.setVisibility(View.VISIBLE);

                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void database() {
        //Accessing the Firebase Database Class and Instantiate by getting Reference Path




// Add a listener to the Switch to handle when the user confirms the order
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the Switch is checked, save the order details to the Firebase Realtime Database
                String orderId = OrderId.getText().toString();
                String date = OrderDate.getText().toString();
                String totalAmount = Price.getText().toString();
                String mpesaId = MpesaId.getText().toString();
                String userName = UserName.getText().toString();
                String PhoneNo = phoneNo.getText().toString();
                String cookingTime = String.valueOf(Time);
                String status = "";


                if (mpesaId.isEmpty() || userName.isEmpty()) {
                    showErrorDialog();
                } else {

                    // Connect to the Firebase Realtime Database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ordersRef = database.getReference("Orders");

                    // Generate a unique key for the new order
                    String orderKey = ordersRef.child(PhoneNo + "- " + mpesaId).getKey();

                    // Create a map to store the order details
                    Map<String, Object> orderMap = new HashMap<>();
                    orderMap.put("name", userName);
                    orderMap.put("phoneNumber", PhoneNo);
                    orderMap.put("mpesaID", mpesaId);
                    //  orderMap.put("locationPin", locationPin);
                    orderMap.put("cookingTime", cookingTime);
                    orderMap.put("orderId", orderId);
                    orderMap.put("date", date);
                    orderMap.put("totalAmount", String.valueOf(totalAmount));
                    orderMap.put("status", status);

                    // Add the order to the "orders" node in the Firebase Realtime Database
                    ordersRef.child(orderKey).setValue(orderMap);

                    PB2.setVisibility(View.GONE);
                    checkPB2.setVisibility(View.VISIBLE);


                    confirmOrder();

                    MpesaId.setVisibility(View.GONE);
                    confirmButton.setVisibility(View.GONE);
                    view4.setVisibility(View.VISIBLE);
                    TrackOrder.setVisibility(View.VISIBLE);

                    TrackOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(TrackOrder.this, Purchases.class);
                            startActivity(intent);
                        }
                    });

                }

            }

        });

        SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
        String currentTime = time.format(Calendar.getInstance().getTime());
        PaymentTime.setText(currentTime);

    }


    //Setting an Alert Dialog For login Failure
    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Placing Order Failed")
                .setMessage("Fill in the necessary details and try again.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    //   This code creates an alert dialog with the title "Confirming Order" ]
//   and the message "Please be patient as we confirm your order.
//   Countdown: 10 seconds" and it will be shown to the user when the confirm button is clicked.
//   It also creates a CountDownTimer that counts down 10 seconds
//   and updates the message in the alert dialog every second.
//   After the 10 seconds, it dismisses the alert dialog and checks the status of the order,
//   and displays a Toast message based on the status of the order.
    private void confirmOrder() {
        Context context = this;


        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
        final String PhoneNo = phoneNo.getText().toString();
        final String mpesaId = MpesaId.getText().toString();
        final String orderKey = ordersRef.child(PhoneNo + "- " + mpesaId).getKey();


        final Handler handler = new Handler();

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirming Order")
                .setMessage("Please be patient as we confirm your order.\n Countdown: 10 seconds")
                .setCancelable(false);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

// Create the CountDownTimer
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                alertDialog.setMessage("Please be patient as we confirm your order.\n Countdown: " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
// if the timer finishes and the order is not confirmed yet we can show an error message

                ordersRef.child(orderKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String status = dataSnapshot.child("status").getValue(String.class);
                        if (!status.equals("Delivering")) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Error")
                                    .setMessage("Error in Transaction. Please try again, if problem persist contact us through support.")
                                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                    .show();

                            alertDialog.dismiss();
                            MpesaId.setVisibility(View.VISIBLE);
                            confirmButton.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }.start();

        final TinyDB tinyDB = new TinyDB(getApplicationContext());

        // Listen for changes to the status field in the database
        ordersRef.child(orderKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("status").getValue(String.class);
                if (status.equals("Delivering")) {
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Order Confirmed and Cart cleared. Taste is our identity", Toast.LENGTH_SHORT).show();


                    // You can also stop the listener here so it doesn't keep listening for updates after the order is confirmed.
                    ordersRef.child(orderKey).removeEventListener(this);


                    PB3.setVisibility(View.GONE);
                    checkPB3.setVisibility(View.VISIBLE);

                    view3.setVisibility(View.VISIBLE);
                    PB4.setVisibility(View.VISIBLE);
                    Status.setVisibility(View.VISIBLE);
                    Status.setText(status);

                    SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
                    String currentTime = time.format(Calendar.getInstance().getTime());
                    PaymentTime.setText(currentTime);
                    ProcessingTime.setText(currentTime);


//Code that sets the ETA TextView by adding the cooking time to the current time:

                    Calendar cal = Calendar.getInstance();

// Parsing the current time back to a date object can throw a ParseException,
// add try-catch block around it.
                    try {
                        cal.setTime(time.parse(currentTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cal.add(Calendar.MINUTE, Time);
                    String eta = time.format(cal.getTime());

                    PickUpTime.setText(eta);
                    textViewETA.setText("Estimated Time of Arrival is: " + eta);


                    //Save the order details to the Firebase Realtime Database
                    String orderId = OrderId.getText().toString();
                    String date = OrderDate.getText().toString();
                    String totalAmount = Price.getText().toString();
                    String mpesaId = MpesaId.getText().toString();
                    String userName = UserName.getText().toString();
                    String PhoneNo = phoneNo.getText().toString();
                    String cookingTime = String.valueOf(Time);
                    String Orderstatus = status;
                    ArrayList<FoodDomain> cartItems = managementCart.getListCart();

                    String TimeofOrder = CurrentTime.getText().toString();
                    String paymentTime = PaymentTime.getText().toString();
                    String processingTime = ProcessingTime.getText().toString();
                    String pickUpTime = PickUpTime.getText().toString();

                    if (mpesaId.isEmpty() || userName.isEmpty()) {
                        showErrorDialog();
                    } else {
                        // Generate a unique key for the new order
                        String orderKey = ordersRef.child(PhoneNo + "- " + mpesaId).getKey();

                        // Create a map to store the order details
                        Map<String, Object> orderMap = new HashMap<>();
                        orderMap.put("name", userName);
                        orderMap.put("phoneNumber", PhoneNo);
                        orderMap.put("mpesaID", mpesaId);
                        //  orderMap.put("locationPin", locationPin);
                        orderMap.put("cookingTime", cookingTime);
                        orderMap.put("orderId", orderId);
                        orderMap.put("date", date);
                        orderMap.put("totalAmount", String.valueOf(totalAmount));
                        orderMap.put("cartItems", cartItems);
                        orderMap.put("status", Orderstatus);
                        orderMap.put("TimeofOrder", TimeofOrder);
                        orderMap.put("PaymentTime", paymentTime);
                        orderMap.put("ProcessingTime", processingTime);
                        orderMap.put("PickUptime", pickUpTime);


                        // Add the order to the "orders" node in the Firebase Realtime Database
                        ordersRef.child(orderKey).setValue(orderMap);

                    }

                    tinyDB.clear();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void getOrderDetails() {
        UserName.setText(object.getName());
        phoneNo.setText(object.getPhoneNumber());
        OrderId.setText(object.getOrderId());
        OrderDate.setText(object.getDate());
        Status.setText("Status: " + object.getStatus());
        Price.setText(object.getTotalAmount());
        Cookingtime.setText("Cooking Time: " + object.getCookingTime() + " Mins");
        CurrentTime.setText(object.getTimeofOrder());
        PaymentTime.setText(object.getPaymentTime());
        ProcessingTime.setText(object.getProcessingTime());
        PickUpTime.setText(object.getPickUpTime());


        // Connect to the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("Orders");

        // Get the order ID from the intent
        final String orderId = object.getOrderId();

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (object.getStatus().equals("Delivered")) {

                        // Setting up the Ready Info
                        ready_info.setText("Order ID : " + object.getOrderId() + " is ready for pick up");

                        checkPB1.setVisibility(View.VISIBLE);
                        view1.setVisibility(View.VISIBLE);
                        checkPB2.setVisibility(View.VISIBLE);

                        MpesaId.setVisibility(View.VISIBLE);
                        MpesaId.setText(object.getMpesaId());

                        checkPB3.setVisibility(View.VISIBLE);
                        view4.setVisibility(View.VISIBLE);
                        checkPB4.setVisibility(View.VISIBLE);

                        textViewETA.setText("Estimated Time of Arrival is: " + object.getPickUpTime());

                        MealReceipt.setVisibility(View.VISIBLE);

                        MealReceipt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                OrderDatabaseDomain Order = new OrderDatabaseDomain();

                                Intent intent = new Intent(TrackOrder.this,Receipt.class);
                                intent.putExtra("object", object );
                                startActivity(intent);

                            }
                        });



                    } else {


                        Cookingtime.setText("Cooking Time: " + object.getCookingTime() + " Mins");
                        // Setting up the Ready Info
                        ready_info.setText("Order ID : " + object.getOrderId() + " is ready for pick up");

                        checkPB1.setVisibility(View.VISIBLE);
                        view1.setVisibility(View.VISIBLE);
                        checkPB2.setVisibility(View.VISIBLE);

                        MpesaId.setVisibility(View.VISIBLE);
                        MpesaId.setText(object.getMpesaId());

                        checkPB3.setVisibility(View.VISIBLE);
                        view4.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.VISIBLE);
                        PB4.setVisibility(View.VISIBLE);

                        textViewETA.setText("Estimated Time of Arrival is: " + object.getPickUpTime());


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TrackOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void NoitemPrompt() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("No Item has been ordered")
                .setMessage("Shop with Likoni Restaurant. Go to Main Menu.")
                .setPositiveButton("Main Menu", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TrackOrder.this, MainMenu.class);
                        intent.putExtra("Phone", Id);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void bottomNavigationView() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout ProfileBtn = findViewById(R.id.ProfileBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout SupportBtn = findViewById(R.id.SupportBtn);
        LinearLayout TrackBtn = findViewById(R.id.TrackOrderBtn);


//Button Response for MainMenu button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrder.this, MainMenu.class);
                intent.putExtra("Phone", Id);
                startActivity(intent);
            }
        });

//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrder.this, Account.class);
                intent.putExtra("PhoneNo", Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrder.this, Cart.class);
                intent.putExtra("PhoneNo", Id);
                startActivity(intent);
            }
        });

//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrder.this, Support.class);
                intent.putExtra("PhoneNo", Id);
                startActivity(intent);
            }
        });

        TrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrder.this, Purchases.class);
                intent.putExtra("PhoneNo", Id);
                startActivity(intent);
            }
        });

    }
    }
