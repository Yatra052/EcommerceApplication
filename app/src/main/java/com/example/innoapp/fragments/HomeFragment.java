package com.example.innoapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.innoapp.R;
import com.example.innoapp.activities.ShowAllActivity;
import com.example.innoapp.adapters.CategoryAdapter;
import com.example.innoapp.adapters.NewProductsAdapter;
import com.example.innoapp.adapters.PopularProductAdapter;
import com.example.innoapp.models.CategoryModel;
import com.example.innoapp.models.NewProductsModel;
import com.example.innoapp.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {



    TextView catShowAll, popularShowAll, newProductShowAll;

    RecyclerView  catRecyclerview, newProductRecyclerView, popularRecyclerview;


    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;


    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    PopularProductAdapter popularProductAdapter;
    List<PopularProductsModel> popularProductsModelList;

    FirebaseFirestore db;




    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        catRecyclerview = root.findViewById(R.id.rec_category);
      newProductRecyclerView = root.findViewById(R.id.new_product_rec);
      popularRecyclerview = root.findViewById(R.id.popular_rec);


      catShowAll = root.findViewById(R.id.category_see_all);
      popularShowAll = root.findViewById(R.id.popular_see_all);
     newProductShowAll= root.findViewById(R.id.newProducts_see_all);


     catShowAll.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(), ShowAllActivity.class);
             startActivity(intent);
         }
     });


     newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });





        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.b5,"Discount On Product ", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.b3, "Purchase on reasonable price", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.n, "Buy From here",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.n2, "Get 70% OFF", ScaleTypes.CENTER_CROP));

          imageSlider.setImageList(slideModels);

          catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

          categoryModelList = new ArrayList<>();

          categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
          catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();



                                            }
                        } else {

                                   }
                    }
                });

        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductRecyclerView.setAdapter(newProductsAdapter);


        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();



                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularProductsModelList = new ArrayList<>();
        popularProductAdapter = new PopularProductAdapter(getContext(),popularProductsModelList);
        popularRecyclerview.setAdapter(popularProductAdapter);




        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductAdapter.notifyDataSetChanged();



                            }
                        } else {
                            Toast.makeText(getActivity(),""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });





        return root;
    }
}