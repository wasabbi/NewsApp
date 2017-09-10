package Console;

import Activitys.*;
import News.*;
import News.DataConsole;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.cai.newsapp.MainActivity;

import java.util.ArrayList;

/**
 * Created by cai on 2017/9/7.
 */

public class Console {
    public static final int refresh = 1;
    private String str;
    private Bitmap bitmap;
    private NewsActivity activity;

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case refresh:
                    //更新ui
                    activity.refresh();
                    break;
            }
        }
    };

    public Console(NewsActivity activity) {
        this.activity = activity;
        bitmap = null;
    }

    public synchronized void CallBackStr(String str){
        if(activity instanceof MainActivity) {
            MainActivity act = (MainActivity) activity;
            ArrayList<News> newNewsList = DataConsole.toNewsArr(str);
            for(News news: newNewsList) {
                try {
                    //图片的大小临时取200，200
                    news.setThumb(Glide.with(act).load(news.getPictures()[0]).asBitmap().into(200, 200).get());
                }
                catch(Exception e) {
                    Log.e("Exception", e.toString());
                }
            }
            act.addNewsList(newNewsList);
        }
        else if(activity instanceof SearchResultActivity) {
            SearchResultActivity act = (SearchResultActivity) activity;
            act.addNewsList(DataConsole.toNewsArr(str));
        }
        else if(true){//增加新闻详情页内容

        }
    }

    public void CallBackBitmap(Bitmap bitmap, String ID) {
        str = ID;
        this.bitmap = bitmap;
    }
}
