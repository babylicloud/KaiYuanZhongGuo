package myuplode.jiyun.com.kaiyuanzhongguo.zonghe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import myuplode.jiyun.com.kaiyuanzhongguo.R;

public class SearchActivity extends Activity implements View.OnClickListener {
    private EditText mEditText;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();


    }

    public void initViews() {
        mEditText = (EditText) findViewById(R.id.Search_EditText);
        mText = (TextView) findViewById(R.id.Search_Text);
        mText.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Search_Text:
                if (mEditText.getText().toString().isEmpty()) {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SearchActivity.this, TwoActivity.class);
                    intent.putExtra("search", mEditText.getText().toString());
                    startActivity(intent);
                }


                break;

        }
    }
}
