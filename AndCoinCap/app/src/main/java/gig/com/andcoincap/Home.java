package gig.com.andcoincap;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gig.com.andcoincap.library.ConvertCoin;

import static gig.com.andcoincap.MainActivity.addCardButton;
import static gig.com.andcoincap.base.BaseActivity.settings;


public class Home extends Fragment {

    ArrayList<CardModel> dataModels;
    private static CustomAdapter adapter;
    View view ;
    WebView btcChart, ethChart ;
    ListView listView ;

    class CardModel {
        String cryptoCurrency ;
        String flatCurrency ;
        String rateTag ;
        int selectedDenom ;
        int savedIndex ;

        public CardModel(String cryptoCurrency, String flatCurrency, String rateTag, int selectedDenom) {
            this.cryptoCurrency = cryptoCurrency ; this.flatCurrency = flatCurrency ;
            this.rateTag = rateTag ; this.selectedDenom = selectedDenom ;
        }
    }

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //btcChart = (WebView) view.findViewById(R.id.btc_chart_webview);
        //ethChart = (WebView) view.findViewById(R.id.eth_chart_webview);
        //treatWebView() ;
        listView = (ListView) view.findViewById(R.id.card_list_view);
        dataModels = new ArrayList<>();
        if (settings.getInt("cards_count", 0) == 0) {
            CardModel cardModel = new CardModel("BTC", "USD", "USD 7012.32", 0);
            cardModel.savedIndex = settings.getInt("cards_count", 0) ; dataModels.add(cardModel); saveCard(cardModel);
            int count = settings.getInt("cards_count", 0) + 1;
             settings.edit().putInt("cards_count", count).apply();
        } else {
            getSavedCards(dataModels);
        }
        adapter = new CustomAdapter(dataModels, getActivity());
        listView.setAdapter(adapter); //settings.edit().putInt("cards_count", 0).apply();

        addCardButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CardModel cardModel = new CardModel("BTC", "USD", "USD 7012.32", 0);
                cardModel.savedIndex = settings.getInt("cards_count", 0) ;
                dataModels.add(cardModel); saveCard(cardModel);
                int count = settings.getInt("cards_count", 0) + 1;
                Log.e("ANDCOINCAP::HOME", "OLD CARD : "+settings.getInt("cards_count", 0));
                settings.edit().putInt("cards_count", count).apply();
                adapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG)
                        .show();
            }
        });

        return view ;
    }

    /**
     * Now we get the saved cards on create so user don't lost all saved info
     * Using database is efficient but why the load and the stress if preference can
     * handle it. Now we load saved card dynamically ;)
     *
     * @param dataModels the arraylist to populate with saved data
     **/
    private void getSavedCards(ArrayList<CardModel> dataModels) {
        for (int a = 0; a < settings.getInt("cards_count", 0); a++) {
            String savedCard = settings.getString("saved_card"+a, "we ran>==< into an >==<error pls reset all your cards>==<0") ;
            if (savedCard.contains("BTC") || savedCard.contains("ETH")) {
                String[] detail = savedCard.split(">==<"); if (detail[0].equals("BTC") || detail[0].equals("ETH")) {
                    dataModels.add(new CardModel(detail[0], detail[1], detail[2], Integer.parseInt(detail[3]))); }
            }

        }
    }

    /**
     * ok now this method is incharge of storing the card in the prefernce
     * @param cardModel the card model to store in the preference
     */
    private void saveCard(CardModel cardModel) {
        settings.edit()
                .putString("saved_card"+settings.getInt("cards_count", 0),
                        cardModel.cryptoCurrency+">==<"+cardModel.flatCurrency+">==<"+cardModel.rateTag+">==<"+cardModel.selectedDenom)
                .apply();
    }


    private class CustomAdapter extends ArrayAdapter<CardModel> implements View.OnClickListener{

        private ArrayList<CardModel> dataSet;
        Context mContext;

        // View lookup cache
        private class ViewHolder {
            TextView txtRate;
            TextView txtCurencyDenom;
            ImageView denomLogo;
            ImageView ccmLogo;
            Spinner currency ;
            ImageButton deleteButton ;
            LinearLayout layout ;
        }

        CustomAdapter(ArrayList<CardModel> data, Context context) {
            super(context, R.layout.card_inflator, data);
            this.dataSet = data;
            this.mContext=context;

        }

        @Override
        public void onClick(View v) {

            int position=(Integer) v.getTag();
            Object object= getItem(position);

            switch (v.getId())
            {
                case R.id.crypto_icon:
                    CardModel cardModel= dataModels.get(position);
                    if (cardModel.cryptoCurrency .equals("BTC")) {
                        cardModel.cryptoCurrency = "ETH" ;
                    } else {
                        cardModel.cryptoCurrency = "BTC" ;
                    }
                    adapter.notifyDataSetChanged();
                    //Toast.makeText(getActivity(), settings.getString("ethrate", "4500.4384"), Toast.LENGTH_LONG).show();
                    break;
                case R.id.delete_button:
                    dataModels.remove(position); int count = settings.getInt("cards_count", 1) ;
                    /**
                     *  now we delete a card from pref
                     **/
                    settings.edit().putString("saved_card"+position,"") .apply();
                    count-- ; settings.edit().putInt("cards_count", count).apply();
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.card_main_layout:
                    CardModel cardModell = dataModels.get(position);
                    /**
                     *  now we move to the convert fragment with all neccessary parameters ;)
                     **/
                    Toast.makeText(getActivity(), ""+cardModell.flatCurrency, Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).fragment = ConvertCoin.newInstance(cardModell.flatCurrency, cardModell.cryptoCurrency);
                    ((MainActivity) getActivity()).notifyFragmentManager(position);
                    break;
            }
        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final CardModel CardModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            final ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.card_inflator, parent, false);
                viewHolder.txtCurencyDenom = (TextView) convertView.findViewById(R.id.card_denom);
                viewHolder.txtRate = (TextView) convertView.findViewById(R.id.card_rate);
                viewHolder.ccmLogo = (ImageView) convertView.findViewById(R.id.crypto_icon);
                viewHolder.denomLogo = (ImageView) convertView.findViewById(R.id.denom_icon);
                viewHolder.currency = (Spinner) convertView.findViewById(R.id.spinner);
                viewHolder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_button);
                viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.card_main_layout);

                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result=convertView; viewHolder.currency.setSelection(position);
            }

            setViewHolder(viewHolder, CardModel, 0);
            viewHolder.currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CardModel.rateTag = ((MainActivity) getActivity()).getSelectedCurrencyRate(position, CardModel.cryptoCurrency);
                    CardModel.flatCurrency = ((MainActivity) getActivity()).getSelectedFlatCurrency(position);
                    CardModel.selectedDenom = position ;
                    //viewHolder.currency.setSelected(true);
                    setViewHolder(viewHolder, CardModel, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            /**
             *  now to ensure when we re launch our app the selected currency persist we modify the
             *  preference at the given or lemme say current index :0
             **/
            settings.edit().putString("saved_card"+position,
             CardModel.cryptoCurrency+">==<"+CardModel.flatCurrency+">==<"+CardModel.rateTag+">==<"+CardModel.selectedDenom)
             .apply();
            viewHolder.ccmLogo.setOnClickListener(this);
            viewHolder.ccmLogo.setTag(position);
            viewHolder.deleteButton.setOnClickListener(this);
            viewHolder.deleteButton.setTag(position);
            viewHolder.layout.setOnClickListener(this);
            viewHolder.layout.setTag(position);
            //Toast.makeText(getActivity(), "SAVED INDEX : "+position, Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).inflateSpinner(viewHolder.currency, CardModel.selectedDenom);
            // Return the completed view to render on screen
            return convertView;
        }

        private void setViewHolder(ViewHolder viewHolder, CardModel CardModel, int pos) {
            viewHolder.txtRate.setText(CardModel.rateTag);
            int coinIcon = R.drawable.btc ;
            if (CardModel.cryptoCurrency.equals("BTC")) {
                coinIcon = R.drawable.btc ;
            } else if (CardModel.cryptoCurrency.equals("ETH")) {
                coinIcon = R.drawable.eth ;
            } viewHolder.ccmLogo.setImageResource(coinIcon);
            viewHolder.denomLogo.setImageResource(((MainActivity) getActivity()).getLocalLogo(pos));
            viewHolder.txtCurencyDenom.setText(((MainActivity) getActivity()).getDenom(pos));
        }
    }


    private void treatWebView() {
        btcChart.getSettings().setJavaScriptEnabled(true); /** sorry i had to**/
        btcChart.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        btcChart.loadUrl("file:///android_asset/webpages/btcchart.html");
        ethChart.getSettings().setJavaScriptEnabled(true); /** sorry i had to**/
        ethChart.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        ethChart.loadUrl("file:///android_asset/webpages/ethchart.html");
        btcChart.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("ANDCOINCAP::MAIN", description);
            }
        });
        ethChart.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("ANDCOINCAP::MAIN", description);
            }
        });
    }

}
