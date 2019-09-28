package com.dextroxd.sellvehicle.Sell;


import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.PostProperty.model.Response_Submit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellFragment extends Fragment {
    EditText bedroooms,rent,type,bathrooms,area,floors,facing,title,description;
    private ApiInterface mApiInterface;
    String final_furnishing;
    String  final_rent,final_bedroom;
    boolean bachelorsallowed=false,parking=false;
    Button submit_button;
    Animation animFadein;
    int codeofbutton = 0;
    int furnished = 0;
    SharedPreferences preferences;
    Button pickImages;
    RadioGroup radioGroup,radioGroup1,furnishing;
    RadioButton radioButton,radioButton1,radioButtonParkingY,radioButtonParkingN;
    private final static int OPEN_DOCUMENT_CODE = 5;
    ArrayList<Uri> imagesUriArrayList;
    public SellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_sell, container, false);
        animFadein = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.fade_in);
        view.startAnimation(animFadein);
        mApiInterface = ApiUtils.getAPIService();
        imagesUriArrayList = new ArrayList<>();
        preferences = getActivity().getApplicationContext().getSharedPreferences("Litstays",0);
        title=(EditText)view.findViewById(R.id.id_Ad);
        final TextView textView_ad=(TextView)view.findViewById(R.id.id_count_ad);
        pickImages = view.findViewById(R.id.id_pickImages);
        bedroooms=(EditText)view.findViewById(R.id.id_bedrooms);
        furnishing=view.findViewById(R.id.radio_furnished);
        rent=(EditText)view.findViewById(R.id.id_maintenance);
        submit_button=(Button)view.findViewById(R.id.id_submit);
        description = view.findViewById(R.id.id_describe);
        type = view.findViewById(R.id.id_type);
        bathrooms = view.findViewById(R.id.id_bathrooms);
        area = view.findViewById(R.id.id_BuiltUp);
        floors = view.findViewById(R.id.id_total_floors);
//        parking = view.findViewById(R.id.id_parking);
        facing = view.findViewById(R.id.id_facing);
        radioGroup = view.findViewById(R.id.radio_group);
        radioGroup1 = view.findViewById(R.id.radio_group1);
        radioButtonParkingY = view.findViewById(R.id.radio_buttonY);
        radioButtonParkingN = view.findViewById(R.id.radio_buttonN);
        radioButton = view.findViewById(R.id.radio_yes);
        radioButton1 = view.findViewById(R.id.radio_no);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId ==R.id.radio_buttonY){
                    parking = true;
                }
                if (checkedId == R.id.radio_buttonN){
                    parking = false;
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId ==R.id.radio_yes){
                    bachelorsallowed = true;
                }
                if (checkedId == R.id.radio_no){
                    bachelorsallowed = false;
                }
            }
        });
        furnishing.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.notfur){
                    furnished = 0;
                }
                else if (checkedId == R.id.semifur){
                    furnished = 1;
                }
                else if (checkedId == R.id.fullyfur){
                    furnished = 2;
                }
            }
        });

        pickImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasStoragePermission(2))
                imagesUriArrayList.clear();
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), OPEN_DOCUMENT_CODE);
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEdittexts();
                MultipartBody.Part[] ImagesArr = new MultipartBody.Part[5];

                for (int index = 0; index < imagesUriArrayList.size(); index++) {
//                    Log.d(TAG, "requestUploadSurvey: survey image " + index + "  " + surveyModel.getPicturesList().get(index).getImagePath());
//                    File file = new File(getPath(getActivity(),imagesUriArrayList.get(index)));
                    File file = new File(getImagePathFromUri(imagesUriArrayList.get(index)));

//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        Uri uri = imagesUriArrayList.get(index);
//                        file = new File(uri.getPath());//create path from uri
////                        final String[] split = file.getPath().split(":");//split the path.
////                        filePath = split[1];
//                    }
//                    else{
//                       file = new File(getPath(getActivity(),imagesUriArrayList.get(index)));
//
//                    }


//                    Log.e("FileSizzer",String.valueOf(file.length()/1024));
//                    try {
//                        file = getBitmapFromPath(getPath(getActivity(),imagesUriArrayList.get(index)));
//                        Log.e("FileSizzer2",String.valueOf(file.length()/1024));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
                    RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                    ImagesArr[index] = MultipartBody.Part.createFormData("image", file.getName(), surveyBody);
                }
                mApiInterface.submitProperty(preferences.getString("auth_Token","hell"),title.getText().toString().trim(),description.getText().toString().trim()
                ,Integer.parseInt(floors.getText().toString().trim()),parking,facing.getText().toString().trim(),ImagesArr,type.getText().toString().trim(),Integer.parseInt(bedroooms.getText().toString().trim()),Integer.parseInt(bathrooms.getText().toString().trim()),furnished,
                        bachelorsallowed,Integer.parseInt(area.getText().toString().trim()),Integer.parseInt(rent.getText().toString().trim()),2000).enqueue(new Callback<Response_Submit>() {
                    @Override
                    public void onResponse(Call<Response_Submit> call, Response<Response_Submit> response) {
                        if(response.code()==200){
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response_Submit> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=title.getText().toString();
                int symbol=text.length();
                textView_ad.setText(""+symbol+"/400");
            }
            @Override
            public void afterTextChanged(Editable s) {
            }}
        );



        // Inflate the layout for this fragment
        return view;


    }
    private void checkEdittexts(){
        if(TextUtils.isEmpty(bedroooms.getText().toString().trim())||rent.getText().toString().trim().isEmpty()||type.getText().toString().trim().isEmpty()||bathrooms.getText().toString().trim().isEmpty()||area.getText().toString().trim().isEmpty()||floors.getText().toString().trim().isEmpty()||facing.getText().toString().trim().isEmpty()||title.getText().toString().trim().isEmpty()||description.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
            return;
        }

    }
    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
//        Cursor cursor = getContentResolver().query(selectedImage,
//                filePathColumn, null, null, null);
//        cursor.moveToFirst();
//
//        int columnIndex = cursor.getColumnIndex(proj[0]);
//        String picturePath = cursor.getString(columnIndex);
//        cursor.close();
//        return picturePath;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_DOCUMENT_CODE && resultCode == RESULT_OK) {

            if (data != null) {
                if(data.getClipData()==null){
                    imagesUriArrayList.add(data.getData());
                    if(imagesUriArrayList.size()>0)
                        pickImages.setText("Change Images");
                    if(imagesUriArrayList.size()==0)
                        pickImages.setText("Pick Image");
                }

                else{

                    Log.e("++data", "" + data.getClipData().getItemCount());// Get count of image here.

                    Log.e("++count", "" + data.getClipData().getItemCount());

                    if (Objects.requireNonNull(data.getClipData()).getItemCount() > 5) {
                        Snackbar snackbar = Snackbar
                                .make(getActivity().findViewById(android.R.id.content), "You can not select more than 5 images", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
                                    }
                                });
                        snackbar.setActionTextColor(Color.BLUE);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.rgb(132,255,255));
                        snackbar.show();

                    } else {
                        imagesUriArrayList.clear();

                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                            imagesUriArrayList.add(data.getClipData().getItemAt(i).getUri());
                        }
//                    Log.e("SIZE", imagesUriArrayList.size() + "");
                        if(imagesUriArrayList.size()>0)
                            pickImages.setText("Change Images");
                        if(imagesUriArrayList.size()==0)
                            pickImages.setText("Pick Image");
                        Toast.makeText(getActivity(),"You have selected "+imagesUriArrayList.get(0)+" images",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("permission",String.valueOf(grantResults[0]));

        }
    }
    private boolean hasStoragePermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }



//    public File getBitmapFromPath(String filePath) throws IOException {
//        File imageFile = new File(filePath);
//        OutputStream fout = new FileOutputStream(imageFile);
//        Bitmap bitmap= BitmapFactory.decodeFile(filePath);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fout);
//        fout.flush();
//        fout.close();
//
//        return imageFile;
//    }
@Nullable
public static String getImagePathFromUri(@Nullable Uri aUri) {
    String imagePath = null;
    if (aUri == null) {
        return imagePath;
    }
    if (DocumentsContract.isDocumentUri(getApplicationContext(), aUri)) {
        String documentId = DocumentsContract.getDocumentId(aUri);
        if ("com.android.providers.media.documents".equals(aUri.getAuthority())) {
            String id = documentId.split(":")[1];
            String selection = MediaStore.Images.Media._ID + "=" + id;
            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
        } else if ("com.android.providers.downloads.documents".equals(aUri.getAuthority())) {
            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                    Long.valueOf(documentId));
            imagePath = getImagePath(contentUri, null);
        }
    } else if ("content".equalsIgnoreCase(aUri.getScheme())) {
        imagePath = getImagePath(aUri, null);
    } else if ("file".equalsIgnoreCase(aUri.getScheme())) {
        imagePath = aUri.getPath();
    }
    return imagePath;
}

    private static String getImagePath(Uri aUri, String aSelection) {
        String path = null;
        Cursor cursor = getApplicationContext()
                .getContentResolver()
                .query(aUri, null, aSelection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}

