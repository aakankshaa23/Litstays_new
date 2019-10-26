package com.dextroxd.sellvehicle.my_account;


import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.sellvehicle.Login_and_signup.LoginActivity;
import com.dextroxd.sellvehicle.ProfileImageView;
import com.dextroxd.sellvehicle.activities.viewEditProfile;
import com.dextroxd.sellvehicle.R;
import com.dextroxd.sellvehicle.network.ApiInterface;
import com.dextroxd.sellvehicle.network.ApiUtils;
import com.dextroxd.sellvehicle.network.GetDetails.Response;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    ImageButton viewProfile;
    ImageView imageView;
    Button logout,addimage;
    String password;
    InputStream imageStream;
    TextView textView,textView1,textView2,textView3;
    private GoogleSignInClient mGoogleSignInClient;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ApiInterface mApiInterface;
    private String imageUrl;
    private GoogleApiClient mGoogleApiClient;
    private final static int OPEN_DOCUMENT_CODE = 5;
    Animation animFadein;
    View view;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_account, container, false);
        animFadein = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.fade_in);
        view.startAnimation(animFadein);
        mApiInterface = ApiUtils.getAPIService();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(view.getContext());
        addimage = view.findViewById(R.id.add_change_image);
        imageView = view.findViewById(R.id.profile_pic);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ordinary Intent for launching a new activity
                Intent intent = new Intent(getActivity(), ProfileImageView.class);
                intent.putExtra("imageUrl",imageUrl);
                // Get the transition name from the string
                String transitionName = getString(R.string.transition_string);

                // Define the view that the animation will start from
                View viewStart = view.findViewById(R.id.profile_pic);

                ActivityOptionsCompat options =

                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                viewStart,   // Starting view
                                transitionName    // The String
                        );
                //Start the Intent
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });
        preferences = getActivity().getApplicationContext().getSharedPreferences("Litstays",0);
        textView = view.findViewById(R.id.text_pic);
//        textView.setText(preferences.getString("Name","Name"));
        textView1 = view.findViewById(R.id.profile_email);
//        textView1.setText(preferences.getString("Email","abc@gmail.com"));
//        textView2 = view.findViewById(R.id.profile_number);
//        textView2.setText(preferences.getString("Phone","1234567890"));
        mApiInterface.getDetails(preferences.getString("idOfUser","hell")).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_SHORT).show();
                textView.setText(response.body().getName());
                textView1.setText(response.body().getEmail());
                password=response.body().getPassword();
///                textView2.setText(String.valueOf(response.body().getPhone()));
                if(response.body().getProfilePic()!=null){
                    Picasso.get().load("http://13.235.43.83:8000/uploads/"+response.body().getProfilePic()).centerCrop().fit().into(imageView);
                    imageUrl = "http://13.235.43.83:8000/uploads/"+response.body().getProfilePic();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
             // Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        viewProfile=view.findViewById(R.id.id_viewProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(view.getContext(), viewEditProfile.class);
                in.putExtra("password",password);
                startActivity(in);
            }
        });






        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasStoragePermission(2)){
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), OPEN_DOCUMENT_CODE);
                }

            }
        });
        logout=view.findViewById(R.id.logout_user);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(view.getContext(), LoginActivity.class);
                                startActivity(i);
                                getActivity().finish();
            }
        });
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                        new ResultCallback<Status>() {
//                            @Override
//                            public void onResult(Status status) {
//                                // ...
//                                Toast.makeText(view.getContext(),"Logged Out",Toast.LENGTH_SHORT).show();
//                                Intent i=new Intent(view.getContext(), LoginActivity.class);
//                                startActivity(i);
//                                getActivity().finish();
//
//                            }
//                        });
//            }
//
//
//        });


        // Inflate the layout for this fragment
        return view;
    }
//    @Override
//    public void onStart() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        mGoogleApiClient.connect();
//        super.onStart();
//    }
    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_DOCUMENT_CODE && resultCode == RESULT_OK) {

            if (data != null) {
                final Uri imageUri = data.getData();
                imageStream = null;
                try {
                    imageStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                Uri uri = data.getData();
                File file = new File(getImagePathFromUri(uri));
                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file);
                final MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), surveyBody);
                mApiInterface.addImage(preferences.getString("auth_Token","hell"),image).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Picasso.get().load("http://13.235.43.83:8000/uploads/"+response.body().getProfilePic()).centerCrop().fit().into(imageView);
                        imageUrl = "http://13.235.43.83:8000/uploads/"+response.body().getProfilePic();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
//                        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });






            }
        }
    }
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

}
