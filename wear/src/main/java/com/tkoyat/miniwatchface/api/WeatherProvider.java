package com.tkoyat.miniwatchface.api;

import android.location.Location;
import com.tkoyat.miniwatchface.R;
import com.tkoyat.miniwatchface.models.Weather;
import com.tkoyat.miniwatchface.models.WeatherProviderType;
import com.tkoyat.miniwatchface.models.metar.CloudQuantity;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import java.util.Calendar;
import java.util.TimeZone;
import org.json.JSONObject;

public abstract class WeatherProvider {

  // include instructor in classes that implement this that has needed info to generate forecast URL

  protected Location mLocation;
  protected Gson mGson;
  protected boolean mRetry;

  protected WeatherProvider(Location location) {
    this.mLocation = location;
    mGson = new Gson();
  }

  protected boolean isDay() {
    System.out.println(TimeZone.getDefault().getID());
    Calendar currentTime = Calendar.getInstance();
    SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(
        new com.luckycatlabs.sunrisesunset.dto.Location(mLocation.getLatitude(),
            mLocation.getLongitude()), TimeZone.getDefault());
    Calendar sunrise = calculator.getOfficialSunriseCalendarForDate(currentTime);
    Calendar sunset = calculator.getOfficialSunsetCalendarForDate(currentTime);
    return currentTime.after(sunrise) && currentTime.before(sunset);
  }

  public abstract WeatherProviderType getType();

  public boolean isMultistep() {
    return false;
  }

  public String getMultistepURL() {
    return null;
  }

  public void parseMultistepResponse(JSONObject jsonObject)
      throws JsonParseException, IllegalArgumentException {
  }

  public abstract String getWeatherURL();

  public abstract Weather parseWeatherResponse(JSONObject jsonObject)
      throws JsonParseException, IllegalArgumentException;

  public Location getLocation() {
    return mLocation;
  }

  public boolean shouldRetry() {
    return mRetry;
  }

  protected int getCloudIcon(CloudQuantity cloudQuantity) {
    switch (cloudQuantity) {
      default:
      case CLR:
      case SKC:
      case NSC:
        return isDay() ? R.drawable.sunny : R.drawable.clear_night;  // sunny/clear night
      case FEW:
      case SCT:
        return isDay() ? R.drawable.mostly_sunny : R.drawable.mostly_clear_night; // mostly sunny
      case BKN:
        return isDay() ? R.drawable.partly_cloudy : R.drawable.partly_cloudy_night; // partly cloudy
      case OVC:
        return isDay() ? R.drawable.mostly_cloudy_day
            : R.drawable.mostly_cloudy_night; // mostly cloudy

    }
  }

}

