package com.example.peerpandaserver_app2.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.peerpandaserver_app2.Common.Common;
import com.example.peerpandaserver_app2.Interface.OnItemClickListener;
import com.example.peerpandaserver_app2.R;
import com.example.peerpandaserver_app2.Interface.OnItemClickListener;

public class TutorViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener {

    public TextView tutor_name;
    public TextView courseTeach;
    public TextView stuid;
    public ImageView tutor_image;
    public RatingBar ratingBar;


    private OnItemClickListener itemClickListener;

    public TutorViewHolder(View itemView){
        super(itemView);

        tutor_name = (TextView)itemView.findViewById(R.id.name);
        //stuid = (TextView)itemView.findViewById(R.id.stuid);
        courseTeach = (TextView)itemView.findViewById(R.id.coursecode);
        tutor_image = (ImageView) itemView.findViewById(R.id.tutor_image);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select action");

        contextMenu.add(0,0, getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1, getAdapterPosition(), Common.DELETE);
    }
}
