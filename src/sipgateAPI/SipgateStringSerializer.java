package sipgateAPI;

import org.apache.xmlrpc.serializer.TypeSerializerImpl;
import org.xml.sax.SAXException;

public class SipgateStringSerializer extends TypeSerializerImpl {

	public static final String STRING_TAG = "string";


	@Override
	public void write(org.xml.sax.ContentHandler handler, Object object)
			throws SAXException {
		write(handler, STRING_TAG, STRING_TAG, object.toString());
		
	}

}
