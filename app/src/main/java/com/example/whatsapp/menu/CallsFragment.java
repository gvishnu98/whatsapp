package com.example.whatsapp.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapp.ContactsActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.adapter.CalllistAdapter;
import com.example.whatsapp.adapter.ChatlistAdapter;
import com.example.whatsapp.adapter.ContactsAdapter;
import com.example.whatsapp.databinding.ActivityContactsBinding;
import com.example.whatsapp.databinding.FragmentCallsBinding;
import com.example.whatsapp.model.Calllist;
import com.example.whatsapp.model.Chatlist;
import com.example.whatsapp.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CallsFragment extends Fragment {

    private static final String TAG="CallsFragment";
    public CallsFragment() {
        // Required empty public constructor
    }

    private FragmentCallsBinding binding;
    private List<Calllist> list = new ArrayList<>();
    private CalllistAdapter adapter;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_calls, container, false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        if (firebaseUser!=null){
            getCallList();
        }


        //recyclerView.setAdapter(new CalllistAdapter(list,getContext()));
        return binding.getRoot();
    }

    private void getCallList() {
        firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots){


                    Calllist calllist = new Calllist(
                            snapshots.getString("userID"),
                            snapshots.getString("userName"),
                            "Yesterday,6:20 am",
                            snapshots.getString("imageProfile"),
                            "incoming"
                    );




                        list.add(calllist);

                }
                adapter = new CalllistAdapter(list, getContext());
                binding.recyclerView.setAdapter(adapter);
            }

        });
    }
    }

