package com.std.framework.ext.tag;

import com.std.framework.core.util.StringUtil;
import com.std.framework.model.ModelHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Luox 下拉列表标签<select><option>..数据库动态加载</option></select>
 */
@SuppressWarnings("serial")
public class CodeTableTag extends TagSupport {

    private String ctId;
    private String tagName;
    private String onChange;
    private String cssClass;
    private String defaultValue;
    private String type;
    private String whereClause;

    public String getCtId() {
        return ctId;
    }

    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getOnChange() {
        return onChange;
    }

    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            TCodeTable tCodeTable = getCodeTableParam(ctId);
            List<TCodeDic> codeList = null;
            String codeTableStr = "";

            if (StringUtil.notBlank(tCodeTable.getTableName())) {
                codeList = doTransCodeList(tCodeTable);
            }
            if (!codeList.isEmpty()) {
                codeTableStr = generateCodeTable(tCodeTable, codeList);
            }

            JspWriter out = this.pageContext.getOut();
            out.println(codeTableStr);

        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
    }

    /**
     * 获取CODETABLE数据配置
     */
    private TCodeTable getCodeTableParam(String ctId) throws Exception {
        TCodeTable tCodeTable = null;

        String sql = "SELECT * FROM T_CODE_TABLE T WHERE T.CODE_TABLE_ID = ?";
        List<String> paramList = new ArrayList<String>();
        paramList.add(ctId);
        List<TCodeTable> list = ModelHelper.findORMClassListBySql(new TCodeTable(), sql, paramList);
        if (!list.isEmpty()) {
            tCodeTable = list.get(0);
        }
        return tCodeTable;
    }

    /**
     * 获取SELECTION列表OPTION数据
     */
    private List<TCodeDic> doTransCodeList(TCodeTable tCodeTable) throws Exception {
        String sql = "SELECT " + tCodeTable.getCodeColumn() + " as code," + tCodeTable.getDescColumn()
                + " as description" + " FROM " + tCodeTable.getTableName();

        if (StringUtil.notBlank(whereClause)) {
            sql = sql + " WHERE " + whereClause;
        }
        sql = sql + " ORDER BY " + tCodeTable.getCodeColumn() + " ASC ";

        List<TCodeDic> list = ModelHelper.findORMClassListBySql(new TCodeDic(), sql, null);
        return list;
    }

    /**
     * 形成HTML标签字符串
     */
    private String generateCodeTable(TCodeTable tCodeTable, List<TCodeDic> codeList) {
        StringBuilder rtnStr = new StringBuilder();

        if (StringUtil.isBlank(type) || type.equals("select")) {
            rtnStr.append("<select id='" + tagName + "' name = '" + tagName + "'");
            if (StringUtil.notBlank(onChange)) {
                if (onChange.indexOf(")") <= 0) {
                    rtnStr.append(" onChange='" + onChange + "();' ");
                } else {
                    rtnStr.append(" onChange='" + onChange + "' ");
                }
            }
            if (StringUtil.notBlank(cssClass)) {
                rtnStr.append(" class='" + cssClass + "'");
            }
            rtnStr.append(" >");
            Iterator<TCodeDic> iterator = codeList.iterator();
            while (iterator.hasNext()) {
                TCodeDic next = iterator.next();
                if (next.getCode().equals(defaultValue)) {
                    rtnStr.append("<option value='" + next.getCode() + "' selected >" + next.getDescription()
                            + " </option>");
                } else {
                    rtnStr.append("<option value='" + next.getCode() + "'>" + next.getDescription() + " </option>");
                }
            }
            rtnStr.append("</select>");
        } else if (type.equals("label")) {
            Iterator<TCodeDic> iterator = codeList.iterator();
            if (StringUtil.isBlank(defaultValue)) {
                rtnStr.append("<input type='text' id='" + tagName + "' name = '" + tagName + "' value='' disabled />");
            } else {
                while (iterator.hasNext()) {
                    TCodeDic next = iterator.next();
                    if (next.getCode().equals(defaultValue)) {
                        rtnStr.append("<input type='text' id='" + tagName + "' name = '" + tagName + "' value='"
                                + next.getDescription() + "' disabled />");
                    }
                }
            }
        }
        return rtnStr.toString();
    }
}