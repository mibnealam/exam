package com.example.task_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.task_02.Adapters.UserAdapter;
import com.example.task_02.Loaders.UserLoader;
import com.example.task_02.Models.User;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<User>> {

    public static final String TAG = MainActivity.class.getName();

    /** URL for user data from the api */
    private static final String USER_REQUEST_URL =
            "http://dropbox.sandbox2000.com/intrvw/users.json";

    /**
     * Constant value for the user loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int USER_LOADER_ID = 1;

    private List<User> userList = new ArrayList<>();

    private RecyclerView userRecyclerView;
    /** Adapter for the list of Users */
    private UserAdapter userAdapter;
    private ProgressBar progressBar;
    /** TextView that is displayed when the list is empty */
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize references to views
        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        userRecyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        userRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        userRecyclerView.setHasFixedSize(true);

        /*
         * The CourseAdapter is responsible for linking our course data with the Views that
         * will end up displaying our course data.
         */
        userAdapter = new UserAdapter();

        userRecyclerView.setAdapter(userAdapter);

        progressBar.setVisibility(View.GONE);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(USER_LOADER_ID, null, MainActivity.this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            progressBar.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<User>> onCreateLoader(int i, Bundle bundle) {
        progressBar.setVisibility(View.VISIBLE);
        // Create a new loader for the given URL
        return new UserLoader(this, USER_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No users found."
        emptyTextView.setText(R.string.no_users);

        // Clear the adapter of previous user data
        userAdapter.setUserData(null);

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (users != null && !users.isEmpty()) {
            userAdapter.setUserData(users);
            emptyTextView.setText(null);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        // Loader reset, so we can clear out our existing data.
        userAdapter.setUserData(null);
    }
}
