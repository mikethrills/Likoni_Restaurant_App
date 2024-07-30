package com.example.likonirestaurante.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likonirestaurante.Adapter.CategoryAdapter;
import com.example.likonirestaurante.Adapter.MenuAdapter;
import com.example.likonirestaurante.Adapter.PopularAdapter;
import com.example.likonirestaurante.Domain.CategoryDomain;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.Helper.ManagementCart;
import com.example.likonirestaurante.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainMenu extends AppCompatActivity {

    // Creating a shared preference Filename and key name value that will be used to access it
    private static final String MESSAGE_ID = "phoneno_prefs";
    private RecyclerView.Adapter adapter, adapter2, adapter3;
    private RecyclerView recyclerViewCategoryView;
    private MenuAdapter menuAdapter;
    private RecyclerView recyclerViewPopularView;
    private TextView CartNo;

    private SearchView searchFood;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        recyclerViewCategory();
        recyclerViewPopular();
        recyclerViewMenu();
        bottomNavigationView();

        ManagementCart managementCart = new ManagementCart(this);
        String NumberInCart = String.valueOf(managementCart.getListCart().size());
        CartNo = findViewById(R.id.cartBtntxt);
        CartNo.setText("Cart: " + NumberInCart);

        searchFood = findViewById(R.id.SearchFood);
        setupSearchView();
    }

    private void setupSearchView() {
        searchFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (menuAdapter != null) {
                    menuAdapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (menuAdapter != null) {
                    menuAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }
    // Creating a Menu recyclerView method
    private void recyclerViewMenu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewMenu = findViewById(R.id.Menuview);
        recyclerViewMenu.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> MenuList = new ArrayList<>();
        MenuList.add(new FoodDomain("Pilau","pilau","Indulge in the flavors of East Africa with our delicious Pilau. Our Pilau is a traditional Kenyan dish made with fragrant rice and a blend of spices, it's a perfect side dish to any meal.", 170.0, 4.6, 45, 65.2));
        MenuList.add(new FoodDomain("Rice and Minji", "rice_minji", "Try our delicious combination of Rice and Minji. Minji is a popular Kenyan dish made with ground meat and spices, it's a great source of protein and it will be served with rice.", 250.0, 4.8, 20, 600.0));
        MenuList.add(new FoodDomain("Rice and Beans", "rice_beans", "Indulge in the flavors of East Africa with our Rice and Beans. Our beans are cooked with a blend of spices and served with rice.", 250.0, 4.7, 25, 600.0));
        MenuList.add(new FoodDomain("Rice and Green grams", "rice_ndegu", "Experience the taste of rice and green grams, a traditional Kenyan dish made with green grams and served with rice.", 200.0, 4.6, 20, 500.0));
        MenuList.add(new FoodDomain("Rice and Beef", "rice_beef", "Try our delicious combination of Rice and Beef. Our beef is cooked with a blend of spices and served with rice.", 250.0, 4.8, 20, 600.0));
        MenuList.add(new FoodDomain("Rice and Matumbo", "rice_matumbo", "Try our unique and delicious combination of Rice and Matumbo. Matumbo is a traditional delicacy made from the intestines of cows, and it's a great source of protein, it will be served with rice.", 250.0, 4.7, 25, 600.0));
        MenuList.add(new FoodDomain("Rice and Chicken", "rice_chicken", "Enjoy a hearty meal with our rice and chicken combo. We serve fluffy and fragrant rice, paired with succulent chicken cooked to perfection. The combo comes with a generous portion of rice and 2 pieces of chicken.", 350.0, 4.5, 20, 600.0));
        MenuList.add(new FoodDomain("Chapati and Minji", "chapati_minji", "Try our delicious combination of Chapati and Minji. Minji is a popular Kenyan dish made with ground meat and spices, it's a great source of protein and it will be served with Chapati.", 250.0, 4.8, 20, 600.0));
        MenuList.add(new FoodDomain("Chapati and Beans", "chapati_beans", "Indulge in the flavors of East Africa with our Chapati and Beans. Our beans are cooked with a blend of spices and served with Chapati, a traditional Kenyan bread.", 250.0, 4.7, 25, 600.0));
        MenuList.add(new FoodDomain("Chapati and Green grams", "chapati_ndengu", "Experience the taste of chapati and green grams, a traditional Kenyan dish made with green grams and served with chapati, a traditional Kenyan bread.", 200.0, 4.6, 20, 500.0));
        MenuList.add(new FoodDomain("Chapati and Beef", "chapati_beef", "Try our delicious combination of Chapati and Beef. Our beef is cooked with a blend of spices and served with chapati, a traditional Kenyan bread.", 250.0, 4.8, 20, 600.0));
        MenuList.add(new FoodDomain("Ugali and Beef","ugali_and_beef","The famous Kenyan beef stew is a go-to when it comes to throwing down meals in the kitchen, ‘Ugali na Nyama’ being our absolute fave! It will be served with Kales as the greens",230.0,4.6,12,90.11));
        MenuList.add(new FoodDomain("Ugali and Matumbo","ugali_matumbo","Matumbo an often neglected (and even loathed) meal can sometimes be the simplect and most full-filling food. order it accompanied by Ugali and Kales served as vegetables",230.0,4.6,12,90.11));
        MenuList.add(new FoodDomain("Ugali and Mayai","ugali_mayai","Ugali Mayai is basically a spicy scrambled egg stew, that is served with Ugali, a common Kenyan maize flour starch. ",230.0,4.6,12,90.11));
        MenuList.add(new FoodDomain("Ugali and Omena","ugali_omena","Omena is a Kenyan meal enjoyed by most people from the lakeside or coastal region. It is a go to meal to fill the feeling of eating a fish. Accompanied with Ugali and Kales served as vegetables  ",230.0,4.6,12,90.11));
        MenuList.add(new FoodDomain("Pasta and Meat Balls", "pasta_meatball", "Indulge in the classic combination of pasta and meatballs. Our pasta is cooked al dente and tossed in a rich and flavorful sauce, paired with tender and juicy meatballs. The combo comes with a generous portion of pasta and 3 meatballs.", 400.0, 4.9, 25, 750.0));
        MenuList.add(new FoodDomain("Pasta and Beef","pasta_beef","Indulge in the classic combination of pasta and beef. Our pasta is cooked al dente and tossed in a rich and flavorful meat sauce, paired with tender and juicy beef. The combo comes with a generous portion of pasta and a substantial serving of beef.", 400.0, 4.9, 25, 800.0));
        MenuList.add(new FoodDomain("Samosa", "samosa", "Try our delicious samosas, a classic Indian snack made with a savory filling of vegetables or meat wrapped in a flaky pastry. Served with a side of chutney.", 120.0, 4.6, 15, 450.0));
        MenuList.add(new FoodDomain("Boiled Eggs", "mayai_boiled", "Boiled eggs are a popular breakfast dish in Kenya, often served with a side of chapati or ugali.", 150.0, 4.5, 15, 200.0));
        MenuList.add(new FoodDomain("Mahamri", "mahamri", "Mahamri is a traditional Swahili breakfast pastry made from dough and coconut milk, often served with a side of tea or coffee.", 100.0, 4.0, 10, 150.0));MenuList.add(new FoodDomain("Sausage", "sausages", "Enjoy a classic and delicious sausage cooked to perfection. Served with a side of chips or on a bun, it's the perfect meal for any time of the day.", 200.0, 4.4, 10, 600.0));
        MenuList.add(new FoodDomain("Smokie", "smokie", "Try our delicious smokie, a sausage wrapped in dough and deep-fried to a golden brown. It's a perfect snack or meal on the go.", 150.0, 4.8, 10, 500.0));
        MenuList.add(new FoodDomain("Chapati", "chapati", "Chapati is a popular flatbread made from wheat flour and water, often served with a variety of savory toppings or curries.", 50.0, 4.5, 5, 100.0));
        MenuList.add(new FoodDomain("Homemade Crisps", "crips", "Treat yourself to a bag of our delicious homemade crisps. Made with fresh potatoes and cooked to perfection. Choose from a variety of flavors such as salt and vinegar, barbecue, and sour cream and onion.", 70.0, 4.8, 15, 400.0));
        MenuList.add(new FoodDomain("Chips Large", "chips_l", "Treat yourself to a large portion of our crispy and tasty chips. Perfect for sharing or for satisfying a big appetite.", 150.0, 4.5, 20, 600.0));
        MenuList.add(new FoodDomain("Chips XL", "chips_xl", "Get even more of our delicious chips with our XL portion. Perfect for satisfying a big appetite or for sharing with friends.", 200.0, 4.5, 20, 800.0));
        MenuList.add(new FoodDomain("Chips XXL", "chips_xxl", "Get the ultimate chip experience with our XXL portion. Perfect for a big group of friends or for satisfying a big appetite.", 250.0, 4.5, 20, 1000.0));
        MenuList.add(new FoodDomain("Chips Masala", "chips_masala", "Try our delicious chips with a twist of masala. Perfectly seasoned and crispy, you won't be able to stop munching on these.", 200.0, 4.5, 20, 800.0));
        MenuList.add(new FoodDomain("Bhajia XL", "bhajia", "Try our Bhajia XL, A crispy snack made from flour and mixed with vegetables such as onions and tomatoes.", 150.0, 4.5, 20, 600.0));
        MenuList.add(new FoodDomain("Bhajia Extra Large", "bhajia", "Get even more of our delicious Bhajia with our Extra Large portion. Perfect for satisfying a big appetite or for sharing with friends.", 200.0, 4.5, 20, 800.0));
        MenuList.add(new FoodDomain("Chicken Tikka Masala", "chicken_tikka_masala", "Try our delicious and creamy chicken tikka masala. Perfectly spiced and cooked to perfection, served with rice or naan.", 400.0, 4.8, 45, 900.0));
        MenuList.add(new FoodDomain("Fries and Chicken", "fries_and_chicken", "Our fries are crispy and golden brown, paired with tender and juicy fried chicken, a perfect combination to satisfy your cravings. Our combo comes with a generous portion of fries and 3 pieces of chicken.", 300.0, 4.8, 15, 650.0));
        MenuList.add(new FoodDomain("Burger and Fries", "burger_fries", "Get a classic burger with a side of crispy fries.", 600.0, 4.9, 30, 1200.0));
        MenuList.add(new FoodDomain("Fries and Chicken","fries_and_chicken","This is a combo of Fries along with chicken",250.0,4.7,12,75.15));
        MenuList.add(new FoodDomain("Fish and Chips", "fish_chips", "Get a crispy and golden fried fish fillet with a side of hot and fluffy chips.", 600.0, 4.8, 25, 1000.0));
        MenuList.add(new FoodDomain("Fries and Chicken","fries_and_chicken","This is a combo of Fries along with chicken",250.0,4.7,12,75.15));
        MenuList.add(new FoodDomain("Tea", "tea", "Experience the traditional Kenyan taste of tea. Our tea is brewed with the best leaves and served with milk and sugar.", 70.0, 4.5, 5, 150.0));
        MenuList.add(new FoodDomain("Coffee", "coffee", "Wake up to the aroma of freshly brewed coffee. Our coffee is made from the best Kenyan coffee beans, served with milk and sugar.", 80.0, 4.8, 5, 200.0));
        MenuList.add(new FoodDomain("Milk", "milk", "Enjoy a glass of fresh milk, perfect for any time of the day.", 50.0, 4.5, 2, 150.0));
        MenuList.add(new FoodDomain("Soda 300ml Bottle", "soda", "This is a combo of Fries along with chicken", 250.0, 4.7, 12, 75.15));
        MenuList.add(new FoodDomain("Maziwa Mala", "maziwa_mala", "Refresh yourself with a glass of Maziwa Mala, a traditional Kenyan beverage made from sour milk. It's a perfect side drink to any meal.", 50.0, 4.5, 2, 150.0));
        MenuList.add(new FoodDomain("Mango Juice", "mango_juice", "Treat yourself to a glass of our delicious Mango Juice, made from fresh and sweet mangoes.", 70.0, 4.8, 5, 200.0));
        MenuList.add(new FoodDomain("Orange Juice", "orange_juice", "Enjoy a glass of fresh Orange Juice, perfect for any time of the day.", 60.0, 4.6, 2, 150.0));
        MenuList.add(new FoodDomain("Soda 300ml Bottle", "soda", "Refresh yourself with a bottle of soda. Choose from a variety of flavors such as cola, lemon, and orange.", 40.0, 4.5, 2, 150.0));
        MenuList.add(new FoodDomain("Soda 500ml Bottle", "soda", "Refresh yourself with a bigger bottle of soda. Choose from a variety of flavors such as cola, lemon, and orange.", 60.0, 4.5, 2, 150.0));

//Connecting the Adapter created to the recycle view container
        adapter3 = new MenuAdapter(this, MenuList);
        recyclerViewMenu.setAdapter(adapter3);

    }

    //Creating a Category recyclerView method
    private void recyclerViewCategory() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryView=findViewById(R.id.Categoryview);
        recyclerViewCategoryView.setLayoutManager(linearLayoutManager);

//Adding items from the Category Domain by ArrayListing
        ArrayList <CategoryDomain> CategoryLists = new ArrayList<>();
        CategoryLists.add(new CategoryDomain("Heavy Meals","heavy_meals"));
        CategoryLists.add(new CategoryDomain("BreakFast","breakfast"));
        CategoryLists.add(new CategoryDomain("Fast Food","fast_food"));
        CategoryLists.add(new CategoryDomain("Snacks","snack"));
        CategoryLists.add(new CategoryDomain("Drinks","drinks"));




//Connecting the Adapter created to the recycle view container
        adapter=new CategoryAdapter(CategoryLists);
        recyclerViewCategoryView.setAdapter(adapter);

    }

    //Creating the Popular recyclerView method
    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularView=findViewById(R.id.PopularView);
        recyclerViewPopularView.setLayoutManager(linearLayoutManager);




//Adding items from the Popular Domain by ArrayListing
        ArrayList<FoodDomain> PopularLists = new ArrayList<>();
        PopularLists.add(new FoodDomain("Pilau","pilau","Indulge in the flavors of East Africa with our delicious Pilau. Our Pilau is a traditional Kenyan dish made with fragrant rice and a blend of spices, it's a perfect side dish to any meal.", 170.0, 4.6, 45, 65.2));
        PopularLists.add(new FoodDomain("Rice and Minji", "rice_minji", "Try our delicious combination of Rice and Minji. Minji is a popular Kenyan dish made with ground meat and spices, it's a great source of protein and it will be served with rice.", 250.0, 4.8, 20, 600.0));
        PopularLists.add(new FoodDomain("Rice and Beans", "rice_beans", "Indulge in the flavors of East Africa with our Rice and Beans. Our beans are cooked with a blend of spices and served with rice.", 250.0, 4.7, 25, 600.0));
        PopularLists.add(new FoodDomain("Fries and Chicken","fries_and_chicken","This is a combo of Fries along with chicken",250.0,4.7,12,75.15));
        PopularLists.add(new FoodDomain("Fish and Chips", "fish_chips", "Get a crispy and golden fried fish fillet with a side of hot and fluffy chips.", 600.0, 4.8, 25, 1000.0));
        PopularLists.add(new FoodDomain("Fries and Chicken","fries_and_chicken","This is a combo of Fries along with chicken",250.0,4.7,12,75.15));
        PopularLists.add(new FoodDomain("Tea", "tea", "Experience the traditional Kenyan taste of tea. Our tea is brewed with the best leaves and served with milk and sugar.", 70.0, 4.5, 5, 150.0));
        PopularLists.add(new FoodDomain("Coffee", "coffee", "Wake up to the aroma of freshly brewed coffee. Our coffee is made from the best Kenyan coffee beans, served with milk and sugar.", 80.0, 4.8, 5, 200.0));
        PopularLists.add(new FoodDomain("Milk", "milk", "Enjoy a glass of fresh milk, perfect for any time of the day.", 50.0, 4.5, 2, 150.0));
        PopularLists.add(new FoodDomain("Soda 300ml Bottle", "soda", "This is a combo of Fries along with chicken", 250.0, 4.7, 12, 75.15));
        PopularLists.add(new FoodDomain("Maziwa Mala", "maziwa_mala", "Refresh yourself with a glass of Maziwa Mala, a traditional Kenyan beverage made from sour milk. It's a perfect side drink to any meal.", 50.0, 4.5, 2, 150.0));
        PopularLists.add(new FoodDomain("Chapati and Minji", "chapati_minji", "Try our delicious combination of Chapati and Minji. Minji is a popular Kenyan dish made with ground meat and spices, it's a great source of protein and it will be served with Chapati.", 250.0, 4.8, 20, 600.0));
        PopularLists.add(new FoodDomain("Chapati and Beans", "chapati_beans", "Indulge in the flavors of East Africa with our Chapati and Beans. Our beans are cooked with a blend of spices and served with Chapati, a traditional Kenyan bread.", 250.0, 4.7, 25, 600.0));
        PopularLists.add(new FoodDomain("Chapati and Green grams", "chapati_ndengu", "Experience the taste of chapati and green grams, a traditional Kenyan dish made with green grams and served with chapati, a traditional Kenyan bread.", 200.0, 4.6, 20, 500.0));
        PopularLists.add(new FoodDomain("Chapati and Beef", "chapati_beef", "Try our delicious combination of Chapati and Beef. Our beef is cooked with a blend of spices and served with chapati, a traditional Kenyan bread.", 250.0, 4.8, 20, 600.0));
        PopularLists.add(new FoodDomain("Ugali and Beef","ugali_and_beef","The famous Kenyan beef stew is a go-to when it comes to throwing down meals in the kitchen, ‘Ugali na Nyama’ being our absolute fave! It will be served with Kales as the greens",230.0,4.6,12,90.11));
        PopularLists.add(new FoodDomain("Ugali and Matumbo","ugali_matumbo","Matumbo an often neglected (and even loathed) meal can sometimes be the simplect and most full-filling food. order it accompanied by Ugali and Kales served as vegetables",230.0,4.6,12,90.11));
        PopularLists.add(new FoodDomain("Ugali and Mayai","ugali_mayai","Ugali Mayai is basically a spicy scrambled egg stew, that is served with Ugali, a common Kenyan maize flour starch. ",230.0,4.6,12,90.11));
        PopularLists.add(new FoodDomain("Ugali and Omena","ugali_omena","Omena is a Kenyan meal enjoyed by most people from the lakeside or coastal region. It is a go to meal to fill the feeling of eating a fish. Accompanied with Ugali and Kales served as vegetables  ",230.0,4.6,12,90.11));
        PopularLists.add(new FoodDomain("Pasta and Meat Balls", "pasta_meatball", "Indulge in the classic combination of pasta and meatballs. Our pasta is cooked al dente and tossed in a rich and flavorful sauce, paired with tender and juicy meatballs. The combo comes with a generous portion of pasta and 3 meatballs.", 400.0, 4.9, 25, 750.0));
        PopularLists.add(new FoodDomain("Pasta and Beef","pasta_beef","Indulge in the classic combination of pasta and beef. Our pasta is cooked al dente and tossed in a rich and flavorful meat sauce, paired with tender and juicy beef. The combo comes with a generous portion of pasta and a substantial serving of beef.", 400.0, 4.9, 25, 800.0));
        PopularLists.add(new FoodDomain("Samosa", "samosa", "Try our delicious samosas, a classic Indian snack made with a savory filling of vegetables or meat wrapped in a flaky pastry. Served with a side of chutney.", 120.0, 4.6, 15, 450.0));
        PopularLists.add(new FoodDomain("Boiled Eggs", "mayai_boiled", "Boiled eggs are a popular breakfast dish in Kenya, often served with a side of chapati or ugali.", 150.0, 4.5, 15, 200.0));
        PopularLists.add(new FoodDomain("Mahamri", "mahamri", "Mahamri is a traditional Swahili breakfast pastry made from dough and coconut milk, often served with a side of tea or coffee.", 100.0, 4.0, 10, 150.0));
        PopularLists.add(new FoodDomain("Sausage", "sausages", "Enjoy a classic and delicious sausage cooked to perfection. Served with a side of chips or on a bun, it's the perfect meal for any time of the day.", 200.0, 4.4, 10, 600.0));
        PopularLists.add(new FoodDomain("Smokie", "smokie", "Try our delicious smokie, a sausage wrapped in dough and deep-fried to a golden brown. It's a perfect snack or meal on the go.", 150.0, 4.8, 10, 500.0));
        PopularLists.add(new FoodDomain("Rice and Beef", "rice_beef", "This is a combo of Fries along with chicken", 250.0, 4.7, 12, 75.15));

        Random r = new Random();
        Collections.shuffle(PopularLists, r);

        for(FoodDomain meal : PopularLists) {
            Log.d("Shuffled Heavy Meals", "Name: " + meal.getTitle() + " Description: " + meal.getDescription());
        }

//Connecting the Adapter created to the recycle view container
        adapter2=new PopularAdapter(PopularLists);
        recyclerViewPopularView.setAdapter(adapter2);

    }

//Creating method for searching through the Menu Array

    private void bottomNavigationView() {

        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout ProfileBtn = findViewById(R.id.ProfileBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout SupportBtn = findViewById(R.id.SupportBtn);
        LinearLayout TrackOrderBtn = findViewById(R.id.TrackOrderBtn);


//Button Response for Profile button
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, Account.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for Cart Button
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, Cart.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });
//Button Response for Support Button
        SupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, Support.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });

//Button Response for TrackOrder Button
        TrackOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, Purchases.class);
                intent.putExtra("PhoneNo",Id);
                startActivity(intent);
            }
        });


    }

}

