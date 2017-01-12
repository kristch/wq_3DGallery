package com.xilehang.tencent;

import java.util.Timer;
import java.util.TimerTask;

import com.xilehang.tencent.tools.GlobealSetting;
import com.xilehang.tencent.tools.ImageUtil;
import com.xilehang.tencent.tools.MyGallery3D;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements OnItemSelectedListener {
	// 明星人物特效轮播
	private int[] imageResIDs;
	private MyGallery3D gallery3d;
	private int index3d = 0;
	private final int AUTOPLAY = 222;

	// 大图轮播图
	String[] imageUrls = new String[] { GlobealSetting.LUNBO_PATH + "1_1.jpg",
			GlobealSetting.LUNBO_PATH + "1_2.jpg",
			GlobealSetting.LUNBO_PATH + "1_3.jpg",
			GlobealSetting.LUNBO_PATH + "1_4.jpg" };
	// 小图轮播
	private int index = 0;
	@SuppressWarnings("deprecation")
	private Gallery g;
	private Integer[] mThumbIds = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		init3dGallery();// 底部轮播

	}

/**
 * 初始化3的 轮播图效果
 * Title: init3dGallery
 * Description:
 */
	private void init3dGallery() {
		imageResIDs = new int[] { R.drawable.huashao, R.drawable.wangjing,
				R.drawable.zhanglunshuo, R.drawable.zhongliti, R.drawable.zhouhaimei };

		gallery3d = (MyGallery3D) findViewById(R.id.mygallery3d);
		ivshow = (ImageView) findViewById(R.id.showImage);
		ImageAdapter3d adapter3d = new ImageAdapter3d();
		gallery3d.setAdapter(adapter3d);
		gallery3d.setSelection((Integer.MAX_VALUE / 2)
				- (Integer.MAX_VALUE / 2) % imageResIDs.length);
		gallery3d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this,
						"当前位置position:" + position + "的图片被选中了",
						Toast.LENGTH_SHORT).show();
			}
		});
		gallery3d.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(MainActivity.this,
						"当前位置position:" + arg2 + "的图片被选中了"+arg3,
						Toast.LENGTH_SHORT).show();
				//ivshow.setImageResource(imageResIDs[(int) arg3]);
				ivshow.setBackgroundResource(imageResIDs[(int) arg3]);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		gallery3d.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
				//return true;
			}
		});

		Timer timer = new Timer();
		timer.schedule(task3d, 1000, 2000);

	}

	/**
	 * 定时器，实现自动播放
	 */
	private TimerTask task3d = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			message.what = AUTOPLAY;
			index3d = gallery3d.getSelectedItemPosition();
			index3d++;
			handler3d.sendMessage(message);
		}
	};
/**
 * 控制3d轮播图是否自动轮播
 */
	private Handler handler3d = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case AUTOPLAY:
				gallery3d.setSelection(index3d);
				break;
			default:
				break;
			}
		}
	};



	/**
	 * 定时器，实现自动播放
	 */
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 2;
			index = g.getSelectedItemPosition();
			index++;
			handler.sendMessage(message);
		}
	};

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				if (index <= 1) {
					index = Integer.MAX_VALUE / 2;
				}
				g.setSelection(index);
				break;

			default:
				break;
			}
		}
	};
	private ImageAdapter adapter1;
	private RelativeLayout regallery;
	private ImageView ivshow;
/**
 * 轮播图片的适配器
 * @author wuweiqi
 * @time 2016-12-15
 *
 */
	public class ImageAdapter extends BaseAdapter {
		private int selectItem;
		public void setSelectItem(int selectItem) {
			if (this.selectItem != selectItem) {
				this.selectItem = selectItem;
				notifyDataSetChanged();
			}
		}
		public ImageAdapter(Context c) {
			mContext = c;
		}
		public int getCount() {
			// return mThumbIds.length;
			return Integer.MAX_VALUE;// 替换的
		}

		public Object getItem(int position) {

			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			/*
			 * ImageView i = new ImageView(mContext);
			 * i.setImageResource(mThumbIds[position%mThumbIds.length]);
			 * i.setAdjustViewBounds(true); i.setLayoutParams(new
			 * Gallery.LayoutParams( LayoutParams.WRAP_CONTENT,
			 * LayoutParams.WRAP_CONTENT)); i.setBackgroundColor(Color.CYAN);
			 * return i;
			 */
			ImageView imageView = new ImageView(mContext);
			imageView.setImageResource(mThumbIds[position % mThumbIds.length]);
			// 取余，让图片循环浏览
			if (selectItem == position) {
				/*
				 * Animation animation = AnimationUtils.loadAnimation(mContext,
				 * R.anim.scale); // 实现动画效果 imageView.setLayoutParams(new
				 * Gallery.LayoutParams(105, 120));
				 * imageView.startAnimation(animation); // 选中时，这是设置的比较大
				 */
				imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
				imageView.setImageResource(R.drawable.ic_launcher);
			} else {
				imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
				// 未选中
			}
			return imageView;

		}

		private Context mContext;
	}

	@Override
	public void onItemSelected(AdapterView<?> adapter, View v, int position,
			long id) {
		// Toast.makeText(this, "你好啊"+position, Toast.LENGTH_SHORT).show();
		adapter1.setSelectItem(position); // 当滑动时，事件响应，调用适配器中的这个方法。
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	/*
	 * @Override public View makeView() { ImageView i = new ImageView(this);
	 * i.setBackgroundColor(0xFF0000f0);
	 * i.setScaleType(ImageView.ScaleType.FIT_CENTER); i.setLayoutParams(new
	 * ImageSwitcher.LayoutParams( LayoutParams.FILL_PARENT,
	 * LayoutParams.FILL_PARENT)); return i; }
	 */
	// 我的方法测试
	private void alignGalleryToLeft(View parentView, Gallery gallery) {
		int galleryWidth = parentView.getWidth();

		// We are taking the item widths and spacing from a dimension resource
		// because:
		// 1. No way to get spacing at runtime (no accessor in the Gallery
		// class)
		// 2. There might not yet be any item view created when we are calling
		// this
		// function
		int itemWidth = MainActivity.this.getResources().getDimensionPixelSize(
				R.dimen.gallery_item_width);
		int spacing = MainActivity.this.getResources().getDimensionPixelSize(
				R.dimen.gallery_spacing);

		// The offset is how much we will pull the gallery to the left in order
		// to simulate
		// left alignment of the first item
		int offset;
		if (galleryWidth <= itemWidth) {
			offset = galleryWidth / 2 - itemWidth / 2 - spacing;
		} else {
			offset = galleryWidth - itemWidth - 2 * spacing;
		}
		offset = 0;

		// Now update the layout parameters of the gallery in order to set the
		// left margin
		MarginLayoutParams mlp = (MarginLayoutParams) gallery.getLayoutParams();
		mlp.setMargins(-offset, mlp.topMargin, mlp.rightMargin,
				mlp.bottomMargin);
	}
/**
 * 初始化3d轮播的适配器
 * @author wuweiqi
 * @time 2016-12-15
 *
 */
	public class ImageAdapter3d extends BaseAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;// 用于循环滚动
		}

		@Override
		public Object getItem(int position) {
			if (position >= imageResIDs.length) {
				position = position % imageResIDs.length;
			}

			return position;
		}

		@Override
		public long getItemId(int position) {
			if (position >= imageResIDs.length) {
				position = position % imageResIDs.length;
			}

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView imageView;
			if (convertView != null) {
				imageView = (ImageView) convertView;
			} else {
				imageView = new ImageView(MainActivity.this);
			}

			if (position >= imageResIDs.length) {
				position = position % imageResIDs.length;
			}

			Bitmap bitmap = ImageUtil.getImageBitmap(getResources(),
					imageResIDs[position]);
			System.out.println("我展示的--"+position);
			BitmapDrawable drawable = new BitmapDrawable(bitmap);
			drawable.setAntiAlias(true); // 消除锯齿
			LayoutParams params = new LayoutParams(149, 280);
			imageView.setLayoutParams(params);
			imageView.setImageDrawable(drawable);
						

			//测试
		/*	LayoutParams params = new LayoutParams(200, 400);
			imageView.setLayoutParams(params);
			imageView.setImageResource(imageResIDs[position]);*/
			return imageView;
		}
	}
}
