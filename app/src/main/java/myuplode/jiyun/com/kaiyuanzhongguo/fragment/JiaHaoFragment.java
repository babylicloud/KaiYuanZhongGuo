package myuplode.jiyun.com.kaiyuanzhongguo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import myuplode.jiyun.com.kaiyuanzhongguo.R;

public class JiaHaoFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_jia_hao_fragment, null);
        return inflate;
    }
}
