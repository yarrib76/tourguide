package com.yamil.tourguide;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.yamil.tourguide.Data.Contract;
import com.yamil.tourguide.Data.DbProvider;

import java.util.ArrayList;
import java.util.Currency;

public class RegresoExcurActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private ListView listaBlueTooth;
    private ArrayList<ListBlueTooth> datos;
    private AdapterBlueTooth adapterBlueTooth;
    private Button btn_scan_regreso;
    private DbProvider provider;
    private Cursor cursor;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regreso_excur);
        btn_scan_regreso = (Button) findViewById(R.id.btn_scan_regreso);
        adapterBlueTooth = new AdapterBlueTooth(this,datos);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        provider = new DbProvider(this);
        listaBlueTooth = (ListView) findViewById(R.id.lista_regreso_bluetooth);
        contentValues = new ContentValues();
        btn_scan_regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos = new ArrayList<ListBlueTooth>();
                adapterBlueTooth = new AdapterBlueTooth(RegresoExcurActivity.this, datos);
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                registerReceiver(mReceiver, filter);
                mBluetoothAdapter.startDiscovery();
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
                regresoBluetooth(device.getAddress());
                query();
            }
        }
    };

    private void query() {
        String[] columns = {Contract.BluetoothActivos.COL_NOMBRE, Contract.BluetoothActivos.COL_MACADDRESS,
        Contract.BluetoothActivos.COL_ESTADO};
        String selection =null;
        String[] selectionArgs = null;
        cursor = provider.query(Contract.BluetoothActivos.TABLA_BLUETOOTHACTIVOS, columns, selection, selectionArgs);
        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                boolean datoBooleanEstado = cursor.getInt(2) > 0;
                boolean procesado = verificaSiYaFueProcesado(cursor.getString(1));
                if (datoBooleanEstado && !procesado){
                    datos.add(new ListBlueTooth(cursor.getString(0), cursor.getString(1), datoBooleanEstado,"El viejo llego bien"));
                }
            }
            cursor.close();
        }
        adapterBlueTooth.notifyDataSetChanged();
        listaBlueTooth.setAdapter(adapterBlueTooth);
    }

    private Boolean verificaSiYaFueProcesado(String macAddress) {
        for (int i = 0; i < datos.size(); i++){
            if (datos.get(i).getMacAddress().equals(macAddress)){
                return true;
            }
        }
        return false;
    }

    private void regresoBluetooth(String address) {
        String[] columns = {Contract.BluetoothActivos.COL_ID, Contract.BluetoothActivos.COL_MACADDRESS};
        String selection = Contract.BluetoothActivos.COL_MACADDRESS + " LIKE ?";
        String[] selectionArgs = {"" + address};
        cursor = provider.query(Contract.BluetoothActivos.TABLA_BLUETOOTHACTIVOS, columns, selection, selectionArgs);
        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                contentValues.put(Contract.BluetoothActivos.COL_ESTADO,1);
                provider.update(Contract.BluetoothActivos.TABLA_BLUETOOTHACTIVOS, contentValues, selection, selectionArgs);
            }
            cursor.close();
        }
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
