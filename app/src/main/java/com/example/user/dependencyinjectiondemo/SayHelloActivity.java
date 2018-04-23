package com.example.user.dependencyinjectiondemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

public class SayHelloActivity extends AppCompatActivity implements SayHelloContract.View, View.OnClickListener {

    @Inject
    SayHelloPresenter presenter;

    // UI properties
    private TextView messageView;
    private EditText firstNameView;
    private EditText lastNameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        // Creates presenter
        DaggerSayHelloComponent.builder()
                .sayHelloPresenterModule(new SayHelloPresenterModule(this, new Person()))
                .build()
                .inject(this);
    }

    private void initViews() {
        messageView = findViewById(R.id.message);
        firstNameView = findViewById(R.id.firstName);
        lastNameView = findViewById(R.id.lastName);

        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.showMessage).setOnClickListener(this);
    }

    @Override
    public void showMessage(String message) {
        messageView.setText(message);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * The View only receives user's action and leaves all remaining tasks for the Presenter
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update:
                presenter.saveName(firstNameView.getText().toString(), lastNameView.getText().toString());
                break;
            case R.id.showMessage:
                presenter.loadMessage();
                break;
        }
    }
}
