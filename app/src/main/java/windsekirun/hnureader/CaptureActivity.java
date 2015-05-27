package windsekirun.hnureader;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

/**
 * HNUReader
 * Class: CaptureActivity
 * Created by WindSekirun on 15. 5. 15..
 */
@SuppressWarnings("ConstantConditions")
public class CaptureActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    private QRCodeReaderView qrview;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        if (Build.VERSION.SDK_INT >= 20) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(0xff455a64);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("QR코드 인식");
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        qrview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrview.setOnQRCodeReadListener(this);
    }

    @Override
    public void onQRCodeRead(String s, PointF[] pointFs) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(s));
        startActivity(i);
        finish();
    }

    @Override
    public void cameraNotFound() {
        Toast.makeText(CaptureActivity.this, "사용 가능한 카메라가 없습니다.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void QRCodeNotFoundOnCamImage() {
    }

    @Override
    public void onResume() {
        super.onResume();
        qrview.getCameraManager().startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        qrview.getCameraManager().stopPreview();
    }

    @Override
    public void onBackPressed() {
        qrview.getCameraManager().stopPreview();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}