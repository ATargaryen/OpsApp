package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dispatch_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dispatch_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Context mContext;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listView;
    TextView textView;
    String[] listItem;
    String role ;

    public Dispatch_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dispatch_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Dispatch_fragment newInstance(String param1, String param2) {
        Dispatch_fragment fragment = new Dispatch_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_dispatch, container, false);

        // intialize the var
        listView=(ListView) view.findViewById(R.id.listView);
        textView=(TextView) view.findViewById(R.id.tesstview);
     //  listItem = getResources().getStringArray(R.array.Company);
        listItem = getArguments().getStringArray("challanlist"); // retrive data from activity bundle





        // set adapter : which is used to set data to UI
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.listlayout, R.id.tesstview, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value=adapter.getItem(position);
                // go to challan activity

                role =   getArguments().getString("role");
                if(role.equals("Supervisor")){
                    Intent intent = new Intent(getActivity(), SupChallanstatus.class);
                    intent.putExtra("Challan_no",value);
                    intent.putExtra("CallType","Dispatch");
                    startActivity(intent);
                }
                if(role.equals("Scaffolder")){
                    Intent intent = new Intent(getActivity(), ScaffChallanstatus.class);
                    intent.putExtra("Challan_no",value);
                    intent.putExtra("CallType","Dispatch");
                    startActivity(intent);
                }


            }
        });


        return view ;
    }
}