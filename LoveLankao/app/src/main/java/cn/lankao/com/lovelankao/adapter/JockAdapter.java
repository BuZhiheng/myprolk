package cn.lankao.com.lovelankao.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.model.Jock;
import cn.lankao.com.lovelankao.viewcontroller.JockActivityController;
/**
 * Created by BuZhiheng on 2016/3/31.
 */
public class JockAdapter extends RecyclerView.Adapter<JockAdapter.MyViewHolder> {
    private Context context;
    private JockActivityController jock;
    private List<Jock> data;
    public JockAdapter(Context context,JockActivityController jock) {
        this.context = context;
        this.jock = jock;
        data = new ArrayList<>();
    }

    public void setData(List<Jock> data) {
        this.data = data;
    }
    public void addData(List<Jock> data) {
        if (this.data == null){
            this.data = new ArrayList<>();
        }
        this.data.addAll(data);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.activity_jock_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Jock news = data.get(position);
        holder.tvContent.setText(news.getContent());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jock.onItemClick(news.getContent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll;
        TextView tvContent;
        public MyViewHolder(View view) {
            super(view);
            ll = (LinearLayout) view.findViewById(R.id.ll_jock_item);
            tvContent = (TextView) view.findViewById(R.id.tv_jock_item_content);
        }
    }
}