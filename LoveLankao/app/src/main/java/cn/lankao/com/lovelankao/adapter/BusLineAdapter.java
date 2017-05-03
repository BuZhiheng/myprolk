package cn.lankao.com.lovelankao.adapter;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.model.BusLine;
import cn.lankao.com.lovelankao.model.CommonCode;
/**
 * Created by BuZhiheng on 2016/3/31.
 */
public class BusLineAdapter extends RecyclerView.Adapter<BusLineAdapter.MyViewHolder> {
    private Activity context;
    private List<BusLine> data;
    public BusLineAdapter(Activity context) {
        this.context = context;
        data = new ArrayList<>();
    }
    public void setData(List<BusLine> data) {
        this.data = data;
    }
    public void addData(List<BusLine> data) {
        if (this.data == null){
            this.data = new ArrayList<>();
        }
        this.data.addAll(data);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.activity_wz_city_item, parent, false));
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final BusLine line = data.get(position);
        holder.tvName.setText(line.lineName);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(CommonCode.INTENT_COMMON_STRING,line.line);
                context.setResult(0,intent);
                context.finish();
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_wz_city);
        }
    }
}