package prochain.com.tronbox.Fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.tron.api.GrpcAPI;
import org.tron.common.utils.ByteArray;
import org.tron.keystore.Wallet;
import org.tron.protos.Protocol;
import org.tron.walletcli.WalletApiWrapper;
import org.tron.walletserver.WalletApi;

import java.math.BigDecimal;
import java.util.Optional;

import prochain.com.tronbox.R;
import prochain.com.tronbox.StatusBarUtils;
import prochain.com.tronbox.utils.fancyDataCenter;
import prochain.com.tronbox.utils.fancyWebView;
import prochain.com.tronbox.wallet.TronToken;
import prochain.com.tronbox.wallet.TronTransferActivity;


public class walletFragment extends android.support.v4.app.Fragment {


    TextView wallet_tron_balance;
    TextView net_params;
    TextView cpu_params;


    String addr;
    double tronBalance;

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

    private void startWebview() {
        {
            Intent intent = new Intent(getActivity(), fancyWebView.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", "波场浏览器");

            String address = fancyDataCenter.getInstance().getTronAddress();
            bundle.putString("url", "https://tronscan.org/#/address/" + address);

            intent.putExtras(bundle);

            startActivity(intent);

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        TextView address = v.findViewById(R.id.address);
        TextView transfer = v.findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wallet_tron_balance.getText().length() == 0) {
                    return;
                }
                startTransfer();
            }
        });


        TextView chain_record = v.findViewById(R.id.chain_record);
        chain_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWebview();
            }
        });
        return v;

    }


    @Override
    public void onStart() {
        super.onStart();


    }


    static int initLoad = 0;

    public void onResume() {
        super.onResume();
        View v = getView();

        TextView address = v.findViewById(R.id.address);
        wallet_tron_balance = v.findViewById(R.id.wallet_tron_balance);
        net_params = v.findViewById(R.id.net_params);
        cpu_params = v.findViewById(R.id.cpu_params);
        addr = fancyDataCenter.getInstance().getTronAddress();
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText(null, addr);
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
        address.setText(addr);


    }


    public void freshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateAccount(addr);
            }
        }).start();
    }

    private void updateAccount(String addr) {
        try {
            WalletApiWrapper walletApi = fancyDataCenter.getInstance().walletApi;
            if (walletApi == null) {
                walletApi = new WalletApiWrapper();
                walletApi.context = getActivity();
            }


            Protocol.Account account = WalletApi.queryAccount(WalletApi.decodeFromBase58Check(addr));


            Log.d("wallet", "the account is " + account);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    BigDecimal big = new BigDecimal(account.getBalance());
                    big = big.divide(new BigDecimal(1000000));
                    double balance = big.floatValue();

                    String formatString = String.format("%.2f", balance);
                    wallet_tron_balance.setText(formatString);
                    tronBalance = balance;
                }
            });

            Optional<GrpcAPI.AssetIssueList> list = WalletApi.getAssetIssueByAccount(WalletApi.decodeFromBase58Check(addr));
            Log.d("wallet", "the account asset list is " + list);


            GrpcAPI.AccountResourceMessage resource = WalletApi.getAccountResource(WalletApi.decodeFromBase58Check(addr));

            Log.d("wallet", "the account resource is " + resource);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    net_params.setText(resource.getFreeNetLimit() + "");

                    cpu_params.setText(resource.getEnergyLimit() - resource.getEnergyUsed() + "");
                }
            });


        } catch (Exception e) {
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


    public void startTransfer() {
        TronToken tronToken = new TronToken();
        tronToken.balance = tronBalance + "";
        tronToken.symbol = "Tron";
        Intent intent = new Intent(getActivity(), TronTransferActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("TronToken", tronToken);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
