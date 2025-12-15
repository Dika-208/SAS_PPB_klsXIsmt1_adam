package com.example.SAS_PPB_klsXIsmt1_adam;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class HasilFragment extends Fragment {

    private TextView tvNama, tvNominal, tvTenor, tvBungaPersen, tvBunga, tvTotal;
    private Button btnReset;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hasil, container, false);

        mainActivity = (MainActivity) getActivity();
        initViews(view);
        displayData();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        tvNama = view.findViewById(R.id.tv_nama);
        tvNominal = view.findViewById(R.id.tv_nominal);
        tvTenor = view.findViewById(R.id.tv_tenor);
        tvBungaPersen = view.findViewById(R.id.tv_bunga_persen);
        tvBunga = view.findViewById(R.id.tv_bunga);
        tvTotal = view.findViewById(R.id.tv_total);
        btnReset = view.findViewById(R.id.btn_reset);
    }

    private void displayData() {
        // Ambil data dari Bundle yang dikirim melalui MainActivity
        Bundle args = getArguments();
        if (args != null) {
            String nama = args.getString("NAMA", "Nama tidak ditemukan");
            String nominal = args.getString("NOMINAL", "Rp 0");
            String tenor = args.getString("TENOR", "0 Bulan");
            String sukuBunga = args.getString("BUNGA_PERSEN", "5.0%");
            String bunga = args.getString("BUNGA", "Rp 0");
            String total = args.getString("TOTAL", "Rp 0");

            // Tampilkan data ke TextView
            tvNama.setText(nama);
            tvNominal.setText(nominal);
            tvTenor.setText(tenor);
            tvBungaPersen.setText(sukuBunga);
            tvBunga.setText(bunga);
            tvTotal.setText(total);
        }
    }

    private void setupListeners() {
        btnReset.setOnClickListener(v -> {
            if (mainActivity != null) {
                mainActivity.resetToForm();
            }
        });
    }
}