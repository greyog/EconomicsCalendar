package com.greyogproducts.greyog.economicscalendar

import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import pl.droidsonroids.jspoon.annotation.Selector
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

/**
 * Created by mac on 09/03/2018.
 */
class LoadHelper {
    companion object {
        var responseListener : ResponseListener? = null

        fun makeRequest() {
            val myService = createService()
            myService.getRaw().enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    t?.printStackTrace()
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    if (response!!.isSuccessful) {
                        println("response ok")
//                        println(response.body()?.string())
                    } else println(response.message())
                }

            })
        }

        private fun createService() : Service {
            return createRetrofit().create(Service::class.java)
        }

        private fun createRetrofit(): Retrofit {
            val clientBuilder = OkHttpClient.Builder()
            class ResponseInterceptor : Interceptor {
                override fun intercept(chain: Interceptor.Chain?): okhttp3.Response {
                    val response = chain!!.proceed(chain.request())
                    val body = response.peekBody(Long.MAX_VALUE)
                    val doc = Jsoup.parse(body?.string())
                    val element = doc.getElementById("ecEventsTable")
                    val itemList = ArrayList<ListItem>()
//                    <tr id="eventRowId_362742" event_attr_id="21" event_timestamp="2018-03-11 23:50:00" onclick="javascript:changeEventDisplay(362742, this, 'overview');">
//                    <td class="first left time">23:50</td>
//                    <td class="flagCur"><span title="Japan" class=" ceFlags Japan">&nbsp;</span> JPY</td>
//                    <td class="sentiment" title="Moderate Volatility Expected"><i class="newSiteIconsSprite grayFullBullishIcon middle"></i><i class="newSiteIconsSprite grayFullBullishIcon middle"></i><i class="newSiteIconsSprite grayEmptyBullishIcon middle"></i></td>
//                    <td class="left event">BSI Large Manufacturing Conditions (Q1)</td>
//                    <td class="bold act redFont event-362742-actual" title="Worse Than Expected" id="eventActual_362742">2.9</td>
//                    <td class="fore" id="eventForecast_362742 event-362742-forecast">10.3</td>
//                    <td class="prev blackFont event-362742-previous" id="eventPrevious_362742">9.7</td>
//                    <td class="diamond" id="eventRevisedFrom_362742">&nbsp;</td>
//                    </tr>
                    for (table in element.select("tbody")) {
                        for (tr in table.select("tr")) {
                            val listItem = ListItem()
                            if (tr.allElements.hasClass("theDay")) {
                                val id = tr.selectFirst("td").id()
                                val txt = tr.selectFirst("td").text()
                                listItem.klas = "day"
                                listItem.eventId = id
                                listItem.date = txt
                                itemList.add(listItem)
                            } else if (!tr.id().contains("eventInfo")){
                                val tds = tr.select("td")
                                println(tds.size)
                                listItem.klas = "event"
                                listItem.eventId = tr.id()
                                listItem.attr = tr.attr("event_attr_id")
                                listItem.date = tr.attr("event_timestamp")
                                listItem.eventTime = tds[0].text()
                                listItem.currency = tds[1].text()
                                listItem.importance = tds[2].attr("title")
                                listItem.description = tds[3].text()
                                listItem.actual = tds[4].text()
                                listItem.forecast = tds[5].text()
                                listItem.previous = tds[6].text()
                                itemList.add(listItem)
                            }
                        }
                    }
                    println(itemList)
                    responseListener?.onResponse(itemList)
                    return response
                }

            }

            clientBuilder.addInterceptor(ResponseInterceptor())
            return Retrofit.Builder()
                    .baseUrl("https://sslecal2.forexprostools.com/")
                    .addConverterFactory(JspoonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build()
        }

//        fun makeOtherRequest() {
//            val retrofit = Retrofit.Builder()
//                    .baseUrl("http://api.learn2crack.com")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            val request = retrofit.create(RequestInterface::class.java)
//            val call = request.json()
//            call.enqueue(object: Callback<JSONResponse> {
//                override fun onFailure(call: Call<JSONResponse>?, t: Throwable?) {
//                    t?.printStackTrace()
//                }
//
//                override fun onResponse(call: Call<JSONResponse>?, response: Response<JSONResponse>?) {
//
//                    println("hhhhhhhhh$response")
//                    responseListener?.onResponse(response?.body())
//
//                }
//            })
//        }
    }
    private interface Service {
        @GET(" ")
        fun getTabl() : Call<Tabl>
        @GET(" ")
        fun getRaw() : Call<ResponseBody>

    }

    interface ResponseListener {
        fun onResponse(list: ArrayList<ListItem>)
    }
}

class Tabl {
    @Selector(".tbody")
    var rows: List<String>? = null
//    lateinit var rows: List<Ro>
}

class AndroidVer {
    @SerializedName("name")
    var andName = ""
    @SerializedName("ver")
    var ver = ""
    @SerializedName("api")
    var api = ""
}

class JSONResponse {
    @SerializedName("android")
    var androids: Array<AndroidVer>? = null
}

