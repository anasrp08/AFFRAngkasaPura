package com.app.angkasapura.angkasapurapk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AircraftAdapter extends RecyclerView.Adapter<AircraftAdapter.MyViewHolder>
implements Filterable{
    private Context context;
    private List<AircraftType> AircraftTypeList;
    private List<AircraftType> AircraftTypeListFiltered;
    private AircraftAdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView aircraftname, aircrafttype;


        public MyViewHolder(View view) {
            super(view);
            aircraftname = view.findViewById(R.id.aircraftname);
            aircrafttype = view.findViewById(R.id.aircrafttype);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onAircraftSelected(AircraftTypeListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    public AircraftAdapter(Context context, List<AircraftType> AircraftList, AircraftAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.AircraftTypeList = AircraftList;
        this.AircraftTypeListFiltered = AircraftList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aircraft_row, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AircraftType contact = AircraftTypeListFiltered.get(position);
        holder.aircraftname.setText(contact.getAircraftname());
        holder.aircrafttype.setText(contact.getAircrafttype());

    }

    @Override
    public int getItemCount() {
        return AircraftTypeListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    AircraftTypeListFiltered = AircraftTypeList;
                } else {
                    List<AircraftType> filteredList = new ArrayList<>();
                    for (AircraftType row : AircraftTypeList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getAircrafttype().toLowerCase().contains(charString.toLowerCase()) || row.getAircraftname().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    AircraftTypeListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = AircraftTypeListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                AircraftTypeListFiltered = (ArrayList<AircraftType>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public interface AircraftAdapterListener {
        void onAircraftSelected(AircraftType contact);
    }
}
