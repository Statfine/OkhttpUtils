package com.sj.at.okhttputils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sj.at.util.OkHttpClientManager;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 作者： Shaojia on 2015/8/24.
 * 邮箱： 309814872@qq.com
 *
 * 本篇博文参考
 * http://blog.csdn.net/lmj623565791/article/details/47911083
 *
 * 方法封装到OkHttpClientManager当中。post get
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private Button get_requst,post_requst,get_requst_util,post_requst_util,img_requst_util,file_requst_util = null;
    private TextView tv_show = null;
    private ImageView img_view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initLayout(){
        get_requst = (Button) this.findViewById(R.id.get_requst);
        post_requst = (Button) this.findViewById(R.id.post_requst);
        get_requst_util = (Button) this.findViewById(R.id.get_requst_util);
        post_requst_util = (Button) this.findViewById(R.id.post_requst_util);
        img_requst_util = (Button) this.findViewById(R.id.img_requst_util);
        file_requst_util = (Button) this.findViewById(R.id.file_requst_util);

        tv_show = (TextView) this.findViewById(R.id.tv_show);
        img_view = (ImageView) this.findViewById(R.id.img_view);

        get_requst.setOnClickListener(this);
        post_requst.setOnClickListener(this);
        get_requst_util.setOnClickListener(this);
        post_requst_util.setOnClickListener(this);
        img_requst_util.setOnClickListener(this);
        file_requst_util.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_requst:
                //创建okHttpClient对象
                OkHttpClient mOkHttpClient = new OkHttpClient();
                //创建一个Request
                final Request request = new Request.Builder()
                        .url("https://github.com/hongyangAndroid")
                        .build();
                //new call
                Call call = mOkHttpClient.newCall(request);
                //请求加入调度
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }
                    //onResponse回调的参数是response，
                    // 一般情况下，比如我们希望获得返回的字符串，可以通过response.body().string()获取；
                    // 如果希望获得返回的二进制字节数组，则调用response.body().bytes()；
                    // 如果你想拿到返回的inputStream，则调用response.body().byteStream()
                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                tv_show.setText(res);
                            }

                        });
                    }
                });
                break;
            case R.id.post_requst:
                /*
                FormEncodingBuilder builder = new FormEncodingBuilder();
                builder.add("username","name");

                Request request = new Request.Builder().url(url).post(builder.build()).build();
                mOkHttpClient.newCall(request).enqueue(new Callback(){});
                 */
                String res = "FormEncodingBuilder builder = new FormEncodingBuilder();\n" +
                            "\n"+
                            "builder.add(\"username\",\"name\");\n" +
                            "\n"+
                            "Request request = new Request.Builder().url(url).post(builder.build()).build();\n" +
                            "\n"+
                            "mOkHttpClient.newCall(request).enqueue(new Callback(){});";
                tv_show.setText(res);
                break;

            case R.id.get_requst_util:
                OkHttpClientManager.getAsyn("https://github.com/Statfine", new OkHttpClientManager.StringCallback()
                {
                    @Override
                    public void onFailure(Request request, IOException e)
                    {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String bytes)
                    {
                        tv_show.setText(bytes);//注意这里是UI线程回调，可以直接操作控件
                    }
                });
                break;
            case R.id.post_requst_util:
                /*
                OkHttpClientManager.postAsyn("Url", new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {

                    }
                }, new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("username", "zhy"),
                        new OkHttpClientManager.Param("password", "123")});
                        */
                String text = "OkHttpClientManager.postAsyn(\"Url\", new OkHttpClientManager.StringCallback() {\n" +
                        "                    @Override\n" +
                        "                    public void onFailure(Request request, IOException e) {\n" +
                        "\n" +
                        "                    }\n" +
                        "\n" +
                        "                    @Override\n" +
                        "                    public void onResponse(String response) {\n" +
                        "\n" +
                        "                    }\n" +
                        "                }, new OkHttpClientManager.Param[]{\n" +
                        "                        new OkHttpClientManager.Param(\"username\", \"zhy\"),\n" +
                        "                        new OkHttpClientManager.Param(\"password\", \"123\")});";
                tv_show.setText(text);

                break;

            case R.id.img_requst_util:
                OkHttpClientManager.displayImage(img_view, "http://images.csdn.net/20150817/1.jpg");
                break;

            case R.id.file_requst_util:
                String textFile = "文件下载：\n"+
                        "OkHttpClientManager.downloadAsyn(\"Url\",\"path\", new OkHttpClientManager.StringCallback() {\n" +
                        "                    @Override\n" +
                        "                    public void onFailure(Request request, IOException e) {\n" +
                        "\n" +
                        "                    }\n" +
                        "                    @Override\n" +
                        "                    public void onResponse(String response) {\n" +
                        "                        //文件下载成功，这里回调的reponse为文件的absolutePath\n" +
                        "                    }\n" +
                        "                });\n" +
                        "\n" +
                        "\n" +
                        "文件上传：\n"+
                        "                try {\n" +
                        "                    OkHttpClientManager.postAsyn(\"url\",//\n" +
                        "                            new OkHttpClientManager.StringCallback()\n" +
                        "                            {\n" +
                        "                                @Override\n" +
                        "                                public void onFailure(Request request, IOException e)\n" +
                        "                                {\n" +
                        "                                    e.printStackTrace();\n" +
                        "                                }\n" +
                        "    \n" +
                        "                                @Override\n" +
                        "                                public void onResponse(String result)\n" +
                        "                                {\n" +
                        "    \n" +
                        "                                }\n" +
                        "                            },new File(\"\"),\n" +
                        "                            \"key\"\n" +
                        "                    );\n" +
                        "                } catch (IOException e) {\n" +
                        "                    e.printStackTrace();\n" +
                        "                }";

                tv_show.setText(textFile);
                break;
            default:
                break;
        }
    }
}
