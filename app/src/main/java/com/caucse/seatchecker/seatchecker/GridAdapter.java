package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>{

    private ArrayList<GridElement> arrays;
    private Context context;
    private static GridItemListener listener;
    private int width;
    private int length;
    protected static final int MODE_CHANGE = 300;
    protected static final int MODE_CHECK = 400;
    private int GridMode;

    GridAdapter(Context context, ArrayList<GridElement> arrays, GridItemListener listener, int width, int length, int mode){
        this.context = context;
        this.arrays = arrays;
        GridAdapter.listener = listener;
        this.width = width;
        this.length = length;
        GridMode = mode;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView ivStatus;
        protected TextView tvPlug;
        public FrameLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            tvPlug = itemView.findViewById(R.id.tvPlug);
            layout = itemView.findViewById(R.id.holderFrame);
            tvPlug.setOnClickListener(this);
            ivStatus = itemView.findViewById(R.id.ivStatusOfSeat);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.width = parent.getMeasuredWidth() / width;
        params.height = parent.getMeasuredWidth() / length;

        view.setLayoutParams(params);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String str = arrays.get(position).getOrientation();
        switch(arrays.get(position).getStatus()){
            case TableInfo.COUNTER :
            case TableInfo.DOOR :
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.doorColor));
                break;
            case TableInfo.ONETABLE:

                switch (str) {
                    case "above":
                        holder.tvPlug.setBackground(context.getResources().getDrawable(R.drawable.above1));
                        break;
                    case "below":
                        holder.tvPlug.setBackground(context.getResources().getDrawable(R.drawable.below1));
                        break;
                    case "right":
                        holder.tvPlug.setBackground(context.getResources().getDrawable(R.drawable.right1));
                        break;
                    case "left":
                        holder.tvPlug.setBackground(context.getResources().getDrawable(R.drawable.left1));
                        break;
                }
                break;
            case TableInfo.FOURTABLE:

                if((( position -1 >= 0 ) && arrays.get(position-1).getStatus() == TableInfo.FOURTABLE)
                        || ((position+1 < arrays.size()) && arrays.get(position+1).getStatus() == TableInfo.FOURTABLE)){
                    holder.tvPlug.setBackground(context.getResources().getDrawable(R.drawable.above4));
                }else{
                    holder.tvPlug.setBackground(context.getResources().getDrawable(R.drawable.right4));
                }
                break;
            case TableInfo.TWOTABLE :
                if(str.equals("right")){
                    holder.tvPlug.setBackground(context.getResources().getDrawable(R.drawable.right2));
                }else{
                    holder.tvPlug.setBackground(context.getResources().getDrawable(R.drawable.above2));
                }
                break;
            case TableInfo.NONE :
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.layout.setBackground(null);



        }
        holder.tvPlug.setText(arrays.get(position).getName());
        if(!arrays.get(position).isEmptySeat()){
            holder.ivStatus.setAlpha(0);
            holder.ivStatus.setBackground(context.getResources().getDrawable(R.drawable.cancel_image));
        }else{
            holder.ivStatus.setBackground(null);

        }
    }

    @Override
    public int getItemCount() {
        return arrays.size();
    }

    public interface GridItemListener{
        void onItemClick(View view, int position);
        void onItemDrag(int startPosition, int endPosition);
    }

    public void setItem(ArrayList<GridElement> info){
        this.arrays = info;
    }

}
