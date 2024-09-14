package com.example.lemmecook_frontend.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.activities.settings.EditProfileActivity;
import com.example.lemmecook_frontend.activities.settings.LandingActivity;
import com.example.lemmecook_frontend.singleton.UserSession;

public class EditProfileComponentJava extends Fragment {
    public EditProfileComponentJava() {
        // Required empty public constructor
    }

    public static EditProfileComponentJava newInstance() {
        EditProfileComponentJava fragment = new EditProfileComponentJava();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile_component_java, container, false);

        TextView editProfile = view.findViewById(R.id.editProfile);
        TextView logOut = view.findViewById(R.id.logOut);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSession.INSTANCE.clear();

                // Start the LandingActivity
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}