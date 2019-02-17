package com.example.trivia;

import android.os.AsyncTask;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DataGetter {

    private String urlString;

    public DataGetter(String urlString) {
        this.urlString = urlString;
    }

    public void getData(ResponseCallback callback) {
        new AsyncGetter(callback).execute();
    }

    private class AsyncGetter extends AsyncTask<Void, Void, Integer> {

        private String response;
        private ResponseCallback callback;

        public AsyncGetter(ResponseCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    connection.disconnect();
                    return 2;
                }
                Scanner scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    response = scanner.next();
                }
                connection.disconnect();
                return 0;
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return 1;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer != 0) {
                callback.onError(integer);
            } else {
                callback.onSuccess(response);
            }
        }
    }

    public interface ResponseCallback {
        void onError(int errorCode);
        void onSuccess(String response);
    }
}
