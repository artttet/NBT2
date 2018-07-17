package chiglintsev.notboringtrails20.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import chiglintsev.notboringtrails20.R;

public class TestFrag3 extends Fragment implements OnMapReadyCallback {

    private Animation test;
    private View globalView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_frag3, null);

    }

    @Override
    public void onResume() {
        super.onResume();


        globalView.startAnimation(test);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animOpen();

    }

    private void animOpen() {
        test = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        globalView = getView().findViewById(R.id.frag3);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}