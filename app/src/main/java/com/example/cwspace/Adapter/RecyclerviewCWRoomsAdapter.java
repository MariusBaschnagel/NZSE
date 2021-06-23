package com.example.cwspace.Adapter;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cwspace.Datenklassen.Room;
import com.example.cwspace.R;
import com.example.cwspace.ui.CoWorkerPackage.CwInfoRoom;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerviewCWRoomsAdapter extends RecyclerView.Adapter<RecyclerviewCWRoomsAdapter.RoomsViewHolder> implements Filterable {

    public class RoomsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView itemname, itemnumseats;
        ImageButton favimage;
        ImageView bookedImage;

        RoomsViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemimage);
            itemname = itemView.findViewById(R.id.itemname);
            itemnumseats = itemView.findViewById(R.id.itemnumseats);
            favimage = itemView.findViewById(R.id.favButton);
            bookedImage = itemView.findViewById(R.id.isBooked);

            itemView.findViewById(R.id.details).setOnClickListener(view -> {

                Log.d("demo","onClick: detail for " + mArrayRooms.get(getAdapterPosition()).getName());

                Intent intent = new Intent(itemView.getContext(), CwInfoRoom.class);
                intent.putExtra("Position",getAdapterPosition());
                itemView.getContext().startActivity(intent);

            });
            itemView.findViewById(R.id.favButton).setOnClickListener(view -> {
                final ImageButton button = favimage;
                Room r = mArrayRooms.get(getAdapterPosition());
                r.toggleFav();
                if(r.getFav()){ //Falls true
                    button.setImageResource(R.drawable.ic_fav);
                }else{  //false
                    button.setImageResource(R.drawable.ic_notfav);
                }

                Log.d("Recyclerview.class","onClick: fav toggled for: " + mArrayRooms.get(getAdapterPosition()).getName());
            });
        }
    }

    private final ArrayList<Room> mArrayRooms;
    private final ArrayList<Room> mArrayRoomsAll;   //List for searchbar

    public RecyclerviewCWRoomsAdapter(Context context, ArrayList<Room> arrayRooms) {
        mArrayRooms = arrayRooms;
        mArrayRoomsAll = new ArrayList<>(mArrayRooms);  //copy roomlist
    }

    @Override
    public int getItemCount() {
        return mArrayRooms.size();
    }

    @NotNull
    @Override
    public RoomsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new RoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomsViewHolder holder, int position) {
        Room room = mArrayRooms.get(position);

        holder.itemname.setText(room.getName());
        holder.itemnumseats.setText(room.getNumSeats());
        if (room.getOccupied()){
            holder.bookedImage.setImageResource(R.drawable.ic_baseline_bookmark_24);
            holder.bookedImage.setColorFilter(Color.RED);
        }else{
            holder.bookedImage.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
            holder.bookedImage.setColorFilter(Color.GREEN);
        }
        if (room.getFav()) {
            holder.favimage.setImageResource(R.drawable.ic_fav);
        } else{
            holder.favimage.setImageResource(R.drawable.ic_notfav);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public Filter getFilter() {
        return roomFilter;
    }

    private final Filter roomFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Room> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(mArrayRoomsAll);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Room item: mArrayRoomsAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
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
            mArrayRooms.clear();
            mArrayRooms.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
}
