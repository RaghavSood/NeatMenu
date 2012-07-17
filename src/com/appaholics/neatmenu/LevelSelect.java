package com.appaholics.neatmenu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Raghav Sood
 * @version 1
 * 
 */
public class LevelSelect extends Activity {
	private int mChapter = 1;
	private int mLevels = 100;
	boolean movingInApp = false;

	public int columns;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE); // Remove the title bar

		setContentView(R.layout.levelselect); // Load the base layout

		// Start of optimal row calculation
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels; // Width of the display
		double density = metrics.density; // Density
		double dp = Math.ceil(width / density); // Calculate DP of width.

		columns = (int) Math.round(dp / 100);
		// End of optimal size calculation
		setupLevels();
	}

	public void setupLevels() {
		LinearLayout groups = (LinearLayout) findViewById(R.id.levels);
		groups.removeAllViews(); // Clear everything

		final double perRow = columns; // Number of columns per row
		final int totalIterations = (int) Math.ceil((double) mLevels / perRow); // Total
																				// iterations
																				// needed
																				// to
																				// create
																				// layout
		int num = 0;
		for (int i = 0; i < totalIterations; i++) {
			LinearLayout row = new LinearLayout(this);
			row.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.FILL_PARENT);
			lp.weight = 1.0f;
			lp.topMargin = 7;
			lp.bottomMargin = 3;
			lp.leftMargin = 10;
			for (int c = 0; c < perRow; ++c) {
				if (num >= mLevels) {
					break;
				}
				final LevelView gv = new LevelView(this, ++num, 50);
				gv.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						if (gv.isSelectable()) {
							Intent intent = new Intent(getBaseContext(), PlayLevel.class);
							intent.putExtra("chapter", mChapter);
							intent.putExtra("levels", mLevels);
							intent.putExtra("level", gv.getLevel());
							movingInApp = true;
							startActivity(intent);
						}
					}
				});
				row.addView(gv, lp);
			}
			groups.addView(row, lp);
		}
		LinearLayout row = new LinearLayout(this);
		row.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		lp.leftMargin = 10;
		ImageView b = new ImageView(getBaseContext());
		b.setImageResource(R.drawable.button_back);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Move one screen back
			}
		});
		LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		blp.gravity = Gravity.CENTER;
		blp.topMargin = 20;
		blp.bottomMargin = 20;
		blp.leftMargin = 20;
		row.addView(b, blp);
		groups.addView(row, lp);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// ignore orientation/keyboard change
		super.onConfigurationChanged(newConfig);
	}
}