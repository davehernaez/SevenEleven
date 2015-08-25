package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.businesslayer.ProductsRetrotfitManager;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 8/11/2015.
 */
public class AddNewProductFragment extends BaseFragment implements View.OnClickListener, TextWatcher {
    @Inject
    AndroidUtils utils;
    @Inject
    ProductsRetrotfitManager productsRetrotfitManager;
    @Inject
    Bus bus;

    @InjectView(R.id.editText_newProductName)
    protected EditText editTextProductname;
    @InjectView(R.id.editText_newProductPrice)
    protected EditText editTextProductPrice;
    @InjectView(R.id.editText_newProductImage)
    protected EditText editTextProductImage;
    @InjectView(R.id.buttonDoneAddNewProduct)
    protected Button buttonDone;
    @InjectView(R.id.buttonCancelNewProduct)
    protected Button buttonCancel;
    @InjectView(R.id.buttonAddImage)
    protected Button buttonAddImage;
    @InjectView(R.id.imageView_uploadImage)
    protected ImageView imageViewUpload;


    @Override
    public void onActivityCreated2(Bundle savedInstanceState) {
        buttonAddImage.setOnClickListener(this);
        buttonDone.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        editTextProductname.requestFocus();
        editTextProductImage.addTextChangedListener(this);

        if (savedInstanceState != null) {
            photo = savedInstanceState.getParcelable("bitmap");
            imageViewUpload.setImageBitmap(photo);
            if (savedInstanceState.getBoolean("dialog") == true) {
                //selectImage().show();
                Log.e("dialog", "Showing" + savedInstanceState.getBoolean("dialog"));
            }
        }
    }

    @Override
    public View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_product, container, false);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState2(Bundle outState) {
        outState.putParcelable("bitmap", photo);
        if (selectImage().isShowing()) {
            outState.putBoolean("dialog", true);
            //selectImage().dismiss();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonAddImage:
                animateFlashPulse(buttonAddImage);
                selectImage().show();

                break;

            case R.id.buttonDoneAddNewProduct:
                animateFlashPulse(buttonDone);
                if (!TextUtils.isEmpty(editTextProductname.getText()) && !TextUtils.isEmpty(editTextProductPrice.getText()) && !TextUtils.isEmpty(editTextProductImage.getText())) {
                    if (addNewProduct(editTextProductname.getText().toString(),
                            Double.parseDouble(editTextProductPrice.getText().toString()),
                            editTextProductImage.getText().toString(), null)) {
                        bus.post(new Product());
                        editTextProductname.setText("");
                        editTextProductImage.setText("");
                        editTextProductImage.setVisibility(getView().GONE);
                        editTextProductPrice.setText("");
                        imageViewUpload.setImageBitmap(null);
                    }
                } else if (!TextUtils.isEmpty(editTextProductname.getText()) && !TextUtils.isEmpty(editTextProductPrice.getText()) && TextUtils.isEmpty(editTextProductImage.getText())) {
                    final String BASE_URL = "http://seventen.tastradesoft.com/android_connect/images/";
                    Bitmap bitmap = ((BitmapDrawable) imageViewUpload.getDrawable()).getBitmap();
                    if (addNewProduct(editTextProductname.getText().toString(),
                            Double.parseDouble(editTextProductPrice.getText().toString()), BASE_URL,
                            ImageString(bitmap))) {
                        bus.post(new Product());
                        editTextProductname.setText("");
                        editTextProductImage.setText("");
                        editTextProductImage.setVisibility(getView().GONE);
                        editTextProductPrice.setText("");
                        imageViewUpload.setImageBitmap(null);
                    }
                } else {
                    utils.alert("Please fill up all fields.");
                }
                break;

            case R.id.buttonCancelNewProduct:
                animateFlashPulse(buttonCancel);
                editTextProductname.setText("");
                editTextProductImage.setText("");
                editTextProductImage.setVisibility(View.GONE);
                editTextProductPrice.setText("");
                imageViewUpload.setImageBitmap(null);
                break;
        }
    }

    private AlertDialog selectImage() {

        final CharSequence[] options = {"Camera", "Gallery", "Paste a URL", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Camera"))

                {

                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 1);


                } else if (options[item].equals("Gallery"))

                {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);


                } else if (options[item].equals("Paste a URL")) {
                    //Do something to paste a URL in an editText
                    editTextProductImage.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    editTextProductImage.requestFocus();

                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        return builder.create();

    }

    public boolean addNewProduct(String productName, Double productPrice, String productImage, String imageString) {

        Product product = new Product();
        product.productName = productName;
        product.productPrice = productPrice;
        product.productImgpath = productImage;
        product.productQty = 0;

        try {
            productsRetrotfitManager.addNewProduct(product, imageString);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void animateFlashPulse(final View view) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.Pulse).delay(50).playOn(view);
                YoYo.with(Techniques.Flash).delay(50).playOn(view);
            }
        });

    }

    Bitmap photo;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            try {
                Picasso.with(getActivity()).load(getImageUri(getActivity(), (Bitmap) data.getExtras().get("data"))).resize(400, 350).into(imageViewUpload);
            } catch (OutOfMemoryError e) {
                utils.alert("Image is too large.");
            }

        }
        if (requestCode == 2 && resultCode == getActivity().RESULT_OK) {

            Uri picUri = data.getData();
            if (picUri != null) {
                try {
                    Picasso.with(getActivity()).load(getImageUri(getActivity(), android.provider.MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri))).resize(400, 350).into(imageViewUpload);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (OutOfMemoryError e) {
                    Toast.makeText(getActivity(), "Image is too large. choose other", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(editTextProductImage.getText().toString())) {
            Picasso.with(getActivity()).load(editTextProductImage.getText().toString()).resize(300, 300).into(imageViewUpload);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public Uri getImageUri(Context context, Bitmap bitmapImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmapImage, "Title", null);
        String imageString = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
        return Uri.parse(path);
    }

    public String ImageString(Bitmap bitmapImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String imageString = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

        return imageString;
    }

    public static AddNewProductFragment newInstance() {
        return new AddNewProductFragment();
    }
}
