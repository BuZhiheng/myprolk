package cn.lankao.com.lovelankao.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.PhotoViewPagerActivity;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.BitmapUtil;
/**
 * Created by HOREN on 2017/12/27.
 */
public class SquareSendPictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<String> datas = new ArrayList<>();
    public void setData(ArrayList<String> datas){
        this.datas = datas;
    }
    public SquareSendPictureAdapter(Context context){
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_square_send_picture_item,parent,false);
        return new Holder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof Holder){
            Holder holder = (Holder) viewHolder;
            BitmapUtil.loadImageNormal(context,holder.imageView,datas.get(position));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PhotoViewPagerActivity.class);
                    intent.putExtra(CommonCode.INTENT_COMMON_OBJ,datas);
                    intent.putExtra(CommonCode.INTENT_COMMON_STRING,datas.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }
    class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_square_send_picture_item);
        }
    }
}