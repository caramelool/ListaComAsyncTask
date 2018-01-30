package caramelo.com.br.listacomasynctask.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import caramelo.com.br.listacomasynctask.R;
import caramelo.com.br.listacomasynctask.model.Android;

/**
 * Created by logonrm on 29/01/2018.
 */

public class AndroidAdapter extends RecyclerView.Adapter<AndroidAdapter.AndroidHolder> {

    private List<Android> list;
    private OnItemClickListener onItemClickListener;

    public AndroidAdapter() {
        this.list = new ArrayList<>();
    }

    public void update(List<Android> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public AndroidAdapter.AndroidHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_android, parent, false);
        return new AndroidHolder(view);
    }

    @Override
    public void onBindViewHolder(AndroidHolder holder, int position) {
        final Android android = getItem(position);
        holder.bind(android);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private Android getItem(int position) {
        return list.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.onItemClickListener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(Android android);
    }

    class AndroidHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView nomeTextView;
        private TextView apiTextView;
        private TextView versaoTextView;
        private ProgressBar progressBar;

        AndroidHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            apiTextView = itemView.findViewById(R.id.apiTextView);
            versaoTextView = itemView.findViewById(R.id.versaoTextView);
            progressBar = itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        Android android = getItem(getAdapterPosition());
                        onItemClickListener.onItemClick(android);
                    }
                }
            });
        }

        private void bind(Android item) {
            Picasso.with(itemView.getContext())
                    .load(item.getUrlImagem())
                    .error(R.drawable.ic_error)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
            nomeTextView.setText(item.getNome());
            apiTextView.setText(item.getApi());
            versaoTextView.setText(item.getVersao());
        }
    }
}
