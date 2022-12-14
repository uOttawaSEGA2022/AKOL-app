package com.example.akolapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Text;
import java.util.List;

public class OdersChefAccepAdapter extends RecyclerView.Adapter<OdersChefAccepVH>{
    List<String> items;
    Context context;

    public OdersChefAccepAdapter(Context context,List<String> items) {
        this.items = items;
        this.context = context;
    }
    public OdersChefAccepAdapter(List<String> items){
        this.items=items;
    }
    @NonNull
    @Override
    public OdersChefAccepVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_cuis_order,parent,false);
        return new OdersChefAccepVH(view).linkadapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull OdersChefAccepVH holder, int position) {
        holder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
class OdersChefAccepVH extends RecyclerView.ViewHolder{
    TextView textView;
    public static Boolean siDone;
    public OdersChefAccepAdapter adapter;
    public OdersChefAccepVH(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.OrderName);
        itemView.findViewById(R.id.reject).setOnClickListener(view -> {
            siDone=false;
        });
        itemView.findViewById(R.id.accept).setOnClickListener( view -> {
            siDone=true;
        });
    }
    public OdersChefAccepVH linkadapter(OdersChefAccepAdapter adapter){
        this.adapter=adapter;
        return this;
    }
}