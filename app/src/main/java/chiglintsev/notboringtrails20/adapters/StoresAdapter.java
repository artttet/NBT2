package chiglintsev.notboringtrails20.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chiglintsev.notboringtrails20.R;
import chiglintsev.notboringtrails20.models.Stores;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder> {

    private List<Stores> stores;

    public StoresAdapter(List<Stores> stores){
        this.stores = stores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.stores_item, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.add(stores.get(i));
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,text,text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.store_name);
            text = itemView.findViewById(R.id.store_text);
            text2 = itemView.findViewById(R.id.store_text2);

        }

        void add(Stores store){
            name.setText(store.name);
            text.setText(store.text);
            text2.setText(store.text2);
        }

    }
}


