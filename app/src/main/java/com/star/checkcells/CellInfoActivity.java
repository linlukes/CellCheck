package com.star.checkcells;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellIdentityNr;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CellInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CellInfoAdapter adapter;
    private List<CellInfoModel> cellInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CellInfoAdapter(cellInfoList);
        recyclerView.setAdapter(adapter);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getCellInfo();
        }
    }

    private void getCellInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
            String networkType = "No network info available";

            if (allCellInfo != null) {
                for (CellInfo cellInfo : allCellInfo) {
                    long cellId = 0, signalStrength = 0;
                    if (cellInfo instanceof CellInfoGsm) {
                        // 2G Network
                        CellSignalStrength cellSignalStrength = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                        cellId = ((CellInfoGsm) cellInfo).getCellIdentity().getCid(); // Cell ID
                        signalStrength = cellSignalStrength.getDbm(); // Signal strength in dBm
                        networkType = "2G";
                    } else if (cellInfo instanceof CellInfoWcdma) {
                        // 3G Network
                        CellSignalStrength cellSignalStrength = ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                        cellId = ((CellInfoWcdma) cellInfo).getCellIdentity().getCid(); // Cell ID
                        signalStrength = cellSignalStrength.getDbm(); // Signal strength in dBm
                        networkType = "3G";
                    } else if (cellInfo instanceof CellInfoLte) {
                        // 4G (LTE) Network
                        CellSignalStrength cellSignalStrength = ((CellInfoLte) cellInfo).getCellSignalStrength();
                        cellId = ((CellInfoLte) cellInfo).getCellIdentity().getCi(); // Cell ID
                        signalStrength = cellSignalStrength.getDbm(); // Signal strength in dBm
                        networkType = "4G";
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && cellInfo instanceof CellInfoNr) {
                        // 5G Network (Android 10 and above)
                        CellSignalStrength cellSignalStrength = ((CellInfoNr) cellInfo).getCellSignalStrength();
                        cellId = ((CellIdentityNr)((CellInfoNr) cellInfo).getCellIdentity()).getNci(); // Cell ID for 5G NR
                        signalStrength = cellSignalStrength.getDbm(); // Signal strength in dBm
                        networkType = "5G";
                    } else {
                        networkType = "Unknown Network";
                    }

                    cellInfoList.add(new CellInfoModel(cellId, signalStrength, networkType));
                }
                adapter.notifyDataSetChanged(); // Update RecyclerView
            } else {
                Log.d("CellInfo", "No cell information available.");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCellInfo();
        }
    }
}