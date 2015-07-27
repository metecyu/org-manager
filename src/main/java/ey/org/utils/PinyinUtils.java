package ey.org.utils;

import net.sourceforge.pinyin4j.PinyinHelper;  
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;  
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;  
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;  
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;  
  
/** 
 * 汉语拼音 帮助类 
 *  
 * @author lili 
 *  
 */  
public class PinyinUtils {  
    /** 
     * 获取汉字串拼音 
     * @param strCN 
     * @return 汉字串拼音 
     * @throws BadHanyuPinyinOutputFormatCombination 
     */  
    public static String getHanyuPinyin(String strCN)  
            throws BadHanyuPinyinOutputFormatCombination {  
        if(null == strCN){  
            return null;  
        }  
        StringBuffer spell = new StringBuffer();  
        char[] charOfCN = strCN.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < charOfCN.length; i++) {  
            // 是否为中文字符  
            if (charOfCN[i] > 128) {  
                String[] spellArray = PinyinHelper.toHanyuPinyinStringArray(  
                        charOfCN[i], defaultFormat);  
                if (null != spellArray) {  
                    spell.append(spellArray[0]);  
                }else{  
                    spell.append(charOfCN[i]);  
                }  
            } else {  
                spell.append(charOfCN[i]);  
            }  
        }  
        return spell.toString();  
    }  
    /** 
     * 获取汉字串 拼音首字母 
     * @param strCN 
     * @return  拼音首字母 
     * @throws BadHanyuPinyinOutputFormatCombination 
     */  
    public static String getFirstHanyuPinyin(String strCN)  
            throws BadHanyuPinyinOutputFormatCombination {  
        if(null == strCN){  
            return null;  
        }  
        StringBuffer firstSpell = new StringBuffer();  
        char[] charOfCN = strCN.toCharArray();  
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < charOfCN.length; i++) {  
            // 是否为中文字符  
            if (charOfCN[i] > 128) {  
                String[] spellArray = PinyinHelper.toHanyuPinyinStringArray(  
                        charOfCN[i], format);  
                // 取拼音首字母  
                if (null != spellArray) {  
                    firstSpell.append(spellArray[0].charAt(0));  
                }else{  
                    firstSpell.append(charOfCN[i]);  
                }  
            } else {  
                firstSpell.append(charOfCN[i]);  
            }  
        }  
        return firstSpell.toString();  
    }  
    /** 
     * 获取 汉字串拼音首字母 & 汉字串拼音 
     *  
     * @param strCN 
     * @return 下标0：汉字串拼音首字母；下标1：汉字串拼音 
     * @throws BadHanyuPinyinOutputFormatCombination 
     */  
    public static String[] getFirstAndHanyuPinyin(String strCN)  
            throws BadHanyuPinyinOutputFormatCombination {  
        if(null == strCN){  
            return null;  
        }  
        StringBuffer firstSpell = new StringBuffer();  
        StringBuffer spell = new StringBuffer();  
        char[] charOfCN = strCN.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < charOfCN.length; i++) {  
            // 是否为中文字符  
            if (charOfCN[i] > 128) {  
                String[] spellArray = PinyinHelper.toHanyuPinyinStringArray(  
                        charOfCN[i], defaultFormat);  
                if (null != spellArray) {  
                    firstSpell.append(spellArray[0].charAt(0));  
                    spell.append(spellArray[0]);  
                }else{  
                    firstSpell.append(charOfCN[i]);  
                    spell.append(charOfCN[i]);  
                }  
            } else {  
                firstSpell.append(charOfCN[i]);  
                spell.append(charOfCN[i]);  
            }  
        }  
        return new String[] { firstSpell.toString(), spell.toString() };  
    }  
    
    
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        try {  
            testPinyin();  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
    }  
    static void testPinyin() throws BadHanyuPinyinOutputFormatCombination{  
        String pinyin = null;  
        String [] firstAndPinyin = null;  
        //String s = "< 中國-China-중국 >";
        
        String s = "俞张平test";  
          
        pinyin = PinyinUtils.getFirstHanyuPinyin( s);  
        System.err.println("getFirstHanyuPinyin("+s+")......["+pinyin+"]");  
          
        pinyin = PinyinUtils.getHanyuPinyin( s);  
        System.err.println("getHanyuPinyin("+s+")......["+pinyin+"]");  
          
        /* firstAndPinyin = PinyinUtils.getFirstAndHanyuPinyin( s);  
        System.err.println("getFirstAndHanyuPinyin("+s+")......"+Arrays.asList(firstAndPinyin));  */
    }  
      
}  