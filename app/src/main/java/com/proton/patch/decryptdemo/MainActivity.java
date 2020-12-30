package com.proton.patch.decryptdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.proton.decrypt.DecryptHelper;
import com.proton.decrypt.bean.EncryptData;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtils.getReadAndWritePermission(this);
        setContentView(R.layout.activity_main);
        decrypt();
    }

    /**
     * 解密数据，使用方法
     * 1，从蓝牙sdk获取到蓝牙数据 （receiveBluetoothData返回的）
     * 2，调用DecryptHelper.decryptFilterData 解密数据，可以得到心电滤波数据
     */
    public void decrypt() {
        byte[] bytes = loadFromAssets(this, "example.dat");
        EncryptData encryptData = DecryptHelper.decryptFilterData(bytes);
        Log.e("MainActivity", "加密数据大小:" + encryptData.getFilterDatas().size() + ",数据:" + encryptData.getFilterDatas());
    }

    public static byte[] loadFromAssets(Context context, String picName) {
        byte[] result = null;
        try {
            InputStream in = context.getResources().getAssets().open(picName);
            int ch;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            result = baos.toByteArray();
            baos.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}