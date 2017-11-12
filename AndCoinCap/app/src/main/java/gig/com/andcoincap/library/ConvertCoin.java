package gig.com.andcoincap.library;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import gig.com.andcoincap.R;

import static gig.com.andcoincap.base.BaseActivity.settings;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ConvertCoin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConvertCoin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view ;
    EditText cryptoValue, flatValue ;

    public ConvertCoin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConvertCoin.
     */
    // TODO: Rename and change types and number of parameters
    public static ConvertCoin newInstance(String param1, String param2) {
        ConvertCoin fragment = new ConvertCoin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_convert_coin, container, false);
        cryptoValue = (EditText) view.findViewById(R.id.coin_value);
        flatValue = (EditText) view.findViewById(R.id.flat_value);
        assignListeners();
        return view ;
    }

    private void assignListeners() {
        cryptoValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence inputString, int arg1,
                                      int arg2, int arg3) {
                try {
                    if (mParam2.equals("BTC")) {
                        double value = Double.parseDouble(inputString.toString()) * Double.parseDouble(settings.getString(mParam1.toLowerCase()+"rate", "5000.65"));
                        flatValue.setText(String.valueOf(value));
                    } else if (mParam2.equals("ETH")) {
                        double value = Double.parseDouble(inputString.toString()) * Double.parseDouble(settings.getString(mParam1.toLowerCase()+"rate", "5000.65"));
                        value = value * Double.parseDouble(settings.getString("ethrate", "5000.65")) ;
                        flatValue.setText(String.valueOf(value));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {



            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        /**flatValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence inputString, int arg1,
                                      int arg2, int arg3) {
                try {
                    if (mParam2.equals("BTC")) {
                        double value = Double.parseDouble(inputString.toString()) / Double.parseDouble(settings.getString(mParam1.toLowerCase()+"rate", "5000.65"));
                        cryptoValue.setText(String.valueOf(value));
                    } else if (mParam2.equals("ETH")) {
                        double value = Double.parseDouble(inputString.toString()) / Double.parseDouble(settings.getString(mParam1.toLowerCase()+"rate", "5000.65"));
                        value = value / Double.parseDouble(settings.getString("ethrate", "5000.65")) ;
                        cryptoValue.setText(String.valueOf(value));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {



            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });**/
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
