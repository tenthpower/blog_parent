///**
// *
// */
//package com.blog.util;
//
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
//
///**
// * 描述:velocity帮助文件，主要用于在模板文件里调用java代码
// * @version:1.0.0
// * @author:谷春
// * @date:2017-7-12 09:36:48
// */
//public class VelocityHelper {
//
//    //日志
//    private static Logger log = LoggerFactory.getLogger(VelocityHelper.class);
//
//    /**
//     * 业务逻辑biz的dto项目
//     */
//    private final static String PAI_CORE_BIZ_DTO_PROJECT = "pai-core-biz-dto";
//
//    /**
//     * 业务逻辑object项目
//     */
//    private final static String PAI_CORE_OBJECT_PROJECT = "pai-core-object";
//
//
//    /**
//     * 返回字段名首字母大写
//     * 用于自动生成代码时的get和set
//     * 如果字段名为空，返回空字符串，否则返回首字母大写的字符串
//     * @param fieldName 字段名
//     * @return String 返回首字母大写的字符串
//     */
//    public static String getFirstUpperField(String fieldName) {
//        if(!StringUtils.hasText(fieldName)) {
//            return "";
//        }
//
//        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//    }
//
//    /**
//     * 返回字段名首字母小写
//     * 用于自动生成代码时的get和set
//     * 如果字段名为空，返回空字符串，否则返回首字母大写的字符串
//     * @param fieldName 字段名
//     * @return String 返回首字母大写的字符串
//     */
//    public static String getFirstLowerField(String fieldName) {
//        if(!StringUtils.hasText(fieldName)) {
//            return "";
//        }
//
//        return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
//    }
//
//    /**
//     * 判断数据类型是否在指定的数据类型范围内
//     * @param dataType 数据类型
//     * @return String 返回java的真实数据类型
//     */
//    public static String getJavaDataType(String dataType) throws Exception {
//        //对数据类型进行验证
//        validateDataType("getJavaDataType", dataType);
//
//        //只有date和decimal类型需要额外导入包，别的类型不需要
//        if(BizCommonConstant.DATATYPE_DATETIME.equals(dataType)) {
//            return "Date";
//        }
//        else if(BizCommonConstant.DATATYPE_DECIMAL.equals(dataType)) {
//            return "BigDecimal";
//        }
//        else if(BizCommonConstant.DATATYPE_PAGECONTROLLERINFO.equals(dataType)) {
//            return BizCommonConstant.DATATYPE_PAGECONTROLLERINFO;
//        }
//        else if(BizCommonConstant.DATATYPE_JSON.equals(dataType)) {
//            return "JsonNode";
//        }
//        else if(BizCommonConstant.DATATYPE_STRING.equals(dataType)) {
//            return "String";
//        }
//        else if(BizCommonConstant.DATATYPE_TEXT.equals(dataType)) {
//            return "String";
//        }
//        else {
//            return dataType;
//        }
//    }
//
//    /**
//     * 判断数据类型是否在指定的数据类型范围内
//     * @param dataType 数据类型
//     * @param childDto 子dto
//     * @return String 返回java的真实数据类型
//     */
//    public static String getJavaDataType(String dataType, String childDto) throws Exception {
//        //如果是数组类型
//        if(BizCommonConstant.DATATYPE_ARRAY.equals(dataType)) {
//            //验证childDto不能为空
//            if(org.apache.commons.lang3.StringUtils.isBlank(childDto)) {
//                log.error("当前参数为数组类型时，子dto不能为空");
//                throw new Exception("当前参数为数组类型时，子dto不能为空");
//            }
//
//            return MessageFormat.format("ArrayList<{0}>", childDto);
//        }
//        return getJavaDataType(dataType);
//    }
//
//    /**
//     * 根据公共属性DTO返回数据类型
//     * 1、如果是object类型，直接返回ref
//     * 2、如果是基础数据了类型，返回正常的javatype
//     * @param commonProperty 公共属性DTO
//     * @return String 返回java的真实数据类型
//     */
//    public static String getJavaDataType(CommonProperty commonProperty) throws Exception {
//        //1、如果是object类型，直接返回ref
//       if(BizCommonConstant.DATATYPE_OBJECT.equals(commonProperty.getType())) {
//           return commonProperty.getRef();
//       }
//
//       //2、如果是基础数据了类型，返回正常的javatype
//       return getJavaDataType(commonProperty.getType());
//    }
//
//    /**
//     * 根据数据类型返回import引用
//     * @param dataType 数据类型
//     * @return String 返回import字符串
//     */
//    public static String getImportByDataType(String dataType) throws Exception {
//        //对数据类型进行验证
//        validateDataType("getImportByDataType", dataType);
//
//        //只有date和decimal类型需要额外导入包，别的类型不需要
//        if(BizCommonConstant.DATATYPE_DATETIME.equals(dataType)) {
//            return "import java.util.Date;";
//        }
//        else if(BizCommonConstant.DATATYPE_DECIMAL.equals(dataType)) {
//            return "import java.math.BigDecimal;";
//        }
//        else if(BizCommonConstant.DATATYPE_PAGECONTROLLERINFO.equals(dataType)) {
//            return "import com.paasit.pai.core.bean.PageControllerInfo;";
//        }
//        else if(BizCommonConstant.DATATYPE_JSON.equals(dataType)) {
//            return "import com.fasterxml.jackson.databind.JsonNode;";
//        }
//        else {
//            return "";
//        }
//    }
//
//    /**
//     * 根据数据类型返回import引用
//     * @param dataType 数据类型
//     * @param childDtoName 子dto，适用于自动生成客户端调用的dto
//     * @return String 返回import字符串
//     */
//    public static String getImportByDataType(String dataType, String childDtoName) throws Exception {
//        //如果是ArrayList类型，默认只处理SystemMessage这个类型
//        //因为如果是基础数据类型，基本上不用处理，比如String,Integer
//        //如果是自定义的其他类型，则因为在相同的包里，也不需要引用
//        //如果还有其他类型，则需要后期修改了
//        if(BizCommonConstant.DATATYPE_ARRAY.equals(dataType)) {
//            StringBuilder sBuilder = new StringBuilder();
//            sBuilder.append("import java.util.ArrayList;");
//            if(BizCommonConstant.DATATYPE_SYSTEMMESSAGE.equals(childDtoName)) {
//                sBuilder.append("\nimport com.paasit.pai.core.bean.SystemMessage;");
//            }
//            return sBuilder.toString();
//        }
//
//        //默认按照之前的方式去处理
//        return getImportByDataType(dataType);
//    }
//
//    /**
//     * 根据数据类型返回mybatis的jdbcType
//     * 数据库类型与jdbctype类型对照参考：http://blog.csdn.net/loongshawn/article/details/50496460
//     * jdbctype数据类型与java类型对照参考：http://ysj5125094.iteye.com/blog/2185024/
//     * @param dataType 数据类型
//     * @return String 返回import字符串
//     */
//    public static String getMyBatisJdbcTypeByDataType(String dataType) throws Exception {
//        //对数据类型进行验证
//        validateDataType("getMyBatisJdbcTypeByDataType", dataType);
//
//        //数据库类型与jdbctype类型对照参考：http://blog.csdn.net/loongshawn/article/details/50496460
//        //jdbctype数据类型与java类型对照参考：http://ysj5125094.iteye.com/blog/2185024/
//        if(BizCommonConstant.DATATYPE_DATETIME.equals(dataType)) {
//            return BizCommonConstant.JDBC_DATATYPE_DATETIME;
//        }
//        else if(BizCommonConstant.DATATYPE_DECIMAL.equals(dataType)) {
//            return BizCommonConstant.JDBC_DATATYPE_DECIMAL;
//        }
//        else if(BizCommonConstant.DATATYPE_INTEGER.equals(dataType)) {
//            return BizCommonConstant.JDBC_DATATYPE_INTEGER;
//        }
//        else if(BizCommonConstant.DATATYPE_STRING.equals(dataType)) {
//            return BizCommonConstant.JDBC_DATATYPE_STRING;
//        }
//        else if(BizCommonConstant.DATATYPE_TEXT.equals(dataType)) {
//            return BizCommonConstant.JDBC_DATATYPE_STRING;
//        }
//        // json类型使用字符串类型
//        else if(BizCommonConstant.DATATYPE_JSON.equals(dataType)) {
//            return BizCommonConstant.JDBC_DATATYPE_STRING;
//        }
//        else {
//            return "";
//        }
//    }
//
//    /**
//     * 根据数据类型返回db的数据类型
//     * 数据库类型与jdbctype类型对照参考：http://blog.csdn.net/loongshawn/article/details/50496460
//     * jdbctype数据类型与java类型对照参考：http://ysj5125094.iteye.com/blog/2185024/
//     * @param dataType 数据类型
//     * @return String 返回import字符串
//     */
//    public static String getDbTypeByDataType(String dataType) throws BizLogicException {
//        //对数据类型进行验证
//        validateDataType("getMyBatisJdbcTypeByDataType", dataType);
//
//        //数据库类型与jdbctype类型对照参考：http://blog.csdn.net/loongshawn/article/details/50496460
//        //jdbctype数据类型与java类型对照参考：http://ysj5125094.iteye.com/blog/2185024/
//        if(BizCommonConstant.DATATYPE_DATETIME.equals(dataType)) {
//            return BizCommonConstant.DB_DATATYPE_DATETIME;
//        }
//        else if(BizCommonConstant.DATATYPE_DECIMAL.equals(dataType)) {
//            return BizCommonConstant.DB_DATATYPE_DECIMAL;
//        }
//        else if(BizCommonConstant.DATATYPE_INTEGER.equals(dataType)) {
//            return BizCommonConstant.DB_DATATYPE_INTEGER;
//        }
//        else if(BizCommonConstant.DATATYPE_STRING.equals(dataType)) {
//            return BizCommonConstant.DB_DATATYPE_STRING;
//        }
//        // json类型使用JSON类型
//        else if(BizCommonConstant.DATATYPE_JSON.equals(dataType)) {
//            return BizCommonConstant.DB_DATATYPE_JSON;
//        }
//        // text类型使用TEXT类型
//        else if(BizCommonConstant.DATATYPE_TEXT.equals(dataType)) {
//            return BizCommonConstant.DB_DATATYPE_TEXT;
//        }
//        else {
//            return "";
//        }
//    }
//
//    /**
//     * 返回不为空的字符串
//     * 在velocity中，如果字符串为null，会原样输出。
//     * @param str 需要被转换的字符串
//     * @return String 做非空判断以后的字符串
//     */
//    public static String getNotNullString(String str) {
//        if(!StringUtils.hasText(str)) {
//            return "";
//        }
//
//        return str;
//    }
//
//    /**
//     * 对数据类型进行验证，不能为空，并且为给定的数据类型
//     * @param methodName 写日志用的方法名
//     * @param dataType 要检验的数据类型
//     * @exception 数据类型验证失败抛出的异常
//     */
//    private static void validateDataType(String methodName, String dataType) throws BizLogicException {
//        if(!StringUtils.hasText(dataType)) {
//            log.error(MessageFormat.format("调用{0}方法时，参数为空", methodName));
//            throw new BizLogicException(new SystemMessage("VelocityHelperMSG", MessageFormat.format("调用{0}方法时，参数为空", methodName)));
//        }
//
//        //如果不是已知的数据类型，出异常
//        if(!BizCommonConstant.DATATYPE_STRING.equals(dataType)
//                && !BizCommonConstant.DATATYPE_INTEGER.equals(dataType)
//                && !BizCommonConstant.DATATYPE_DECIMAL.equals(dataType)
//                && !BizCommonConstant.DATATYPE_DATETIME.equals(dataType)
//                && !BizCommonConstant.DATATYPE_ARRAY.equals(dataType)
//                && !BizCommonConstant.DATATYPE_OBJECT.equals(dataType)
//                && !BizCommonConstant.DATATYPE_JSON.equals(dataType)
//                && !BizCommonConstant.DATATYPE_TEXT.equals(dataType)
//                && !BizCommonConstant.DATATYPE_PAGECONTROLLERINFO.equals(dataType)) {
//            log.error(MessageFormat.format("调用{0}方法时，参数必须为[{1},{2},{3},{4},{5},{6},{7},{8}]中的一种,实际为:{9}."
//                    , methodName
//                    , BizCommonConstant.DATATYPE_STRING
//                    , BizCommonConstant.DATATYPE_DATETIME
//                    , BizCommonConstant.DATATYPE_DECIMAL
//                    , BizCommonConstant.DATATYPE_INTEGER
//                    , BizCommonConstant.DATATYPE_ARRAY
//                    , BizCommonConstant.DATATYPE_OBJECT
//                    , BizCommonConstant.DATATYPE_JSON
//                    , BizCommonConstant.DATATYPE_PAGECONTROLLERINFO
//                    , BizCommonConstant.DATATYPE_TEXT
//                    , dataType));
//            throw new BizLogicException(new SystemMessage("VelocityHelperMSG", MessageFormat.format("调用{0}方法时，参数必须为[{1},{2},{3},{4},{5},{6},{7},{8}]中的一种,实际为:{9}."
//                    , methodName
//                    , BizCommonConstant.DATATYPE_STRING
//                    , BizCommonConstant.DATATYPE_DATETIME
//                    , BizCommonConstant.DATATYPE_DECIMAL
//                    , BizCommonConstant.DATATYPE_INTEGER
//                    , BizCommonConstant.DATATYPE_ARRAY
//                    , BizCommonConstant.DATATYPE_OBJECT
//                    , BizCommonConstant.DATATYPE_JSON
//                    , BizCommonConstant.DATATYPE_PAGECONTROLLERINFO
//                    , BizCommonConstant.DATATYPE_TEXT
//                    , dataType)));
//        }
//    }
//
//    /**
//     * 根据api地址对地址上的参数进行拆解
//     * 示例：/api/v0_1/test/{id}/{name}
//     * 则：第一个参数表示id，第二个表示name
//     * @param argType 参数类别：path/body
//     * @param index 参数的序号
//     * @param api 包含path的地址
//     */
//    public static String getPathVariable(String argType, int index, String api) {
//        //如果是path参数类型，则进行处理
//        if(BizCommonConstant.ARG_TYPE_PATH.equals(argType)) {
//            //解析api地址
//            List<String> variables = Stream.of(api.split("\\/"))
//                    .filter(x->x.startsWith("{"))
//                    .collect(Collectors.toList());
//            //如果api地址上没有这个参数，则不做任何处理
//            if(variables == null || variables.size() == 0) {
//                return "";
//            }
//
//            //如果下标超过参数的size，则也不处理
//            if(index - 1 > variables.size()) {
//                return "";
//            }
//
//            return MessageFormat.format("@PathVariable(name=\"{0}\")"
//                    , variables.get(index - 1).replace("{", "").replace("}", ""));
//        }
//        return "";
//    }
//
//    /**
//     * 生成restClient文件时获取需要导入的命名空间
//     * 示例：import org.springframework.web.bind.annotation.PathVariable;
//     * import com.paasit.pai.core.blogic.dto.test.v0_1.TestF01ReqtM01;
//     * 则：第一个参数表示id，第二个表示name
//     * @param commonRemoteList 公共远程调用DTO
//     * @param packageName 命名空间
//     */
//    public static String getImportByRestClient(ArrayList<CommonRemote> commonRemoteList, String packageName) {
//        StringBuilder sb = new StringBuilder();
//        //判断是否是有path参数
//        long count = commonRemoteList.stream()
//        .filter(x->x.getArgs().stream()
//                .filter(y->BizCommonConstant.ARG_TYPE_PATH.equals(y.getIn())).count() > 0).count();
//        if(count > 0) {
//            sb.append("import org.springframework.web.bind.annotation.PathVariable;\n");
//        }
//
//        //添加参数的dto的引用
//        Map<String, String> dtoMap = new HashMap<String, String>();
//        for(CommonRemote commonRemote : commonRemoteList) {
//            for(CommonProperty commonProperty : commonRemote.getArgs()) {
//                if(BizCommonConstant.DATATYPE_OBJECT.equals(commonProperty.getType())) {
//                    if(!dtoMap.containsKey(commonProperty.getRef())) {
//                        sb.append(MessageFormat.format("import {0}.{1};\n", packageName, commonProperty.getRef()));
//                        dtoMap.put(commonProperty.getRef(), commonProperty.getRef());
//                    }
//                }
//            }
//
//            //添加返回值的dto的引用
//            if(BizCommonConstant.DATATYPE_OBJECT.equals(commonRemote.getReturns().getType())) {
//                if(!dtoMap.containsKey(commonRemote.getReturns().getRef())) {
//                    sb.append(MessageFormat.format("import {0}.{1};\n", packageName, commonRemote.getReturns().getRef()));
//                    dtoMap.put(commonRemote.getReturns().getRef(), commonRemote.getReturns().getRef());
//                }
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 生成restClientTemplate文件时获取需要导入的命名空间
//     * 根据情况需要导入的包名有：
//     * 1、与请求方式有关：比如DeleteMapping、PutMapping、RequestBody、PathVariable
//     * 2、与restClientDto有关的包
//     * 3、与rest的dto有关的包
//     * 4、导入与client有关的包
//     * 示例：import org.springframework.web.bind.annotation.PathVariable;
//     * import com.paasit.pai.core.blogic.dto.test.v0_1.TestF01ReqtM01;
//     * 则：第一个参数表示id，第二个表示name
//     * @param entityName 实体名称
//     * @param releaseAfterVer 发布之后的版本号
//     * @param commonObjectMethodList 方法列表
//     * @param clientPackageName 与客户端有关的命名空间
//     * @param blogicPackageName 与blogic有关的命名空间
//     */
//    public static String getImportForRestClientTemplate(String entityName, String releaseAfterVer, ArrayList<CommonObjectMethod> commonObjectMethodList, String clientPackageName, String blogicPackageName) {
//        StringBuilder sb = new StringBuilder();
//        //1、与请求方式有关：比如GetMapping、DeleteMapping、PutMapping、RequestBody、PathVariable
//        if(commonObjectMethodList.stream().filter(x->BizCommonConstant.HTTP_METHOD_GET.equals(x.getHttpMethod())).count() > 0) {
//            sb.append("import org.springframework.web.bind.annotation.GetMapping;\n");
//            sb.append("import org.springframework.web.bind.annotation.PathVariable;\n");
//        }
//        if(commonObjectMethodList.stream().filter(x->BizCommonConstant.HTTP_METHOD_DELETE.equals(x.getHttpMethod())).count() > 0) {
//            sb.append("import org.springframework.web.bind.annotation.DeleteMapping;\n");
//        }
//        if(commonObjectMethodList.stream().filter(x->BizCommonConstant.HTTP_METHOD_POST.equals(x.getHttpMethod())).count() > 0) {
//            sb.append("import org.springframework.web.bind.annotation.PostMapping;\n");
//        }
//        if(commonObjectMethodList.stream().filter(x->BizCommonConstant.HTTP_METHOD_PUT.equals(x.getHttpMethod())).count() > 0) {
//            sb.append("import org.springframework.web.bind.annotation.PutMapping;\n");
//        }
//        //如果有Delete、Post、Put请求等，需要加入RequestBody
//        if(commonObjectMethodList.stream().filter(x->BizCommonConstant.HTTP_METHOD_PUT.equals(x.getHttpMethod())
//                || BizCommonConstant.HTTP_METHOD_DELETE.equals(x.getHttpMethod())
//                || BizCommonConstant.HTTP_METHOD_POST.equals(x.getHttpMethod())).count() > 0) {
//            sb.append("import org.springframework.web.bind.annotation.RequestBody;\n");
//        }
//
//        //如果是list方法，需要引入ArrayList
//        if(commonObjectMethodList.stream().filter(x->BizCommonConstant.METHOD_TYPE_LIST.equals(x.getType())).count() > 0) {
//            sb.append("import java.util.ArrayList;\n");
//        }
//
//        //2、与restClientDto有关的包
//        Map<String, String> dtoNameMap = new HashMap<String, String>();
//        ArrayList<CommonRemote> commonRemoteList = (ArrayList<CommonRemote>) commonObjectMethodList.stream().map(x->x.getRemote()).collect(Collectors.toList());
//        for(CommonRemote commonRemote : commonRemoteList) {
//            for (Map.Entry<String, ArrayList<CommonProperty>> entry : commonRemote.getRefs().entrySet()) {
//                //如果是PageControllerInfo或SystemMessage不需要导入
//                if(BizCommonConstant.DATATYPE_PAGECONTROLLERINFO.equals(entry.getKey())
//                    || BizCommonConstant.DATATYPE_SYSTEMMESSAGE.equals(entry.getKey())) {
//                    continue;
//                }
//                if(!dtoNameMap.containsKey(entry.getKey())) {
//                    //${clientPackageName}.client.dto.${entityLowerName}.v${version}.{ref}
//                    sb.append(MessageFormat.format("import {0}.dto.{1}.v{2}.{3};\n"
//                            , clientPackageName
//                            , getFirstLowerField(entityName)
//                            , releaseAfterVer
//                            , entry.getKey()));
//                    dtoNameMap.put(entry.getKey(), entry.getKey());
//                }
//            }
//        }
//
//        //3、与rest的dto有关的包
//        for(CommonObjectMethod commonObjectMethod : commonObjectMethodList) {
//            //添加requestDTO的命名空间
//            //dto示例：{package}.{entityLowerName}.v{version}.{entityUpperName}{dtoName}
//            sb.append(MessageFormat.format("import {0}.{1}.v{2}.{3}{4};\n"
//                    , blogicPackageName
//                    , VelocityHelper.getFirstLowerField(entityName)
//                    , releaseAfterVer
//                    , VelocityHelper.getFirstUpperField(entityName)
//                    , commonObjectMethod.getRequestDtoName()));
//            //get方法或list只需要导入respDtoName
//            if(BizCommonConstant.METHOD_TYPE_GET.equals(commonObjectMethod.getType())
//                || BizCommonConstant.METHOD_TYPE_LIST.equals(commonObjectMethod.getType())) {
//                //dto示例：{package}.{entityLowerName}.v{version}.{entityUpperName}{dtoName}
//                sb.append(MessageFormat.format("import {0}.{1}.v{2}.{3}{4};\n"
//                        , blogicPackageName
//                        , VelocityHelper.getFirstLowerField(entityName)
//                        , releaseAfterVer
//                        , VelocityHelper.getFirstUpperField(entityName)
//                        , commonObjectMethod.getRespDetailDtoName()));
//            }
//            //别的方法需要导入responseDTO
//            if(!BizCommonConstant.METHOD_TYPE_GET.equals(commonObjectMethod.getType())) {
//                //dto示例：{package}.{entityLowerName}.v{version}.{entityUpperName}{dtoName}
//                sb.append(MessageFormat.format("import {0}.{1}.v{2}.{3}{4};\n"
//                        , blogicPackageName
//                        , VelocityHelper.getFirstLowerField(entityName)
//                        , releaseAfterVer
//                        , VelocityHelper.getFirstUpperField(entityName)
//                        , commonObjectMethod.getRespDtoName()));
//            }
//        }
//
//        //4、导入与client有关的包
//        List<String> serviceNameList = commonObjectMethodList
//                .stream().map(x->x.getRemote().getServiceName())
//                .distinct().collect(Collectors.toList());
//        for(String serviceName : serviceNameList) {
//            //client包名的组成情况：{clientPackage}.{entityLowerName}.v{version}.{entityUpperName}{serviceName}_v{version}Client
//            sb.append(MessageFormat.format("import {0}.{1}.v{2}.{3}{4}_v{5}Client;\n"
//                    //客户端的包名
//                    , clientPackageName
//                    , getFirstLowerField(entityName)
//                    , releaseAfterVer
//                    , getFirstUpperField(entityName)
//                    , getFirstUpperField(serviceName)
//                    , releaseAfterVer));
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 根据属性判断是否需要引入JsonIgnore包
//     */
//    public static String getJsonIgnoreByAttrList(ArrayList<CommonObjectFieldVO> commonObjectFieldVOList) {
//        if(commonObjectFieldVOList == null || commonObjectFieldVOList.size() == 0) {
//            return "";
//        }
//
//        StringBuilder sBuilder = new StringBuilder();
//
//        if(commonObjectFieldVOList.stream().filter(x->x.isIgnore() == true).count() > 0) {
//            sBuilder.append("import com.fasterxml.jackson.annotation.JsonIgnore;");
//        }
//        // 如果属性的第一个字母和第二个字母的大小写不一致，则添加jsonProperty注解
//        for(CommonObjectFieldVO commonObjectFieldVO : commonObjectFieldVOList) {
//            String coleName = commonObjectFieldVO.getColName();
//            if(coleName.length() > 1) {
//                if(Character.isUpperCase(coleName.charAt(1))
//                    && Character.isLowerCase(coleName.charAt(0))) {
//                    sBuilder.append(sBuilder.length() > 0 ? "\r\nimport com.fasterxml.jackson.annotation.JsonProperty;" : "import com.fasterxml.jackson.annotation.JsonProperty;");
//                    break;
//                }
//                if(Character.isUpperCase(coleName.charAt(0))
//                    && Character.isLowerCase(coleName.charAt(1))) {
//                    sBuilder.append(sBuilder.length() > 0 ? "\r\nimport com.fasterxml.jackson.annotation.JsonProperty;" : "import com.fasterxml.jackson.annotation.JsonProperty;");
//                    break;
//                }
//            }
//        }
//
//        return sBuilder.toString();
//    }
//
//    /**
//     * 判断当前属性是否是json过滤JSONPATH
//     */
//    public static Boolean isFilterJsonPath(String colName) {
//        if(org.apache.commons.lang3.StringUtils.isBlank(colName)) {
//            return false;
//        }
//
//        if(colName.endsWith(BizCommonConstant.JSON_PATH_FILTER)) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * 判断当前属性是否是json字段JSONPATH
//     */
//    public static Boolean isFieldJsonPath(String colName) {
//        if(org.apache.commons.lang3.StringUtils.isBlank(colName)) {
//            return false;
//        }
//
//        if(colName.endsWith(BizCommonConstant.JSON_PATH_FIELD)) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * 判断当前属性是否是Json类型的字段
//     */
//    public static Boolean isJsonType(String dataType) {
//        if(org.apache.commons.lang3.StringUtils.equals(BizCommonConstant.DATATYPE_JSON, dataType)) {
//            return true;
//        }
//
//        return false;
//    }
//    /**
//     * 判断当前属性是否是Decimal类型 或者 是Integer类型的字段
//     */
//    public static Boolean isDecimalOrIntegerType(String dataType) {
//        if(org.apache.commons.lang3.StringUtils.equals(BizCommonConstant.DATATYPE_INTEGER, dataType)
//                || org.apache.commons.lang3.StringUtils.equals(BizCommonConstant.DATATYPE_DECIMAL, dataType)) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * 根据javaBean规范，如果第一个字母和第二个字母的大小写不一致，则需要引入JsonProperty
//     */
//    public static String getJsonProperty(String colName) {
//        if(colName.length() > 1) {
//            if(Character.isUpperCase(colName.charAt(1))
//                && Character.isLowerCase(colName.charAt(0))) {
//                return MessageFormat.format("@JsonProperty(\"{0}\")", colName);
//            }
//            if(Character.isUpperCase(colName.charAt(0))
//                && Character.isLowerCase(colName.charAt(1))) {
//                return MessageFormat.format("@JsonProperty(\"{0}\")", colName);
//            }
//        }
//
//        return "";
//    }
//
//    /**
//     * 获取UUid
//     * @author 戴黎民
//     * @param
//     * @return String
//     * @throws Exception
//     */
//    public static String getUUid() {
//        return UUID.randomUUID().toString().replace("-", "");
//    }
//
//    /**
//     * 获取GUid
//     * @author 戴黎民
//     * @param
//     * @return String
//     * @throws Exception
//     */
//    public static String getGUid() {
//        return UUID.randomUUID().toString();
//    }
//
//    /**
//     * 更换项目名
//     * 描述：如果项目名是pai-core-biz-dto则返回pai-core-object，否则返回当前项目名
//     * @param projectName 项目名
//     * @return String 返回真实的项目名
//     */
//    public static String getProjectName(String projectName) throws Exception {
//        // 判断是否为空
//        if(org.apache.commons.lang3.StringUtils.isBlank(projectName)) {
//            return "";
//        }
//        //如果是数组类型
//        if(org.apache.commons.lang3.StringUtils.equals(PAI_CORE_BIZ_DTO_PROJECT, projectName)) {
//            return PAI_CORE_OBJECT_PROJECT;
//        }
//        return projectName;
//    }
//}
