package com.dhairya.aapnidukan.BottamNavTabs;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dhairya.aapnidukan.NetworkUtils;
import com.dhairya.aapnidukan.R;
import com.dhairya.aapnidukan.ViewPagerAdapter;
import com.dhairya.aapnidukan.databinding.FragmentHomeTabBinding;

import java.util.Timer;
import java.util.TimerTask;

public class HomeTab extends Fragment {

    private FragmentHomeTabBinding binding;
    private LinearLayout onlineLayout, offlineLayout;
    private Button refreshButton;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] images = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground};
    private int currentPage = 0;
    private Timer timer;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeTabBinding.inflate(inflater, container, false);
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

        try {
            updateLayoutVisibility();
            viewPager = root.findViewById(R.id.viewPager);
            dotsLayout = root.findViewById(R.id.dotsLayout);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), images);
            viewPager.setAdapter(adapter);

            addDotsIndicator(0);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    addDotsIndicator(position);
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            // Auto slide functionality
            final Handler handler = new Handler(Looper.getMainLooper());
            final Runnable update = new Runnable() {
                public void run() {
                    if (currentPage == images.length) {
                        currentPage = 0;
                    }
                    viewPager.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, 2000, 3000); // Change second parameter (2000) for auto slide duration in milliseconds
        } catch (Exception e) {
            Log.d("error",e.getMessage());
        }

        return root;
    }

    private void addDotsIndicator(int position) {
        ImageView[] dots = new ImageView[images.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageResource(R.drawable.dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dotsLayout.addView(dots[i], params);
        }

        if (dots.length > 0) {
            dots[position].setImageResource(R.drawable.dotone);
        }
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
