package com.orzangleli.aidlserverdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mPriceEt;
    private Button mPayBtn;

    private IAliPayInterface mAliPayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceEt = this.findViewById(R.id.price);
        mPayBtn = this.findViewById(R.id.pay);

        connectService();

        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mPriceEt.getText().toString())) {
                    Toast.makeText(MainActivity.this, "价格不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mAliPayService == null) {
                    connectService();
                } else if (mAliPayService != null) {
                    try {
                        mAliPayService.pay(Double.parseDouble(mPriceEt.getText().toString()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void connectService() {
        Intent intent = new Intent();
        intent.setPackage("com.orzangleli.aidlserverdemo");
        intent.setAction("com.orzangleli.aidlserverdemo.AliPayService.START");
//        intent = createExplicitFromImplicitIntent(this, intent);
        this.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mAliPayService = IAliPayInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mAliPayService = null;
            }
        }, BIND_AUTO_CREATE);
    }


    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

}
