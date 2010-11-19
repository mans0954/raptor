/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptormua.runtimeutils;


import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * @author philsmart
 *
 */
public class SDMXHandler {

	public static org.sdmx.resources.sdmxml.schemas.v2_0.message.MessageType createSDMXMessage(){

		uk.ac.cardiff.raptor.sdmx.compact.ObjectFactory compactObjFactory = new uk.ac.cardiff.raptor.sdmx.compact.ObjectFactory();
		org.sdmx.resources.sdmxml.schemas.v2_0.message.ObjectFactory messageObjFactory = new org.sdmx.resources.sdmxml.schemas.v2_0.message.ObjectFactory();
		org.sdmx.resources.sdmxml.schemas.v2_0.common.ObjectFactory commonObjFactory = new org.sdmx.resources.sdmxml.schemas.v2_0.common.ObjectFactory();


		uk.ac.cardiff.raptor.sdmx.compact.DataSetType dataSet = compactObjFactory.createDataSetType();

		org.sdmx.resources.sdmxml.schemas.v2_0.message.CompactDataType compact = messageObjFactory.createCompactDataType();
		org.sdmx.resources.sdmxml.schemas.v2_0.message.HeaderType header =  messageObjFactory.createHeaderType();

		header.setID("RPTR007");
		header.setTest(false);
		header.setTruncated(false);
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");;
		header.setPrepared(formatter.format(date));
		compact.setHeader(header);
		header.setDataSetAgency("Raptor Statistics Engine");
		org.sdmx.resources.sdmxml.schemas.v2_0.message.PartyType senderType = messageObjFactory.createPartyType();
		senderType.setId("RPTRSender");
		header.getSender().add(senderType);
		org.sdmx.resources.sdmxml.schemas.v2_0.common.TextType headerName = commonObjFactory.createTextType();
		headerName.setLang("en");
		headerName.setValue("Raptor007");
		header.getName().add(headerName);

		header.setReportingBegin(formatter.format(date));
		header.setReportingEnd(formatter.format(date));

		DatatypeFactory df;
		try {
			df = DatatypeFactory.newInstance();
			XMLGregorianCalendar calender =df.newXMLGregorianCalendar();
			calender.setYear(1975);
			calender.setMonth(DatatypeConstants.AUGUST);
			calender.setDay(11);
			calender.setHour(6);
			calender.setMinute(44);
			calender.setSecond(0);
			calender.setMillisecond(0);
			calender.setTimezone(5);
			header.setExtracted(calender);
		}
		catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//now do the series
		uk.ac.cardiff.raptor.sdmx.compact.SeriesType series = compactObjFactory.createSeriesType();
		series.setTIMEFORMAT(uk.ac.cardiff.raptor.sdmx.compact.CLTIMEFORMAT.P_1_M);
		series.setCOLLECTION(uk.ac.cardiff.raptor.sdmx.compact.CLCOLLECTION.B);
		series.setFREQ(uk.ac.cardiff.raptor.sdmx.compact.CLFREQ.M);
		series.setTYPE(uk.ac.cardiff.raptor.sdmx.compact.CLTYPE.AUTH);

		//setup some observations and add to the series
		uk.ac.cardiff.raptor.sdmx.compact.ObsType obs = compactObjFactory.createObsType();
		obs.setOBSVALUE(new Double(234));
		obs.setTIMEPERIOD("2000-01");
		uk.ac.cardiff.raptor.sdmx.compact.ObsType obsTwo = compactObjFactory.createObsType();
		obsTwo.setOBSVALUE(new Double(543223));
		obsTwo.setTIMEPERIOD("2000-02");

		series.getObs().add(obs);
		series.getObs().add(obsTwo);

		//now do dataset
		dataSet.getSiblingGroupOrSeriesOrAnnotations().add(series);


		//add to header to compact
		compact.setHeader(header);
		compact.setDataSet(compactObjFactory.createDataSet(dataSet));



		StringWriter writer = new StringWriter();
		JAXBContext context;
		JAXBException jaxException = null;
		try {
			context = JAXBContext.newInstance("org.sdmx.resources.sdmxml.schemas.v2_0.message:org.sdmx.resources.sdmxml.schemas.v2_0.metadatareport:" +
					"org.sdmx.resources.sdmxml.schemas.v2_0.compact:org.sdmx.resources.sdmxml.schemas.v2_0.genericmetadata:" +
					"org.sdmx.resources.sdmxml.schemas.v2_0.cross:org.sdmx.resources.sdmxml.schemas.v2_0.utility:" +
					"org.sdmx.resources.sdmxml.schemas.v2_0.generic");
			Marshaller m = context.createMarshaller();
			m.marshal( new JAXBElement(new QName("","CompactData"),org.sdmx.resources.sdmxml.schemas.v2_0.message.CompactDataType.class,compact), writer);
			//m.marshal(compact, writer);
			System.out.println(writer);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jaxException = e;

		}

		return compact;

	}

}
