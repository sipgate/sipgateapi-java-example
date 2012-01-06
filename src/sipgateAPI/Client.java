package sipgateAPI;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.common.TypeFactory;

public class Client
{

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException
	{
		URL sipgateURL = new URL("http://api.sipgate.net/RPC2");
		String username = "johndoe@example.org";
		String password = "password";

		XmlRpcClient client = getClient(sipgateURL, username, password);

		Object eventlistResult = getEventList(client);
		System.out.println(eventlistResult);
		
		Object historyResult = getHistoryByDate(client);
		System.out.println(historyResult);		
	}

	/**
	 * returns client for sipgate API
	 * 
	 * @param sipgateURL
	 * @param username
	 * @param password
	 * @return
	 */
	private static XmlRpcClient getClient(URL sipgateURL, String username, String password)
	{
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

		config.setServerURL(sipgateURL);
		config.setBasicUserName(username);
		config.setBasicPassword(password);

		XmlRpcClient client = new XmlRpcClient();
		TypeFactory sipgateTypeFactory = new SipgateTypeFactory(client);
		client.setTypeFactory(sipgateTypeFactory);
		client.setConfig(config);

		return client;
	}

	/**
	 * calls a API function
	 * 
	 * @param methodName
	 * @param params
	 * @param client
	 * @return
	 */
	private static Object callAPI(String methodName, HashMap<String, Object> params, XmlRpcClient client)
	{
		Object result = null;
		try
		{
			result = client.execute(methodName, new Object[] { params });
		}
		catch (XmlRpcException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * requests events for calls
	 * 
	 * @param client
	 * @return
	 */
	private static Object getHistoryByDate(XmlRpcClient client)
	{
		String methodName = "samurai.HistoryGetByDate";

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MONTH, -1);
		Date lastUpdate = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);

		String periodStart = formatter.format(lastUpdate);
		String periodEnd = formatter.format(calendar.getTime());

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("PeriodStart", periodStart);
		params.put("PeriodEnd", periodEnd);

		return callAPI(methodName, params, client);
	}

	/**
	 * requests events for calls and voicemails
	 * 
	 * @param client
	 * @return
	 */
	private static Object getEventList(XmlRpcClient client)
	{
		String methodName = "samurai.EventListGet";

		ArrayList<String> toses = new ArrayList<String>();
		toses.add("X-call");
		toses.add("voice");

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("Offset", 0);
		params.put("Limit", 0);
		params.put("TOS", toses);

		return callAPI(methodName, params, client);
	}

}
