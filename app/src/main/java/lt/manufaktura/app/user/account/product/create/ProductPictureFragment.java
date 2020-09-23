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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lt.manufaktura.app.FileHelper;
import lt.manufaktura.app.R;
import lt.manufaktura.app.databinding.FragmentProductPictureBinding;
import lt.manufaktura.app.model.product.Product;
import lt.manufaktura.app.model.product.ProductViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


@AndroidEntryPoint
public class ProductPictureFragment extends Fragment {

    @Inject
    SharedPreferences prefs;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_PICK_PHOTO = 2;
    String currentPhotoPath;
    Uri photoURI;
    private ProductViewModel productViewModel;
    private FragmentProductPictureBinding binding;

    Product p;

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
        Log.d("TAGGG", "viewModelProduct: " + productViewModel.getProduct().toString());

        p = new Product();
        p.setName("TestProductName");
        p.setSection("Stalius");
        p.setPrice(33.33);
        p.setCategory("TestCategory");
        p.setDescription("TestDescription");
        p.setProductPicture("tut xz");

        binding.createBtnId.setOnClickListener(v -> {
//            encodeBitMapToBase64(currentPhotoPath);
            Log.d("TAGGG", "bindingProduct: " + binding.getProduct().toString());
            Log.d("TAGGG", "p: " + p.toString());
            productViewModel.createProduct("Bearer " + prefs.getString("Token", ""),
                    p);
//                    binding.getProduct());
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_productPictureFragment_to_userProductionFragment);
        });

        binding.takePictureBtnId.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        binding.openGalleryBtnId.setOnClickListener(v -> {
            pickFromGallery();
        });
        return binding.getRoot();
    }

    private void pickFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Intent intent = gallery.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    private void encodeBitMapToBase64(Uri uri) {
//        File f = new File(uri);
//        Uri imageUri = Uri.fromFile(f);
        try {
            InputStream imageStream = requireActivity().getContentResolver().openInputStream(uri);
            Log.d("TAGGG", " imageStream: " + (imageStream != null));
            Bitmap selectedImageBM = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImageBM.compress(Bitmap.CompressFormat.JPEG, 100,
                    byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            Log.d("TAGGG", " bytes: " + bytes.length);
            String image = Base64.encodeToString(bytes, 0);
            p.setProductImage(image);

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
                Log.d("TAGGG", ex.getMessage());
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

    private void setPic(String imagePath) {
        // Get the dimensions of the View
        int targetW = binding.imageViewId.getWidth();
        int targetH = binding.imageViewId.getHeight();
        Log.d("TAGGG", " targetW: " + targetW + " targetH: " + targetH);
        Log.d("TAGGG", " Image Path: " + imagePath);

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
//        galleryAddPic();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAGGG", " onActivityResult, Intent: " + (data == null));
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.d("TAGGG", " onActivityResult, result: " + resultCode);
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
                    currentPhotoPath = uri.getPath();
                    binding.imageViewId.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}