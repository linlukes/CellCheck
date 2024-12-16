package com.star.checkcells;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CellInfoAdapter extends RecyclerView.Adapter<CellInfoAdapter.CellInfoViewHolder> {

    private List<CellInfoModel> cellInfoList;

    public CellInfoAdapter(List<CellInfoModel> cellInfoList) {
        this.cellInfoList = cellInfoList;
    }

    @NonNull
    @Override
    public CellInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cell_info, parent, false);
        return new CellInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CellInfoViewHolder holder, int position) {
        CellInfoModel cellInfo = cellInfoList.get(position);
        holder.tvCellType.setText("Cell Type: " + cellInfo.getNetworkType());
        holder.tvCellId.setText("Cell ID: " + cellInfo.getCellId());
        holder.tvSignalStrength.setText("Signal Strength: " + cellInfo.getSignalStrength());
    }

    @Override
    public int getItemCount() {
        return cellInfoList.size();
    }

    static class CellInfoViewHolder extends RecyclerView.ViewHolder {
        TextView tvCellType, tvCellId, tvSignalStrength;

        public CellInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCellType = itemView.findViewById(R.id.tvCellType);
            tvCellId = itemView.findViewById(R.id.tvCellId);
            tvSignalStrength = itemView.findViewById(R.id.tvSignalStrength);
        }
    }
}
