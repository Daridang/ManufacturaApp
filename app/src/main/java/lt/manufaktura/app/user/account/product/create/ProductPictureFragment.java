package lt.manufaktura.app.user.account.product.create;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentProductPictureBinding;
import lt.manufaktura.app.model.product.ProductViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


@AndroidEntryPoint
public class ProductPictureFragment extends Fragment {
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;
    private ProductViewModel productViewModel;
    private FragmentProductPictureBinding binding;

    public ProductPictureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAGGG", " onCreate ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_product_picture, container, false
        );

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        binding.setViewmodel(productViewModel);
        binding.setProduct(productViewModel.getProduct());

        binding.createBtnId.setOnClickListener(v -> {
            encodeBitMapToBase64();
            Log.d("TAGGG", binding.getProduct().toString());
            productViewModel.createProduct(binding.getProduct());
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productPictureFragment_to_userProductionFragment);
        });

        binding.takePictureBtnId.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });
        return binding.getRoot();
    }

    private void uploadImage() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        File f = new File(currentPhotoPath);
        RequestBody image = RequestBody.create(MediaType.parse("image/*"), f);
        builder.addFormDataPart("user[ProductImage]", f.getName(), image);
        MultipartBody requestBody = builder.build();
        productViewModel.uploadProduct(requestBody);
    }

    private void encodeBitMapToBase64() {
        File f = new File(currentPhotoPath);
        Uri imageUri = Uri.fromFile(f);
        try {
            InputStream imageStream = requireActivity().getContentResolver().openInputStream(imageUri);
            Bitmap selectedImageBM = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImageBM.compress(Bitmap.CompressFormat.JPEG, 100,
                    byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            String image = Base64.encodeToString(bytes, 0);
            binding.getProduct().setProductPicture(image);
        } catch (FileNotFoundException e) {
            Toast.makeText(requireContext(), "File not found.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("TAGGG", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "lt.manufaktura.app.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        requireActivity().sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = binding.imageViewId.getWidth();
        int targetH = binding.imageViewId.getHeight();
        Log.d("TAGGG", " targetW: " + targetW + " targetH: " + targetH);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = 4;
        if (targetW > 0 && targetH > 0) {
            scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        binding.imageViewId.setImageBitmap(bitmap);
        galleryAddPic();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAGGG", " onActivityResult, Intent: " + (data == null));
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.d("TAGGG", " onActivityResult, result: " + resultCode);
            setPic();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}