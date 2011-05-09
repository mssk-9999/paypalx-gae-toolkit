
package com.paypal.adaptive.api.responses;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;

import com.paypal.adaptive.core.PayError;
import com.paypal.adaptive.core.ResponseEnvelope;


/**
 * SetPaymentOptionsResponse is returned as a result of the CancelPreapproval API.
 */
public class SetPaymentOptionsResponse {

	HashMap<String, String> setPaymentOptionsResponseParams;
	protected ResponseEnvelope responseEnvelope;
	protected ArrayList<PayError> payErrorList;

    /**
     * Gets the value of the responseEnvelope property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseEnvelope }
     *     
     */
    public ResponseEnvelope getResponseEnvelope() {
        return responseEnvelope;
    }


    public SetPaymentOptionsResponse(String responseString){
    	
    	setPaymentOptionsResponseParams = new HashMap<String, String>();
    	// parse the string and load into the object
    	String[] nmValPairs = responseString.split("&");
    	for(String nmVal: nmValPairs){
    		String[] field = nmVal.split("=");
    		setPaymentOptionsResponseParams.put(field[0], (field.length > 1)?field[1].trim():"");
    	}

    	responseEnvelope = new ResponseEnvelope(setPaymentOptionsResponseParams);
    	
    	payErrorList = new ArrayList<PayError>();
    	// we will parse 10 errors for now
    	for(int i = 0; i < 10; i++){
    		if(setPaymentOptionsResponseParams.containsKey("error(" + i +").errorId")){
    			PayError pErr = new PayError( setPaymentOptionsResponseParams, i);
    			payErrorList.add(pErr);
    		} else {
    			break;
    		}
    	}
    }

public String toString(){
		
		StringBuilder outStr = new StringBuilder();
		
		outStr.append("<table border=1>");
		outStr.append("<tr><th>");
		outStr.append(this.getClass().getSimpleName());
		outStr.append("</th><td></td></tr>");
		BeanInfo info;
		try {
			info = Introspector.getBeanInfo( this.getClass(), Object.class );
			for ( PropertyDescriptor pd : info.getPropertyDescriptors() ) {
				try {
					String name = pd.getName();
					Object value = this.getClass().getDeclaredField(name).get(this);
					if(value != null) {
						outStr.append("<tr><td>");
						outStr.append(pd.getName());
						outStr.append("</td><td>");
						outStr.append(value.toString());
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				outStr.append("</td></tr>");
			}
	    } catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outStr.append("</table>");
		return outStr.toString(); 
		
	}

/**
 * @return the payErrorList
 */
public ArrayList<PayError> getPayErrorList() {
	return payErrorList;
}

}
