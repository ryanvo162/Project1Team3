package com.ps14483.project1_team3.fragment;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.adapter.ChoAdapter;
import com.ps14483.project1_team3.model.Cho;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ChoFragment extends Fragment {
    RecyclerView rcv;
    ChoAdapter adapter;
    ImageView img;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("PetShop");
    DatabaseReference spref = reference.child("sanpham");
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    TextInputLayout tilten, tilmaulong, tilgia;
    EditText edten, edmaulong, edgia, edchitiet;
    FloatingActionButton flb;
    Button btnok, btncancel,btnchonhinh;
    Uri uriI,sourceimg;
    String imgURL;

    ArrayList<String> list = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cho, container, false);
        rcv = view.findViewById(R.id.rc_cho);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Cho> options = new FirebaseRecyclerOptions.Builder<Cho>()
                .setQuery(spref.child("Cho"), Cho.class).build();
        adapter = new ChoAdapter(options, getContext());
        rcv.setAdapter(adapter);
        flb = view.findViewById(R.id.flb_cho);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(), 0);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public void openDialog(Context context, int type) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_chomeo);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_none);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        edchitiet = dialog.findViewById(R.id.edchitiet);
        edten = dialog.findViewById(R.id.edTenpet);
        edmaulong = dialog.findViewById(R.id.edmaulong);
        edgia = dialog.findViewById(R.id.edgiapet);
        btnok = dialog.findViewById(R.id.btnOKpet);
        tilgia = dialog.findViewById(R.id.tilgiapet);
        tilten = dialog.findViewById(R.id.tilTenpet);
        tilmaulong = dialog.findViewById(R.id.tilmaulong);
        img = dialog.findViewById(R.id.dialog_img_pet);
        btnchonhinh=dialog.findViewById(R.id.btnchonhinh);
        btnchonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        img.setImageURI(uriI);
                    }
                });

            }
        });



        btncancel = dialog.findViewById(R.id.btnCancelpet);
//Ki???m l???i nh???p
        KiemloiNhap();
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnok.setText("Add");
        KiemloiNhap();
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    String id = spref.child("Cho").push().getKey();
                    String tenpet = edten.getText().toString();
                    String maulongpet = edmaulong.getText().toString();
                    String chitiet = edchitiet.getText().toString();
                    String gia1 = edgia.getText().toString();
                    // Th??m h??nh
                    try {
                        StorageReference storageReference = storage.child(System.currentTimeMillis() + "." + getFileExtension(uriI));
                        storageReference.putFile(uriI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Toast.makeText(getContext(),"Xin ch??? ch??t",Toast.LENGTH_SHORT).show();
                                        imgURL = uri.toString();
                                        if (id.length()==0 || tenpet.length()==0 || maulongpet.length()==0 || gia1.length() < 4 ) {
                                            Toast.makeText(getContext(), "B???n ch??a ????p ???ng ????? y??u c???u", Toast.LENGTH_SHORT).show();
                                        } else {
                                            int gia = Integer.parseInt(gia1);
                                            DatabaseReference newref = spref.child("Cho");
                                            Cho cho = new Cho(id, tenpet, maulongpet, gia, imgURL, chitiet);
                                            newref.child(id).setValue(cho).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getContext(), "Th??nh C??ng", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    }catch (NullPointerException e)
                    {
                        Toast.makeText(getContext(),"Ch??a ch???n h??nh",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            //

        });

        dialog.show();
    }

    public void KiemloiNhap() {
        edten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edten.length() == 0) {
                    tilten.setError("T??n kh??ng ???????c b??? tr???ng");
                } else {
                    tilten.setErrorEnabled(false);
                }
            }
        });
        edmaulong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edmaulong.length() == 0) {
                    tilmaulong.setError("M??u l??ng kh??ng ???????c b??? tr???ng");
                } else {
                    tilmaulong.setErrorEnabled(false);
                }
            }
        });
        edgia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edgia.length() < 4) {
                    tilgia.setError("Gi?? t???i thi???u l?? 1000");
                } else {
                    tilgia.setErrorEnabled(false);
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);


    }

    public void chooseImg() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            uriI = data.getData();
        }
    }

    private String getFileExtension(Uri mUri) {

            ContentResolver cr = getContext().getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}
