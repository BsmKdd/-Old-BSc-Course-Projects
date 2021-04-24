package com.example.mygym;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scanMachineFragment extends Fragment implements ZXingScannerView.ResultHandler
{
    private ZXingScannerView scannerView;
    private TextView scanResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.scan_machine_fragment, container, false);

        scannerView = view.findViewById(R.id.codeScanner);

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
                            @Override
                            public void handleResult(Result rawResult)
                            {
                               String machineQode = rawResult.getText();
                               Intent scannedMachineIntent = new Intent(getActivity(), scannedMachineDetails.class);
                               scannedMachineIntent.putExtra("scannedQR", machineQode);
                               startActivity(scannedMachineIntent);
                               onPause();

                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getActivity(), "Permission not granted, you can't use this feature,", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();


        return view;

    }

    @Override
    public void onDestroy()
    {
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void onResume()
    {
        scannerView.startCamera();

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
                            @Override
                            public void handleResult(Result rawResult)
                            {
                                String machineQode = rawResult.getText();
                                Intent scannedMachineIntent = new Intent(getActivity(), scannedMachineDetails.class);
                                scannedMachineIntent.putExtra("scannedQR", machineQode);
                                startActivity(scannedMachineIntent);
                                onPause();

                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getActivity(), "Permission not granted, you can't use this feature,", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

    }
}
