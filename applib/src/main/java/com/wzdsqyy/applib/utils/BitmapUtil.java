package com.wzdsqyy.applib.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BitmapUtil {

    public static Bitmap compressBitmap(String path, int reqsW, int reqsH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, reqsW, reqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 外部存储器的图片
     *
     * @param context
     * @return key为目录，键为目录下的文件
     */
    public static HashMap<String, ArrayList<String>> scanExternalImages(Context context) {
        return scanImages(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    /**
     * @param context
     * @param imageUri
     * @return
     */
    public final static HashMap<String, ArrayList<String>> scanImages(Context context, Uri imageUri) {
        String[] clumons = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_MODIFIED};
        String[] imageType = new String[]{"image/jpeg", "image/png", "image/jpg"};// 只查询jpeg和png的图片
        String where = MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=?";
        ContentResolver mContentResolver = context.getContentResolver();
        Cursor mCursor = mContentResolver.query(imageUri, clumons, where, imageType, MediaStore.Images.Media.DATE_MODIFIED);
        HashMap<String, ArrayList<String>> dirs = new HashMap<>();
        while (mCursor.moveToNext()) {
            int index = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            if (index != -1) {
                String imgPath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int end;
                if (TextUtils.isEmpty(imgPath) || (end = imgPath.lastIndexOf(File.separator)) == -1) {
                    continue;
                } else {
                    String dir = imgPath.substring(0, end);//获取目录
                    ArrayList<String> images = dirs.get(dir);
                    if (images == null) {
                        images = new ArrayList<>();
                        dirs.put(dir, images);
                    }
                    images.add(imgPath);
                }
            }
        }
        mCursor.close();
        return dirs;
    }

    public final static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * @return
     */
    public static File compress(String path,String save) {
        File source = new File(path);
        if (!source.exists()) {
            return null;
        } else {
            File desFile = new File(save);
            if (!desFile.exists()) {
                try {
                    desFile.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            int angle = getImageSpinAngle(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 1;
            BitmapFactory.decodeFile(path, options);
            int width = options.outWidth;
            int height = options.outHeight;
            int thumbW = width % 2 == 1 ? width + 1 : width;//保证是2的整数倍
            int thumbH = height % 2 == 1 ? height + 1 : height;//保证是2的整数倍
            //保证宽小于高
            if (thumbH > thumbW) {
                width = thumbW;
            } else {
                height = thumbH;
            }
            double ratio = ((double) width / height);
            double scale;
            if (ratio > 1 || ratio < 0.5) {
                int multiple = (int) Math.ceil(height / (1280.0 / ratio));
                thumbW = width / multiple;
                thumbH = height / multiple;
                scale = ((thumbW * thumbH) / (1280.0 * (1280 / ratio))) * 500;
                scale = scale < 100 ? 100 : scale;
            } else if (ratio > 0.5625) {
                if (height < 1664) {
                    scale = (width * height) / Math.pow(1664, 2) * 150;
                    scale = scale < 60 ? 60 : scale;
                } else if (height >= 1664 && height < 4990) {
                    thumbW = width / 2;
                    thumbH = height / 2;
                    scale = (thumbW * thumbH) / Math.pow(2495, 2) * 300;
                    scale = scale < 60 ? 60 : scale;
                } else if (height >= 4990 && height < 10240) {
                    thumbW = width / 4;
                    thumbH = height / 4;
                    scale = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
                    scale = scale < 100 ? 100 : scale;
                } else {
                    int multiple = height / 1280 == 0 ? 1 : height / 1280;
                    thumbW = width / multiple;
                    thumbH = height / multiple;
                    scale = (thumbW * thumbH) / Math.pow(2560, 2) * 300;
                    scale = scale < 100 ? 100 : scale;
                }
            } else {
                int multiple = height / 1280 == 0 ? 1 : height / 1280;
                thumbW = width / multiple;
                thumbH = height / multiple;
                scale = (thumbW * thumbH) / (1440.0 * 2560.0) * 200;
                scale = scale < 100 ? 100 : scale;
            }
            Bitmap bitmap = compressBitmap(source.getAbsolutePath(), width, height);
            bitmap = rotatingImage(angle, bitmap);
            return saveImage(desFile.getAbsolutePath(), bitmap, (long) scale);
        }
    }

    private File firstCompress(@NonNull File file) {
        int minSize = 60;
        int longSide = 720;
        int shortSide = 1280;
        String filePath = file.getAbsolutePath();
        long size = 0;
        long maxSize = file.length() / 5;
        int angle = getImageSpinAngle(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(filePath, options);
        int width = options.outWidth;
        int height = options.outHeight;
        //保证宽小于高
        if (height < width) {
            int temp = height;
            height = width;
            width = temp;
        }
        double scale = (double) width / (double) height;
        if (scale <= 1.0 && scale > 0.5625) {
            width = width > shortSide ? shortSide : width;
            height = width * height / width;
            size = minSize;
        } else if (scale <= 0.5625) {
            height = height > longSide ? longSide : height;
            width = height * width / height;
            size = maxSize;
        }
        Bitmap bitmap = compressBitmap(file.getAbsolutePath(), width, height);
        bitmap = rotatingImage(angle, bitmap);
        return saveImage(file.getAbsolutePath(), bitmap, size);
    }

    /**
     * obtain the image rotation angle
     *
     * @param path path of target image
     */
    private static int getImageSpinAngle(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
        }
        return degree;
    }

    /**
     * 旋转图片
     * rotate the image with specified angle
     *
     * @param angle  the angle will be rotating 旋转的角度
     * @param bitmap target image               目标图片
     */
    private static Bitmap rotatingImage(int angle, Bitmap bitmap) {
        //rotate image
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        //create a new image
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 保存图片到指定路径
     * Save image with specified size
     *
     * @param filePath the image file save path 储存路径
     * @param bitmap   the image what be save   目标图片
     * @param size     the file size of image   期望大小
     */
    private static File saveImage(String filePath, Bitmap bitmap, long size) {
        File result = new File(filePath.substring(0, filePath.lastIndexOf("/")));
        if (!result.exists() && !result.mkdirs()) return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);
        while (stream.toByteArray().length / 1024 > size) {
            stream.reset();
            options -= 6;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            fos.write(stream.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }

        return new File(filePath);
    }
}