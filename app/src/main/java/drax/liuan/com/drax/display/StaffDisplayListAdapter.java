package drax.liuan.com.drax.display;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import drax.liuan.com.drax.R;

public class StaffDisplayListAdapter extends RecyclerView.Adapter<StaffDisplayListAdapter.ItemHolder> {
    private LayoutInflater inflater;
    private ArrayList<PersonBean> personList;

    StaffDisplayListAdapter(Context context, ArrayList<PersonBean> personBeans) {
        this.inflater = LayoutInflater.from(context);
        this.personList = personBeans;

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemHolder(inflater.inflate(R.layout.item_person_display_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView imgHead;
        private TextView tvName;
        private TextView tvType;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            this.imgHead = itemView.findViewById(R.id.img_item_person_display_head);
            this.tvName = itemView.findViewById(R.id.tv_item_person_display_name);
            this.tvType = itemView.findViewById(R.id.tv_item_person_display_type);
        }
    }
}
