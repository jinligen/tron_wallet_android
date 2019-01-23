package prochain.com.tronbox.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tron.api.GrpcAPI;
import org.tron.common.utils.ByteArray;
import org.tron.keystore.Wallet;
import org.tron.protos.Protocol;
import org.tron.walletcli.WalletApiWrapper;
import org.tron.walletserver.WalletApi;

import java.util.Optional;

import prochain.com.tronbox.R;
import prochain.com.tronbox.StatusBarUtils;
import prochain.com.tronbox.utils.fancyDataCenter;


public class walletFragment extends android.support.v4.app.Fragment {


    TextView wallet_tron_balance;
    TextView net_params;
    TextView cpu_params;


    String addr;

    public walletFragment() {
        // Required empty public constructor
    }


    public static walletFragment newInstance(String param1, String param2) {
        walletFragment fragment = new walletFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtils.setWindowStatusBarColor(getActivity(), R.color.color_white_transparent);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        TextView address = v.findViewById(R.id.address);
        return  v;

    }


    @Override
    public void onStart() {
        super.onStart();


    }



    static int initLoad = 0;

    public void onResume()
    {
        super.onResume();
        View v = getView();

        TextView address = v.findViewById(R.id.address);
        wallet_tron_balance = v.findViewById(R.id.wallet_tron_balance);
        net_params = v.findViewById(R.id.net_params);
        cpu_params = v.findViewById(R.id.cpu_params);
        addr = fancyDataCenter.getInstance().getTronAddress();
        address.setText(addr);



    }


    public void freshData()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateAccount(addr);
            }
        }).start();
    }

    private void updateAccount(String addr)
    {
        try {
            WalletApiWrapper walletApi = fancyDataCenter.getInstance().walletApi;
            if (walletApi==null)
            {
                walletApi = new WalletApiWrapper();
                walletApi.context = getActivity();
            }


            Protocol.Account account =  WalletApi.queryAccount(WalletApi.decodeFromBase58Check(addr));


            Log.d("wallet", "the account is " + account);

            Optional<GrpcAPI.AssetIssueList> list =  WalletApi.getAssetIssueByAccount( WalletApi.decodeFromBase58Check(addr));
            Log.d("wallet", "the account asset list is " + list);

            GrpcAPI.AccountResourceMessage resource = WalletApi.getAccountResource( WalletApi.decodeFromBase58Check(addr));

            Log.d("wallet", "the account resource is " + resource);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    double balance = account.getBalance() / 1000000;
                    wallet_tron_balance.setText(balance + "");

                    net_params.setText(resource.getFreeNetLimit() + "");

                    cpu_params.setText(resource.getEnergyLimit()-resource.getEnergyUsed() + "");
                }
            });



        }catch (Exception e)
        {
            Log.d("wallet", "the login error " + e.toString());

        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
