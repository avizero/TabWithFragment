package com.example.avi.tabwithfragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import static android.R.id.list;

public class MyListFragment extends ListFragment implements OnItemClickListener {

	//String[] menutitles;
	//TypedArray menuIcons;
    String[] listSDir;
	Map listDir;

	CustomAdapter adapter;
	private List<RowItem> rowItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		return inflater.inflate(R.layout.list_fragment, null, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		//menutitles = getResources().getStringArray(R.array.titles);
		//menuIcons = getResources().obtainTypedArray(R.array.icons);

		listDir = StatusMpv.getListDir();

		rowItems = new ArrayList<RowItem>();

       // (Object[]) listDir.get("dirs");
       // ((String[]) listDir.get("dirs")).length;

        Object[] oD = (Object[]) listDir.get("dirs");
        String[] sD = Arrays.copyOf(oD, oD.length, String[].class);
        Arrays.sort(sD, String.CASE_INSENSITIVE_ORDER);

        Object[] oF = (Object[]) listDir.get("files");
        String[] sF = Arrays.copyOf(oF, oF.length, String[].class);
        Arrays.sort(sF, String.CASE_INSENSITIVE_ORDER);

       listSDir = concatStr (sD, sF);
        //listImg = imageIdGet(sD,sF);

        for (int i = 0; i < listSDir.length; i++) {
            if (i < sD.length + 1){
                RowItem items = new RowItem(listSDir[i], R.drawable.ic_folder_black_24dp);
                rowItems.add(items);
            }
            else {
                RowItem items = new RowItem(listSDir[i], R.drawable.ic_movie_black_24dp);
                rowItems.add(items);
            }

        }


	//	for (int i = 0; i < menutitles.length; i++) {
	//		RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(
	//				i, -1));
    //
	//		rowItems.add(items);
	//	}

		adapter = new CustomAdapter(getActivity(), rowItems);
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
        rowItems.get(position).getIcon();

        if (rowItems.get(position).getIcon() == R.drawable.ic_folder_black_24dp) {
            // Toast.makeText(MainActivity.this, "Change dir " + catforlist[+position] + " " + position, Toast.LENGTH_SHORT).show();
            DownloadTask mt = new DownloadTask(getActivity());
            mt.execute("getDir", listSDir[position]);

            Void result;   //TODO ужасный хак чтобы потождать пока данлоад вернет данные
            try {
                result = mt.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            listDir = StatusMpv.getListDir();
            rowItems = new ArrayList<RowItem>();
            Object[] oD = (Object[]) listDir.get("dirs");
            String[] sD = Arrays.copyOf(oD, oD.length, String[].class);
            Arrays.sort(sD, String.CASE_INSENSITIVE_ORDER);
            Object[] oF = (Object[]) listDir.get("files");
            String[] sF = Arrays.copyOf(oF, oF.length, String[].class);
            Arrays.sort(sF, String.CASE_INSENSITIVE_ORDER);
            listSDir = concatStr (sD, sF);
            for (int i = 0; i < listSDir.length; i++) {
                if (i < sD.length + 1){
                    RowItem items = new RowItem(listSDir[i], R.drawable.ic_folder_black_24dp);
                    rowItems.add(items);
                }
                else {
                    RowItem items = new RowItem(listSDir[i], R.drawable.ic_movie_black_24dp);
                    rowItems.add(items);
                }

            }
            adapter = new CustomAdapter(getActivity(), rowItems);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);

            adapter.notifyDataSetChanged();
            //tabLayout.addTab(tabLayout.newTab().setText("Filelist"));
            //listDir = StatusMpv.getListDir();
         //   con = showResult();
         //   catforlist = con.listSDir;
         //   imageId = con.listImg;
          //  CustomList adapter = new CustomList(MainActivity.this, catforlist, imageId);
          ///  list.setAdapter(adapter);
          // adapter.notifyDataSetChanged();
// запуск проигрывания
        } else {
            Toast.makeText(getActivity(), "Playing " + listSDir[position], Toast.LENGTH_SHORT).show();
            DownloadTask mt = new DownloadTask(getActivity());
            mt.execute("startPlay", listSDir[+position]);
        }


       // Toast.makeText(getActivity(), listSDir[position], Toast.LENGTH_SHORT)
		//		.show();

	}

    private String[] concatStr(String[] A, String[] B) {
        int aLen = A.length;
        int bLen = B.length;

        String[] C = new String[aLen+bLen+1];
        C[0] = "..";
        System.arraycopy(A, 0, C, 1, aLen);
        System.arraycopy(B, 0, C, aLen+1, bLen);
        return C;
    }
}
