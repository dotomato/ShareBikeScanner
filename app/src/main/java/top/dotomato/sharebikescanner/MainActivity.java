package top.dotomato.sharebikescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.dotomato.sharebikescanner.DataModel.MyCode;
import top.dotomato.sharebikescanner.DataModel.MyCodeResult;
import top.dotomato.sharebikescanner.Server.MyAction1;
import top.dotomato.sharebikescanner.Server.Server;

public class MainActivity extends AppCompatActivity {

    final static int SCAN_CODE = 0;

    @BindView(R.id.backImageView)
    public ImageView mBackImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.scanButton)
    public void scanButtonClick(){
        Intent i = new Intent(this, ScanActivity.class);
        this.startActivityForResult(i, SCAN_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == SCAN_CODE){
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("code");
                Gson gson = new Gson();
                MyCode myCode = gson.fromJson(result, MyCode.class);
                if (!myCode.type.equals("car"))
                    return;
                Server.getApi().returnCar(myCode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyAction1<MyCodeResult>() {
                            @Override
                            public void call() {
                                if (mVar.result.equals("success")){
                                    Intent i = new Intent(MainActivity.this, ReturnActivity.class);
                                    MainActivity.this.startActivity(i);
                                }
                            }
                        });
            }
        }
    }
}
