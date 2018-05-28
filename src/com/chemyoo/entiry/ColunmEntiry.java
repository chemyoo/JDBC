package com.chemyoo.entiry;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年1月20日 下午2:12:18
 * @since 2018年1月20日 下午2:12:18
 * @description 类说明
 */
public class ColunmEntiry {

	private String colunmName;

	private String dataType;

	private boolean isPrimaryKey = false;

	/**
	 * @return the colunmName
	 */
	public String getColunmName() {
		return colunmName;
	}

	/**
	 * @param colunmName
	 *            the colunmName to set
	 */
	public void setColunmName(String colunmName) {
		this.colunmName = colunmName;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the isPrimaryKey
	 */
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	 * @param isPrimaryKey
	 *            the isPrimaryKey to set
	 */
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

}
