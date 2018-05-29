package bruteforce.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MathService extends Service {

    private final IBinder mBinder = new MyBinder();

    public MathService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        MathService getService() {
            return MathService.this;
        }
    }

    public int add(int x, int y) {
        return x + y;
    }

}
