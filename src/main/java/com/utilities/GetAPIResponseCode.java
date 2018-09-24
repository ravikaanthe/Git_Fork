package com.utilities;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

public class GetAPIResponseCode extends BaseClass{
	
	// Constructor
			public  GetAPIResponseCode(WebDriver driver) {
				this.driver = driver;

			}
	
	public void getResponseCode(){
	// then ask for all the performance logs from this request
    // one of them will contain the Network.responseReceived method
    // and we shall find the "last recorded url" response
    LogEntries logs = driver.manage().logs().get("performance");

    int status = -1;

    System.out.println("\nList of log entries:\n");

    for (Iterator<LogEntry> it = logs.iterator(); it.hasNext();)
    {
        LogEntry entry = it.next();

        try
        {
            JSONObject json = new JSONObject(entry.getMessage());

            System.out.println(json.toString());

            JSONObject message = json.getJSONObject("message");
            String method = message.getString("method");

            if (method != null
                    && "Network.responseReceived".equals(method))
            {
                JSONObject params = message.getJSONObject("params");

                JSONObject response = params.getJSONObject("response");
                String messageUrl = response.getString("url");

                if ((Param.getProperty("siteURL")).equals(messageUrl))
                {
                    status = response.getInt("status");

                    System.out.println(
                            "---------- bingo !!!!!!!!!!!!!! returned response for "
                                    + messageUrl + ": " + status);

                    System.out.println(
                            "---------- bingo !!!!!!!!!!!!!! headers: "
                                    + response.get("headers"));
                }
            }
        } catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    System.out.println("\nstatus code: " + status);

	}
}
