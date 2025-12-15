package com.example.SAS_PPB_klsXIsmt1_adam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private LinearLayout bottomNavigation;
    private LinearLayout navMateri, navForm, navHasil;
    private ImageView iconMateri, iconForm, iconHasil;
    private TextView textMateri, textForm, textHasil;

    private boolean isFormCompleted = false;

    // Data untuk dikirim ke HasilFragment - TAMBAH 1 VARIABEL
    private String hasilNama, hasilNominal, hasilTenor, hasilSukuBunga, hasilBunga, hasilTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupNavigation();

        // Tampilkan Fragment Materi pertama kali
        showFragment(new MateriFragment());
        setActiveNav(navMateri);
    }

    private void initViews() {
        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        navMateri = findViewById(R.id.nav_materi);
        navForm = findViewById(R.id.nav_form);
        navHasil = findViewById(R.id.nav_hasil);

        iconMateri = findViewById(R.id.icon_materi);
        iconForm = findViewById(R.id.icon_form);
        iconHasil = findViewById(R.id.icon_hasil);

        textMateri = findViewById(R.id.text_materi);
        textForm = findViewById(R.id.text_form);
        textHasil = findViewById(R.id.text_hasil);
    }

    private void setupNavigation() {
        navMateri.setOnClickListener(v -> {
            showFragment(new MateriFragment());
            setActiveNav(navMateri);
        });

        navForm.setOnClickListener(v -> {
            showFragment(new FormFragment());
            setActiveNav(navForm);
        });

        navHasil.setOnClickListener(v -> {
            if (isFormCompleted) {
                // Kirim data ke HasilFragment - TAMBAH 1 DATA
                HasilFragment hasilFragment = new HasilFragment();
                Bundle bundle = new Bundle();
                bundle.putString("NAMA", hasilNama);
                bundle.putString("NOMINAL", hasilNominal);
                bundle.putString("TENOR", hasilTenor);
                bundle.putString("BUNGA_PERSEN", hasilSukuBunga); // TAMBAH INI
                bundle.putString("BUNGA", hasilBunga);
                bundle.putString("TOTAL", hasilTotal);
                hasilFragment.setArguments(bundle);

                showFragment(hasilFragment);
                setActiveNav(navHasil);
            } else {
                showFragment(new FormFragment());
                setActiveNav(navForm);
            }
        });
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setActiveNav(LinearLayout activeNav) {
        // Reset semua nav ke tidak aktif
        setNavInactive(navMateri, iconMateri, textMateri);
        setNavInactive(navForm, iconForm, textForm);
        setNavInactive(navHasil, iconHasil, textHasil);

        // Set nav yang aktif
        setNavActive(activeNav);
    }

    private void setNavActive(LinearLayout nav) {
        if (nav == navMateri) {
            navMateri.setBackgroundResource(R.drawable.nav_active_background);
            iconMateri.setColorFilter(ContextCompat.getColor(this, R.color.white));
            textMateri.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (nav == navForm) {
            navForm.setBackgroundResource(R.drawable.nav_active_background);
            iconForm.setColorFilter(ContextCompat.getColor(this, R.color.white));
            textForm.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else if (nav == navHasil) {
            navHasil.setBackgroundResource(R.drawable.nav_active_background);
            iconHasil.setColorFilter(ContextCompat.getColor(this, R.color.white));
            textHasil.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    private void setNavInactive(LinearLayout nav, ImageView icon, TextView text) {
        nav.setBackgroundResource(R.drawable.nav_inactive_background);
        icon.setColorFilter(ContextCompat.getColor(this, R.color.text_secondary));
        text.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));

        // Khusus untuk navHasil, set alpha jika form belum selesai
        if (nav == navHasil && !isFormCompleted) {
            nav.setAlpha(0.5f);
            icon.setAlpha(0.5f);
            text.setAlpha(0.5f);
        } else {
            nav.setAlpha(1.0f);
            icon.setAlpha(1.0f);
            text.setAlpha(1.0f);
        }
    }

    // Method untuk menyimpan data hasil perhitungan dari FormFragment
    // PERBAIKAN: TAMBAH 1 PARAMETER
    public void saveHasilData(String nama, String nominal, String tenor,
                              String sukuBunga, String bunga, String total) {
        this.hasilNama = nama;
        this.hasilNominal = nominal;
        this.hasilTenor = tenor;
        this.hasilSukuBunga = sukuBunga; // TAMBAH INI
        this.hasilBunga = bunga;
        this.hasilTotal = total;
        this.isFormCompleted = true;

        // Aktifkan navHasil
        navHasil.setAlpha(1.0f);
        iconHasil.setAlpha(1.0f);
        textHasil.setAlpha(1.0f);
    }

    // Method untuk pindah ke HasilFragment langsung dari FormFragment
    // PERBAIKAN: GANTI MENJADI 6 PARAMETER
    public void showHasilFragment(String nama, String nominal, String tenor,
                                  String sukuBunga, String bunga, String total) {
        saveHasilData(nama, nominal, tenor, sukuBunga, bunga, total);

        HasilFragment hasilFragment = new HasilFragment();
        Bundle bundle = new Bundle();
        bundle.putString("NAMA", nama);
        bundle.putString("NOMINAL", nominal);
        bundle.putString("TENOR", tenor);
        bundle.putString("BUNGA_PERSEN", sukuBunga); // TAMBAH INI
        bundle.putString("BUNGA", bunga);
        bundle.putString("TOTAL", total);
        hasilFragment.setArguments(bundle);

        showFragment(hasilFragment);
        setActiveNav(navHasil);
    }

    public void resetToForm() {
        isFormCompleted = false;
        showFragment(new FormFragment());
        setActiveNav(navForm);

        // Set navHasil kembali ke semi-transparan
        navHasil.setAlpha(0.5f);
        iconHasil.setAlpha(0.5f);
        textHasil.setAlpha(0.5f);
    }
}