package com.example.sabjimandi.ShopInnerDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabjimandi.MainDashboard.MainDashboard;
import com.example.sabjimandi.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;

public class ShopInnerDetails extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {


    //gesture

    private GestureDetector gestureDetector;

    //favourite icon hooks
    ImageView favouriteIcon;


    RecyclerView recyclerViewShopItem;
    ArrayList<ShopInnerDetailsHelperClass> shopInnerDetailsList;
    ShopInnerDetailsAdapter shopInnerDetailsAdapter;
    DatabaseReference mRef;
    String position, positionItem;

    TextView shopName, shopGenre, shopRating, shopDistance;
    int n, m;

    private BottomSheetBehavior mBottomSheetBehaviour;
    TextView itemValue, itemValue2;

    ImageView orderImage;
    TextView orderName, orderCost;
    String orderRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_inner_details);
        recyclerViewShopItem = findViewById(R.id.recyclerview_shop_items);
        position = getIntent().getStringExtra("position");
        mRef = FirebaseDatabase.getInstance().getReference();

        shopName = findViewById(R.id.shop_name);
        shopDistance = findViewById(R.id.shop_delivery_status);
        shopGenre = findViewById(R.id.shop_genre);
        shopRating = findViewById(R.id.shop_rating);


        View bottomsheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehaviour = BottomSheetBehavior.from(bottomsheet);
        itemValue = findViewById(R.id.item_amount_custom_1);
        itemValue2 = findViewById(R.id.item_amount_custom_2);

        orderImage = findViewById(R.id.order_image);
        orderName = findViewById(R.id.order_name);
        orderCost = findViewById(R.id.order_price);
        generalData();
        getDataFromFirebase();

        this.gestureDetector = new GestureDetector(this, this);
        gestureDetector.setOnDoubleTapListener(this);

        favouriteIcon = findViewById(R.id.favourite_icon);
        favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String _orderName = orderName.getText().toString().trim();
                favouriteIcon.setImageResource(R.drawable.filled_favorite_icon);
                DatabaseReference _mmRef = FirebaseDatabase.getInstance().getReference("User");
                String id =_mmRef.push().getKey();
                _mmRef.child(sharedPreferences.getString("phoneNo", "")).child("favorite").child(id).setValue(_orderName);

            }
        });

    }

    public void callAddToCart(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String _orderName = orderName.getText().toString().trim();
        String _orderCost = orderCost.getText().toString().trim();
        String _orderImage = shopInnerDetailsList.get(Integer.parseInt(positionItem)).getItemImage();
        DatabaseReference _mmRef = FirebaseDatabase.getInstance().getReference("User");
        _mmRef.child(sharedPreferences.getString("phoneNo", "")).child("cart").child(position + positionItem)
                .child("itemName").setValue(_orderName);
        _mmRef.child(sharedPreferences.getString("phoneNo", "")).child("cart").child(position + positionItem)
                .child("itemCost").setValue(_orderCost);
        _mmRef.child(sharedPreferences.getString("phoneNo", "")).child("cart").child(position + positionItem)
                .child("itemQuantity").setValue(String.valueOf(n + m));
        _mmRef.child(sharedPreferences.getString("phoneNo", "")).child("cart").child(position + positionItem)
                .child("itemImage").setValue(_orderImage);
        StyleableToast.makeText(this, "Added to Cart", R.style.exampleToast).show();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("cartStatus","yes");
    }

    public void showAlerter(View v) {

        Alerter.create(this)
                .setTitle("Order booked")
                .setText("booking successful")
                .setBackgroundColorRes(R.color.red)
                .setDuration(5000)
                .setIcon(R.drawable.order_done_icon)
                .setIconColorFilter(getColor(R.color.lightWhite))
                .enableSwipeToDismiss()
                .show();

    }


    public void callBackScreen(View view) {
        Intent i = new Intent(getApplicationContext(), MainDashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();

    }


    public void callRemove(View view) {
        n = Integer.parseInt(itemValue.getText().toString().trim());
        if (n > 0)
            n = n - 1;
        itemValue.setText(String.valueOf(n));
        int r = Integer.parseInt(orderRate) / 2;
        orderCost.setText(String.valueOf(r * m + n * Integer.parseInt(orderRate)));
    }

    public void callAdd(View view) {
        n = Integer.parseInt(itemValue.getText().toString().trim());
        n = n + 1;
        itemValue.setText(String.valueOf(n));
        int r = Integer.parseInt(orderRate) / 2;
        orderCost.setText(String.valueOf(r * m + n * Integer.parseInt(orderRate)));

    }

    public void callRemove2(View view) {

        m = Integer.parseInt(itemValue2.getText().toString().trim());
        if (m > 0)
            m = m - 1;
        itemValue2.setText(String.valueOf(m));
        int r = Integer.parseInt(orderRate) / 2;
        orderCost.setText(String.valueOf(r * m + n * Integer.parseInt(orderRate)));


    }

    public void callAdd2(View view) {
        m = Integer.parseInt(itemValue2.getText().toString().trim());
        m = m + 1;
        itemValue2.setText(String.valueOf(m));
        int r = Integer.parseInt(orderRate) / 2;
        orderCost.setText(String.valueOf(r * m + n * Integer.parseInt(orderRate)));
    }


    private void generalData() {
        DatabaseReference mmRef = FirebaseDatabase.getInstance().getReference("General");
        mmRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                shopName.setText(snapshot.child("shops").child("0" + String.valueOf(Integer.parseInt(position) + 1))
                        .child("shopName").getValue().toString());
                shopDistance.setText(snapshot.child("shops").child("0" + String.valueOf(Integer.parseInt(position) + 1))
                        .child("shopDistanceTime").getValue().toString());
                shopGenre.setText(snapshot.child("shops").child("0" + String.valueOf(Integer.parseInt(position) + 1))
                        .child("shopGenre").getValue().toString());
                shopRating.setText(snapshot.child("shops").child("0" + String.valueOf(Integer.parseInt(position) + 1))
                        .child("shopRating").getValue().toString());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getDataFromFirebase() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewShopItem.setLayoutManager(gridLayoutManager);
        recyclerViewShopItem.setHasFixedSize(true);

        shopInnerDetailsList = new ArrayList<>();
        Update();
    }

    private void Update() {
        Query query = mRef.child("General").child("shops").child("0" + String.valueOf(Integer.parseInt(position) + 1)).child("items");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopInnerDetailsList.clear();

                for (DataSnapshot itemsnapshot : snapshot.getChildren()) {
                    ShopInnerDetailsHelperClass shopInnerDetailsHelperClass = new ShopInnerDetailsHelperClass();
                    shopInnerDetailsHelperClass.setItemName(itemsnapshot.child("name").getValue().toString());
                    shopInnerDetailsHelperClass.setItemImage(itemsnapshot.child("image").getValue().toString());
                    shopInnerDetailsHelperClass.setItemAmount(itemsnapshot.child("amount").getValue().toString());
                    shopInnerDetailsList.add(shopInnerDetailsHelperClass);
                }
                shopInnerDetailsAdapter = new ShopInnerDetailsAdapter(getApplicationContext(), shopInnerDetailsList);
                recyclerViewShopItem.setAdapter(shopInnerDetailsAdapter);
                shopInnerDetailsAdapter.notifyDataSetChanged();
                shopInnerDetailsAdapter.setOnItemClickListener(new ShopInnerDetailsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int _position) {
                        positionItem = String.valueOf(_position);
                        orderName.setText(shopInnerDetailsList.get(_position).getItemName());
                        orderRate = shopInnerDetailsList.get(_position).getItemAmount();
                        Picasso.get().load(shopInnerDetailsList.get(_position).getItemImage()).into(orderImage);
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        n = 0;
                        m = 0;
                        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
