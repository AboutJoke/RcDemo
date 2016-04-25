package com.fantasy.rcdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasy.rcdemo.R;
import com.fantasy.rcdemo.domain.DescribeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fantasy on 2016/4/25.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    protected ArrayList<T> mObjects;
    private Context ctx;
    protected final Object mLock = new Object();


    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public RecyclerViewAdapter(Context ctx, List<T> l) {
        this.ctx = ctx;
        mObjects = new ArrayList<T>();
        if (l != null)
            mObjects.addAll(l);
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置布局文件
        View view = View.inflate(ctx, R.layout.card_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
        //设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.OnItemClick(holder.itemView, pos);
                }
            });
        }

        //设置长按事件
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemLongClickListener.OnItemLongClick(v, pos);
                    return true;
                }
            });
        }

        DescribeItem t = (DescribeItem) mObjects.get(position);
        holder.desc.setText(t.getPer_desc());
        holder.play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                add(pos);
            }
        });
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                delete(pos);
            }
        });
    }


    //删除
    protected abstract void delete(int position);

    //添加
    protected abstract void add(int position);


    @Override
    public int getItemCount() {
        return mObjects.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        ImageView video_pic;
        Button play_btn;
        Button delete_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.text_title);
            desc= (TextView) itemView.findViewById(R.id.text_desc);
            video_pic= (ImageView) itemView.findViewById(R.id.video_pic);
            play_btn= (Button) itemView.findViewById(R.id.add_btn);
            delete_btn= (Button) itemView.findViewById(R.id.delete_btn);
        }
    }

    /**
     * 添加数据
     *
     * @param position
     * @param item
     */
    public void addData(int position, T item) {
        synchronized (mLock) {
            mObjects.add(item);
        }
        notifyItemInserted(position);
    }

    /**
     * 移除数据
     *
     * @param position
     */
    public void removeData(int position) {
        synchronized (mLock) {
            mObjects.remove(position);
        }
        notifyItemRemoved(position);
    }

    public void clear() {
        this.mObjects.clear();
    }

}
