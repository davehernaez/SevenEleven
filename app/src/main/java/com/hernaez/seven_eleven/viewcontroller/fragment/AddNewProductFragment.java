package com.hernaez.seven_eleven.viewcontroller.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.businesslayer.ProductsRetrotfitManager;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.viewcontroller.activity.MainActivity;

import java.io.File;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 8/11/2015.
 */
public class AddNewProductFragment extends BaseFragment implements View.OnClickListener {
    @Inject
    AndroidUtils utils;
    @Inject
    ProductsRetrotfitManager productsRetrotfitManager;

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonAddImage:
                animateFlashPulse(buttonAddImage);
                selectImage();

                break;

            case R.id.buttonDoneAddNewProduct:
                animateFlashPulse(buttonDone);
                if (!TextUtils.isEmpty(editTextProductname.getText()) && !TextUtils.isEmpty(editTextProductPrice.getText()) && !TextUtils.isEmpty(editTextProductImage.getText())) {
                    if (addNewProduct(editTextProductname.getText().toString(),
                            Double.parseDouble(editTextProductPrice.getText().toString()),
                            editTextProductImage.getText().toString())) {
                        editTextProductname.setText("");
                        editTextProductImage.setText("");
                        editTextProductImage.setVisibility(getView().GONE);
                        editTextProductPrice.setText("");
                    }
                } else {
                    utils.alert("Please fill up all fields.");
                }
                break;

            case R.id.buttonCancelNewProduct:
                animateFlashPulse(buttonCancel);
                editTextProductname.setText("");
                editTextProductImage.setText("");
                editTextProductImage.setVisibility(getView().GONE);
                editTextProductPrice.setText("");
                break;
        }
    }

    private void selectImage() {

        final CharSequence[] options = {"Camera", "Gallery", "Paste a URL", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Camera"))

                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File file = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

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

        builder.show();

    }

    public boolean addNewProduct(String productName, Double productPrice, String productImage) {

        Product product = new Product();
        product.productName = productName;
        product.productPrice = productPrice;
        product.productImgpath = productImage;
        product.productQty = 0;

        try {
            productsRetrotfitManager.addNewProduct(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void animateFlashPulse(View view) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        YoYo.with(Techniques.Pulse).delay(100).playOn(view);
        YoYo.with(Techniques.Flash).delay(200).playOn(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);
    }

    public static AddNewProductFragment newInstance() {
        return new AddNewProductFragment();
    }

}
