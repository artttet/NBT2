package omsk.turinfo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import omsk.turinfo.R;
import omsk.turinfo.models.Museums;

public class DosugAdapter extends  RecyclerView.Adapter<DosugAdapter.ViewHolder> {

    private List<Museums> museums;


    public DosugAdapter(List<Museums> museums) {
        this.museums = museums;
    }



    @NonNull
    @Override
    public DosugAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.dosug_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DosugAdapter.ViewHolder viewHolder, int i) {
        viewHolder.add(museums.get(i));
    }

    @Override
    public int getItemCount() {
        return museums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             name = itemView.findViewById(R.id.dosug_title);
             text = itemView.findViewById(R.id.dosug_text);
        }

        public void add(Museums museum){
            name.setText(museum.title);
            text.setText(museum.text);
        }

    }
}
