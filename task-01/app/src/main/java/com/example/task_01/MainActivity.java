package com.example.task_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private static String DOWNLOAD_URL = "http://dropbox.sandbox2000.com/intrvw/SampleVideo_1280x720_30mb.mp4";

    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;
    int notificationId = 1;
    int PROGRESS_MAX = 100;

    //keeps track of the activity lifecycle for showing notification progress
    private boolean isPaused;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        //initialize notification to show progress
        notificationManager = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID);
        builder.setContentTitle("Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        // Issue the initial notification with zero progress
        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 0;
        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);

        findViewById(R.id.download_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadFileAsyncTask().execute(DOWNLOAD_URL);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        notificationManager.notify(notificationId, builder.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
        notificationManager.cancel(notificationId);
    }

    //DownloadFileAsyncTask runs on background thread and downloads file from internet
    //also updates the UI with progress and status
    public class DownloadFileAsyncTask extends AsyncTask<String, Integer, String> {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_horizontal);

        @Override
        protected String doInBackground(String... strings) {
            int count;
            try {
                URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lenghtOfFile = connection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream("/sdcard/Download/SampleVideo_1280x720_30mb.mp4");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress((int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return "Task Completed.";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(0);
            Toast.makeText(MainActivity.this, "Download will start shortly!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, "Download complete!", Toast.LENGTH_SHORT).show();
            if (isPaused) {
                // When done, update the notification one more time to remove the progress bar
                builder.setContentText("Download complete")
                        .setProgress(0, 0, false);
                notificationManager.notify(notificationId, builder.build());
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            if (isPaused) {
                // To show progress, update PROGRESS_CURRENT and update the notification with:
                builder.setContentText("Download in progress")
                        .setProgress(PROGRESS_MAX, values[0], false);
                notificationManager.notify(notificationId, builder.build());
            }
        }
    }
}
