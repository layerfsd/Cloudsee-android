package com.jovision.adapters;

import java.util.ArrayList;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jovision.Consts;
import com.jovision.activities.BaseFragment;
import com.jovision.newbean.Device;

public class MyDeviceListAdapter extends BaseAdapter {
	private ArrayList<Device> deviceList;
	private BaseFragment mfragment;
	private LayoutInflater inflater;
	private boolean showDelete = false;

	private int[] devResArray = { R.drawable.device_bg_1,
			R.drawable.device_bg_2, R.drawable.device_bg_3,
			R.drawable.device_bg_4 };

	public MyDeviceListAdapter(Context con, BaseFragment fragment) {
		mfragment = fragment;
		inflater = (LayoutInflater) fragment.getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ArrayList<Device> dataList) {
		deviceList = dataList;
	}

	// 控制是否显示删除按钮
	public void setShowDelete(boolean flag) {
		showDelete = flag;
	}

	@Override
	public int getCount() {
		int count = 0;
		int last = deviceList.size() % 2;
		if (0 == last) {
			count = deviceList.size() / 2;
		} else {
			count = deviceList.size() / 2 + 1;
		}
		return count;
	}

	@Override
	public Object getItem(int arg0) {
		return deviceList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		DeviceHolder deviceHolder;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.mydevice_list_item, null);
			deviceHolder = new DeviceHolder();
			deviceHolder.devLayoutL = (RelativeLayout) convertView
					.findViewById(R.id.dev_layout_l);
			deviceHolder.devNameL = (TextView) convertView
					.findViewById(R.id.dev_name_l);
			deviceHolder.onLineStateL = (TextView) convertView
					.findViewById(R.id.dev_online_l);
			deviceHolder.wifiStateL = (TextView) convertView
					.findViewById(R.id.dev_wifi_l);
			deviceHolder.devImgL = (ImageView) convertView
					.findViewById(R.id.dev_image_l);
			deviceHolder.devDeleteL = (ImageView) convertView
					.findViewById(R.id.dev_delete_l);
			deviceHolder.editDevL = (RelativeLayout) convertView
					.findViewById(R.id.dev_edit_l);
			deviceHolder.editDevIVL = (ImageView) convertView
					.findViewById(R.id.dev_editiv_l);

			deviceHolder.devLayoutR = (RelativeLayout) convertView
					.findViewById(R.id.dev_layout_r);
			deviceHolder.devNameR = (TextView) convertView
					.findViewById(R.id.dev_name_r);
			deviceHolder.onLineStateR = (TextView) convertView
					.findViewById(R.id.dev_online_r);
			deviceHolder.wifiStateR = (TextView) convertView
					.findViewById(R.id.dev_wifi_r);
			deviceHolder.devImgR = (ImageView) convertView
					.findViewById(R.id.dev_image_r);
			deviceHolder.devDeleteR = (ImageView) convertView
					.findViewById(R.id.dev_delete_r);
			deviceHolder.editDevR = (RelativeLayout) convertView
					.findViewById(R.id.dev_edit_r);
			deviceHolder.editDevIVR = (ImageView) convertView
					.findViewById(R.id.dev_editiv_r);

			convertView.setTag(deviceHolder);
		} else {
			deviceHolder = (DeviceHolder) convertView.getTag();
		}
		deviceHolder.devNameL.setText(deviceList.get(position * 2).getFullNo());
		deviceHolder.onLineStateL.setText("在线");
		deviceHolder.wifiStateL.setText("wifi");

		int lastL = (position * 2) % 4;
		int lastR = (position * 2 + 1) % 4;
		// 按规律设置背景色
		if (0 == lastL || 2 == lastL) {
			deviceHolder.devLayoutL.setBackgroundResource(devResArray[lastL]);
		}
		if (1 == lastR || 3 == lastR) {
			deviceHolder.devLayoutR.setBackgroundResource(devResArray[lastR]);
		}

		// 控制删除按钮显示隐藏
		if (showDelete) {
			deviceHolder.devDeleteL.setVisibility(View.VISIBLE);
			deviceHolder.devDeleteR.setVisibility(View.VISIBLE);
			deviceHolder.editDevL.setVisibility(View.VISIBLE);
			deviceHolder.editDevR.setVisibility(View.VISIBLE);
		} else {
			deviceHolder.devDeleteL.setVisibility(View.GONE);
			deviceHolder.devDeleteR.setVisibility(View.GONE);
			deviceHolder.editDevL.setVisibility(View.GONE);
			deviceHolder.editDevR.setVisibility(View.GONE);
		}

		if (position * 2 + 1 < deviceList.size()) {
			deviceHolder.devLayoutR.setVisibility(View.VISIBLE);
			deviceHolder.devNameR.setText(deviceList.get(position * 2 + 1)
					.getFullNo());
			deviceHolder.onLineStateR.setText("在线");
			deviceHolder.wifiStateR.setText("wifi");
		} else {
			deviceHolder.devLayoutR.setVisibility(View.GONE);
		}

		// 左侧按钮事件
		deviceHolder.devLayoutL.setOnClickListener(new DevOnClickListener(1, 1,
				position));
		deviceHolder.devDeleteL.setOnClickListener(new DevOnClickListener(1, 3,
				position));
		deviceHolder.devLayoutL
				.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View arg0) {
						mfragment.onNotify(Consts.DEVICE_ITEM_LONG_CLICK,
								position, 0, null);
						return false;
					}
				});
		deviceHolder.editDevL.setOnClickListener(new DevOnClickListener(1, 4,
				position));
		deviceHolder.editDevL.setOnClickListener(new DevOnClickListener(1, 4,
				position));

		// 右侧按钮事件
		deviceHolder.devLayoutR.setOnClickListener(new DevOnClickListener(2, 1,
				position));
		deviceHolder.devDeleteR.setOnClickListener(new DevOnClickListener(2, 3,
				position));
		deviceHolder.devLayoutR
				.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View arg0) {
						mfragment.onNotify(Consts.DEVICE_ITEM_LONG_CLICK,
								position, 0, null);
						return false;
					}
				});
		deviceHolder.editDevR.setOnClickListener(new DevOnClickListener(2, 4,
				position));
		deviceHolder.editDevR.setOnClickListener(new DevOnClickListener(2, 4,
				position));
		return convertView;
	}

	// 设备单击事件
	class DevOnClickListener implements OnClickListener {
		private int tag = 0;// 左右标志 1：左 2：右
		private int operate = 1;// 1：点击播放 2：点击查看设备通道 3.点击删除设备 4.编辑设备
		private int line = 0;// 行号
		private int position = 0;// 列表中的位置

		public DevOnClickListener(int leftOrRight, int method, int linePos) {
			tag = leftOrRight;
			operate = method;
			line = linePos;
			if (1 == tag) {
				position = line * 2;
			} else if (2 == tag) {
				position = line * 2 + 1;
			}

		}

		@Override
		public void onClick(View arg0) {
			if (1 == operate || 2 == operate) {
				mfragment.onNotify(Consts.DEVICE_ITEM_CLICK, position, 0, null);
			} else if (3 == operate) {
				mfragment.onNotify(Consts.DEVICE_ITEM_DEL_CLICK, position, 0,
						null);
			} else if (4 == operate) {
				mfragment.onNotify(Consts.DEVICE_EDIT_CLICK, position, 0, null);
			}
		}

	}

	class DeviceHolder {
		RelativeLayout devLayoutL;
		TextView devNameL;
		TextView onLineStateL;
		TextView wifiStateL;
		ImageView devImgL;
		ImageView devDeleteL;
		RelativeLayout editDevL;
		ImageView editDevIVL;

		RelativeLayout devLayoutR;
		TextView devNameR;
		TextView onLineStateR;
		TextView wifiStateR;
		ImageView devImgR;
		ImageView devDeleteR;
		RelativeLayout editDevR;
		ImageView editDevIVR;
	}

}
