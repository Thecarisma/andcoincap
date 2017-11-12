package gig.com.andcoincap.base;

/**
 * Created by Thecarisma on 11-Nov-17.
 */

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gig.com.andcoincap.R;
import gig.com.andcoincap.library.FontUtil;

/**
 * I prefer a base activity so as to apply theme effect to all app activity
 * simultaneously and also declare variables that is available through out the activities
 * **/
public class BaseActivity  extends AppCompatActivity {

    /**
     * 'settings' is used to get user preference through out the program
     *  spinnerArray' to populate each card spinner
     *  'spinnerAdapter' the adapter attached to each spinner in each card
     * **/
    public static SharedPreferences settings ;
    List<String> spinnerArray ;
    ArrayAdapter<String> spinnerAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        /**
         * The default font "SERIF" sucks to me. UI matters to end-user
         * ok let me be clear i love OSWALD and PACIFICO font ;)
         * **/
        FontUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/" + settings.getString("FONT", "oswald.ttf"));
        getConversionrates() ;
    }

    public int getLocalLogo(int position) {
        int logo = R.drawable.us_flag ;
        if (position == 0 ) {
            logo = R.drawable.us_flag ;
        } else if (position == 1) {
            logo = R.drawable.nigeria ;
        } else if (position == 2) {
            logo = R.drawable.euro ;
        } else if (position == 3) {
            logo = R.drawable.britain ;
        } else if (position == 4) {
            logo = R.drawable.south_africa ;
        } else if (position == 5) {
            logo = R.drawable.india ;
        } else if (position == 6) {
            logo = R.drawable.canada ;
        } else if (position == 7) {
            logo = R.drawable.libya ;
        } else if (position == 8) {
            logo = R.drawable.brunei ;
        } else if (position == 9) {
            logo = R.drawable.singapore ;
        } else if (position == 10) {
            logo = R.drawable.brazil ;
        } else if (position == 11) {
            logo = R.drawable.isreal ;
        } else if (position == 12) {
            logo = R.drawable.saudi_arabia ;
        } else if (position == 13) {
            logo = R.drawable.ghana ;
        } else if (position == 14) {
            logo = R.drawable.turkey ;
        } else if (position == 15) {
            logo = R.drawable.new_zealand ;
        } else if (position == 16) {
            logo = R.drawable.switzerland ;
        } else if (position == 17) {
            logo = R.drawable.cayman ;
        } else if (position == 18) {
            logo = R.drawable.latvia ;
        } else if (position == 19) {
            logo = R.drawable.jordan ;
        }
        return logo ;
    }

    public String getSelectedCurrencyRate(int position, String crypto) {
        String currency = "USD " ; double value =0.0 ;
        String val = "6201.64" ; DecimalFormat df = new DecimalFormat("####0.00"); //we rounding to two decimal
        if (position == 0 ) {
            currency = "USD " ; val = settings.getString("usdrate", "7200.21"); // USD
        } else if (position == 1) {
            currency = "NGN " ; val = settings.getString("ngnrate", "2089321.84"); // NGN
        } else if (position == 2) {
            currency = "EUR " ; val = settings.getString("eurrate", "4500.4384"); // EUR
        } else if (position == 3) {
            currency = "GBP " ; val = settings.getString("gbprate", "4500.4384"); // GBP
        } else if (position == 4) {
            currency = "ZAR " ; val = settings.getString("zarrate", "4500.4384"); // ZAR
        } else if (position == 5) {
            currency = "INR " ; val = settings.getString("inrrate", "4500.4384"); // INR
        } else if (position == 6) {
            currency = "CAD " ; val = settings.getString("cadrate", "4500.4384"); // CAD
        } else if (position == 7) {
            currency = "LYD " ; val = settings.getString("lydrate", "4500.4384"); // LYD
        } else if (position == 8) {
            currency = "BND " ; val = settings.getString("bndrate", "4500.4384"); // BND
        } else if (position == 9) {
            currency = "SGD " ; val = settings.getString("sgdrate", "4500.4384"); // SGD
        } else if (position == 10) {
            currency = "BRL " ; val = settings.getString("brlrate", "4500.4384"); // BRl
        } else if (position == 11) {
            currency = "ILS " ; val = settings.getString("ilsrate", "4500.4384"); // ILS
        } else if (position == 12) {
            currency = "RUH " ; val = settings.getString("ruhrate", "4500.4384"); // RUH
        } else if (position == 13) {
            currency = "GHS " ; val = settings.getString("ghsrate", "4500.4384"); // GHS
        } else if (position == 14) {
            currency = "TRY " ; val = settings.getString("tryrate", "4500.4384"); // TRY
        } else if (position == 15) {
            currency = "NZD " ; val = settings.getString("nzdrate", "4500.4384"); // NZD
        } else if (position == 16) {
            currency = "CHF " ; val = settings.getString("chfrate", "4500.4384"); // CHF
        } else if (position == 17) {
            currency = "KYD " ; val = settings.getString("kydrate", "4500.4384"); // KYD
        } else if (position == 18) {
            currency = "LVL " ; val = settings.getString("lvlrate", "4500.4384"); // LVL
        } else if (position == 19) {
            currency = "JOD " ; val = settings.getString("jodrate", "4500.4384"); // JOD
        }
        if (crypto.equals("ETH")) {
            value = Double.parseDouble(val) * Double.parseDouble(settings.getString("ethrate", "504.4384"));
            currency = currency + df.format(value) ;
        } else {
            currency = currency + val ;
        }
        

        return currency ;
    }

    public String getDenom(int position) {
        String currency = "US Dollar" ;

        if (position == 0 ) {
            currency = "US Dollar" ;
        } else if (position == 1) {
            currency = "Nigeria Naira" ;
        } else if (position == 2) {
            currency = "Euro" ;
        } else if (position == 3) {
            currency = "British Pound" ;
        } else if (position == 4) {
            currency = "South Africa Rand" ;
        } else if (position == 5) {
            currency = "Indian Rupee" ;
        } else if (position == 6) {
            currency = "Canadian Dollar" ;
        } else if (position == 7) {
            currency = "Libyan Dollar" ;
        } else if (position == 8) {
            currency = "Brunei Dollar" ;
        } else if (position == 9) {
            currency = "Singapore Dollar" ;
        } else if (position == 10) {
            currency = "Brazillian Real" ;
        } else if (position == 11) {
            currency = "Isreal New Shekel" ;
        } else if (position == 12) {
            currency = "Riyadh" ;
        } else if (position == 13) {
            currency = "Ghananian Cedi" ;
        } else if (position == 14) {
            currency = "Turkish Lira" ;
        } else if (position == 15) {
            currency = "New Zealand Dollar" ;
        } else if (position == 16) {
            currency = "Switzerland Franc" ;
        } else if (position == 17) {
            currency = "Cayman Island Dolar" ;
        } else if (position == 18) {
            currency = "Latvian Lat" ;
        } else if (position == 19) {
            currency = "Jordanian Diinar" ;
        }

        return currency ;
    }

    public String getSelectedFlatCurrency(int position) {
        String currency = "USD" ;
        if (position == 0 ) {
            currency = "USD" ; // USD
        } else if (position == 1) {
            currency = "NGN" ; // NGN
        } else if (position == 2) {
            currency = "EUR" ; // EUR
        } else if (position == 3) {
            currency = "GBP" ; // GBP
        } else if (position == 4) {
            currency = "ZAR" ; // ZAR
        } else if (position == 5) {
            currency = "INR" ; // INR
        } else if (position == 6) {
            currency = "CAD" ; // CAD
        } else if (position == 7) {
            currency = "LYD" ; // LYD
        } else if (position == 8) {
            currency = "BND" ; // BND
        } else if (position == 9) {
            currency = "SGD" ; // SGD
        } else if (position == 10) {
            currency = "BRL" ; // BRl
        } else if (position == 11) {
            currency = "ILS" ; // ILS
        } else if (position == 12) {
            currency = "RUH" ; // RUH
        } else if (position == 13) {
            currency = "GHS" ; // GHS
        } else if (position == 14) {
            currency = "TRY" ; // TRY
        } else if (position == 15) {
            currency = "NZD" ; // NZD
        } else if (position == 16) {
            currency = "CHF" ; // CHF
        } else if (position == 17) {
            currency = "KYD" ; // KYD
        } else if (position == 18) {
            currency = "LVL" ; // LVL
        } else if (position == 19) {
            currency = "JOD" ; // JOD
        }
        return currency ;
    }

    public void inflateSpinner(Spinner spinner, int position) {
        spinnerArray =  new ArrayList<String>();
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerArray);
        String[] banks = settings.getString("currencies",
                "USD>==<NGN>==<EUR>==<GBP>==<ZAR>==<INR>==<CAD>==<LYD>==<BND>==<SGD>==<BRL>==<ILS>==<RUH>==<GHS>==<TRY>==<NZD>==<CHF>==<KYD>==<LVL>==<JOD")
                .split(">==<");
        for (String bank : banks) {
            spinnerArray.add(bank);
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter); spinner.setSelection(position);
    }

    /**
     * - As a standard app we dontwant to fetch price every now and then so we update price evry
     * 5 seconds and easy on network and UI thread.
     * - All the prices and rate are saved in preference, cos we dont want a situation whereby the
     * app is useless if there is no network.
     * - Also we saved our card in prefence too."What where are the cards" is a disappointing expression
     * if user reopen the app and the cards are all gone. BOOM
     **/
    private void getConversionrates() {
        new GetPriceRates().execute();
    }

    class GetPriceRates extends AsyncTask<Void, Void, Boolean> {

        boolean success = false ;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... args) {
            try {
                JSONParser jsonParser = new JSONParser();
                JSONObject json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD");
                /**GET THE USD RATE**/settings.edit().putString("usdrate", json.getString("USD")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=NGN");
                /**GET THE NGN RATE**/ settings.edit().putString("ngnrate", json.getString("NGN")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR");
                /**GET THE EUR RATE**/ settings.edit().putString("eurrate", json.getString("EUR")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=GBP");
                /**GET THE GBP RATE**/ settings.edit().putString("gbprate", json.getString("GBP")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=ZAR");
                /**GET THE ZAR RATE**/ settings.edit().putString("zarrate", json.getString("ZAR")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=INR");
                /**GET THE INR RATE**/ settings.edit().putString("inrrate", json.getString("INR")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=CAD");
                /**GET THE CAD RATE**/ settings.edit().putString("cadrate", json.getString("CAD")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR");
                /**GET THE LYD RATE**/ settings.edit().putString("lydrate", json.getString("EUR")).apply(); //lyd has error
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=BND");
                /**GET THE BND RATE**/ settings.edit().putString("bndrate", json.getString("BND")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=SGD");
                /**GET THE SGD RATE**/ settings.edit().putString("sgdrate", json.getString("SGD")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=BRL");
                /**GET THE BRL RATE**/ settings.edit().putString("brlrate", json.getString("BRL")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=ILS");
                /**GET THE ILS RATE**/ settings.edit().putString("ilsrate", json.getString("ILS")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=R");
                /**GET THE RUH RATE**/ settings.edit().putString("ruhrate", json.getString("R")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=GHS");
                /**GET THE GHS RATE**/ settings.edit().putString("ghsrate", json.getString("GHS")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=TRY");
                /**GET THE TRY RATE**/ settings.edit().putString("tryrate", json.getString("TRY")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=NZD");
                /**GET THE NZD RATE**/ settings.edit().putString("nzdrate", json.getString("NZD")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=CHF");
                /**GET THE CHF RATE**/ settings.edit().putString("chfrate", json.getString("CHF")).apply();
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR");
                /**GET THE KYD RATE**/ settings.edit().putString("kydrate", json.getString("EUR")).apply(); //kyd has error
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR");
                /**GET THE LVL RATE**/ settings.edit().putString("lvlrate", json.getString("EUR")).apply(); //lvl has error
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=JOD");
                /**GET THE JOD RATE**/ settings.edit().putString("jodrate", json.getString("JOD")).apply();
                /**
                 * ok now for ethereum. There is no need to get each currency over the network so we ease up the
                 * app by get the conversion rate of ethereum to btc and convert when neccessary ;)
                 **/
                json = jsonParser.getJSONFromUrl("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=BTC");
                /**GET THE ETH RATE**/ settings.edit().putString("ethrate", json.getString("BTC")).apply();
            } catch (Exception e) {
                Log.e("ANDCOINCAP::MAINTAG", e.getMessage());
            }
            //String json = jsonParser.makeServiceCall(url);
            //Log.e(BLOGTAG, "Value : "+json);

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }


}
