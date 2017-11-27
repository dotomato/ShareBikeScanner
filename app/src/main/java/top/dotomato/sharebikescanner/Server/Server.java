package top.dotomato.sharebikescanner.Server;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import top.dotomato.sharebikescanner.DataModel.MyCode;
import top.dotomato.sharebikescanner.DataModel.MyCodeResult;

/**
 * Created by chen on 17-11-2.
 * Copyright *
 */

public class Server {


    final static private String TAG = "Server";

    //    final static private String BASEURL = "www.dotomato.win";
    final static private String BASEURL = "http://dotomato.top:5001"; //这里要填上树莓派的地址
    final static private String VERSION = "/api/v0.01";

    public interface ServerInterface {

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST(VERSION + "/return_car")
        Observable<MyCodeResult> returnCar(@Body MyCode var);

    }


    private static ServerInterface mServer=null;

    public static ServerInterface getApi(){
        if (mServer==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            mServer = retrofit.create(ServerInterface.class);
        }
        return mServer;
    }
}
