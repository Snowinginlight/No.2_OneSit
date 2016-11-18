package minework.onesit.fragment.mine;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import minework.onesit.R;

/**
 * Created by 无知 on 2016/11/12.
 */

public class MineFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mineLayout = inflater.inflate(R.layout.mine_layout,container,false);
        return mineLayout;
    }
}
