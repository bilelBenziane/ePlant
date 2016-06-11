using App2.Common;
using Newtonsoft.Json;
using System;
using System.Collections.ObjectModel;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace App2
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class Statistics : Page
    {
        DispatcherTimer mytimer;
        int i = 0;

        public static string ServerLink = "http://192.168.43.65/eplant/";
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();

        public Statistics()
        {
            this.InitializeComponent();
            this.navigationHelper = new NavigationHelper(this);
            this.navigationHelper.LoadState += this.NavigationHelper_LoadState;
            this.navigationHelper.SaveState += this.NavigationHelper_SaveState;

            mytimer = new DispatcherTimer();
            mytimer.Interval = new TimeSpan(0, 0, 0, 1, 0);
            mytimer.Tick += new EventHandler<Object>(myTime_Tick);

        }

        private async void myTime_Tick(object sender, object e)
        {
            try
            {
                Random random = new Random();
                Windows.Web.Http.HttpClient httpClient = new Windows.Web.Http.HttpClient();
                string response = await httpClient.GetStringAsync(new Uri(ServerLink +
                    "check.php?random=" + random.Next().ToString()));
                //to.Text = response;
                myClassBinder root = JsonConvert.DeserializeObject<myClassBinder>(response);
                string t = root.item[0].temp + "°C";
                string g = root.item[0].gas + "%";
                string h = root.item[0].humidity + "%";
                string w = root.item[0].water + "%";
                string l = root.item[0].light + "";

                temp.Text = t;
                gas.Text = g;
                water.Text = w;
                light.Text = l;
                humidity.Text = h;
            }
            catch (Exception ex)
            {
                var dialog_catch = new MessageDialog(ex.ToString());
                await dialog_catch.ShowAsync();

            }

        }


        /// <summary>
        /// Gets the <see cref="NavigationHelper"/> associated with this <see cref="Page"/>.
        /// </summary>
        public NavigationHelper NavigationHelper
        {
            get { return this.navigationHelper; }
        }

        /// <summary>
        /// Gets the view model for this <see cref="Page"/>.
        /// This can be changed to a strongly typed view model.
        /// </summary>
        public ObservableDictionary DefaultViewModel
        {
            get { return this.defaultViewModel; }
        }

        /// <summary>
        /// Populates the page with content passed during navigation.  Any saved state is also
        /// provided when recreating a page from a prior session.
        /// </summary>
        /// <param name="sender">
        /// The source of the event; typically <see cref="NavigationHelper"/>
        /// </param>
        /// <param name="e">Event data that provides both the navigation parameter passed to
        /// <see cref="Frame.Navigate(Type, Object)"/> when this page was initially requested and
        /// a dictionary of state preserved by this page during an earlier
        /// session.  The state will be null the first time a page is visited.</param>
        private async void NavigationHelper_LoadState(object sender, LoadStateEventArgs e)
        {
            mytimer.Start();
            /*
            try
            {
                Random random = new Random();
                Windows.Web.Http.HttpClient httpClient = new Windows.Web.Http.HttpClient();
                string response = await httpClient.GetStringAsync(new Uri(ServerLink + 
                    "check.php?random="+random.Next().ToString()));
                //to.Text = response;
                myClassBinder root = JsonConvert.DeserializeObject<myClassBinder>(response);
                string t = root.item[0].temp+"°C";
                string g = root.item[0].gas + "%";
                string h = root.item[0].humidity +"%";
                string w = root.item[0].water + "%";
                string l = root.item[0].light+"";

                temp.Text = t;
                gas.Text =  t;
                water.Text = w;
                light.Text = l;
                humidity.Text = h;
            }
            catch (Exception ex)
            {
                var dialog_catch = new MessageDialog(ex.ToString());
                await dialog_catch.ShowAsync();

            }*/
            
        }

        /// <summary>
        /// Preserves state associated with this page in case the application is suspended or the
        /// page is discarded from the navigation cache.  Values must conform to the serialization
        /// requirements of <see cref="SuspensionManager.SessionState"/>.
        /// </summary>
        /// <param name="sender">The source of the event; typically <see cref="NavigationHelper"/></param>
        /// <param name="e">Event data that provides an empty dictionary to be populated with
        /// serializable state.</param>
        private void NavigationHelper_SaveState(object sender, SaveStateEventArgs e)
        {
        }

        #region NavigationHelper registration

        /// <summary>
        /// The methods provided in this section are simply used to allow
        /// NavigationHelper to respond to the page's navigation methods.
        /// <para>
        /// Page specific logic should be placed in event handlers for the  
        /// <see cref="NavigationHelper.LoadState"/>
        /// and <see cref="NavigationHelper.SaveState"/>.
        /// The navigation parameter is available in the LoadState method 
        /// in addition to page state preserved during an earlier session.
        /// </para>
        /// </summary>
        /// <param name="e">Provides data for navigation methods and event
        /// handlers that cannot cancel the navigation request.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            mytimer.Start();
            //this.navigationHelper.OnNavigatedTo(e);
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            mytimer.Stop();
           // this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion

        private void Main_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(Choices));
        }
    }

    class serviceClass
    {
        public int id { get; set; }
        public string humidity { get; set; }
        public string temp { get; set; }
        public string gas { get; set; }
        public string water { get; set; }
        public string light { get; set; }
        public string relay1{ get; set; }
        public string relay2 { get; set; }
        public string relay3 { get; set; }
        public string twoweek{ get; set; }

    }

    class myClassBinder
    {
        public ObservableCollection<serviceClass> item { get; set; }
    }

}
