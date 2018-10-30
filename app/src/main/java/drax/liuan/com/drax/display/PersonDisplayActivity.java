package drax.liuan.com.drax.display;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import drax.liuan.com.drax.R;

public class PersonDisplayActivity extends Activity {
    private LinearLayout llDisplay;
    private Button btnAdd1, btnAdd2, btnAdd3, btnAdd4;
    private PersonDisplayAnimHelper personDisplayAnimHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_display);
        this.llDisplay = findViewById(R.id.llDisplay);
        this.btnAdd1 = findViewById(R.id.btnAdd);
        this.btnAdd2 = findViewById(R.id.btnAdd2);
        this.btnAdd3 = findViewById(R.id.btnAdd3);
        this.btnAdd4 = findViewById(R.id.btnAdd4);
        this.personDisplayAnimHelper = new PersonDisplayAnimHelper(this.llDisplay);
        this.btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("");
                personDisplayAnimHelper.addItem(strings);
            }
        });
        this.btnAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("");
                strings.add("");
                personDisplayAnimHelper.addItem(strings);
            }
        });
        this.btnAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("");
                strings.add("");
                strings.add("");
                personDisplayAnimHelper.addItem(strings);
            }
        });
        this.btnAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                personDisplayAnimHelper.addItem(strings);
            }
        });
    }


}
