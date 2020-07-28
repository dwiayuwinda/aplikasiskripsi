package com.example.aplikasiskripsi;

import java.util.ArrayList;
import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadSuccess(ArrayList<SupplierDB> daftarsupp);
    void onFirebaseLoadFailed(String message);
}
