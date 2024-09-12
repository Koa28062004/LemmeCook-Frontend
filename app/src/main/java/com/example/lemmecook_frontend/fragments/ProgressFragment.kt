// ProgressFragment.kt
package com.example.lemmecook_frontend.fragments

import ProgressComponent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.lemmecook_frontend.ui.theme.LemmeCookFrontendTheme

class ProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ComposeView
        return ComposeView(requireContext()).apply {
            setContent {
                LemmeCookFrontendTheme {
                    // Call your composable function here
                    ProgressComponent(
                        currentCalo = 1284,
                        currentFat = 290,
                        currentPro = 650,
                        currentCarb = 850,
                        goalFat = 1000,
                        goalPro = 1000,
                        goalCarb = 1000,
                        allowChange = true
                    )
                }
            }
        }
    }
}
