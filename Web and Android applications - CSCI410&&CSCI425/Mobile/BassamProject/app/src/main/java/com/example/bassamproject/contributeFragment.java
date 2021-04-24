package com.example.bassamproject;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.photoutil.CameraPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.POST;


/**
 * A simple {@link Fragment} subclass.
 */
public class contributeFragment extends Fragment {


    //Old code from the previous upload fragment, It's now just a webview

//    Spinner universityContributeSpinner;
//    universityItem selectedUniversityItem;
//    private universityAdapter universityContributeAdapter;
//    private ArrayList<universityItem> universityContributeList;
//
//    Spinner schoolContributeSpinner;
//    universityItem selectedSchoolItem;
//    private universityAdapter schoolContributeAdapter;
//    private ArrayList<universityItem> schoolContributeList = new ArrayList<>();
//
//
//    Spinner majorContributeSpinner;
//    universityItem selectedMajorItem;
//    private universityAdapter majorContributeAdapter;
//    private ArrayList<universityItem> majorContributeList = new ArrayList<>();
//    RequestQueue queue;
//
//    Boolean isYearSelected = true, isSemesterSelected = true, isNumberSelected = true,
//            isMajorSelected = true, isSchoolSelected = true, isUniversitySelected = true;

//    private String key = "BassamMobile";

    public contributeFragment() {
        // Required empty public constructor
    }


//    Button uploadButton;
//    CameraPhoto cameraPhoto;
//    final int CAMERA_REQUEST = 13323;
//    private static final int RESULT_OK = 1000;
//    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;


    @SuppressLint({"SetJavaScriptEnabled","WrongViewCast"})

    private SwipeRefreshLayout refreshLayout;

    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private WebView contributeView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_contribute, container, false);

        contributeView = rootView.findViewById(R.id.contributePage);

        WebSettings mWebSettings = contributeView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);


        contributeView.setWebViewClient(new MyBrowser() {
            public void onPageFinished(WebView view, String url)
            {
                //Adding the javascript needed to hide the elements I don't want to see
                contributeView.loadUrl("javascript:(function() { " +
                        "document.getElementById('contributeText').style.display=\"none\"; " +
                        "document.getElementById('uploadHeader').style.display=\"none\"; " +
                        "document.getElementById('navTabs').style.display=\"none\"; " +
                        "document.getElementById('headerDiv').style.display=\"none\"; " +
                        "document.getElementById('toDeleteBr').style.display=\"none\"; " +
                        "document.getElementById('toGiveMx').classList.add(\"mx-auto\"); " +
                        "document.getElementById('containerId').classList.remove(\"p-0\"); " +
                        "document.getElementById('containerId').classList.remove(\"container\"); " +
                        //Hiding the 000webhost banner at the bottom since it obstructs the upload button
                        "document.querySelector('[style=\"text-align: right;position: fixed;z-index:9999999;bottom: 0;width: auto;right: 1%;cursor: pointer;line-height: 0;display:block !important;\"]').style.display=\"none\";" +
                        "})()");
            }
        });
        contributeView.loadUrl("https://bassampreviouses.000webhostapp.com/index.php");

        //The below code is a snippet from Stackoverflow to allow the webview to actual access file
        contributeView.setWebChromeClient(new WebChromeClient() {

            protected void openFileChooser(ValueCallback uploadMsg, String acceptType)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }



            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try
                {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e)
                {
                    uploadMessage = null;
                    Toast.makeText(getActivity().getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        });



        //The below code is my older fragment's content, I commented it instead of deleting it
        // in case I wanted to remove the webview and actually implement the upload functionality
        //with a proper form



//
//        cameraPhoto = new CameraPhoto(getContext());
//        uploadButton = rootView.findViewById(R.id.btnUpload);
//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
//                    // Start the image capture intent to take photo
//                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//                }
//            }
//        });


//        initList();
//        Spinner spinYear = (Spinner)rootView.findViewById(R.id.yearContributeSpinner);
//
//        ArrayList<String> years = new ArrayList<String>();
//        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
//        years.add("Year:");
//        int j = 1;
//        for (int i = 1975; i <= thisYear; i++) {
//            years.add(Integer.toString(i));
//        }
//
//        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,years);
//        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinYear.setAdapter(yearAdapter);
//
//        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0)
//                {
//                    isYearSelected = false;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                isYearSelected = false;
//            }
//        });
//
//        final Spinner semesterSpinner = rootView.findViewById(R.id.semesterContributeSpinner);
//        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0)
//                {
//                    isSemesterSelected = false;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                isSemesterSelected = false;
//            }
//        });
//
//        final Spinner numberSpinner = rootView.findViewById(R.id.numberContributeSpinner);
//        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0)
//                {
//                    isNumberSelected = false;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                isNumberSelected = false;
//            }
//        });
//
//
//
//        queue = Volley.newRequestQueue(getContext());
//
//        universityContributeSpinner = rootView.findViewById(R.id.universityContributeSpinner);
//        universityContributeAdapter = new universityAdapter(getContext(), universityContributeList);
//        universityContributeSpinner.setAdapter(universityContributeAdapter);
//
//
//        selectedUniversityItem = (universityItem)universityContributeSpinner.getSelectedItem();
//
//        universityContributeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//
//                selectedUniversityItem = (universityItem)universityContributeSpinner.getSelectedItem();
//
//                if(!schoolContributeList.isEmpty())
//                {
//                    schoolContributeList.clear();
//                    schoolContributeSpinner.setAdapter(null);
//                    schoolContributeAdapter.notifyDataSetChanged();
//                }
//                if(position != 0) fillSchools();
//                else isUniversitySelected = false;
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        //*****************School Code**************************************************************
//
//        schoolContributeSpinner = rootView.findViewById(R.id.schoolContributeSpinner);
//        selectedSchoolItem = (universityItem)schoolContributeSpinner.getSelectedItem();
//
//        schoolContributeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                selectedSchoolItem = (universityItem)schoolContributeSpinner.getSelectedItem();
//
//                if(!majorContributeList.isEmpty())
//                {
//                    majorContributeList.clear();
//                    majorContributeSpinner.setAdapter(null);
//                    majorContributeAdapter.notifyDataSetChanged();
//                }
//                if(position != 0) fillMajors();
//                else isMajorSelected = false;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        //*****************Major Code**************************************************************
//        majorContributeSpinner = rootView.findViewById(R.id.majorContributeSpinner);
//        selectedMajorItem = (universityItem)majorContributeSpinner.getSelectedItem();
//
//        majorContributeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                if(position == 0 ) isMajorSelected = false;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        return rootView;
//    }
//
//
//    private void fillSchools()
//    {
//        String selectedUni  = selectedUniversityItem.getmUniversityValue();
//        String urlSchool = "https://bassampreviouses.000webhostapp.com/indexSchoolsAction.php?id="+ selectedUni;
//
//        JsonArrayRequest schoolRequest = new JsonArrayRequest(Request.Method.GET, urlSchool,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray ja) {
//                        try {
//                            for (int i = 0;i < ja.length();i++) {
//                                JSONObject school = ja.getJSONObject(i);
//
//                                String schoolId = school.getString("id");
//                                String schoolName = school.getString("name");
//                                universityItem schoolItem = new universityItem(schoolName, schoolId);
//
//                                schoolContributeList.add(schoolItem);
//                                schoolContributeAdapter =  new universityAdapter(getContext(), schoolContributeList);
//                                schoolContributeSpinner.setAdapter(schoolContributeAdapter);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                if(error instanceof TimeoutError)
//                    Toast.makeText(getContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
//                else if(error instanceof NoConnectionError)
//                    Toast.makeText(getContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
//                else if(error instanceof ServerError)
//                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
//                else if(error instanceof ParseError)
//                    Toast.makeText(getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//        queue.add(schoolRequest);
//    }
//
//    public void fillMajors()
//    {
//        String selectedSchool  = selectedSchoolItem.getmUniversityValue();
//        String majorUrl = "https://bassampreviouses.000webhostapp.com/indexMajorsAction.php?id="+ selectedSchool;
//        // Instantiate the RequestQueue.
//
//        JsonArrayRequest majorRequest = new JsonArrayRequest(Request.Method.GET, majorUrl,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray ja) {
//                        try {
//                            for (int i = 0;i < ja.length();i++) {
//                                JSONObject major = ja.getJSONObject(i);
//
//                                String majorId = major.getString("id");
//                                String majorName = major.getString("name");
//                                universityItem majorItem = new universityItem(majorName, majorId);
//
//                                majorContributeList.add(majorItem);
//                                majorContributeAdapter =  new universityAdapter(getContext(), majorContributeList);
//                                majorContributeSpinner.setAdapter(majorContributeAdapter);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                if(error instanceof TimeoutError)
//                    Toast.makeText(getContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
//                else if(error instanceof NoConnectionError)
//                    Toast.makeText(getContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
//                else if(error instanceof ServerError)
//                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
//                else if(error instanceof ParseError)
//                    Toast.makeText(getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//        queue.add(majorRequest);
//    }
//
//
//
//    private void initList()
//    {
//        universityContributeList = new ArrayList<>();
//        universityItem university1 = new universityItem("Select Univerity:", "");
//        universityItem university2 = new universityItem("American University of Beirut", "AUB");
//        universityItem university3 = new universityItem("Lebanese International University", "LIU");
//        universityItem university4 = new universityItem("Lebanese American University", "LAU");
//        universityItem university5 = new universityItem("American University of Science and Technology", "AUST");
//
//        universityContributeList.add(university1);
//        universityContributeList.add(university2);
//        universityContributeList.add(university3);
//        universityContributeList.add(university4);
//        universityContributeList.add(university5);
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(resultCode == RESULT_OK)
//        {
//            if(resultCode == CAMERA_REQUEST)
//            {
//                cameraPhoto.getPhotoPath();
//            }
//        }
//    }
        return rootView;
    }


    //More stackoverflow snippets, basically the whole upload functionality is made with snippets.

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            view.loadData("Maaf Internet Anda tidak stabil", "text/html", "utf-8");
            super.onReceivedError(view, request, error);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == REQUEST_SELECT_FILE)
            {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        }
        else if (requestCode == FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        else
            Toast.makeText(getActivity().getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }
}




