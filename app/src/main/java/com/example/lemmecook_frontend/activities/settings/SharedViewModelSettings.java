package com.example.lemmecook_frontend.activities.settings;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavHostController;

public class SharedViewModelSettings extends ViewModel {
    private NavHostController navController;

    public NavHostController getData() {
        return navController;
    }

    public void setData(NavHostController navController) {
        this.navController = navController;
    }
}
