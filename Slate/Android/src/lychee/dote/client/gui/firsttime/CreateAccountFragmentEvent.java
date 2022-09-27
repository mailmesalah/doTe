package lychee.dote.client.gui.firsttime;

//Event Interface to let fragments communicate with activity
public interface CreateAccountFragmentEvent {
	public void onButtonClicked(int currentFragment, int nextFragment);
}
