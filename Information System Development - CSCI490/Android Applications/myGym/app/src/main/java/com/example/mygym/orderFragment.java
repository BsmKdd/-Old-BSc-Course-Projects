package com.example.mygym;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class orderFragment extends Fragment implements orderConfirm.confirmDialogListener
{
    private RecyclerView mealRecyclerView;
    private RecyclerView saladRecyclerView;
    private RecyclerView drinkRecyclerView;
    private RecyclerView barRecyclerView;

    private ProgressBar menuItemProgressBar;

    private menuItemAdapter MIBarAdapter;
    private menuItemAdapter MIMealAdapter;
    private menuItemAdapter MISaladAdapter;
    private menuItemAdapter MIDrinkAdapter;

    private ArrayList<menuItem> menuBarItems;
    private ArrayList<menuItem> menuMealItems;
    private ArrayList<menuItem> menuSaladItems;
    private ArrayList<menuItem> menuDrinkItems;

    private ArrayList<menuItem> menuSelectedItems;
    private ArrayList<Integer> selectedIDs;

    RecyclerView.LayoutManager barLayoutManager;
    RecyclerView.LayoutManager mealLayoutManager;
    RecyclerView.LayoutManager saladLayoutManager;
    RecyclerView.LayoutManager drinkLayoutManager;

    private String mealName = "";
    private String saladName = "";
    private String drinkName = "";
    private String barName = "";

    private double mealPrice = 0.0;
    private double saladPrice = 0.0;
    private double drinkPrice = 0.0;
    private String barPrice = "";
    private BigDecimal totalPrice = new BigDecimal("0.0");

    private BigDecimal totalCalories = new BigDecimal("0.0");
    private BigDecimal totalSugar = new BigDecimal("0.0");
    private BigDecimal totalProtein = new BigDecimal("0.0");
    private BigDecimal totalFats = new BigDecimal("0.0");
    private BigDecimal totalCarbohydrates = new BigDecimal("0.0");

    int preparationTime = 0;

    private Button viewOrder;
    private Button clearOrder;

    private EditText searchMenuET;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestoreDB;
    FirebaseUser currentUser;
    private String currentUid;
    String memberFName = "";
    String memberID = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_fragment, container, false);

        mealRecyclerView = rootView.findViewById(R.id.menuMealItemRecyclerView);
        saladRecyclerView = rootView.findViewById(R.id.menuSaladItemRecyclerView);
        drinkRecyclerView = rootView.findViewById(R.id.menuDrinkItemRecyclerView);
        barRecyclerView = rootView.findViewById(R.id.menuBarItemRecyclerView);
        selectedIDs = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUid = currentUser.getUid();

        viewOrder = rootView.findViewById(R.id.viewOrderButton);
        clearOrder = rootView.findViewById(R.id.clearOrderButton);
        
        searchMenuET = rootView.findViewById(R.id.searchMenuET);
        searchMenuET.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                filter(s.toString());
            }
        });

        menuItemProgressBar = rootView.findViewById(R.id.menuItemProgressBar);

        menuBarItems = new ArrayList<>();
        menuSaladItems = new ArrayList<>();
        menuMealItems = new ArrayList<>();
        menuDrinkItems = new ArrayList<>();
        menuSelectedItems = new ArrayList<>();

        barLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mealLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        saladLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        drinkLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mealRecyclerView.setHasFixedSize(true);
        saladRecyclerView.setHasFixedSize(true);
        drinkRecyclerView.setHasFixedSize(true);
        barRecyclerView.setHasFixedSize(true);
        if(getActivity() != null)
        {
            mealRecyclerView.setLayoutManager(mealLayoutManager);
            saladRecyclerView.setLayoutManager(saladLayoutManager);
            drinkRecyclerView.setLayoutManager(drinkLayoutManager);
            barRecyclerView.setLayoutManager(barLayoutManager);

        }

        populate();


        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmDialogue();
            }
        });

        clearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearOrder();

            }
        });

        return rootView;
    }

    public void populate()
    {

        String url = "https://gym-senior-2020.000webhostapp.com/Mobile%20Actions/returnMenuItemsAction.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {
                        menuItemProgressBar.setVisibility(View.GONE);
                        menuBarItems.clear();
                        try {
                            if(ja.length() > 0) {
                                for (int i = 0; i < ja.length(); i++) {
                                    Log.i("crap", ja.toString());
                                    JSONObject menuItem = ja.getJSONObject(i);

                                    int id = Integer.parseInt(menuItem.getString("id"));
                                    String itemName = menuItem.getString("itemName");
                                    String itemImg = menuItem.getString("itemImg");
                                    String itemDescription = menuItem.getString("itemDescription");
                                    String itemType = menuItem.getString("type");

                                    int calories = Integer.parseInt(menuItem.getString("calories"));
                                    int fats = Integer.parseInt(menuItem.getString("fats"));
                                    int carbohydrates = Integer.parseInt(menuItem.getString("carbohydrates"));
                                    int protein = Integer.parseInt(menuItem.getString("protein"));
                                    int sugar = Integer.parseInt(menuItem.getString("sugar"));
                                    double itemPrice = Double.parseDouble(menuItem.getString("itemPrice"));
                                    int preparation = Integer.parseInt(menuItem.getString("preparation"));
                                    int available = Integer.parseInt(menuItem.getString("available"));

                                    String itemImgURL = "https://gym-senior-2020.000webhostapp.com/menuItem%20Images/" + itemImg;

                                    menuItem item = new menuItem(id, itemName, itemDescription, itemType,
                                            itemImgURL, fats, protein, carbohydrates, sugar, itemPrice, calories, preparation,
                                            available);

                                    if(itemType.equalsIgnoreCase("bar")) menuBarItems.add(item);
                                    else if(itemType.equalsIgnoreCase("meal")) menuMealItems.add(item);
                                    else if(itemType.equalsIgnoreCase("salad")) menuSaladItems.add(item);
                                    else if(itemType.equalsIgnoreCase("drink")) menuDrinkItems.add(item);
                                    else Log.i("itemtype", "Unidentified item type.");

                                }
                                MIMealAdapter = new menuItemAdapter(menuMealItems, getContext());
                                mealRecyclerView.setAdapter(MIMealAdapter);
                                MIMealAdapter.notifyDataSetChanged();

                                MISaladAdapter = new menuItemAdapter(menuSaladItems, getContext());
                                saladRecyclerView.setAdapter(MISaladAdapter);
                                MISaladAdapter.notifyDataSetChanged();

                                MIDrinkAdapter = new menuItemAdapter(menuDrinkItems, getContext());
                                drinkRecyclerView.setAdapter(MIDrinkAdapter);
                                MIDrinkAdapter.notifyDataSetChanged();

                                MIBarAdapter = new menuItemAdapter(menuBarItems, getContext());
                                barRecyclerView.setAdapter(MIBarAdapter);
                                MIBarAdapter.notifyDataSetChanged();
//                                viewOrder.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(getContext() != null)
                {
                    if (error instanceof TimeoutError)
                        Toast.makeText(getContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof NoConnectionError)
                        Toast.makeText(getContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ServerError)
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    else if (error instanceof ParseError) {
                        Toast.makeText(getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    menuItemProgressBar.setVisibility(View.GONE);

                }

            }
        });

        requestQueue.add(jsonRequest);

    }


    private void openConfirmDialogue()
    {
        BigDecimal mealBig = new BigDecimal("0.0");
        BigDecimal saladBig = new BigDecimal("0.0");
        BigDecimal drinkBig = new BigDecimal("0.0");
        BigDecimal barBig = new BigDecimal("0.0");

        BigDecimal mealBigCalories = new BigDecimal("0.0");
        BigDecimal saladBigCalories = new BigDecimal("0.0");
        BigDecimal drinkBigCalories = new BigDecimal("0.0");
        BigDecimal barBigCalories = new BigDecimal("0.0");
        totalCalories = new BigDecimal("0.0");

        BigDecimal mealBigProtein = new BigDecimal("0.0");
        BigDecimal saladBigProtein = new BigDecimal("0.0");
        BigDecimal drinkBigProtein = new BigDecimal("0.0");
        BigDecimal barBigProtein = new BigDecimal("0.0");
        totalProtein = new BigDecimal("0.0");

        BigDecimal mealBigSugar = new BigDecimal("0.0");
        BigDecimal saladBigSugar = new BigDecimal("0.0");
        BigDecimal drinkBigSugar = new BigDecimal("0.0");
        BigDecimal barBigSugar = new BigDecimal("0.0");
        totalSugar = new BigDecimal("0.0");

        BigDecimal mealBigFats = new BigDecimal("0.0");
        BigDecimal saladBigFats = new BigDecimal("0.0");
        BigDecimal drinkBigFats= new BigDecimal("0.0");
        BigDecimal barBigFats= new BigDecimal("0.0");
        totalFats = new BigDecimal("0.0");

        BigDecimal mealBigCarbohydrates = new BigDecimal("0.0");
        BigDecimal saladBigCarbohydrates = new BigDecimal("0.0");
        BigDecimal drinkBigCarbohydrates = new BigDecimal("0.0");
        BigDecimal barBigCarbohydrates = new BigDecimal("0.0");
        totalCarbohydrates = new BigDecimal("0.0");

        totalPrice = new BigDecimal("0.0");

        preparationTime = 0;
        for(int i = 0; i < menuMealItems.size(); i++)
        {
            if(menuMealItems.get(i).getSelected() > 0)
            {
                mealName = menuMealItems.get(i).getMenuItemName();
                mealPrice = menuMealItems.get(i).getMenuItemPrice();
                mealBig = new BigDecimal(Double.toString(mealPrice));
                selectedIDs.add(menuMealItems.get(i).getMenuItemId());
                mealBigCalories = new BigDecimal(Integer.toString(menuMealItems.get(i).getMenuItemCalories()));
                mealBigProtein = new BigDecimal(Integer.toString(menuMealItems.get(i).getMenuItemProtein()));
                mealBigSugar = new BigDecimal(Integer.toString(menuMealItems.get(i).getMenuItemSugar()));
                mealBigFats = new BigDecimal(Integer.toString(menuMealItems.get(i).getMenuItemFats()));
                mealBigCarbohydrates = new BigDecimal(Integer.toString(menuMealItems.get(i).getMenuItemCarbohydrates()));
                preparationTime += menuMealItems.get(i).getMenuPreparation();
                menuSelectedItems.add(menuMealItems.get(i));
                break;
            }
        }

        for(int i = 0; i < menuSaladItems.size(); i++)
        {
            if(menuSaladItems.get(i).getSelected() > 0)
            {
                saladName = menuSaladItems.get(i).getMenuItemName();
                saladPrice = menuSaladItems.get(i).getMenuItemPrice();
                saladBig = new BigDecimal(Double.toString(saladPrice));
                selectedIDs.add(menuSaladItems.get(i).getMenuItemId());
                saladBigCalories = new BigDecimal(Integer.toString(menuSaladItems.get(i).getMenuItemCalories()));
                saladBigProtein = new BigDecimal(Integer.toString(menuSaladItems.get(i).getMenuItemProtein()));
                saladBigSugar = new BigDecimal(Integer.toString(menuSaladItems.get(i).getMenuItemSugar()));
                saladBigFats = new BigDecimal(Integer.toString(menuSaladItems.get(i).getMenuItemFats()));
                saladBigCarbohydrates = new BigDecimal(Integer.toString(menuSaladItems.get(i).getMenuItemCarbohydrates()));
                preparationTime += menuSaladItems.get(i).getMenuPreparation();
                menuSelectedItems.add(menuSaladItems.get(i));
                break;
            }
        }

        for(int i = 0; i < menuDrinkItems.size(); i++)
        {
            if(menuDrinkItems.get(i).getSelected() > 0)
            {
                drinkName = menuDrinkItems.get(i).getMenuItemName();
                drinkPrice = menuDrinkItems.get(i).getMenuItemPrice();
                drinkBig = new BigDecimal(Double.toString(drinkPrice));
                selectedIDs.add(menuDrinkItems.get(i).getMenuItemId());
                drinkBigCalories = new BigDecimal(Integer.toString(menuDrinkItems.get(i).getMenuItemCalories()));
                drinkBigProtein = new BigDecimal(Integer.toString(menuDrinkItems.get(i).getMenuItemProtein()));
                drinkBigSugar = new BigDecimal(Integer.toString(menuDrinkItems.get(i).getMenuItemSugar()));
                drinkBigFats = new BigDecimal(Integer.toString(menuDrinkItems.get(i).getMenuItemFats()));
                drinkBigCarbohydrates = new BigDecimal(Integer.toString(menuDrinkItems.get(i).getMenuItemCarbohydrates()));
                preparationTime += menuDrinkItems.get(i).getMenuPreparation();
                menuSelectedItems.add(menuDrinkItems.get(i));
                break;
            }
        }

        barPrice = "";
        barName = "";
        for(int i = 0; i < menuBarItems.size(); i++)
        {
            if(menuBarItems.get(i).getSelected() > 0)
            {
                barName += menuBarItems.get(i).getSelected() + "x " +  menuBarItems.get(i).getMenuItemName() + "\n";
                BigDecimal tempBig1 = new BigDecimal(Double.toString(menuBarItems.get(i).getMenuItemPrice()));
                BigDecimal intBig = new BigDecimal(menuBarItems.get(i).getSelected());
                barBig = barBig.add(intBig.multiply(tempBig1));
                selectedIDs.add(menuBarItems.get(i).getMenuItemId());
                BigDecimal tempCalories = new BigDecimal(Integer.toString(menuBarItems.get(i).getMenuItemCalories()));
                BigDecimal tempProtein = new BigDecimal(Integer.toString(menuBarItems.get(i).getMenuItemProtein()));
                BigDecimal tempSugar = new BigDecimal(Integer.toString(menuBarItems.get(i).getMenuItemSugar()));
                BigDecimal tempFats = new BigDecimal(Integer.toString(menuBarItems.get(i).getMenuItemFats()));
                BigDecimal tempCarbohydrates = new BigDecimal(Integer.toString(menuBarItems.get(i).getMenuItemCarbohydrates()));

                barBigCalories = barBigCalories.add(intBig.multiply(tempCalories));
                barBigProtein = barBigProtein.add(intBig.multiply(tempProtein));
                barBigSugar = barBigSugar.add(intBig.multiply(tempSugar));
                barBigFats = barBigFats.add(intBig.multiply(tempFats));
                barBigCarbohydrates = barBigCarbohydrates.add(intBig.multiply(tempCarbohydrates));

                menuSelectedItems.add(menuBarItems.get(i));
                preparationTime += menuBarItems.get(i).getMenuPreparation();
                barPrice += (intBig.multiply(tempBig1)).toString() + "$\n";
            }
        }

        Log.i("bigDecimal", totalPrice.toString());

        totalPrice =  new BigDecimal((mealBig.add(saladBig.add(drinkBig.add(barBig)))).toString());

        totalCalories =  new BigDecimal((mealBigCalories.add(saladBigCalories.add(drinkBigCalories.add(barBigCalories)))).toString());
        totalProtein =  new BigDecimal((mealBigProtein.add(saladBigProtein.add(drinkBigProtein.add(barBigProtein)))).toString());
        totalSugar =  new BigDecimal((mealBigSugar.add(saladBigSugar.add(drinkBigSugar.add(barBigSugar)))).toString());
        totalFats =  new BigDecimal((mealBigFats.add(saladBigFats.add(drinkBigFats.add(barBigFats)))).toString());
        totalCarbohydrates =  new BigDecimal((mealBigCarbohydrates.add(saladBigCarbohydrates.add(drinkBigCarbohydrates.add(barBigCarbohydrates)))).toString());

        Bundle bundle = new Bundle();

        bundle.putString("mealName", mealName);
        bundle.putDouble("mealPrice", mealPrice);

        bundle.putString("saladName", saladName);
        bundle.putDouble("saladPrice", saladPrice);

        bundle.putString("drinkName", drinkName);
        bundle.putDouble("drinkPrice", drinkPrice);

        bundle.putString("barName", barName);
        bundle.putString("barPrice", barPrice);

        bundle.putString("totalPrice", totalPrice.toString());
        bundle.putString("totalCalories", totalCalories.toString());
        bundle.putString("totalProtein", totalProtein.toString());
        bundle.putString("totalSugar", totalSugar.toString());
        bundle.putString("totalFats", totalFats.toString());
        bundle.putString("totalCarbohydrates", totalCarbohydrates.toString());
        bundle.putIntegerArrayList("selectedIDs", selectedIDs);
        bundle.putInt("prepTime", preparationTime);

        orderConfirm confirmDialogue = new orderConfirm();
        confirmDialogue.setArguments(bundle);
        confirmDialogue.setTargetFragment(orderFragment.this, 1);
        confirmDialogue.show(getFragmentManager(), "confirmOrder Dialogue");
    }


    private void filter(String toString)
    {
        String filterText = toString;
        ArrayList<menuItem> filterMeal = new ArrayList<>();
        ArrayList<menuItem> filteredSalad = new ArrayList<>();
        ArrayList<menuItem> filterDrink = new ArrayList<>();
        ArrayList<menuItem> filterBar = new ArrayList<>();

        String[] Split = filterText.split(" ");
        for(menuItem item: menuMealItems)
        {
            int match = 0;
            for(int i = 0; i < Split.length; i++)
            {
                if(item.getMenuItemName().toLowerCase().contains(Split[i].toLowerCase()))
                {
                    match++;
                }
            }
            if(match == Split.length) filterMeal.add(item);
        }

        for(menuItem item: menuSaladItems)
        {
            int match = 0;
            for(int i = 0; i < Split.length; i++)
            {
                if(item.getMenuItemName().toLowerCase().contains(Split[i].toLowerCase()))
                {
                    match++;
                }
            }
            if(match == Split.length) filteredSalad.add(item);
        }

        for(menuItem item: menuDrinkItems)
        {
            int match = 0;
            for(int i = 0; i < Split.length; i++)
            {
                if(item.getMenuItemName().toLowerCase().contains(Split[i].toLowerCase()))
                {
                    match++;
                }
            }
            if(match == Split.length) filterDrink.add(item);
        }

        for(menuItem item: menuBarItems)
        {
            int match = 0;
            for(int i = 0; i < Split.length; i++)
            {
                if(item.getMenuItemName().toLowerCase().contains(Split[i].toLowerCase()))
                {
                    match++;
                }
            }
            if(match == Split.length) filterBar.add(item);
        }

        MIMealAdapter.filterList(filterMeal);
        MISaladAdapter.filterList(filteredSalad);
        MIDrinkAdapter.filterList(filterDrink);
        MIBarAdapter.filterList(filterBar);

    }

    @Override
    public void orderConfirmed() {

        MainActivity activity = (MainActivity) getActivity();
        memberID = String.valueOf(activity.getMemberID());
        memberFName = activity.getMemberFName();

        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("ByMember", memberFName + "#" + memberID);
        orderDetails.put("ByUID", currentUid);

        int barCount = 1;
        for(int i = 0; i < menuSelectedItems.size(); i++)
        {
            menuItem mItem = menuSelectedItems.get(i);
            if(mItem.getMenuItemType().equalsIgnoreCase("meal") ||
                mItem.getMenuItemType().equalsIgnoreCase("salad") ||
                mItem.getMenuItemType().equalsIgnoreCase("drink"))

                orderDetails.put(mItem.getMenuItemType() + " Item", mItem.getMenuItemName() + '#' + mItem.getSelected() + '$' + mItem.getMenuItemPrice());
            else
            {
                BigDecimal barPriceBig = new BigDecimal(Double.toString(mItem.getMenuItemPrice()));
                BigDecimal barSelectedBig = new BigDecimal(Integer.toString(mItem.getSelected()));
                BigDecimal barTotalbig = barSelectedBig.multiply(barPriceBig);
                orderDetails.put("bar Item " + barCount, mItem.getMenuItemName() + '#' + mItem.getSelected() + '$' + barTotalbig);
                barCount++;
            }
        }

        orderDetails.put("Total Price", totalPrice.toString());
        orderDetails.put("Preparation Time", preparationTime);

        firestoreDB.collection("orders").add(orderDetails)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                //Sending order to mySQL *****************************************************

                MainActivity activity = (MainActivity) getActivity();
                memberID = String.valueOf(activity.getMemberID());
                final JSONArray jsonSelect = new JSONArray();
                jsonSelect.put(documentReference.getId());
                jsonSelect.put(memberID);
                jsonSelect.put(totalPrice.toString());
                for(int i = 0; i < menuSelectedItems.size(); i++)
                    for(int j = 0; j < menuSelectedItems.get(i).getSelected(); j++)
                        jsonSelect.put(menuSelectedItems.get(i).getMenuItemId());

                clearOrder();

                Log.i("jsonSelect", jsonSelect.toString());

                String url = "https://gym-senior-2020.000webhostapp.com/Mobile%20Actions/addOrderAction.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response)
                            {
                                Log.i("response" , response);
                            }
                        }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if(getContext() != null)
                        {
                            if (error instanceof TimeoutError)
                                Toast.makeText(getContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof NoConnectionError)
                                Toast.makeText(getContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof ServerError)
                                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof ParseError)
                                Toast.makeText(getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("order", jsonSelect.toString());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);



                menuSelectedItems.clear();
                final Snackbar snackBar = Snackbar.make(getView(), "Order Sent", Snackbar.LENGTH_SHORT);
                snackBar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call your action method here
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                menuSelectedItems.clear();
                final Snackbar snackBar = Snackbar.make(getView(), "An error occurred while sending the notification", Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call your action method here
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
                Log.i("order", e.getMessage());
            }
        });
        
    }

    public void clearOrder()
    {
        mealName = "";
        saladName = "";
        drinkName = "";
        barName = "";

        mealPrice = 0.0;
        saladPrice = 0.0;
        drinkPrice = 0.0;
        barPrice = "0.0";

        totalPrice = new BigDecimal("0.0");
        preparationTime = 0;

        for(int i = 0; i < menuMealItems.size(); i++)
        {
            menuMealItems.get(i).setSelected(0);
        }

        for(int i = 0; i < menuSaladItems.size(); i++)
        {
            menuSaladItems.get(i).setSelected(0);
        }

        for(int i = 0; i < menuDrinkItems.size(); i++)
        {
            menuDrinkItems.get(i).setSelected(0);
        }

        for(int i = 0; i < menuBarItems.size(); i++)
        {
            menuBarItems.get(i).setSelected(0);
        }

        MIMealAdapter.notifyDataSetChanged();
        MISaladAdapter.notifyDataSetChanged();
        MIDrinkAdapter.notifyDataSetChanged();
        MIBarAdapter.notifyDataSetChanged();
    }
}

