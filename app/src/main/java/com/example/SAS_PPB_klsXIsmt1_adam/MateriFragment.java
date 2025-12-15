package com.example.SAS_PPB_klsXIsmt1_adam;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class MateriFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materi, container, false);

        TextView tvMateri = view.findViewById(R.id.tv_materi);

        String materi = "📊 DEPOSITO BERJANGKA\n\n" +
                "💡 Apa itu Deposito?\n" +
                "Deposito adalah produk simpanan di bank yang penyimpanannya dilakukan dalam jangka waktu tertentu dengan bunga lebih tinggi dari tabungan biasa.\n\n" +
                "⭐ Karakteristik Deposito:\n" +
                "• Memiliki jangka waktu (tenor) tetap\n" +
                "• Bunga lebih tinggi dari tabungan biasa\n" +
                "• Tidak dapat ditarik sebelum jatuh tempo\n" +
                "• Dijamin LPS (Lembaga Penjamin Simpanan)\n\n" +
                "🧮 Cara Menghitung Bunga:\n" +
                "Bunga = (Nominal × Suku Bunga × Tenor) ÷ (100 × 12)\n" +
                "Total Akhir = Nominal + Bunga\n\n" +
                "📋 Contoh Perhitungan:\n" +
                "Nominal: Rp10.000.000\n" +
                "Bunga: 5% per tahun\n" +
                "Tenor: 6 bulan\n\n" +
                "Bunga = (10.000.000 × 5 × 6) ÷ (100 × 12)\n" +
                "Bunga = Rp250.000\n\n" +
                "Total Akhir = Rp10.000.000 + Rp250.000\n" +
                "Total = Rp10.250.000";

        tvMateri.setText(materi);

        return view;
    }
}