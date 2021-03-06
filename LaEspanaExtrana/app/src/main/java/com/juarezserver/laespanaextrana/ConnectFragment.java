package com.juarezserver.laespanaextrana;

/**
 * Created by modes on 23/12/2016.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnectFragment extends Fragment {


    private ListView mListView;
    private ProgressDialog mProgress;


    public ConnectFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);

        mListView = (ListView) rootView.findViewById(R.id.comunidadesListView);

        mProgress = new ProgressDialog(this.getActivity());
        mProgress.setMessage("Cargando Comunidades Autónomas...");
        mProgress.show();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://la-espana-extrana.firebaseio.com/comunidades");

        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                this.getActivity(),
                String.class,
                android.R.layout.simple_list_item_1,
                databaseReference
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model);
                mProgress.dismiss();

            }
        };
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String topic = String.valueOf(parent.getItemAtPosition(position));

              Log.d("Comunidad",topic);

                //PASA VALOR SELECCIONADO AL SIGUIENTE FRAGMENT


                EnclavesFragment myDetailFragment = new EnclavesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("KEY_DETAIL", topic);
                myDetailFragment.setArguments(bundle);


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, myDetailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        mListView.setAdapter(firebaseListAdapter);


        return rootView;
    }




}