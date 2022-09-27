package lychee.dote.client.gui.dashboard;

import lychee.dote.client.R;
import lychee.dote.client.R.layout;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClubFragment extends Fragment {

	public ClubFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_club, container, false);
	}

}
