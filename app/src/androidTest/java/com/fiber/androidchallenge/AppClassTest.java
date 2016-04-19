package com.fiber.androidchallenge;

import android.content.pm.PackageInfo;
import android.test.ApplicationTestCase;
import android.test.MoreAsserts;

import com.fiber.androidchallenge.utils.App;


/**
 * Created by mahmud on 17/4/2016.
 */
public class AppClassTest extends ApplicationTestCase<App> {
    private App application;
    public AppClassTest() {
        super(App.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        application = getApplication();

    }

    public void testCorrectVersion() throws Exception {

        PackageInfo info = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        assertNotNull(info);
        MoreAsserts.assertMatchesRegex("\\d\\.\\d", info.versionName);
    }
}
