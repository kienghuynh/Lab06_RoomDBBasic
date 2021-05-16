package com.example.danhsachsinhvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Interface_SendUser {

    Button btnSave, btnDelete, btnCancel;
    RecyclerView rcvData;
    EditText edtName;
    UserDatabase db;
    List<User> lstUser = new ArrayList<User>();
    UserAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    User userChon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectView();
        db = UserDatabase.getInstance(this);
        rcvData.setHasFixedSize(true);
        getAllUser();
        insertUser();
        deleteUser();
    }

    public void getAllUser()
    {
        lstUser.clear();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lstUser = db.mainDao().getAllUser();
        adapter = new UserAdapter(lstUser, this);
        rcvData.setLayoutManager(layoutManager);
        rcvData.setAdapter(adapter);
    }



    public void insertUser()
    {
        Context context = this;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String name = edtName.getText().toString().trim();
                    User us = new User(name);
                    db.mainDao().insert(us);
                    Toast.makeText(context, "Thêm user thành công", Toast.LENGTH_LONG).show();
                    getAllUser();
                    Clear();
                }
            }
        });
    }

    public void deleteUser()
    {
        Context context = this;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userChon == null)
                    Toast.makeText(context, "Vui lòng chọn user cần xóa", Toast.LENGTH_LONG).show();
                else
                {
                    db.mainDao().delete(userChon);
                    Toast.makeText(context, "Xóa user thành công", Toast.LENGTH_LONG).show();
                    getAllUser();
                    Clear();
                }
            }
        });
    }

    void Clear()
    {
        userChon = null;
        edtName.setText(null);
    }

    void ConnectView()
    {
        btnSave = (Button)findViewById(R.id.btnAdd);
        btnDelete = (Button)findViewById(R.id.btnRemove);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        rcvData = (RecyclerView)findViewById(R.id.rcvDSName);
        edtName = (EditText)findViewById(R.id.edtName);
    }

    @Override
    public void sendUser(User user) {
        userChon = user;
        edtName.setText(user.getName());
    }
}