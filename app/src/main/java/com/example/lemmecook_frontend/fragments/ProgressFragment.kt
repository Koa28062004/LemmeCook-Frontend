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
                        allowChange = true
                    )
                }
            }
        }
    }
}
