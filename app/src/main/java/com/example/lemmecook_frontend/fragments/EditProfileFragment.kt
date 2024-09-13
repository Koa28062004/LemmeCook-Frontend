package com.example.lemmecook_frontend.fragments

import EditProfileComponent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.lemmecook_frontend.activities.settings.SharedViewModelSettings
import com.example.lemmecook_frontend.ui.theme.LemmeCookFrontendTheme

class EditProfileFragment : Fragment() {

    private lateinit var navController: NavHostController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Obtain the ViewModel and get the NavHostController
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModelSettings::class.java)
        navController = sharedViewModel.data ?: run {
            Log.e("EditProfileFragment", "NavHostController is not available")
            // Handle the scenario where NavController might not be set
            // Optionally, provide fallback logic if needed
            return ComposeView(requireContext()).apply {
                setContent {
                    LemmeCookFrontendTheme {
                        // Provide a fallback UI or inform the user if NavHostController is missing
                        Text(text = "Navigation Controller not available")
                    }
                }
            }
        }

        // Return ComposeView with the NavHostController passed to the EditProfileComponent
        return ComposeView(requireContext()).apply {
            setContent {
                LemmeCookFrontendTheme {
                    // Always pass the NavHostController to EditProfileComponent
                    EditProfileComponent(navController)
                }
            }
        }
    }
}

