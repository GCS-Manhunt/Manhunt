package manhuntgame.app;

import basewindow.BasePlatformHandler;

public class AndroidPlatformHandler extends BasePlatformHandler
{
    @Override
    public void quit()
    {
        System.exit(0);
    }

    @Override
    public void openLink(String url)
    {
        /*Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager pm = ((AndroidApplication) Gdx.app).getContext().getPackageManager();
        pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        ((AndroidApplication)Gdx.app).runOnUiThread(new Runnable() {
            @Override
            public void run () {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                // LiveWallpaper and Daydream applications need this flag
                if (!(((AndroidApplication)Gdx.app).getContext() instanceof Activity))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((AndroidApplication)Gdx.app).startActivity(intent);
            }
        });*/
    }

    @Override
    public void updateLocation()
    {

    }
}
