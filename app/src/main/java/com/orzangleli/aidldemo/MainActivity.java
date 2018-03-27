package com.orzangleli.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orzangleli.aidlserverdemo.IAliPayInterface;
import com.orzangleli.aidlserverdemo.OrderVo;


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
                        OrderVo orderVo = new OrderVo(System.currentTimeMillis()+"", "买苹果", mPriceEt.getText().toString(), "5");
                        mAliPayService.pay(orderVo);
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


}
