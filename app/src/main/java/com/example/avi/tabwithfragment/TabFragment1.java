package com.example.avi.tabwithfragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.timroes.axmlrpc.XMLRPCClient;

public class TabFragment1 extends Fragment {

    // считаем нажатия кнопки
    private int mButtonPressed = 0;

    // счетчик времени
    private long mTime = 0L;
    private TextView mCounterTextView;
    private TextView mTimeTextView;
    private SeekBar mSeekbar;
    private TextView mTextFilename;
    final Handler mHandler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_1, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mTimeTextView = (TextView) getActivity().findViewById(R.id.timePosTextView);
        mTextFilename = (TextView) getActivity().findViewById(R.id.textFilename);
        mCounterTextView = (TextView) getActivity().findViewById(R.id.durationTextView);
//        if (mTime == 0L) {
//            mTime = SystemClock.uptimeMillis();
//            mHandler.removeCallbacks(timeUpdaterRunnable);
//            // Добавляем Runnable-объект timeUpdaterRunnable в очередь
//            // сообщений, объект должен быть запущен после задержки в 100 мс
//            mHandler.postDelayed(timeUpdaterRunnable, 100);
//        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
  //                      new SyncAsyncTask().execute();
                        new ControlsAsyncTask().execute();
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000); //it executes this every 1000ms

    }


        // Описание Runnable-объекта
//    private Runnable timeUpdaterRunnable = new Runnable() {
//        public void run() {
//            // вычисляем время
//            final long start = mTime;
//            long millis = SystemClock.uptimeMillis() - start;
//            int second = (int) (millis / 1000);
//            int min = second / 60;
//            second = second % 60;
//            // выводим время
//            mTimeTextView.setText("" + min + ":" + String.format("%02d", second));
//            // повторяем через каждые 2000 миллисекунд
//            mHandler.postDelayed(this, 2000);
//        }
//    };

    @Override
    public void onPause() {
        // Удаляем Runnable-объект для прекращения задачи
//        mHandler.removeCallbacks(timeUpdaterRunnable);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Добавляем Runnable-объект
//        mHandler.postDelayed(timeUpdaterRunnable, 100);
    }
    public void onClick(View v) {
        mCounterTextView.setText("" + ++mButtonPressed);
    }
    class ControlsAsyncTask extends AsyncTask<String, Void, StatusMpv> {

        private Exception exception;

        protected StatusMpv doInBackground(String... urls) {
            try {
//                URL url = new URL(urls[0]);
//                SAXParserFactory factory = SAXParserFactory.newInstance();
//                SAXParser parser = factory.newSAXParser();
//                XMLReader xmlreader = parser.getXMLReader();
//                RssHandler theRSSHandler = new RssHandler();
//                xmlreader.setContentHandler(theRSSHandler);
//                InputSource is = new InputSource(url.openStream());
//                xmlreader.parse(is);
                XMLRPCClient client = new XMLRPCClient(new URL(StatusMpv.getServerAddress()));
                client.setTimeout(5); //5 sec
                StatusMpv.setControlsMap((HashMap)client.call("getControlsMap"));
                //StatusMpv.setCurrentPlayFile((String) client.call("getFilename"));
                //StatusMpv.setCurrentPlayFile((String)client.call("getFilename"));
               // System.out.println((String)client.call("getFilename"));
             //   System.out.println(StatusMpv.getCurrentPlayFile());
//                return theRSSHandler.getFeed();
                return null;
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }

        protected void onPostExecute(StatusMpv feed) {
            //String time = (String)StatusMpv.getControlsMap().get("time-pos");
           // mTimeTextView.setText("" + time + ":" + String.format("%02d", time));
           //System.out.println(StatusMpv.getCurrentPlayFile());
            System.out.println(Arrays.toString(StatusMpv.getControlsMap().entrySet().toArray()));
            //String time = String.valueOf(StatusMpv.getControlsMap().get("time-pos"));
            int time = (int)StatusMpv.getControlsMap().get("time-pos");

           // int hours = time / 3600;
            //int minutes = (time % 3600) / 60;
            int minutes = time / 60;
            int seconds = time % 60;

//            mTimeTextView.setText("" + min + ":" + String.format("%02d", second));

           // timeString = String.format("%02d",seconds);
            mTimeTextView.setText(String.format("%02d",minutes) + ":" + String.format("%02d",seconds));
            mSeekbar = (SeekBar)getActivity().findViewById(R.id.seekBar6);
            mSeekbar.setMax(1300); //TODO проверить существование int
            mSeekbar.setProgress(time);

            mTextFilename.setText((String )StatusMpv.getControlsMap().get("filename"));
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}