package lt.manufaktura.app.user.account.product.create;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
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

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentProductPictureBinding;
import lt.manufaktura.app.model.product.ProductViewModel;
import static android.app.Activity.RESULT_OK;


@AndroidEntryPoint
public class ProductPictureFragment extends Fragment {

    @Inject
    SharedPreferences prefs;

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri photoURI;
    private ProductViewModel productViewModel;
    private FragmentProductPictureBinding binding;

    public ProductPictureFragment() {
        // Required empty public constructor
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
            if (binding.getProduct().getProductImage() == null) {
                Toast.makeText(requireContext(), "Choose image", Toast.LENGTH_LONG).show();
            } else if (binding.productPictureNameInputId.getText().toString().isEmpty()) {
                binding.productPictureNameInputId.setError("Enter image name");
            } else {
                productViewModel.createProduct("Bearer " + prefs.getString("Token", ""), binding.getProduct());
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.action_productPictureFragment_to_userProductionFragment);
            }
        });

        binding.takePictureBtnId.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        binding.openGalleryBtnId.setOnClickListener(v -> {
            pickFromGallery();
        });
        return binding.getRoot();
    }

    private Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight()
        );
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width, height, filter);
    }

    private void pickFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Intent intent = gallery.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    private void encodeBitMapToBase64(Uri uri) {
        try {
            InputStream imageStream = requireActivity().getContentResolver().openInputStream(uri);
            Bitmap selectedImageBM = BitmapFactory.decodeStream(imageStream);
            Bitmap scaledBitmap = scaleDown(selectedImageBM, 492f, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();

            String image = Base64.encodeToString(bytes, 0);

            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            binding.imageViewId.setImageBitmap(decodedByte);
            binding.getProduct().setProductImage(image);

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
                ex.fillInStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(requireContext(),
                        "lt.manufaktura.app.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private void setPic(String imagePath) {
        // Get the dimensions of the View
        int targetW = binding.imageViewId.getWidth();
        int targetH = binding.imageViewId.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imagePath, bmOptions);

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

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        binding.imageViewId.setImageBitmap(bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic(photoURI.getPath());
            encodeBitMapToBase64(photoURI);
        }

        if (requestCode == REQUEST_PICK_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap =
                            MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                    encodeBitMapToBase64(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}