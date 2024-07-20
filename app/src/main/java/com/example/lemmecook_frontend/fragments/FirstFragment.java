package com.example.lemmecook_frontend.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lemmecook_frontend.api.ApiService;
import com.example.lemmecook_frontend.models.Model;
import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.databinding.FragmentFirstBinding;
import com.example.lemmecook_frontend.utilities.RetrofitClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {


    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Inputs are valid, proceed with registration
                    registerUser();
                }
            }
        });
        return binding.getRoot();
    }

    private boolean validateInputs() {
        boolean isValid = true;
        if (binding.FirstName.getEditText().getText().toString().isEmpty()) {
            binding.FirstName.setError("Required");
            isValid = false;
        } else {
            binding.FirstName.setError(null);
        }

        if (binding.LastName.getEditText().getText().toString().isEmpty()) {
            binding.LastName.setError("Required");
            isValid = false;
        } else {
            binding.LastName.setError(null);
        }
        if (binding.PhoneNumber.getEditText().getText().toString().isEmpty()) {
            binding.PhoneNumber.setError("Required");
            isValid = false;
        } else {
            binding.PhoneNumber.setError(null);
        }
        if (binding.Email.getEditText().getText().toString().isEmpty()) {
            binding.Email.setError("Required");
            isValid = false;
        } else {
            binding.PhoneNumber.setError(null);
        }

        // You can add more validation rules for email and phone number if needed

        return isValid;
    }


    private void registerUser() {
        // Process registration logic here

        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar);
        if (dialog.getWindow() != null) dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false); // Prevent dialog from being dismissed on outside touch
        dialog.show();

        String fname = binding.FirstName.getEditText().getText().toString();
        String lname = binding.LastName.getEditText().getText().toString();
        String email = binding.Email.getEditText().getText().toString();
        String phone = binding.PhoneNumber.getEditText().getText().toString();


        Model model = new Model(fname, lname, email, phone);
        ApiService apiService = RetrofitClient.getConnection().create(ApiService.class);
        Call<Model> call = apiService.createTask(model);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    // Task created successfully
                    Toast.makeText(getContext(), "Task created successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    // Task creation failed
                    Toast.makeText(getContext(), "Failed to create task", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}