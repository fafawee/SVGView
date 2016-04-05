package com.fagawee.svg.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;
import java.util.List;

/*svg 视图  
 * tianjiangwei 2016-03-21*/
@SuppressLint("DrawAllocation")
public class SvgView extends View{

	private float scalex=1;
	private float scaley=1;
	private int gravity=Gravity.CENTER;
	private ScaleType type;
	private Matrix matrix;
	/*svgl路径解析器*/
	private SvgParser parser;
	/*svg src*/
	private String svgData;
	private Path path;
	private float position;
	private PathMeasure measure; 
	private float lenght;
	private Paint mPaint;
	private int pathNum;
	private int pending=2;
	private Rect pathRect;
	private boolean isMeasure=false;

	private List<PathEntity> pathList=new ArrayList<PathEntity>();
	public SvgView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	public SvgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}
	public SvgView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
		// TODO Auto-generated constructor stub
	}
	private void init()
	{
		matrix=new Matrix();
		parser=new SvgParser();
		initPaint();
	}
	private void initPaint()
	{
		mPaint=new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(2);
		mPaint.setStyle(Style.STROKE);
	}
	public Paint getPaint()
	{
		return mPaint;
	}
	
	public Matrix getSVGMatrix() {
		return matrix;
	}
	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}
	public void setSvgData(String src)
	{
		if (src!=null) {
			this.svgData=src;
			doSvfPath();
			
		}
		
	}
	
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
	public void setScale(float sx,float sy)
	{
		scalex=sx;
		scaley=sy;
		matrix.setScale(scalex, scaley);
	}
	public void setGravity(int gravity)
	{
		this.gravity=gravity;
		
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		if (!isMeasure&&parser!=null) {
			buildScale();
			layout();			
			isMeasure=true;
		}
		
	}
	/*设置path进度*/
	public void setProgress(float position)
	{
		this.position=position;
		invalidate();
	}
	/*获取点长度*/
	public float getLength()
	{
		if (path==null) {
			throw new SvgExecption("you must call doSvfPath()");
		}

		return lenght;
	}
	/*分配path*/
	private void doSvfPath()
	{
		path=parser.doPath(svgData);
		if (pathRect==null) {
			pathRect=parser.buildPathRect(path);
		}
		completeLength(path);
	}

	public int getPahtNum()
	{
		if (path==null) {
			throw new SvgExecption("you must call doSvfPath()");
		}
		return pathNum;
	}
	@SuppressLint({ "WrongCall", "DrawAllocation" })
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		if (pathList!=null&&pathList.size()>0) {
			float[] f=getIndex(position);
			
			
			for (int i = 0; i <=(int)f[0] ; i++) {
				Path temp=new Path();
				temp.addPath(pathList.get(i).path);
				temp.transform(matrix);
				canvas.drawPath(temp, mPaint);
			}
		
		if ((int)f[0]<pathNum) {
			Path path_dst=new Path();
			measure=new PathMeasure(pathList.get((int)f[0]+1).path, false);
			measure.getSegment(0, f[1], path_dst, true);
			path_dst.transform(matrix);
			canvas.drawPath(path_dst, mPaint);
		}
		}
			
			

	}
	
	private void  completeLength(Path p)
	{
		lenght=0;
		pathNum=0;
		PathMeasure	temp=new PathMeasure(p, false);
		while(temp.nextContour())
		{
			Path path_dst=new Path();
			lenght+=temp.getLength();			
			temp.getSegment(0, temp.getLength(), path_dst, true);
			pathList.add(new PathEntity(path_dst, temp.getLength()));
			pathNum++;
		}
		
	}
	
	public class PathEntity
	{
		public Path path;
		public float lenght;
		public PathEntity(Path path, float lenght) {
			super();
			this.path = path;
			this.lenght = lenght;
		}
		
	}
	
	
	private float[] getIndex(float progress)
	{
		float index=-1;
		float others=0;
		/*index shengyu*/
		float[] result=new float[2] ;
		float all = 0;
		for (int i = 0; i < pathList.size(); i++) {
			float l=pathList.get(i).lenght;
			all+=l;
			if (progress<=all) {
				result[0]=index;
				result[1]=progress-(all-l);		
				break;
			}
			else
			{
				index++;
			}
		}
		
		return result;
	}
	
	private void layout()
	{
		if (pathRect!=null) {
			if (gravity==Gravity.RIGHT) {
				float dx=getWidth()-pathRect.width();
				dx=dx-mPaint.getStrokeWidth();
				matrix.postTranslate(dx, 0);
			}
			else if(gravity==(Gravity.RIGHT|Gravity.CENTER_VERTICAL))
			{
				float dx=getWidth()-pathRect.width();				
				float dy=(getHeight()-pathRect.height())/2;
				dx=dx-mPaint.getStrokeWidth()/2;
				dy=dy-mPaint.getStrokeWidth()/2;
				matrix.postTranslate(dx, dy);
			}
			else if(gravity==Gravity.BOTTOM)
			{
				float dy=getHeight()-pathRect.height();
				dy=dy-mPaint.getStrokeWidth()/2;
				matrix.postTranslate(0, dy);
			}
			else if(gravity==(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL))
			{
				float dx=(getWidth()-pathRect.width())/2;
				float dy=getHeight()-pathRect.height();
				dx=dx-mPaint.getStrokeWidth()/2;
				dy=dy-mPaint.getStrokeWidth()/2;
				matrix.postTranslate(dx, dy);
			}
			else if(gravity==Gravity.CENTER_HORIZONTAL)
			{
				float dx=(getWidth()-pathRect.width())/2;
				dx=dx-mPaint.getStrokeWidth()/2;
				matrix.postTranslate(dx, 0);
			}
			else if(gravity==Gravity.CENTER_VERTICAL)
			{
				float dy=(getHeight()-pathRect.height())/2;
				dy=dy-mPaint.getStrokeWidth()/2;
				matrix.postTranslate(0, dy);
			}
			else if(gravity==Gravity.CENTER)
			{
				
				float dx=(getWidth()-pathRect.width())/2;
				float dy=(getHeight()-pathRect.height())/2;
				dx=dx-mPaint.getStrokeWidth()/2;
				dy=dy-mPaint.getStrokeWidth()/2;
				matrix.postTranslate(dx, dy);
			}
		}
		
	}
	
	private int getW()
	{
		return getWidth()-pending*2>0?getWidth()-pending*2:0;
	}
	private int getH()
	{
		
		return getHeight()-pending*2>0?getHeight()-pending*2:0;
	}
	
	private void buildScale()
	{
		if (type!=null) {
			if (type==ScaleType.FIT_XY) {
				float sx=(float)getW()/(float)pathRect.width();
				float sy=(float)getH()/(float)pathRect.height();
				pathRect.right=(int) (sx*pathRect.width()+pathRect.left);
				pathRect.bottom=(int) (sy*pathRect.height()+pathRect.top);
				matrix.postScale(sx, sy);
				
			}
		}
	}
	public void sesScaleType(ScaleType type)
	{
		this.type=type;
		
	}
	
	
}
