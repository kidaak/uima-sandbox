/* 
 Copyright 2009-2010	Regents of the University of Colorado.  
 All rights reserved. 

 Licensed under the Apache License, Version 2.0 (the "License"); 
 you may not use this file except in compliance with the License. 
 You may obtain a copy of the License at 

 http://www.apache.org/licenses/LICENSE-2.0 

 Unless required by applicable law or agreed to in writing, software 
 distributed under the License is distributed on an "AS IS" BASIS, 
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 See the License for the specific language governing permissions and 
 limitations under the License.
 */
package org.uimafit.examples.tutorial.ex1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.junit.Test;
import org.uimafit.examples.tutorial.type.RoomNumber;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.TypeSystemDescriptionFactory;
import org.uimafit.util.JCasUtil;

/**
 * This class demonstrates some simple tests using uimaFIT. A slightly better set of tests can be
 * found in RoomNumberAnnotator2Test
 * 
 * @author Philip
 */
public class RoomNumberAnnotator1Test {

	/**
	 * This test instantiates the analysis engine using the descriptor file generated by
	 * org.uimafit.tutorial.ex1.RoomNumberAnnotator.main(String[]).
	 * 
	 * This test isn't so bad but requires an xml descriptor file for the analysis engine. While the
	 * descriptor was automatically generated, you might not want your tests to rely on descriptor
	 * files. This test also creates a new JCas object. Creating a new JCas is a bit expensive (>40
	 * ms on my laptop) - so this can really add up over a large test suite.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRNA1() throws Exception {
		AnalysisEngine roomNumberAnnotatorAE = AnalysisEngineFactory
				.createAnalysisEngine("org.uimafit.examples.tutorial.ex1.RoomNumberAnnotator");
		JCas jCas = roomNumberAnnotatorAE.newJCas();
		jCas.setDocumentText("The meeting is over at Yorktown 01-144");
		roomNumberAnnotatorAE.process(jCas);

		RoomNumber roomNumber = JCasUtil.selectByIndex(jCas, RoomNumber.class, 0);
		assertNotNull(roomNumber);
		assertEquals("01-144", roomNumber.getCoveredText());
		assertEquals("Yorktown", roomNumber.getBuilding());
	}

	/**
	 * This test is a bit better because we don't need a descriptor file for the analysis engine. We
	 * still need a descriptor file for the type system but this isn't so bad as we expect there to
	 * be a descriptor file for the type system as it is used to generate the Java jCas types. We
	 * are still, unfortunately, creating a new JCas which is not so great.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRNA2() throws Exception {
		TypeSystemDescription typeSystemDescription = TypeSystemDescriptionFactory
				.createTypeSystemDescription("org.uimafit.examples.TypeSystem");
		AnalysisEngine roomNumberAnnotatorAE = AnalysisEngineFactory.createPrimitive(
				RoomNumberAnnotator.class, typeSystemDescription);
		JCas jCas = roomNumberAnnotatorAE.newJCas();
		jCas.setDocumentText("The meeting is over at Yorktown 01-144");
		roomNumberAnnotatorAE.process(jCas);

		RoomNumber roomNumber = JCasUtil.selectByIndex(jCas, RoomNumber.class, 0);
		assertNotNull(roomNumber);
		assertEquals("01-144", roomNumber.getCoveredText());
		assertEquals("Yorktown", roomNumber.getBuilding());
	}

	/**
	 * This test requires no descriptor files for either the TypeSystemDescription or the
	 * AnalysisEngine.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRNA3() throws Exception {
		AnalysisEngine roomNumberAnnotatorAE = AnalysisEngineFactory.createPrimitive(
				RoomNumberAnnotator.class);
		JCas jCas = roomNumberAnnotatorAE.newJCas();
		jCas.setDocumentText("The meeting is over at Yorktown 01-144");
		roomNumberAnnotatorAE.process(jCas);

		RoomNumber roomNumber = JCasUtil.selectByIndex(jCas, RoomNumber.class, 0);
		assertNotNull(roomNumber);
		assertEquals("01-144", roomNumber.getCoveredText());
		assertEquals("Yorktown", roomNumber.getBuilding());
	}

}