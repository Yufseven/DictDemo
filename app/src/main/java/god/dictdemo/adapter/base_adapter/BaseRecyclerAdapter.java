package god.dictdemo.adapter.base_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private int layoutId;
    private List<? extends BaseBean> data;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private boolean clickFlag = true;

    public BaseRecyclerAdapter(int layoutId, List<? extends BaseBean> data) {
        this.layoutId = layoutId;
        this.data = data;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        final BaseViewHolder holder = new BaseViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    if (clickFlag) {
                        onItemClickListener.onItemClick(v, holder.getLayoutPosition(), data.get(holder.getLayoutPosition()));
                    }
                    clickFlag = true;
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v, holder.getLayoutPosition(), data.get(holder.getLayoutPosition()));
                    clickFlag = false;
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        convert(holder, data.get(position), position);
    }

    protected abstract <T extends BaseBean> void convert(BaseViewHolder holder, T baseBean, int position);

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public void updateData(List<? extends BaseBean> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        <T> void onItemClick(View v, int position, T bean);
    }

    public interface OnItemLongClickListener {
        <T> void onItemLongClick(View v, int position, T bean);
    }
}
