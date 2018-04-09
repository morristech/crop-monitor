package com.celpa.celpaapp.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitmapUtils {

    private static final String BASE_IMG_NAME = "celpa";
    private static final String IMG_EXT = ".jpg";

    public static String saveBitmapToStorage(Context context, byte[] jpeg) {
        Bitmap result = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);

        String fileName = BASE_IMG_NAME + System.currentTimeMillis() / 1000 + IMG_EXT;
        File file = new File(context.getExternalFilesDir(null), fileName);

        try {
            OutputStream fOut = new FileOutputStream(file);
            result.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    public static Bitmap createBitmap(Context context, byte[] jpeg) {
        Bitmap result = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);

        String fileName = BASE_IMG_NAME + System.currentTimeMillis() / 1000 + IMG_EXT;
        File file = new File(context.getExternalFilesDir(null), fileName);

        try {
            OutputStream fOut = new FileOutputStream(file);
            result.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Bitmap getBitmapFromStorage(Context context, String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap result = BitmapFactory.decodeFile(filePath, options);

        return result;
    }

}
