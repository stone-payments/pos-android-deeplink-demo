using Android.App;
using Android.OS;
using Android.Support.V7.App;
using Android.Runtime;
using Android.Widget;
using System.Linq;
using BR.Com.Stone.Posandroid.Paymentapp.Deeplink;
using BR.Com.Stone.Posandroid.Paymentapp.Deeplink.Model;
using BR.Com.Stone.Posandroid.Paymentapp.Deeplink.Exception;
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
        PaymentDeeplink payment;
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            SetContentView(Resource.Layout.activity_main);
            payment = new PaymentDeeplink(this);
            Button button = FindViewById<Button>(Resource.Id.deepLinkButton);
            TextView resultTextView = FindViewById<TextView>(Resource.Id.deepLinkTextView);
            try
            {
                PaymentResponse paymentResponse = payment.ReceiveDeeplinkResponse(Intent);
                if (paymentResponse != null)
                {
                    resultTextView.Text = paymentResponse.ToString();
                }
            }catch(PaymentException e)
            {
                resultTextView.Text = e.Message;
            }
            
            button.Click += delegate
            {
                SendDeepLink();
            };
        }

        private void SendDeepLink()
        {
            int amount = 100;
            Java.Lang.Integer installmentCount = (Java.Lang.Integer) null;
            String returnScheme = "xamarindeeplink";
            Java.Lang.Integer orderId = null;
            Java.Lang.Boolean editableAmount = Java.Lang.Boolean.False;

            PaymentInfo paymentInfo = new PaymentInfo(amount, TransactionType.Credit, installmentCount, orderId, editableAmount, returnScheme, InstallmentType.Merchant);

            payment.SendDeepLink(paymentInfo);
        }
    }
}