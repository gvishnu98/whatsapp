package com.example.whatsapp.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.whatsapp.ContactsActivity;
import com.example.whatsapp.R;
import com.example.whatsapp.adapter.ContactsAdapter;
import com.example.whatsapp.adapter.StatusAdapter;
import com.example.whatsapp.databinding.ActivityContactsBinding;
import com.example.whatsapp.databinding.FragmentStatusBinding;
import com.example.whatsapp.model.Chatlist;
import com.example.whatsapp.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StatusFragment extends Fragment {

    public StatusFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "StatusFragment";
    private List<Chatlist> list = new ArrayList<>();
    private StatusAdapter adapter;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    private FragmentStatusBinding binding;
    private ArrayList<String> allUserID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_status, container, false);

        allUserID=new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        if (firebaseUser!=null){
            getContactList();
        }

        getProfile();
        return binding.getRoot();
    }

    private void getProfile() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("Users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String imageProfile = documentSnapshot.getString("imageProfile");

                Glide.with(getContext()).load(imageProfile).into(binding.imageProfile);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void getContactList() {

        firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot snapshots:queryDocumentSnapshots) {

                    Chatlist chat = new Chatlist(
                            snapshots.getString("userID"),
                            snapshots.getString("userName"),
                            "Today,11:20 am",
                            "",
                            snapshots.getString("imageProfile"));


                    list.add(chat);

                }

                adapter = new StatusAdapter(list,getContext());
                binding.recyclerView.setAdapter(adapter);
            }

        });
    }



}