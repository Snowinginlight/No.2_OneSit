package minework.onesit.fragment.find;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import minework.onesit.R;

/**
 * Created by 无知 on 2016/11/12.
 */

public class FindFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View findLayout = inflater.inflate(R.layout.find_layout,container,false);
        return findLayout;
    }
}
