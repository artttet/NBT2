package chiglintsev.notboringtrails20.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import chiglintsev.notboringtrails20.R;

public class TestFrag4 extends Fragment {

    private Animation test;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_frag4, null);

    }

    @Override
    public void onResume() {
        super.onResume();


        view.startAnimation(test);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animOpen();

    }

    private void animOpen() {
        test = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        view = getView().findViewById(R.id.frag4);
    }
}
