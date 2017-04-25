package cn.lankao.com.lovelankao.model;
import java.io.Serializable;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class JWzCitys implements Serializable{
    public String city_name;
    public String city_code;
    public String abbr;
    public String engine;//是否需要发动机号0:不需要 1:需要
    public String engineno;//需要几位发动机号0:全部 1-9 :需要发动机号后N位
    public String classa;//是否需要车架号0,不需要 1,需要
    public String classno;//需要几位车架号0:全部 1-9: 需要车架号后N位
    public String regist;
    public String registno;
}