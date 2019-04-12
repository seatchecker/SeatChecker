package com.caucse.seatchecker.seatchecker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<TableInfo> tables;
    private Context context;
    private static GridItemListener listener;
    private int width;
    private int length;


    GridAdapter(Context context, ArrayList<TableInfo> tables, GridItemListener listener, int width, int length){
        this.context = context;
        this.tables = tables;
        GridAdapter.listener = listener;
        this.width = width;
        this.length = length;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        listener.onItemDrag(fromPosition,toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        protected TextView tvPlug;
        public FrameLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            tvPlug = itemView.findViewById(R.id.tvPlug);
            layout = itemView.findViewById(R.id.holderFrame);
            tvPlug.setOnClickListener(this);
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
        //todo : change setting, size of gridview
        params.width = parent.getMeasuredWidth() / width;
        params.height = parent.getMeasuredWidth() / length;

        view.setLayoutParams(params);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch(tables.get(position).getKindTable()){
            case TableInfo.COUNTER :
            case TableInfo.DOOR :
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.doorColor));
                break;
            case TableInfo.FOURTABLE:
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.fourTable));
                break;
            case TableInfo.TWOTABLE :
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.twoTable));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public interface GridItemListener{
        void onItemClick(View view, int position);
        void onItemDrag(int startPosition, int endPosition);
    }

    public void setItem(ArrayList<TableInfo> info){
        this.tables = info;
    }



}
