package com.example.android_api_challenge2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.android_api_challenge2.Adapter.StateAdapter;
import com.example.android_api_challenge2.Service.DataService;
import com.example.android_api_challenge2.R;
import com.example.android_api_challenge2.Model.State;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowAllStatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowAllStatesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static StateAdapter stateAdapter;
    private static RecyclerView recyclerView;
    private ArrayList<State> allStates;
    private static EditText inputSearch;
    private DataService dataService;
    private static Context mContext;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFirstFragmentInteractionListener mListener;

    public ShowAllStatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainFirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowAllStatesFragment newInstance(String param1, String param2) {
        ShowAllStatesFragment fragment = new ShowAllStatesFragment();
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
        final View v = inflater.inflate(R.layout.fragment_main_first, container, false);
        dataService = new DataService();
        mContext = getActivity();
        recyclerView = v.findViewById(R.id.ListViewsir);
        inputSearch = v.findViewById(R.id.inputSearch);

        dataService.getStates(); //run asyncTask

        return v;
    }

    public void setAllStates(ArrayList<State> states) {
        allStates = states;
        stateAdapter = new StateAdapter(mContext,allStates);
        recyclerView.setAdapter(stateAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        onTextChangeForSearch();
    }

    public void onTextChangeForSearch()
    {
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                stateAdapter = new StateAdapter(mContext, stateAdapter.CostumeFilter(allStates, cs.toString()));
                recyclerView.setAdapter(stateAdapter);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFirstFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstFragmentInteractionListener) {
            mListener = (OnFirstFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFirstFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFirstFragmentInteraction(Uri uri);
    }

}