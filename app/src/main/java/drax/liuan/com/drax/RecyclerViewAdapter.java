package drax.liuan.com.drax;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import drax.liuan.com.drax.view.StaffClockInView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.InnerViewHolder> {
    private LayoutInflater inflater;
    private Random random;
    private ArrayList<StaffClockInView.Person> list;
    private int itemVisibleWidth;

    public RecyclerViewAdapter(Context context, ArrayList<StaffClockInView.Person> list) {
        this.inflater = LayoutInflater.from(context);
        this.random = new Random();
        this.list = list;
    }

    public void setItemVisibleWidth(int itemVisibleWidth) {
        this.itemVisibleWidth = itemVisibleWidth;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerViewHolder(inflater.inflate(R.layout.item_staff_display_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final InnerViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.llBackground.getLayoutParams();
        layoutParams.width = itemVisibleWidth;
        holder.llBackground.setLayoutParams(layoutParams);

        holder.tvIndex.setText(this.list.get(position).name);
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        holder.llBackground.setBackgroundColor(Color.rgb(r, g, b));

        r = random.nextInt(255);
        g = random.nextInt(255);
        b = random.nextInt(255);
        holder.imgIcon.setBackgroundColor(Color.rgb(r, g, b));
    }

    @Override
    public int getItemCount() {
        return this.list == null ? 0 : this.list.size();
    }

    class InnerViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llBackground;
        private TextView tvIndex;
        private ImageView imgIcon;

        InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.llBackground = itemView.findViewById(R.id.llBackground);
            this.imgIcon = itemView.findViewById(R.id.imgIcon);
            this.tvIndex = itemView.findViewById(R.id.tvIndex);
        }
    }
}
