package com.asus.sheldon.camera4fun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.camera2.params.Face;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FaceDetection implements Camera.FaceDetectionListener {

    private String TAG="FaceDetection:";
    private boolean isDetecting=false;
    private Context mContext;
    private FaceView faceView;
    private int mCamID=0;

    public FaceDetection(Context c, FaceView mFV){
        mContext = c;
        faceView = mFV;
        //faceView.setMinimumHeight(500);
        //faceView.setMinimumWidth(300);
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        //Log.e(TAG,"faces.length :"+faces.length);

        if(faces != null){
            if (faces.length > 0){
                /*
                Log.e(  TAG, "face detected: "+ faces.length +
                        "X: " + faces[0].rect.centerX() +
                        "Y: " + faces[0].rect.centerY() );
                */
                faceView.setFaces(faces ,mCamID);
                faceView.setVisibility(View.VISIBLE);
            } else{
                faceView.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void startFaceDetection(Camera mCamera, FaceDetection fd , int mCI){
        // Try starting Face Detection
        mCamID = mCI;
        Camera.Parameters params = mCamera.getParameters();
        // start face detection only *after* preview has started
        if (params.getMaxNumDetectedFaces() > 0){
            // camera supports face detection, so can start it:
            isDetecting=true;
            Log.v(TAG, "face number:"+params.getMaxNumDetectedFaces());
            mCamera.setFaceDetectionListener(fd);
            mCamera.startFaceDetection();
        }
    }

    public void stopFaceDetection(Camera mCamera) {
        if (isDetecting==true) {
            Log.d(TAG, "stop FaceDetection:");
            faceView.clearFaces();
            mCamera.setFaceDetectionListener(null);
            mCamera.stopFaceDetection();
            isDetecting=false;
        }
    }
}


