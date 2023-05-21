    package com.example.zxingreader;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Toast;

    import androidx.activity.result.ActivityResultLauncher;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.zxing.client.android.Intents;
    import com.journeyapps.barcodescanner.ScanContract;
    import com.journeyapps.barcodescanner.ScanOptions;

    public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        public void onButtonClick(View view) {
            ScanOptions options = new ScanOptions();
            options.setCaptureActivity(ScanQRActivity.class);
            options.setOrientationLocked(false);
            options.setBeepEnabled(false);
            barcodeLauncher.launch(options);
        }

        private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if(result.getContents() == null) {
                        Intent originalIntent = result.getOriginalIntent();
                        if (originalIntent == null) {
                            Log.d("MainActivity", "Cancelled scan");
                            Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                        } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                            Log.d("MainActivity", "Cancelled scan due to missing camera permission");
                            Toast.makeText(MainActivity.this, "Cancelled due to missing camera permission", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("MainActivity", "Scanned");
                        Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    }
                });
    }