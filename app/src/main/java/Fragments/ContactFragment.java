package Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.techtitudetribe.aadharshila.R;

public class ContactFragment extends Fragment {

    private static String number = "+91 6397213673";
    private static final int REQUEST_CALL = 1;
    private LinearLayout layoutGmail,layoutPhone;
    private CardView layoutFacebook,layoutTwitter,layoutBrowser;
    private Button message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.contact_fragment,container,false);

        layoutFacebook = v.findViewById(R.id.layout_facebook);
        layoutTwitter = v.findViewById(R.id.layout_twitter);
        layoutGmail = v.findViewById(R.id.layout_gmail);
        layoutPhone=v.findViewById(R.id.layout_phone);
        layoutBrowser =v.findViewById(R.id.layout_browser);
        message = v.findViewById(R.id.message_contact);

        layoutFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = openFacebook(getActivity());
                startActivity(facebookIntent);
            }
        });
        layoutGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = openGmail(getActivity());
                startActivity(emailIntent);
            }
        });
        layoutBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://satkarya.000webhostapp.com"));
                startActivity(webIntent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = openWhatsapp(getActivity());
                startActivity(messageIntent);
            }
        });
        layoutTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = getOpenTwitterIntent(getActivity());
                startActivity(twitterIntent);
            }
        });
        layoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        return v;
    }
    private void makePhoneCall() {
        String number = "8218852235";
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(getActivity(),"Permission Denied...",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Intent openFacebook(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/155533938413508"));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Please install facebook...", Toast.LENGTH_SHORT).show();
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Satkarya"));
        }

    }
    public static Intent openGmail(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.google.android.gm", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:e.Aadharshila.techtitudetribe@gmail.com"));
        } catch (Exception ex) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/2/#inbox?compose=new"));
        }
    }
    public static Intent openWhatsapp(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.whatsapp", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + number));
        } catch (Exception ex) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + number));
        }
    }

    public static Intent getOpenTwitterIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.twitter.android", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/satkarya_rec"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/satkarya_rec"));
        }
    }
}
