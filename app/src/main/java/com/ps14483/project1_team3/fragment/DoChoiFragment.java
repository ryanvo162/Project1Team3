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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.adapter.DoChoiAdapter;
import com.ps14483.project1_team3.model.Dochoi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class DoChoiFragment extends Fragment {
    RecyclerView rcv;
    DoChoiAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("PetShop");
    DatabaseReference spref = reference.child("sanpham");
    TextInputLayout tilten, tilgia;
    FloatingActionButton flb;
    ArrayList<String> list = new ArrayList<String>();
    EditText edten,edchitiet,edgia;
    Button btnok,btncancel;
    Uri uri;
    String imgURL;
    ImageView img;
    StorageReference storage= FirebaseStorage.getInstance().getReference();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dochoi, container, false);
        rcv = view.findViewById(R.id.rc_dochoi);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Dochoi> options = new FirebaseRecyclerOptions.Builder<Dochoi>()
                .setQuery(spref.child("DoChoi"), Dochoi.class).build();
        adapter = new DoChoiAdapter(options, getContext());
        rcv.setAdapter(adapter);
        flb = view.findViewById(R.id.flb_dochoi);
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
        dialog.setContentView(R.layout.dialog_sp);
        edten = dialog.findViewById(R.id.edTen);
        edgia = dialog.findViewById(R.id.edgia);
        btnok = dialog.findViewById(R.id.btnOK);
        edchitiet=dialog.findViewById(R.id.edchitietsp);
        tilgia=dialog.findViewById(R.id.tilgiasp);
        tilten=dialog.findViewById(R.id.tilTensp);
        btncancel = dialog.findViewById(R.id.btnCancel);
        btnok.setText("ADD");
        edgia.setHint("Giá");

        img=dialog.findViewById(R.id.dialog_img_sp);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
                Picasso.get().load(uri).into(img);
            }
        });
        KiemloiNhap();
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = spref.child("DoChoi").push().getKey();
                String ten = edten.getText().toString();
                String chitiet=edchitiet.getText().toString();
                int gia = Integer.parseInt(edgia.getText().toString());
                StorageReference storageReference=storage.child(System.currentTimeMillis()+"."+getFileExtension(uri));
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imgURL=uri.toString();
                                DatabaseReference newref = spref.child("DoChoi");
                                Dochoi item = new Dochoi(id,ten, gia,imgURL,chitiet);
                                newref.child(id).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                                Toast.makeText(getContext(), imgURL, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
        dialog.show();
    }

    public void KiemloiNhap()
    {
        edten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edten.length()==0)
                {
                    tilten.setError("Tên không được bỏ trống");
                }else {
                    tilten.setErrorEnabled(false);
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
                if (edgia.length()<4)
                {
                    tilgia.setError("Giá tối thiểu là 1000");
                }else {
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
    //Phần hình ảnh
    public void chooseImg()
    {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2&&resultCode == RESULT_OK&&data!=null)
        {
            uri=data.getData();

        }
    }


    private String getFileExtension(Uri mUri)
    {
        ContentResolver cr= getContext().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}
