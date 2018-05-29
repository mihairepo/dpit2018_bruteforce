package bruteforce.ro.financedemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bruteforce.ro.financedemo.services.ConvertResponse;
import bruteforce.ro.financedemo.services.ForgeApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinanceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;
    private ForgeApi forgeAPI;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    private void createForgeAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ForgeApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        forgeAPI = retrofit.create(ForgeApi.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        findViewById(R.id.buttonConvert).setOnClickListener(this);

        createForgeAPI();
    }

    @Override
    public void onClick(View v) {
        EditText e1 = (EditText)findViewById(R.id.editFromValuta);
        EditText e2 = (EditText)findViewById(R.id.editToValuta);
        EditText e3 = (EditText)findViewById(R.id.editSuma);
        String from = e1.getText().toString();
        String to = e2.getText().toString();
        String quantity = e3.getText().toString();
        forgeAPI.convert(from,to,quantity,ForgeApi.api_key).enqueue(convertCallback);
    }

    Callback<ConvertResponse> convertCallback = new Callback<ConvertResponse>(){

        @Override
        public void onResponse(Call<ConvertResponse> call, Response<ConvertResponse> response) {
            if (response.isSuccessful()) {
                ((EditText)findViewById(R.id.editResult)).setText(""+response.body().getValue());
            }else{
                ((EditText)findViewById(R.id.editResult)).setText("Eroare!");
            }
        }

        @Override
        public void onFailure(Call<ConvertResponse> call, Throwable t) {
            t.printStackTrace();
        }
    };


}
