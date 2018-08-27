package chiglintsev.notboringtrails20.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import chiglintsev.notboringtrails20.InMoreActivity;
import chiglintsev.notboringtrails20.R;

public class MoreFragment extends Fragment {
    private ListView listView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.more_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addToolbar(view);
        final String[] mores = new String[]{
                "Больше узнать об Омске",
                "Провести досуг в Омске",
                "Остаться на ночлег",
                "Сувенирные лавки",
                "О приложении"
        };

        listView = view.findViewById(R.id.more_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.more_list_item,mores);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: {
                        Intent intent = new Intent(getContext(), InMoreActivity.class);
                        intent.putExtra("POSITION_FROM_MORE_FRAGMENT", i);
                        intent.putExtra("TITLE_FROM_MORE_FRAGMENTS", "Больше узнать об Омске");
                        startActivity(intent);
                    }break;
                    case 1: {
                        Intent intent = new Intent(getContext(), InMoreActivity.class);
                        intent.putExtra("POSITION_FROM_MORE_FRAGMENT", i);
                        intent.putExtra("TITLE_FROM_MORE_FRAGMENTS", "Провести досуг в Омске");
                        startActivity(intent);
                    }break;
                    case 2: {
                        Intent intent = new Intent(getContext(), InMoreActivity.class);
                        intent.putExtra("POSITION_FROM_MORE_FRAGMENT", i);
                        intent.putExtra("TITLE_FROM_MORE_FRAGMENTS", "Остаться на ночлег");
                        startActivity(intent);
                    }break;
                    case 3: {
                        Intent intent = new Intent(getContext(), InMoreActivity.class);
                        intent.putExtra("POSITION_FROM_MORE_FRAGMENT", i);
                        intent.putExtra("TITLE_FROM_MORE_FRAGMENTS", "Сувенирные лавки");
                        startActivity(intent);
                    }break;
                    case 4: {
                        Intent intent = new Intent(getContext(), InMoreActivity.class);
                        intent.putExtra("POSITION_FROM_MORE_FRAGMENT", i);
                        intent.putExtra("TITLE_FROM_MORE_FRAGMENTS", "О приложении");
                        startActivity(intent);
                    }break;
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        animOpen();
    }

    private void animOpen() {
        Animation transition = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        View view = getView().findViewById(R.id.more_list);
        view.startAnimation(transition);
    }

    private void addToolbar(View view){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Ещё");
    }


}
