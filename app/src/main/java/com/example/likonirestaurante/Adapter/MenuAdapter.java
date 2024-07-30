package com.example.likonirestaurante.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.likonirestaurante.Activity.FoodDetails;
import com.example.likonirestaurante.Domain.FoodDomain;
import com.example.likonirestaurante.Helper.ManagementCart;
import com.example.likonirestaurante.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements Filterable {
    private final List<FoodDomain> popularDomains;
    private final List<FoodDomain> foodListFull; // Copy of the list for filtering
    private final Context context;

    public MenuAdapter(Context context, ArrayList<FoodDomain> popularDomains) {
        this.context = context;
        this.popularDomains = popularDomains;
        this.foodListFull = new ArrayList<>(popularDomains); // Initialize the full list
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoodDomain foodItem = popularDomains.get(position);

        // Adding Kenya to the locale to get its position, currency, and time
        Locale locale = new Locale("en", "KE");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        // Extracting the data from where it is stored
        holder.title.setText(foodItem.getTitle());
        holder.fee.setText(numberFormat.format(foodItem.getFee()));
        holder.rating.setText(String.valueOf(foodItem.getRatings()));
        holder.prepMinutesTxt.setText(foodItem.getTime() + " Mins");

        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(foodItem.getPicture(), "drawable",
                        holder.itemView.getContext().getPackageName());

        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.picture);

        holder.showDetails.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), FoodDetails.class);
            intent.putExtra("object", foodItem);
            holder.itemView.getContext().startActivity(intent);
        });

        holder.AddBtn.setOnClickListener(view -> {
            ManagementCart managementCart = new ManagementCart(holder.itemView.getContext());
            int numberInCart = 1;

            foodItem.setNumberInCart(numberInCart);
            managementCart.insertFood(foodItem);

            Toast.makeText(holder.itemView.getContext(), "Item Added to your Cart", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return popularDomains.size();
    }

    @Override
    public Filter getFilter() {
        return foodFilter;
    }

    private final Filter foodFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FoodDomain> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(foodListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (FoodDomain item : foodListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            popularDomains.clear();
            popularDomains.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee, rating, prepMinutesTxt;
        ImageView picture, AddBtn;
        ConstraintLayout showDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.all_menu_name);
            picture = itemView.findViewById(R.id.all_menu_image);
            fee = itemView.findViewById(R.id.MenuPriceTag);
            AddBtn = itemView.findViewById(R.id.MenuAddBtn);
            rating = itemView.findViewById(R.id.all_menu_rating);
            prepMinutesTxt = itemView.findViewById(R.id.all_menu_deliverytime);
            showDetails = itemView.findViewById(R.id.ShowDetailsBtn);
        }
    }
}
