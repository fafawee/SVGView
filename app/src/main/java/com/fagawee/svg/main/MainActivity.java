package com.fagawee.svg.main;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.fagawee.svg.R;

import java.text.DecimalFormat;

import com.fagawee.svg.lib.SvgView;

public class MainActivity extends Activity {
	public static final String fc="M41 40L236 235L427 45C427 45 523 -72 751 39L556 237L747 431C747 431 854 527 750 747L551 556L353 749C353 749 245 853 43 746L235 554L41 353C41 353 -60 253 41 40Z";
	public static final String fc1="M27 29L148 150L272 28C272 28 338 -33 468 28L348 150L474 280C474 280 527 350 469 469L348 350L224 468C224 468 154 536 26 470L148 348L30 230C30 230 -34 174 27 29Z";
	public static final String svgs="M120 8C120 8 114 24 88 10C88 10 55 -1 29 17C3 35 4 60 7 76C10 92 20 98 26 102C26 102 52 118 69 125C86 132 108 148 112 158C112 158 126 192 98 214C98 214 78 234 38 212C38 212 21 204 10 165C10 165 0 168 2 168L14 230L24 222C24 222 32 218 46 226C62 236 110 236 124 202C124 202 144 170 125 141C125 141 114 122 88 111C62 100 23 82 23 68C23 56 20 14 66 16C66 16 101 9 118 62L125 60L120 8Z"
									+"M165 85L218 85L218 93C218 93 199 90 204 105C209 120 236.75 200 236.75 200L271 107C271 107 276 93 258 93L258 85L302 85L302 93C302 93 292 92 289 96C289 96 284 100 281 106L236 230L229 230L185 103C185 103 181 93 173 93L165 93C165 93 166 85 165 85Z"
									+"M430 94C430 94 402 68 362 93C362 93 345 104 344 130C344 130 343 151 362 166C362 166 363 168 360 169C358 170 348 176 346 184C345 192 343 199 360 208C360 208 364 210 359 211C354 212 342 220 339 228C337 234 330 252 348 264C366 276 386 282 428 274C428 274 459 266 461 240C463 215 449 211 447 209C446 208 440 204 398 198C356 193 367 194 366 194C364 193 352 182 370 171C370 171 371 170 374 172C376 173 386 178 396 177C406 176 430 176 442 148C454 120 436 104 436 100C436 100 448 88 453 100C453 100 456 110 467 102C467 102 472 99 469 90C466 80 458 81 456 81C456 81 446 84 440 88C435 92 430 94 430 94ZM394 91M797 93M803 404M761 236M382 167C382 167 409.5 177.5 422.5 156.75C422.5 156.75 431 146.5 426 114C426 114 425 96 398 91C398 91 386 88 371 100C371 100 351 127 369 156C369 156 374 163 379 166C384 168 382 167 382 167ZM370 212C370 212 422.75 216 434.75 222C447 228 452 253 431 263C410 273 363 276 352 248C352 248 346 225 367 214L370 212Z";
	public static final String fllower="M212.125 264.875C212.125 264.875 41 318 16 239C16 239 18 95 119 142C119 142 177.5 165.25 225.5 227.25C225.5 227.25 128 83 188 48C248 13 309 47 319 61C319 61 353.375 111.75 268.375 226.75C268.375 226.75 390 95 439 141C439 141 497 186 473 254C473 254 443.75 308.75 280.75 264.5C279.9055 264.2708 431.5 325 428 383C428 383 424 442 342 459C260 476 246.625 290.875 246.625 287.875C246.625 284.875 231 455 170 458C109 461 62.75 410 63 384C63 384 59.25 323.5 212.125 264.875ZM245.375 279.375C245.375 279.375 272.125 279.125 272.125 250.125C272 250 271 227 244 226C244 226 218.625 226.75 218.625 253.75C218.625 253.75 223.625 281.375 245.625 279.375Z";
	public static final String s=
			"M50 98C50 98 98 98 98 48C98 48 96 2 48 2C48 2 4 2 2 49C2 49 2 98 50 98ZM678 140M48 98C48 98 73 99 72 73C72 73 72 51 51 50C51 50 27.75 49.25 26.75 27.25C27 27 27 2.5 47.25 2.5M51 81C51 81 55 81 55 77C55 77 55 73.25 51 73.25C48 74 48 77 48 77C48 77 48 80 51 81ZM50 23C50 23 54.375 22.25 54.375 27.25C54 27 54 31 51 31C51 31 46 31 46 27C46 27 46 23 50 23Z";

;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_main);
		 final SvgView svg=(SvgView) findViewById(R.id.svg);
		 View svgd= findViewById(R.id.svgd);
		 Button restart=(Button) findViewById(R.id.restart);
		final TextView text=(TextView) findViewById(R.id.text);
		text.setText("进度: 00.0"+"%"+"         已用时："+"    总时长："+"  s");
		//svg.animate().translationXBy(100).setDuration(2*1000).start();
		svg.setSvgData(svgs);
		
		svg.setGravity(Gravity.CENTER);
		//svg.getMatrix().postTranslate(-7, -17);
		svg.sesScaleType(ScaleType.FIT_XY);
		svg.getPaint().setColor(Color.RED);
		//svg.getPaint().setStyle(Style.FILL);
		//int num=svg.getPahtNum();		
		final ObjectAnimator animator=ObjectAnimator.ofFloat(svg, "progress", svg.getLength());
		animator.setDuration(4*1000);
		animator.setInterpolator(new LinearInterpolator());
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float percent=((Float)animation.getAnimatedValue())/svg.getLength();
				DecimalFormat format=new DecimalFormat("#0.0");
				text.setText("进度: "+format.format(percent*100)+"%"+"         已用时："+(int)(animation.getCurrentPlayTime()/1000)+"s"+"  总时长："+(int)(animation.getDuration()/1000)+"s");
				
			}
		});
		restart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				animator.start();
			}
		});
		animator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@SuppressLint("NewApi")
			@Override
			public void onAnimationEnd(Animator animation) {
				//rotation(svg, 3, 9*1000);
				//animationIV.setVisibility(View.VISIBLE);
				/*svg.animate().alpha(0).setDuration(500).start();
				animationIV.animate().alpha(1).setDuration(500).withEndAction(new Runnable() {
					 
					@Override
					public void run() {
						animationIV.animate().rotation(-360*10000).setInterpolator(new LinearInterpolator()).setDuration(1000*10000).start();
						
					}
				}).start();*/
				//AnimationDrawable draw=(AnimationDrawable) animationIV.getDrawable();
			//	draw.start();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	public void rotation(View v,int x,long duration)
	{
		v.setPivotX(v.getWidth()/2);
		v.setPivotY(v.getHeight()/2);
		//v.animate().translationXBy(100).setDuration(2*1000).start();
		v.animate().rotationY(x*360).setDuration(duration).start();
	}

}
