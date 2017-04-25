package cn.lankao.com.lovelankao.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.model.JWzMsg;
/**
 * Created by BuZhiheng on 2016/3/31.
 */
public class WzMsgAdapter extends RecyclerView.Adapter<WzMsgAdapter.MyViewHolder> {
    private Context context;
    private List<JWzMsg> data;
    public WzMsgAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }
    public void setData(List<JWzMsg> data) {
        this.data = data;
    }
    public void addData(List<JWzMsg> data) {
        if (this.data == null){
            this.data = new ArrayList<>();
        }
        this.data.addAll(data);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.activity_wz_msg_item, parent, false));
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final JWzMsg msg = data.get(position);
        holder.tvTime.setText("时间:"+msg.date);
        holder.tvArea.setText("地点:"+msg.area);
        holder.tvAct.setText("违章行为:"+msg.act);
        holder.tvFen.setText("扣分:"+msg.fen);
        holder.tvMoney.setText("罚款:"+msg.money);
        if ("1".equals(msg.handled)){
            holder.tvHandle.setText("处理");
        } else {
            holder.tvHandle.setText("未处理");
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvArea;
        TextView tvAct;
        TextView tvFen;
        TextView tvMoney;
        TextView tvHandle;
        public MyViewHolder(View view) {
            super(view);
            tvTime = (TextView) view.findViewById(R.id.tv_wz_msg_time);
            tvArea = (TextView) view.findViewById(R.id.tv_wz_msg_area);
            tvAct = (TextView) view.findViewById(R.id.tv_wz_msg_act);
            tvFen = (TextView) view.findViewById(R.id.tv_wz_msg_fen);
            tvMoney = (TextView) view.findViewById(R.id.tv_wz_msg_money);
            tvHandle = (TextView) view.findViewById(R.id.tv_wz_msg_handled);
        }
    }
}