package com.dhairya.aapnidukan.BottamNavTabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dhairya.aapnidukan.NetworkUtils;
import com.dhairya.aapnidukan.R;
import com.dhairya.aapnidukan.databinding.ActivityMainBinding;
import com.dhairya.aapnidukan.databinding.FragmentAccountBinding;
import com.dhairya.aapnidukan.databinding.FragmentCartTabBinding;


public class Account extends Fragment {
    private FragmentAccountBinding binding;
    private LinearLayout onlineLayout, offlineLayout;
    private Button refreshButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        onlineLayout = root.findViewById(R.id.online_layout);
        offlineLayout = root.findViewById(R.id.offline_layout);
        refreshButton = root.findViewById(R.id.btn_network_refresh);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });

        updateLayoutVisibility();
        return root;

    }

    private void updateLayoutVisibility() {
        if (NetworkUtils.isConnected(requireContext())) {
            onlineLayout.setVisibility(View.VISIBLE);
            offlineLayout.setVisibility(View.GONE);
        } else {
            onlineLayout.setVisibility(View.GONE);
            offlineLayout.setVisibility(View.VISIBLE);
        }
    }

    private void refreshPage() {
        updateLayoutVisibility();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}