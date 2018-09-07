package et.tsingtaopad.main;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.core.web.WebDelegateImpl;

import et.tsingtaopad.R;
import et.tsingtaopad.base.BaseActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 仿微信交互方式Demo
 * Created by YoKeyword on 16/6/30.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findFragment(MainFragment.class) == null) {
           loadRootFragment(R.id.fl_container, new MainFragment());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
