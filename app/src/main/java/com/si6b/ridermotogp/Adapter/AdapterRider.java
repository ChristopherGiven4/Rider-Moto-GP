package com.si6b.ridermotogp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.si6b.ridermotogp.API.APIRequestData;
import com.si6b.ridermotogp.API.RetroServer;
import com.si6b.ridermotogp.Activity.MainActivity;
import com.si6b.ridermotogp.Activity.TambahActivity;
import com.si6b.ridermotogp.Activity.UbahActivity;
import com.si6b.ridermotogp.Model.ModelResponse;
import com.si6b.ridermotogp.Model.ModelRider;
import com.si6b.ridermotogp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRider extends RecyclerView.Adapter<AdapterRider.HolderRider>{
    private Context ctx;
    private List<ModelRider> listRider;

    public AdapterRider(Context ctx, List<ModelRider> listRider) {
        this.ctx = ctx;
        this.listRider = listRider;
    }

    @NonNull
    @Override
    public HolderRider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rider, parent, false);
        HolderRider HR = new HolderRider(view);
        return HR;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRider holder, int position) {
        ModelRider MR = listRider.get(position);

        holder.tvId.setText(String.valueOf(MR.getId()));
        holder.tvNama.setText(MR.getNama());
        holder.tvSponsor.setText(MR.getSponsor());
        holder.tvNegara.setText(MR.getNegara());
        holder.tvNomor.setText(MR.getNomor());

        Glide
                .with(ctx)
                .load(MR.getFoto())
                .placeholder(R.drawable.img)
                .into(holder.ivRider);
    }

    @Override
    public int getItemCount() {
        if (listRider != null){
            return listRider.size();
        }
        else{
            Toast.makeText(ctx, "Data Tidak Tersedia", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public class HolderRider extends RecyclerView.ViewHolder{
        TextView tvId, tvNama, tvSponsor, tvNegara, tvNomor;
        ImageView ivRider;

        public HolderRider(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvSponsor = itemView.findViewById(R.id.tv_sponsor);
            tvNegara = itemView.findViewById(R.id.tv_negara);
            tvNomor = itemView.findViewById(R.id.tv_nomor);
            ivRider = itemView.findViewById(R.id.iv_rider);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian!");
                    pesan.setMessage("Operasi yang anda inginkan");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteRider();
                            dialogInterface.dismiss();
                            ((MainActivity)ctx).retrieveAllRider();
                        }
                    });

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", tvId.getText().toString());
                            kirim.putExtra("xNama", tvNama.getText().toString());
                            kirim.putExtra("xNomor", tvNomor.getText().toString());
                            kirim.putExtra("xNegara", tvNegara.getText().toString());
                            kirim.putExtra("xSponsor", tvSponsor.getText().toString());
                            ctx.startActivity(kirim);
                        }
                    });

                    pesan.show();

                    return false;
                }
            });
        }

        private void deleteRider(){
            int id = Integer.parseInt(tvId.getText().toString());

            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDeleteData(id);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    if(kode == 1){
                        Toast.makeText(ctx, "Selamat! Sukses Menghapus Data Rider", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(ctx, "Perhatian! Gagal Menghapus Data Rider!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
