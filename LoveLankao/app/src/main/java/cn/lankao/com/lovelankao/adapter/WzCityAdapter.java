package cn.lankao.com.lovelankao.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.WZActivity;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.JWzCitys;
/**
 * Created by BuZhiheng on 2016/3/31.
 */
public class WzCityAdapter extends RecyclerView.Adapter<WzCityAdapter.MyViewHolder> {
    private Context context;
    private List<JWzCitys> data;
    public WzCityAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }
    public void setData(List<JWzCitys> data) {
        this.data = data;
    }
    public void addData(List<JWzCitys> data) {
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
        final JWzCitys city = data.get(position);
        holder.tvName.setText(city.city_name);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WZActivity.class);
                intent.putExtra(CommonCode.INTENT_COMMON_OBJ,city);
                context.startActivity(intent);
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