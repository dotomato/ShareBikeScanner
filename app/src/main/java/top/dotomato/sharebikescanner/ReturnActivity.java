package top.dotomato.sharebikescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReturnActivity extends AppCompatActivity {


    @BindView(R.id.stateButton)
    public Button mStateButton;

    private int mTimeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        ButterKnife.bind(this);
        mTimeCount = 5;
        mTimerEnable = true;
        mStateButton.post(mTimer);
    }

    private boolean mTimerEnable;
    private Runnable mTimer = new Runnable() {
        @Override
        public void run() {
            String s = String.format("确定%d" ,mTimeCount);
            mStateButton.setText(s);
            mTimeCount -= 1;
            if (mTimeCount==-1){
                mTimerEnable = false;
                ReturnActivity.this.finish();
            }
            if (mTimerEnable)
                mStateButton.postDelayed(this, 1000);
        }
    };
}
