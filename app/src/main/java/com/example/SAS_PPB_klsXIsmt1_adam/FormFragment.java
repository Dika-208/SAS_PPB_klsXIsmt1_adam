package com.example.SAS_PPB_klsXIsmt1_adam;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.text.NumberFormat;
import java.util.Locale;

public class FormFragment extends Fragment {

    private EditText etNama, etNominal, etSukuBunga;
    private RadioGroup rgTenor;
    private Button btnHitung;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);

        mainActivity = (MainActivity) getActivity();
        initViews(view);
        setupListeners();
        updateButtonState();

        return view;
    }

    private void initViews(View view) {
        etNama = view.findViewById(R.id.et_nama);
        etNominal = view.findViewById(R.id.et_nominal);
        etSukuBunga = view.findViewById(R.id.et_suku_bunga);
        rgTenor = view.findViewById(R.id.rg_tenor);
        btnHitung = view.findViewById(R.id.btn_hitung);

        // Set default suku bunga 5%
        etSukuBunga.setText("5.0");
    }

    private void setupListeners() {
        // Real-time validation for nama
        etNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Real-time validation for nominal with auto-formatting
        etNominal.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    etNominal.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[.,]", "");

                    if (!cleanString.isEmpty()) {
                        try {
                            long parsed = Long.parseLong(cleanString);
                            String formatted = NumberFormat.getNumberInstance(Locale.US).format(parsed);
                            current = formatted;
                            etNominal.setText(formatted);
                            etNominal.setSelection(formatted.length());
                        } catch (NumberFormatException e) {
                            current = "";
                        }
                    } else {
                        current = "";
                    }

                    etNominal.addTextChangedListener(this);
                }
                updateButtonState();
            }
        });

        // Real-time validation for suku bunga
        etSukuBunga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Radio group listener
        rgTenor.setOnCheckedChangeListener((group, checkedId) -> updateButtonState());

        // Hitung button listener
        btnHitung.setOnClickListener(v -> {
            if (validateForm()) {
                calculateAndShowResult();
            }
        });
    }

    private void updateButtonState() {
        boolean isFormValid = validateFormSilent();

        if (isFormValid) {
            btnHitung.setEnabled(true);
            btnHitung.setBackgroundColor(getResources().getColor(R.color.primary_color));
        } else {
            btnHitung.setEnabled(false);
            btnHitung.setBackgroundColor(getResources().getColor(R.color.accent_color));
        }
    }

    private boolean validateFormSilent() {
        // Check nama
        String nama = etNama.getText().toString().trim();
        if (nama.isEmpty()) {
            return false;
        }

        // Check nominal
        String nominalStr = etNominal.getText().toString().replaceAll("[.,]", "");
        if (nominalStr.isEmpty()) {
            return false;
        }

        try {
            long nominal = Long.parseLong(nominalStr);
            if (nominal < 1000000) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // Check tenor
        if (rgTenor.getCheckedRadioButtonId() == -1) {
            return false;
        }

        // Check suku bunga
        String sukuBungaStr = etSukuBunga.getText().toString().trim();
        if (sukuBungaStr.isEmpty()) {
            return false;
        }

        try {
            double sukuBunga = Double.parseDouble(sukuBungaStr);
            if (sukuBunga <= 0 || sukuBunga > 100) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private boolean validateForm() {
        String nama = etNama.getText().toString().trim();
        if (nama.isEmpty()) {
            showToast("Username harus diisi!");
            return false;
        }

        String nominalStr = etNominal.getText().toString().replaceAll("[.,]", "");
        if (nominalStr.isEmpty()) {
            showToast("Nominal deposito harus diisi!");
            return false;
        }

        try {
            long nominal = Long.parseLong(nominalStr);
            if (nominal < 1000000) {
                showToast("Nominal minimal Rp 1.000.000!");
                return false;
            }
        } catch (NumberFormatException e) {
            showToast("Format nominal tidak valid!");
            return false;
        }

        if (rgTenor.getCheckedRadioButtonId() == -1) {
            showToast("Pilih tenor deposito!");
            return false;
        }

        String sukuBungaStr = etSukuBunga.getText().toString().trim();
        if (sukuBungaStr.isEmpty()) {
            showToast("Suku bunga harus diisi!");
            return false;
        }

        try {
            double sukuBunga = Double.parseDouble(sukuBungaStr);
            if (sukuBunga <= 0) {
                showToast("Suku bunga harus lebih dari 0%!");
                return false;
            }
            if (sukuBunga > 100) {
                showToast("Suku bunga maksimal 100%!");
                return false;
            }
        } catch (NumberFormatException e) {
            showToast("Format suku bunga tidak valid!");
            return false;
        }

        return true;
    }

    private void calculateAndShowResult() {
        if (!validateForm()) {
            return;
        }

        // Get data
        String nama = etNama.getText().toString().trim();
        String nominalStr = etNominal.getText().toString().replaceAll("[.,]", "");
        long nominal = Long.parseLong(nominalStr);

        // Get suku bunga dari input user
        double sukuBunga = Double.parseDouble(etSukuBunga.getText().toString());

        // Get selected tenor
        int selectedId = rgTenor.getCheckedRadioButtonId();
        RadioButton rb = getView().findViewById(selectedId);
        String tenorText = rb.getText().toString();
        int tenorBulan = Integer.parseInt(tenorText.replaceAll("[^0-9]", ""));

        // Calculate bunga berdasarkan rumus: Bunga = (Nominal × SukuBunga × Tenor) ÷ (100 × 12)
        double bunga = (nominal * sukuBunga * tenorBulan) / (100 * 12);
        double total = nominal + bunga;

        // Format numbers
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String nominalFormatted = formatter.format(nominal);
        String bungaFormatted = formatter.format(bunga);
        String totalFormatted = formatter.format(total);
        String sukuBungaFormatted = String.format("%.1f%%", sukuBunga);

        // Kirim data ke MainActivity untuk ditampilkan di HasilFragment
        if (mainActivity != null) {
            mainActivity.showHasilFragment(
                    nama,
                    nominalFormatted,
                    tenorText,
                    sukuBungaFormatted,
                    bungaFormatted,
                    totalFormatted
            );
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}