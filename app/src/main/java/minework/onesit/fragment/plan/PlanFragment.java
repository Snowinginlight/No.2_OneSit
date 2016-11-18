package minework.onesit.fragment.plan;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import minework.onesit.R;

/**
 * Created by 无知 on 2016/11/12.
 */

public class PlanFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View planLayout = inflater.inflate(R.layout.plan_layout, container, false);
        return planLayout;
    }
}
