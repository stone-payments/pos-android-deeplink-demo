using Android.App;
using Android.OS;
using Android.Support.V7.App;
using Android.Widget;
using System.Web;
using Android.Content;
using System;

namespace PaymentDeeplinkDemo
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme", MainLauncher = true)]
    [IntentFilter(new[] { Android.Content.Intent.ActionView },
              Categories = new[] { Android.Content.Intent.CategoryBrowsable, Android.Content.Intent.CategoryDefault },
              DataScheme = "xamarindeeplink",
              DataHost = "pay-response",
              AutoVerify = true
        )]
    public class MainActivity : AppCompatActivity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            SetContentView(Resource.Layout.activity_main);

            Button button = FindViewById<Button>(Resource.Id.deepLinkButton);
            TextView resultTextView = FindViewById<TextView>(Resource.Id.deepLinkTextView);

            Android.Net.Uri.Builder uriBuilder = new Android.Net.Uri.Builder();
            uriBuilder.Authority("pay");
            uriBuilder.Scheme("payment-app");

            uriBuilder.AppendQueryParameter("amount", "200");
            uriBuilder.AppendQueryParameter("editable_amount", "0"); // 1 = true and 0 = false
            uriBuilder.AppendQueryParameter("transaction_type", "credit"); //DEBIT, CREDIT, VOUCHER
            uriBuilder.AppendQueryParameter("installment_type", "merchant"); //MERCHANT, ISSUER, NONE
            uriBuilder.AppendQueryParameter("return_scheme", "xamarindeeplink");
            
            String order_id = null;
            if (order_id != null)
            {
                uriBuilder.AppendQueryParameter("order_id", order_id);
            }

            String installment_count = "2";
            if (installment_count != null)
            {
                uriBuilder.AppendQueryParameter("installment_count", installment_count); // 2 to 99
            }

            try
            {
                if (Intent.Data != null)
                {
                    resultTextView.Text = Intent.DataString;
                }
            }catch(Exception e)
            {
                resultTextView.Text = e.Message;
            }
            
            button.Click += delegate
            {
                Intent intent = new Intent(Intent.ActionView);
                intent.AddFlags(ActivityFlags.NewTask);
                intent.SetData(uriBuilder.Build());
                StartActivity(intent);
            };
        }
    }
}