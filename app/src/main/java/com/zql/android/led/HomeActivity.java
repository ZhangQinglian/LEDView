package com.zql.android.led;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.zql.android.led.data.LEDDao;
import com.zql.android.led.data.LEDDashboard;
import com.zql.android.led.data.LEDEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    private ListView mLEDList ;

    private LEDAdapter mAdapter;

    private FloatingActionButton mFAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LEDService.own().initFirstLED();
        initView();
        initData();
        //init the first led
    }

    private void initView(){
        mLEDList = (ListView) findViewById(R.id.led_list);
        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LEDService.own().addLED();
                fetchLEDData();
            }
        });
    }


    private void initData(){
        mAdapter = new LEDAdapter();
        mLEDList.setAdapter(mAdapter);
        fetchLEDData();
    }

    private void fetchLEDData(){

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<LEDEntity> ledEntities = LEDService.own().getLEDData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(ledEntities);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }
    private class LEDAdapter extends BaseAdapter{

        private List<LEDEntity> ledEntities = new ArrayList<>();

        private void setData(List<LEDEntity> entities){
            ledEntities.clear();
            ledEntities.addAll(entities);
        }

        @Override
        public int getCount() {
            return ledEntities.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return ledEntities.get(i).id;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LEDHolder holder ;
            if(view == null){
                view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.led_item,viewGroup,false);
                holder = new LEDHolder();
                holder.ledView = view.findViewById(R.id.led_view);
                view.setTag(holder);

            }

            LEDEntity entity = ledEntities.get(i);
            holder = (LEDHolder) view.getTag();
            holder.ledView.setLED(entity.content,
                    entity.textColor,
                    entity.textSize,
                    entity.ledSize);

            LEDDashboard ledDashboard = (LEDDashboard) view;
            ledDashboard.init(i,entity,HomeActivity.this);
            return view;
        }

        private class LEDHolder{
            LEDView ledView;
        }
    }
}
