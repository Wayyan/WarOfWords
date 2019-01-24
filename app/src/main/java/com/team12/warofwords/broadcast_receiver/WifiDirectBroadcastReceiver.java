package com.team12.warofwords.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import com.team12.warofwords.TwoPlayerActivity;

/**
 * Created by Way yan on 10/24/2018.
 */

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private TwoPlayerActivity mActivity;
    private boolean isStartConnecting = true;

    public WifiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, TwoPlayerActivity activity) {
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {

            } else {

            }
        }
        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mManager != null) {
                mManager.requestPeers(mChannel, mActivity.peerListListener);
            }
        }

        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (mManager == null)
                return;

            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {
                //isStartConnecting=false;
                mManager.requestConnectionInfo(mChannel, mActivity.connectionInfoListener);
            }
//            } else if (isStartConnecting) {
//                isStartConnecting=false;
//                Toast.makeText(mActivity, "Ignore", Toast.LENGTH_SHORT).show();
//            }
            else
            {
               // isStartConnecting=true;
                Toast.makeText(mActivity, "Disconnected", Toast.LENGTH_SHORT).show();
                //context.startActivity(new Intent(context,DisconnectActivity.class));

            }

        }
        if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }

    }
}
