/* First created by JCasGen Tue Apr 08 18:30:34 CEST 2008 */
package org.apache.uima.tm.type;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;

/**
 * Updated by JCasGen Thu Apr 24 17:12:22 CEST 2008
 * 
 * @generated
 */
public class QUESTION_Type extends SENTENCEEND_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {
    return fsGenerator;
  }

  /** @generated */
  private final FSGenerator fsGenerator = new FSGenerator() {
    public FeatureStructure createFS(int addr, CASImpl cas) {
      if (QUESTION_Type.this.useExistingInstance) {
        // Return eq fs instance if already created
        FeatureStructure fs = QUESTION_Type.this.jcas.getJfsFromCaddr(addr);
        if (null == fs) {
          fs = new QUESTION(addr, QUESTION_Type.this);
          QUESTION_Type.this.jcas.putJfsFromCaddr(addr, fs);
          return fs;
        }
        return fs;
      } else
        return new QUESTION(addr, QUESTION_Type.this);
    }
  };

  /** @generated */
  public final static int typeIndexID = QUESTION.typeIndexID;

  /**
   * @generated
   * @modifiable
   */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.apache.uima.tm.type.QUESTION");

  /**
   * initialize variables to correspond with Cas Type and Features
   * 
   * @generated
   */
  public QUESTION_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl) this.casType, getFSGenerator());

  }
}