package test;

import android.content.Context;
import android.test.ApplicationTestCase;

import com.android.volley.base.VolleyApplication;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by oscarrodriguez on 4/10/14.
 */


public class VolleyApplicationTest extends ApplicationTestCase<VolleyApplication> {

        Context context;
        InputStream conf;
        public static String  DEFAULT_SERVER ="default";
        public static String  DEVELOPMENT_ENV ="development";
        public static String  CONFIG_FILE_PATH = "src/main/config.yml";
        public static String  SERVER_HOST ="Host";
        


        public VolleyApplicationTest() {
          super(VolleyApplication.class);
        }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context=getContext();

    }

    public void testPreconditions() {
        assertNotNull("getContext is null", context);
    }




    public void testLoadConfigYml() throws IOException {


        /*InputStream conf=null;
        try {
          AssetManager assetManager = context.getResources().getAssets();
          conf=assetManager.open(CONFIG_FILE_PATH);
         } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertNotNull("The file no is found ",conf);

        Map config =(Map) new Yaml().load(conf);
        Object defaultServer =   config.get(DEFAULT_SERVER);
        assertNotNull(defaultServer.toString());
        assertEquals(DEVELOPMENT_ENV, defaultServer.toString());

        Object configDevelopment =   config.get(defaultServer.toString());
        String devKey=new Yaml().dump(configDevelopment);
        Map devServerValues =(Map) new Yaml().load(devKey);

        Object basicAuthObject =   devServerValues.get(API_BASIC_AUTH);
        String basicAuthKey=new Yaml().dump(basicAuthObject);
        Map basicAuthValues =(Map) new Yaml().load(basicAuthKey);

        assertEquals("false",basicAuthValues.get(API_BASIC_AUTH_ENABLED).toString());
        assertEquals("xxxx",basicAuthValues.get(API_BASIC_AUTH_USERNAME).toString());
        assertEquals("xxxx",basicAuthValues.get(API_BASIC_AUTH_PASSWORD).toString());
        assertEquals("www.google.com",devServerValues.get(SERVER_HOST));
        assertEquals("http",devServerValues.get(API_PROTOCOL));*/

 }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }



}
