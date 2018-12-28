package com.example.test20181227.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test20181227.Bean.UserBean;
import com.example.test20181227.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public boolean isLiear = true;
    private Context mContext;
    private List<UserBean.DataBean> mData;



    public RecyclerAdapter(boolean isLiear, Context mContext) {
        this.isLiear = isLiear;
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setmData(List<UserBean.DataBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       RecyclerView.ViewHolder viewHolder = null;
       if(isLiear){
           View v = LayoutInflater.from(mContext).inflate(R.layout.item_linear_adapter,viewGroup,false);

           viewHolder = new ViewHolderLinear(v);
       }else {
           View v = LayoutInflater.from(mContext).inflate(R.layout.item_grid_adapter,viewGroup,false);
           viewHolder = new ViewHolderLinear(v);
       }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

              ViewHolderLinear holderLinear = (ViewHolderLinear) viewHolder;
              String image = mData.get(i).getImages();
              String replac = image.split("\\|")[0].replace("https","http");
              holderLinear.recycle_image.setImageURI(Uri.parse(replac));
              holderLinear.recycle_title.setText(mData.get(i).getTitle());
              holderLinear.recycle_price.setText(mData.get(i).getPrice()+"");
              holderLinear.layout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      onClickLayout.onClick(mData.get(i).getPid());
                  }
              });




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolderLinear extends RecyclerView.ViewHolder {
        private SimpleDraweeView recycle_image;
        private TextView recycle_title;
        private TextView recycle_price;
        private ConstraintLayout layout;
        public ViewHolderLinear(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            recycle_image = itemView.findViewById(R.id.recycle_image);
            recycle_title = itemView.findViewById(R.id.recycle_title);
            recycle_price = itemView.findViewById(R.id.recycle_price);

        }
    }
    OnClickLayout onClickLayout;

    public void setOnClickLayout(OnClickLayout onClickLayout) {
        this.onClickLayout = onClickLayout;
    }

    public interface OnClickLayout{
        void onClick(int postion);
    }
}
