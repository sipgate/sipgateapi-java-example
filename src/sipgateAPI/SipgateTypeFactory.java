package sipgateAPI;

import java.util.TimeZone;

import org.apache.xmlrpc.XmlRpcConfig;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.common.XmlRpcWorkerFactory;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.xml.sax.SAXException;

public class SipgateTypeFactory extends TypeFactoryImpl
{

	public SipgateTypeFactory(XmlRpcController pController)
	{
		super(pController);
	}

	public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig, Object pObject) throws SAXException
	{
		if (pObject instanceof String)
		{
			return new SipgateStringSerializer();
		}
		else
		{
			return super.getSerializer(pConfig, pObject);
		}
	}

	public SipgateTypeFactory()
	{
		this(new XmlRpcController()
		{

			private XmlRpcConfig config = new XmlRpcConfig()
			{

				@Override
				public TimeZone getTimeZone()
				{
					return TimeZone.getDefault();
				}

				@Override
				public boolean isEnabledForExtensions()
				{
					return false;
				}

			};

			@Override
			public XmlRpcConfig getConfig()
			{
				return this.config;
			}

			@Override
			protected XmlRpcWorkerFactory getDefaultXmlRpcWorkerFactory()
			{
				throw new RuntimeException("use constructor with real RpcController to use this function.");
			}

		});
	}
}
