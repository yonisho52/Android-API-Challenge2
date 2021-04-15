package com.example.android_api_challenge2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_api_challenge2.Activity.MainActivity;
import com.example.android_api_challenge2.Adapter.StateAdapter;
import com.example.android_api_challenge2.Service.DataService;
import com.example.android_api_challenge2.R;
import com.example.android_api_challenge2.Model.State;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowStateBordersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowStateBordersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private StateAdapter stateAdapter;
    private RecyclerView recyclerView;
    private OnSecondFragmentInteractionListener mListener;

    public ShowStateBordersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowStateBordersFragment newInstance(String param1, String param2) {
        ShowStateBordersFragment fragment = new ShowStateBordersFragment();
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
        View v = inflater.inflate(R.layout.fragment_second, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button b = (Button) v.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go_back_fragment();
            }
        });

        recyclerView = v.findViewById(R.id.ListViewRe);

        // Inflate the layout for this fragment
        final DataService dataService = new DataService();

        Intent intent = getActivity().getIntent();

        State state = (State) intent.getSerializableExtra("StateOb");
        ArrayList<State> bordersList = dataService.getStateBorders(state.getBorders());
        if(bordersList.size() == 0 )
            Toast.makeText(getContext() , "Sorry No Borders" , Toast.LENGTH_LONG).show();

        stateAdapter = new StateAdapter(getContext(),bordersList);
        recyclerView.setAdapter(stateAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSecondFragmentInteraction(uri);
        }
    }
    private void Go_back_fragment() {

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity.GoBack();

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSecondFragmentInteractionListener) {
            mListener = (OnSecondFragmentInteractionListener) context;
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
    public interface OnSecondFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSecondFragmentInteraction(Uri uri);
    }
}