/*
 * Locationforecast_2_0
 * Weather forecast for a specified place
 *
 * OpenAPI spec version: 2.0
 * Contact: weatherapi-adm@met.no
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.tkoyat.miniwatchface.api.met.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Forecast
 */
public class Forecast {

  @SerializedName("timeseries")
  private List<ForecastTimeStep> timeseries = new ArrayList<ForecastTimeStep>();

  @SerializedName("meta")
  private ForecastMeta meta = null;

  public Forecast timeseries(List<ForecastTimeStep> timeseries) {
    this.timeseries = timeseries;
    return this;
  }

  public Forecast addTimeseriesItem(ForecastTimeStep timeseriesItem) {
    this.timeseries.add(timeseriesItem);
    return this;
  }

  /**
   * Get timeseries
   *
   * @return timeseries
   **/

  public List<ForecastTimeStep> getTimeseries() {
    return timeseries;
  }

  public void setTimeseries(List<ForecastTimeStep> timeseries) {
    this.timeseries = timeseries;
  }

  public Forecast meta(ForecastMeta meta) {
    this.meta = meta;
    return this;
  }

  /**
   * Get meta
   *
   * @return meta
   **/

  public ForecastMeta getMeta() {
    return meta;
  }

  public void setMeta(ForecastMeta meta) {
    this.meta = meta;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Forecast forecast = (Forecast) o;
    return Objects.equals(this.timeseries, forecast.timeseries) &&
        Objects.equals(this.meta, forecast.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timeseries, meta);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Forecast {\n");

    sb.append("    timeseries: ").append(toIndentedString(timeseries)).append("\n");
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first
   * line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
