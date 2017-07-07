package com.example.ZswPID.PIDTest;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

// Adapter for holding devices found through scanning.
public class LeDeviceListAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> mLeDevices;  //(mLeDevices 存储扫描到的蓝牙设备)

    private LayoutInflater mInflater;
    protected OnClickListener mListener;//
    public LeDeviceListAdapter(Context context) {   //构造函数
        super();
        mLeDevices = new ArrayList<BluetoothDevice>();
        mInflater = LayoutInflater.from(context);
    }

    public void addDevice(BluetoothDevice device) {
        if(!mLeDevices.contains(device)) {
            mLeDevices.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
    }

    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        // General ListView optimization code.（通用的视图优化代码）
        if (view == null) {
            view = mInflater.inflate(R.layout.activity_le_device_list_adapter, null);

            viewHolder = new ViewHolder();

            viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
            viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);

            view.setTag(viewHolder);
            
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        BluetoothDevice device = mLeDevices.get(i);     
        final String sDeviceName = device.getName();
        //get in connect immediately when right device is found   Yini
        if (sDeviceName != null && sDeviceName.length() > 0)
            viewHolder.deviceName.setText(sDeviceName);
        else
        viewHolder.deviceName.setText(R.string.unknown_device);
        viewHolder.deviceAddress.setText(device.getAddress());

        return view;
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        CheckBox checkBox;
    }

    public interface OnClickListener {
        public abstract void onClick(ListView l, View v, int position, long id);
    }
}
