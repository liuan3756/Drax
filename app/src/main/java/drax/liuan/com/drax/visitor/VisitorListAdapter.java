package drax.liuan.com.drax.visitor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import drax.liuan.com.drax.R;

public class VisitorListAdapter extends RecyclerView.Adapter<VisitorListAdapter.VisitorHolder> {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<String> arrayList;

    private Random random;

    public VisitorListAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = list;

        this.random = new Random();
    }

    @NonNull
    @Override
    public VisitorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VisitorHolder(layoutInflater.inflate(R.layout.item_visitor_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorHolder visitorHolder, int i) {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        visitorHolder.itemView.setBackgroundColor(Color.rgb(r, g, b));
        visitorHolder.tvVisitorName.setText("访客 " + i);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class VisitorHolder extends RecyclerView.ViewHolder {
        private TextView tvVisitorName;

        VisitorHolder(@NonNull View itemView) {
            super(itemView);
            this.tvVisitorName = itemView.findViewById(R.id.tvVisitorName);

        }
    }
}


