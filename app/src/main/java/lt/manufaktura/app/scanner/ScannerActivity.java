package lt.manufaktura.app.scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.ActivityScannerBinding;

public class ScannerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    private ActivityScannerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.activity_scanner,
                null,
                false
        );
        setContentView(binding.getRoot());

        binding.readBarcodeBtnId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode_btn_id) {
            Log.d("TAGGG", "wtf:::??? ");
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, binding.autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, binding.useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    binding.statusMessage.setText(R.string.barcode_success);
                    //binding.barcodeValue.setText(barcode.displayValue);
                    binding.barcodeValue.append(barcode.rawValue);
                    //binding.barcodeValue.append("\n" + barcode.url.url);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    binding.statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                binding.statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}