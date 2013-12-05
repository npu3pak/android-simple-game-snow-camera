package com.example.BrightnessFiltering;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    public static final int UPDATE_PERIOD = 1;
    public static final int LETTERS_SPEED = 2;
    public static final int BUG_DOTS_COUNT = 5000;
    public static final int SHELF_DOTS_COUNT = 500;
    public static final int REQUEST_CODE_TAKE_PHOTO = 10;

    private Handler handler = new Handler();
    private CanvasView canvasView;
    private Position[] startPositions;
    private Uri fileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        canvasView = (CanvasView) findViewById(R.id.main_canvas);
        takePhoto();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, UPDATE_PERIOD);
    }

    private void createPoints() {
        for (int i = 0; i < BUG_DOTS_COUNT; i++) {
            Position pointPosition = startPositions[randomInt(startPositions.length)];
            canvasView.bugPoints.add(new Point(pointPosition.x, pointPosition.y));
        }
        for (int i = 0; i < SHELF_DOTS_COUNT; i++) {
            Position pointPosition = startPositions[randomInt(startPositions.length)];
            canvasView.shelfPoints.add(new Point(pointPosition.x, pointPosition.y));
        }
    }

    private int randomInt(int maxVal) {
        return (int) (Math.random() * maxVal);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateLettersPosition();
            handler.postDelayed(this, UPDATE_PERIOD);
        }
    };

    public void updateLettersPosition() {
        for (Point point : canvasView.shelfPoints) {
            if (point.x > 0 && point.y > 0) {
                point.x = point.x - LETTERS_SPEED;
            } else {
                Position pointPosition = startPositions[randomInt(startPositions.length)];
                point.x = pointPosition.x;
                point.y = pointPosition.y;
            }
        }
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    private static Uri getOutputMediaFileUri() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "npu3pak.ImageProcessing");
        if (!mediaStorageDir.exists())
            if (!mediaStorageDir.mkdirs())
                return null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                processPhoto(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else finish();
    }

    private void processPhoto(Bitmap photo) {
        startPositions = ImageAnalyzer.getBrightnessFilteredPositions(0.3F, photo);
        createPoints();
    }
}