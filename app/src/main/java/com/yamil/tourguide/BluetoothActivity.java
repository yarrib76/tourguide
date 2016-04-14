package com.yamil.tourguide;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.yamil.tourguide.Data.Contract;
import com.yamil.tourguide.Data.DbProvider;

import java.util.ArrayList;

public class BluetoothActivity extends AppCompatActivity {
    private Button btn_scan;
    private Button btn_btn_agrega_confirmados;
    private BluetoothAdapter mBluetoothAdapter;
    private DbProvider provider;
    private ContentValues contentValues;
    private ListView listaBlueTooth;
    private ArrayList<ListBlueTooth> datos;
    private AdapterBlueTooth adapterBlueTooth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_btn_agrega_confirmados = (Button) findViewById(R.id.btn_agrega_confirmados);
        listaBlueTooth = (ListView) findViewById(R.id.lista_blueTooth);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        provider = new DbProvider(this);
        contentValues = new ContentValues();
<<<<<<< HEAD
        datos = new ArrayList<ListBlueTooth>();
=======
>>>>>>> ad44a799fcfeaecd9ad3fc226807a369cac51080
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos = new ArrayList<ListBlueTooth>();
                adapterBlueTooth = new AdapterBlueTooth(BluetoothActivity.this, datos);
                listaBlueTooth.setAdapter(adapterBlueTooth);
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                registerReceiver(mReceiver, filter);
                mBluetoothAdapter.startDiscovery();
            }
        });
        listaBlueTooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (datos.get(position).getActivo()) {
                    datos.get(position).setEstado("Inactivo");
                    datos.get(position).setActivo(false);
                } else {
                    datos.get(position).setEstado("Activo");
                    datos.get(position).setActivo(true);
                }
                adapterBlueTooth.notifyDataSetChanged();
            }
        });
        btn_btn_agrega_confirmados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provider.deleteAllTable(Contract.BluetoothActivos.TABLA_BLUETOOTHACTIVOS);
                for (int i = 0; i < datos.size(); i++ ){
                    if (datos.get(i).getActivo()){
                        contentValues.put(Contract.BluetoothActivos.COL_NOMBRE,datos.get(i).getNombre());
                        contentValues.put(Contract.BluetoothActivos.COL_MACADDRESS,datos.get(i).getMacAddress());
                        contentValues.put(Contract.BluetoothActivos.COL_ESTADO,0);
                        provider.insert(Contract.BluetoothActivos.TABLA_BLUETOOTHACTIVOS,contentValues);
                    }
                }
            }
        });
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                datos.add(new ListBlueTooth(device.getName(), device.getAddress(),false));
                //Toast.makeText(BluetoothActivity.this,"Found device " + device.getName() + device.getAddress(), Toast.LENGTH_SHORT).show();
                adapterBlueTooth.notifyDataSetChanged();
                listaBlueTooth.setAdapter(adapterBlueTooth);
            }
        }
    };
    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}
